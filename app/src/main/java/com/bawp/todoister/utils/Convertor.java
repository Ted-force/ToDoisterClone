package com.bawp.todoister.utils;

import androidx.room.TypeConverter;

import com.bawp.todoister.model.Priority;

import java.util.Date;

public class Convertor {

    @TypeConverter
    public static Date TimeStampToDate(Long value) {
        return value == null ? null:new Date(value);
    }

    @TypeConverter
    public static Long DateToStamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String PriorityToString(Priority priority) {
        return priority == null ? null : priority.name();
    }

    @TypeConverter
    public static Priority StringToPriority(String value) {
        return value == null ? null : Priority.valueOf(value);
    }

}
