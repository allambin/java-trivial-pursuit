package com.pixtends;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CardBuilder implements ActionListener {

    private JTextArea question;
    private JTextArea answer;
    private JFrame frame;
    private JComboBox<String> typesComboBox;
    private JButton nextButton;
    private ArrayList<Card> cardList;
    private String type;

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
        JPanel answerPanel = new JPanel();
        initPanel(answerScrollPane, answerPanel, "Answer", 100);

        // QUESTION panel containing question scrollpane
        JPanel questionPanel = new JPanel();
        initPanel(questionScrollPane, questionPanel, "Question", 300);

        JPanel typesPanel = new JPanel();
        typesPanel.setLayout(new BoxLayout(typesPanel, BoxLayout.Y_AXIS));
        typesPanel.add(typesComboBox);
        typesPanel.setBorder(BorderFactory.createTitledBorder("Type"));
        typesPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));

        JPanel savePanel = new JPanel();
        savePanel.setLayout(new BorderLayout(0, 0));
        savePanel.add(nextButton);
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

    private void initPanel(JScrollPane scrollPane, JPanel panel, String label, int height) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(scrollPane);
        panel.setBorder(BorderFactory.createTitledBorder(label));
        panel.setMaximumSize(new Dimension(Short.MAX_VALUE, height));
    }

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
        Card card = new Card(question.getText(), answer.getText(), CardTypeManager.findFromDomain(type));
        cardList.add(card);
        question.setText("");
        answer.setText("");
        typesComboBox.setSelectedIndex(0);
        question.requestFocus();
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

        }
    }

    public class NewMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    public class TypeComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            type = (String)cb.getSelectedItem();
        }
    }
}
