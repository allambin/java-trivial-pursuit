package com.pixtends;

public class Card {
    private String question;
    private String answer;
    enum Color {
        BLUE,
        PINK,
        YELLOW,
        MAROON,
        GREEN,
        ORANGE
    }
    enum Type {
        GEOGRAPHY,
        ENTERTAINMENT,
        HISTORY,
        ARTS_LITERATURE,
        SCIENCES_NATURE,
        SPORTS_HOBBIES
    }

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public String typeToColor(Type type) {
        switch (type) {
            case GEOGRAPHY:
                return "blue";
            case ENTERTAINMENT:
                return "pink";
            case HISTORY:
                return "yellow";
            case ARTS_LITERATURE:
                return "maroon";
            case SCIENCES_NATURE:
                return "green";
            case SPORTS_HOBBIES:
                return "orange";
            default:
                return "blank";
        }
    }

    public static String[] getTypesArray() {
        return new String[]{"Geography", "Entertainment", "History", "Arts & Literature", "Sciences & Nature", "Sports & Hobbies"};
    }
}

