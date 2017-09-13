package com.pastir.network;

import android.location.Location;

import com.pastir.model.Results;
import com.pastir.model.SunriseSunset;
import com.pastir.presenter.MorningVersesPresenter;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Class used to handle calls to API and responses
 */

public class WebController {


    private static final String NOT_FORMATTED = "0";

    public static void loadSunriseSunset(final MorningVersesPresenter presenter, Location location) {
        Map<String,String> params = new HashMap<>();
        params.put("lat",String.valueOf(location.getLatitude()));
        params.put("lng",String.valueOf(location.getLongitude()));
        params.put("formatted",NOT_FORMATTED);
        Observable<SunriseSunset> sunriseSunset = RestClient.getInstance().service.getSunriseSunset(params);
        sunriseSunset.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        success -> presenter.openCloudDialog(success.getResults()),
                        error -> GenericErrorHandler.handleError(error,presenter.getContext())
                );

    }
}
