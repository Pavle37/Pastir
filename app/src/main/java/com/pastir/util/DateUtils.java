package com.pastir.util;

import com.pastir.model.MorningVerse;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Used for converting Strings into dates and do other date-related
 * things
 */

public class DateUtils {

    /*This is used because if the current time is after 12:00 on a given day, the comparator of dates
    * will return the next day, because the next day starts at 00:00 and we get +1 error
    *
    * If we add a half day offset, and it is 23:59 on 17.01.2018, it will be closer to
    * 12:00 on 17.01.2018 than 12:00 on 18.01.2018
    */
    private static final long HALF_DAY_OFFSET = 12*60*60*1000;

    private static DateFormat sDateFormat = new SimpleDateFormat("dd.MM.yyyy.");


    public static int getMorningVersePositionClosestToToday(List<MorningVerse> list){

        /*Firstly, convert the list od morning verses to a list of dates*/
        List<Date> dates = morningVersesToDates(list);
        long today = System.currentTimeMillis();

        /*Sort the list so that the nearest date will be in the first place retrieved from the list*/
        Date closest = Collections.min(dates, (date1, date2) -> {
            long diff1 = Math.abs(date1.getTime() + HALF_DAY_OFFSET - today);
            long diff2 = Math.abs(date2.getTime() + HALF_DAY_OFFSET - today);
            return Long.compare(diff1, diff2);
        });

        /*Then return the index of that element*/
        return dates.indexOf(closest);
    }

    /**
     * From the morning verses it creates a list of dates
     *
     * @param list Morning verses that we want to look convert to dates
     * @return list of dates that correlate with list of morning verses. If a date couldn't be parsed,
     * the beginning of time is returned instead.
     */
    private static List<Date> morningVersesToDates(List<MorningVerse> list){

        List<Date> dates = new ArrayList<>(list.size());

        for(MorningVerse mv: list){
            Date date = new Date(0); //Furthest date from today
            try {
                 date = sDateFormat.parse(mv.getDate());
            } catch (ParseException e) {/*Ignore*/}
            dates.add(date);
        }

        return dates;
    }

}
