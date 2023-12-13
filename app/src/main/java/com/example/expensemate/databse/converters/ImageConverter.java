package com.example.expensemate.databse.converters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class ImageConverter {

    @TypeConverter
    public static byte[] fromBitmap(Bitmap bitmap) {
        try {
            // convert bitmap to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            return stream.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    @TypeConverter
    public static Bitmap toBitmap(byte[] image) {
        try {
            // convert byte array to bitmap
            return BitmapFactory.decodeByteArray(image, 0, image.length);
        } catch (Exception e) {
            return null;
        }
    }
}
