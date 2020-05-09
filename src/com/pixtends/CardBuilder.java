package com.pixtends;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class CardBuilder implements ActionListener {

    private JTextArea question;
    private JTextArea answer;
    private JFrame frame;
    private JComboBox<String> typesComboBox;
    private JButton nextButton;
    private JButton previousButton;
    private JButton saveButton;
    private ArrayList<Card> cardList;
    private String type;
    private int currentIndex;

    public CardBuilder() {
        cardList = new ArrayList<>();

        frame = new JFrame("Card Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font font = new Font("Arial", Font.BOLD, 16);

        ArrayList<CardType> cardTypes = CardTypeManager.getAll();
        String[] availableTypes = new String[cardTypes.size()];
        for (int i = 0; i < cardTypes.size(); i++) {
            availableTypes[i] = cardTypes.get(i).getDomain();
        }
        typesComboBox = new JComboBox<>(availableTypes);
        typesComboBox.addActionListener(new TypeComboBoxListener());
        type = (String)typesComboBox.getSelectedItem();

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        nextButton.setEnabled(false);
        previousButton = new JButton("Previous");
        previousButton.addActionListener(this);
        previousButton.setEnabled(false);
        saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        // QUESTION textarea
        question = new JTextArea();
        initTextArea(question, font);

        // QUESTION scrollpane containing question textarea
        JScrollPane questionScrollPane = new JScrollPane(question);
        initScrollPane(questionScrollPane);

        // ANSWER textarea
        answer = new JTextArea();
        initTextArea(answer, font);

        // ANSWER scrollpane containing answer textarea
        JScrollPane answerScrollPane = new JScrollPane(answer);
        initScrollPane(answerScrollPane);

        // ANSWER panel containing answer scrollpane
        JPanel answerPanel = createPanel(answerScrollPane, "Answer", 100);

        // QUESTION panel containing question scrollpane
        JPanel questionPanel = createPanel(questionScrollPane, "Question", 300);

        JPanel typesPanel = new JPanel();
        typesPanel.setLayout(new BoxLayout(typesPanel, BoxLayout.Y_AXIS));
        typesPanel.add(typesComboBox);
        typesPanel.setBorder(BorderFactory.createTitledBorder("Type"));
        typesPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));

        JPanel savePanel = new JPanel();
        savePanel.setLayout(new BorderLayout(0, 0));
        savePanel.add(previousButton, BorderLayout.WEST);
        savePanel.add(saveButton, BorderLayout.CENTER);
        savePanel.add(nextButton, BorderLayout.EAST);
        savePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));

        Container container = frame.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(questionPanel);
        container.add(answerPanel);
        container.add(typesPanel);
        container.add(savePanel);

        JMenuBar menuBar = initMenuBar();
        frame.setJMenuBar(menuBar);

        frame.pack();
        frame.setSize(400, 500);
        frame.setVisible(true);
        frame.setLocation(100, 100);
    }

    /**
     * Create a JPanel with a BoxLayout containing a scrollPane
     *
     * @param scrollPane a scrollPane to add to the panel
     * @param label the label of the "fieldset"
     * @param height height of the panel
     */
    private JPanel createPanel(JScrollPane scrollPane, String label, int height) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(scrollPane);
        panel.setBorder(BorderFactory.createTitledBorder(label));
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, height));
        return panel;
    }

    /**
     * Prepare the menu and its items
     *
     * @return JMenuBar
     */
    private JMenuBar initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem loadMenuItem = new JMenuItem("Load");
        newMenuItem.addActionListener(new NewMenuListener());
        saveMenuItem.addActionListener(new SaveMenuListener());
        loadMenuItem.addActionListener(new LoadMenuListener());
        menu.add(newMenuItem);
        menu.add(saveMenuItem);
        menu.add(loadMenuItem);
        menuBar.add(menu);
        return menuBar;
    }

    private void initTextArea(JTextArea textArea, Font font) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(font);
        textArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    textArea.transferFocus();
                    e.consume();
                }
            }
        });
    }

    private void initScrollPane(JScrollPane scrollPane) {
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CardBuilder();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Next":
                currentIndex++;
                if (currentIndex >= cardList.size()) {
                    reset();
                } else {
                    displayCard(currentIndex);
                }
                break;
            case "Previous":
                currentIndex--;
                displayCard(currentIndex);
                break;
            case "Save":
                if (currentIndex >= cardList.size()) {
                    // new card
                    Card card = new Card(question.getText(), answer.getText(), CardTypeManager.findFromDomain(type));
                    cardList.add(card);
                } else {
                    // existing card
                    Card card = cardList.get(currentIndex);
                    card.setQuestion(question.getText());
                    card.setAnswer(answer.getText());
                    card.setType(CardTypeManager.findFromDomain(type));
                }
                saveButton.setText("Saved!");
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        saveButton.setText("Save");
                    }
                }, 1000 * 2);
                break;
        }

        handleButtonsState();
    }

    private void handleButtonsState() {
        if (currentIndex < cardList.size()) {
            nextButton.setEnabled(true);
        } else {
            previousButton.setEnabled(true);
            nextButton.setEnabled(false);
        }

        if (currentIndex == 0) {
            previousButton.setEnabled(false);
        }
    }

    public class SaveMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // save to file
            JFileChooser fileSave = new JFileChooser();
            fileSave.showSaveDialog(frame);
            File file = fileSave.getSelectedFile();

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                CSVFormatter formatter = new CSVFormatter();
                for (Card card : cardList) {
                    String string = card.formatStringForFile(formatter);
                    writer.write(string + "\n");
                }
                writer.close();
            } catch (IOException err) {
                System.out.println("Couldn't write to file");
                System.out.println(err.getStackTrace());
            }
        }
    }

    public class LoadMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileLoad = new JFileChooser();
            fileLoad.showOpenDialog(frame);
            File file = fileLoad.getSelectedFile();

            try {
                CSVParser parser = new CSVParser();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;

                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    if (line.endsWith("\"")) {
                        Card card = Card.parseString(sb.toString(), parser);
                        sb = new StringBuilder();
                        cardList.add(card);
                    }
                }
                reader.close();
                currentIndex = cardList.size();
                JOptionPane.showMessageDialog(null, "File loaded.");
                nextButton.setEnabled(false);
                previousButton.setEnabled(true);
            } catch (IOException err) {
                System.out.println("Couldn't load file");
                System.out.println(err.getStackTrace());
            }
        }
    }

    public class NewMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            reset();
            cardList.clear();
            JOptionPane.showMessageDialog(null, "New file loaded.");
            previousButton.setEnabled(false);
            nextButton.setEnabled(false);
        }
    }

    private void reset() {
        question.setText("");
        answer.setText("");
        typesComboBox.setSelectedIndex(0);
        question.requestFocus();
    }

    private void displayCard(int index) {
        Card card = cardList.get(index);
        question.setText(card.getQuestion());
        answer.setText(card.getAnswer());
        typesComboBox.setSelectedIndex(CardTypeManager.findIndexFromDomain(card.getType().getDomain()));
    }

    public class TypeComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            type = (String)cb.getSelectedItem();
        }
    }
}
