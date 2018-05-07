package com.minhtetoo.asartaline.data.vos;

import com.google.gson.annotations.SerializedName;

public class GeneralTasteVO {
    @SerializedName("tasteId")
    private String tasteId;

    @SerializedName("taste")
    private String taste;

    @SerializedName("tasteDesc")
    private String desc;

    public String getTasteId() {
        return tasteId;
    }

    public String getTaste() {
        return taste;
    }

    public String getDesc() {
        return desc;
    }
}
