package com.pixtends;

import java.lang.reflect.Array;
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

    public String formatStringForFile(FormatterInterface formatter) {
        ArrayList<String> list = new ArrayList<>();
        list.add(question);
        list.add(answer);
        list.add(type.getDomain());
        return formatter.format(list);
    }
}

