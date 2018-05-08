package com.minhtetoo.asartaline.data.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.minhtetoo.asartaline.data.vos.GeneralTasteVO;

import java.util.List;

public class GeneralTasteTypeConverter {
    @TypeConverter
    public String listToString(List<GeneralTasteVO> tastes) {
        if (tastes != null) {
            return new Gson().toJson(tastes, new TypeToken<List<GeneralTasteVO>>() {}.getType());
        }
        return null;
    }

    @TypeConverter
    public List<GeneralTasteVO> stringToList(String tastes) {
        if (tastes != null) {
            return new Gson().fromJson(tastes, new TypeToken<List<GeneralTasteVO>>() {}.getType());
        }
        return null;
    }
}
