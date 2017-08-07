package com.pastir.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Model used for sunset and sunrise
 */

public class Cloud {

    @SerializedName("sunrise")
    @Expose
    private String sunrize;
    @SerializedName("sunset")
    @Expose
    private String sunset;

    public String getSunrize() {
        return sunrize;
    }

    public void setSunrize(String sunrize) {
        this.sunrize = sunrize;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }
}
