package com.pixtends;

import java.util.ArrayList;

public class CardTypeManager {

    public static ArrayList<CardType> getAll() {
        ArrayList<CardType> list = new ArrayList<>();
        list.add(new CardType("Geography", "blue"));
        list.add(new CardType("Entertainment", "pink"));
        list.add(new CardType("History", "yellow"));
        list.add(new CardType("Arts & Literature", "maroon"));
        list.add(new CardType("Sciences & Nature", "green"));
        list.add(new CardType("Sports & Hobbies", "orange"));
        return list;
    }

    public static CardType findFromDomain(String domain) {
        ArrayList<CardType> list = getAll();
        for (CardType cardType : list) {
            if (cardType.getDomain().equals(domain)) {
                return cardType;
            }
        }

        return null;
    }
}
