package com.pastir.network;


import com.pastir.model.SunriseSunset;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Used to map GET and POST requests to their pojo classes
 */
public interface APIService {

    @GET("json")
    Observable<SunriseSunset> getSunriseSunset(@QueryMap Map<String,String> params);

}
