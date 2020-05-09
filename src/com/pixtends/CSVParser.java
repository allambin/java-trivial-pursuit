package com.pixtends;

public class CSVParser implements ParserInterface {
    @Override
    public String[] parseString(String string) {
        String[] data = string.split(",");
        for (int i = 0; i < data.length; i++) {
            String datum = data[i];
            datum = datum.replaceFirst("^\"", "");
            datum = datum.replaceFirst("\"$", "");
            data[i] = datum;
        }

        return data;
    }
}
