package com.example.taskmanegerapplication.Utils;

import java.text.DateFormat;
import java.util.Date;

public final class DateTimeFormat {

    public static String getDateFormat(Date date){
        return DateFormat.getDateInstance(DateFormat.SHORT).format(date);
    }

    public static String getTimeFormat(Date date){
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
    }
}
