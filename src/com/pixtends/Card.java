package com.pixtends;

import java.util.ArrayList;

public class Card {
    private String question;

    private String answer;
    private CardType type;

    public Card(String question, String answer, CardType type) {
        this.question = question;
        this.answer = answer;
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public CardType getType() {
        return type;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public String formatStringForFile(FormatterInterface formatter) {
        ArrayList<String> list = new ArrayList<>();
        list.add(question);
        list.add(answer);
        list.add(type.getDomain());
        return formatter.format(list);
    }

    public static Card parseString(String string, ParserInterface parser) {
        String[] splitString = parser.parseString(string);
        return new Card(splitString[0], splitString[1], CardTypeManager.findFromDomain(splitString[2]));
    }
}

