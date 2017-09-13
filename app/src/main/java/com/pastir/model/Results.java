
package com.pastir.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Results {

    @SerializedName("sunrise")
    @Expose
    private String sunrise;
    @SerializedName("sunset")
    @Expose
    private String sunset;

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getTimezonedSunrise() {
        return timezoneDate(sunrise);
    }

    public String  getTimezonedSunset() {
        return timezoneDate(sunset);
    }

    private String timezoneDate(String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date parsed = format.parse(date);

            SimpleDateFormat result = new SimpleDateFormat("HH:mm:ss");
            result.setTimeZone(TimeZone.getDefault());

            return result.format(parsed);
        }
        catch (ParseException e){
            return "";
        }
    }


}
