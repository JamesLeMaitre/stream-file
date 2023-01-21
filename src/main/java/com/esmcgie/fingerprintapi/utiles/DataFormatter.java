package com.esmcgie.fingerprintapi.utiles;


import java.util.List;

public class DataFormatter<T> {
    public Formatter<T> renderData(boolean status, T data, String message) {
        Formatter formatter = new Formatter<T>();
        formatter.setStatus(status);
        formatter.setData(data);
        formatter.setMessage(message);
        return formatter;
    }

    public Formatter<List<T>> renderDataArray(boolean status, List<T> data, String message) {
        Formatter formatter = new Formatter<List<T>>();
        formatter.setStatus(status);
        formatter.setData(data);
        formatter.setTotal(data.size());
        formatter.setMessage(message);
        return formatter;
    }


    public Formatter<String> renderStringData(boolean status, String data, String message) {
        Formatter formatter = new Formatter<>();
        formatter.setStatus(status);
        formatter.setData(data);
        formatter.setMessage(message);
        return formatter;
    }

    public Formatter<Double> renderDoubleData(boolean status, Double data, String message) {
        Formatter formatter = new Formatter<>();
        formatter.setStatus(status);
        formatter.setData(data);
        formatter.setMessage(message);
        return formatter;
    }


    public FormatterDoubleString<String> renderDoubleStringData(boolean status, Double data, String message) {
        String total = data + "";
        String[] valuesSplited = total.split("E");
        String pow = "0";
        String value = valuesSplited[0];

        if (valuesSplited.length > 1) {
            pow = valuesSplited[1];
        }

        FormatterDoubleString formatterDoubleString = new FormatterDoubleString<>();
        formatterDoubleString.setStatus(status);
        formatterDoubleString.setData(value);
        formatterDoubleString.setPow(pow);
        formatterDoubleString.setMessage(message);
        return formatterDoubleString;
    }
}