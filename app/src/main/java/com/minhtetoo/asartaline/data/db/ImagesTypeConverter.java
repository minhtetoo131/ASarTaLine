package com.minhtetoo.asartaline.data.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class ImagesTypeConverter {
    @TypeConverter
    public String listToString(List<String> images) {
        if (images != null) {
            return new Gson().toJson(images, new TypeToken<List<String>>() {}.getType());
        }
        return null;
    }

    @TypeConverter
    public List<String> stringToList(String images) {
        if (images != null) {
            return new Gson().fromJson(images, new TypeToken<List<String>>() {}.getType());
        }
        return null;
    }
}
