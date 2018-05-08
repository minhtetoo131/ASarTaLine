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

    public void setTasteId(String tasteId) {
        this.tasteId = tasteId;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
