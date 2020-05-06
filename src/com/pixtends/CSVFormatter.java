package com.pixtends;

import java.util.ArrayList;

public class CSVFormatter implements FormatterInterface {
    @Override
    public String format(ArrayList<String> strings) {
        return '"' + String.join("\",\"", strings) + '"';
    }
}
