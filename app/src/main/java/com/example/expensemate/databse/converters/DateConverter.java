package com.example.expensemate.databse.converters;

import androidx.room.TypeConverter;

import java.util.Date;


// START source: https://stackoverflow.com/questions/50313525/room-using-date-field
public class DateConverter {
    @TypeConverter
    public static Date toDate(Long dateLong) {
        return dateLong == null ? null : new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date) {
        return date == null ? null : date.getTime();
    }
}
// END source: https://stackoverflow.com/questions/50313525/room-using-date-field
