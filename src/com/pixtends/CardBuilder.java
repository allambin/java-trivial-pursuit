package com.pixtends;

import javax.swing.*;
import java.awt.*;

public class CardBuilder {

    private JTextArea question;
    private JTextArea answer;
    private JFrame frame;
    private JComboBox<String> typesComboBox;
    private JButton saveButton;

    public CardBuilder() {
        frame = new JFrame("Card Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font font = new Font("Arial", Font.BOLD, 16);

        String[] availableTypes = Card.getTypesArray();
        typesComboBox = new JComboBox<>(availableTypes);

        saveButton = new JButton("Save");

        // QUESTION textarea
        question = new JTextArea();
        question.setLineWrap(true);
        question.setWrapStyleWord(true);
        question.setFont(font);

        // QUESTION scrollpane containing question textarea
        JScrollPane questionScrollPane = new JScrollPane(question);
        questionScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        questionScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // ANSWER textarea
        answer = new JTextArea();
        answer.setLineWrap(true);
        answer.setWrapStyleWord(true);
        answer.setFont(font);

        // ANSWER scrollpane containing answer textarea
        JScrollPane answerScrollPane = new JScrollPane(answer);
        answerScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        answerScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        // ANSWER panel containing answer scrollpane
        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BoxLayout(answerPanel, BoxLayout.Y_AXIS));
        answerPanel.add(answerScrollPane);
        answerPanel.setBorder(BorderFactory.createTitledBorder("Answer"));
        answerPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 100));

        // QUESTION panel containing question scrollpane
        JPanel questionPanel = new JPanel();
        questionPanel.setLayout(new BoxLayout(questionPanel, BoxLayout.Y_AXIS));
        questionPanel.add(questionScrollPane);
        questionPanel.setBorder(BorderFactory.createTitledBorder("Question"));
        questionPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 300));

        JPanel typesPanel = new JPanel();
        typesPanel.setLayout(new BoxLayout(typesPanel, BoxLayout.Y_AXIS));
        typesPanel.add(typesComboBox);
        typesPanel.setBorder(BorderFactory.createTitledBorder("Type"));
        typesPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 50));

        JPanel savePanel = new JPanel();
        savePanel.setLayout(new BorderLayout(0, 0));
        savePanel.add(saveButton);
        savePanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 40));

        Container container = frame.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(questionPanel);
        container.add(answerPanel);
        container.add(typesPanel);
        container.add(savePanel);

        frame.pack();
        frame.setSize(400, 500);
        frame.setVisible(true);
        frame.setLocation(100, 100);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CardBuilder();
            }
        });
    }
}
