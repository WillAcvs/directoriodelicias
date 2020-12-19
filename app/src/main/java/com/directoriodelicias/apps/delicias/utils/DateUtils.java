package com.directoriodelicias.apps.delicias.utils;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;

import com.directoriodelicias.apps.delicias.AppController;
import com.directoriodelicias.apps.delicias.appconfig.AppConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by Droideve on 8/17/2016.
 */

public class DateUtils {


    public static String getCurrentDay() {

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime()).toLowerCase();
    }


    public static String getPrepareSimpleDate(String inputDate, String outputSchema) {

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputSchema, Locale.ENGLISH);

        try {

            Date date = inputFormat.parse(inputDate);
            return outputFormat.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "N/A";
    }


    public static Long getDiff(String toyBornTime, String schema) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(schema);

        try {

            Date oldDate = dateFormat.parse(toyBornTime);
            Date currentDate = new Date();

            long diff = oldDate.getTime() - currentDate.getTime();
            long seconds = diff / 1000;
            long minutes = seconds / 60;
            long hours = minutes / 60;
            long days = hours / 24;

            Log.e("oldDate", "" + oldDate.getTime() + " - " + dateFormat.format(oldDate.getTime()));
            Log.e("currentDate", "" + currentDate.getTime() + " - " + dateFormat.format(oldDate.getTime()));
            Log.e("Difference: ", " seconds: " + seconds + " minutes: " + minutes
                    + " hours: " + hours + " days: " + days);

            // Log.e("toyBornTime", "" + toyBornTime);

            return diff;

        } catch (ParseException e) {

            e.printStackTrace();
        }

        return Long.valueOf(0);
    }


    public static String getUTC(String schema) {

        Date myDate = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTime(myDate);
        Date time = calendar.getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        String dateAsString = outputFmt.format(time);

        return dateAsString;
    }


    public static String getLocalTime(String schema) {

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat(schema);
        return outputFmt.format(todayDate);

    }


    public static String getDateByTimeZone(String dateStr, String schema) {
        String inputPattern = "yyyy-MM-dd";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        inputFormat.setTimeZone(TimeZone.getDefault());

        Locale current = AppController.getInstance().getResources().getConfiguration().locale;
        try {

            Date inputDate = inputFormat.parse(dateStr);
            SimpleDateFormat formatter = null;

            if (schema != null) {
                if (AppConfig.APP_DEBUG)
                    Log.e("dateUtilsSchema", schema + " - " + current);
                formatter = new SimpleDateFormat(schema, current);
            } else {
                formatter = new SimpleDateFormat("dd MMMM yyyy hh:mm", current);
            }

            formatter.setTimeZone(TimeZone.getDefault());
            return formatter.format(inputDate);

        } catch (Exception e) {
            e.printStackTrace();
            return dateStr;
        }
    }


    public static String prepareOutputDate(String dateStr, String schema, Context context) {

        String inputPattern = "yyyy-MM-dd hh:mm";

        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);

        try {

            inputFormat.setTimeZone(TimeZone.getDefault());
            Date inputDate = inputFormat.parse(dateStr);

            String hourFormat = "hh:mm";

            if (context != null) {
                if (hourFormat12(context)) {
                    hourFormat = "hh:mm";
                } else {
                    hourFormat = "kk:mm";
                }
            }

            SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy " + hourFormat);
            SimpleDateFormat formatterHour = new SimpleDateFormat(hourFormat);

            if (schema != null) {
                formatter = new SimpleDateFormat(schema);
                formatterHour = new SimpleDateFormat(schema);
            }


            formatter.setTimeZone(TimeZone.getDefault());
            formatterHour.setTimeZone(TimeZone.getDefault());

            int diffrence = minutesDifference(dateStr);

            if (diffrence < 1440) {
                return formatterHour.format(inputDate);
            } else {
                return formatter.format(inputDate);
            }


        } catch (ParseException e) {


            return dateStr;
        }

    }


    public static boolean isLessThan24(String dateString, String format) {

        try {

            SimpleDateFormat inputFormatter = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            inputFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date dateBegin = inputFormatter.parse(dateString);

            Date dateCurrent = new Date();


            long result = dateBegin.getTime() - dateCurrent.getTime();
            long days = TimeUnit.DAYS.convert(result, TimeUnit.MILLISECONDS);
            long hours = TimeUnit.HOURS.convert(result, TimeUnit.MILLISECONDS);

            if (AppConfig.APP_DEBUG) {
                Log.e("upcoming tz: ", TimeZone.getDefault().getDisplayName());
                Log.e("upcoming date: " + dateBegin + " days:", days + " " + result);
                Log.e("upcoming date: " + dateBegin + " hours:", hours + " " + result);
                Log.e("upcoming", "==================  ===================");
            }

            if (hours < 24 && hours >= 0) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static int minutesDifference(String dateStr) {

        int MILLI_TO_MINUTE = 1000 * 60;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:m:s");

        Date currentDate = new Date();

        try {

            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = formatter.parse(dateStr);

            formatter.setTimeZone(TimeZone.getDefault());
            String newDate = formatter.format(date);
            date = formatter.parse(newDate);


            return (int) (currentDate.getTime() - date.getTime()) / MILLI_TO_MINUTE;

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return 0;
    }


    private static boolean hourFormat12(Context context) {

        Calendar mCalendar = null;
        return !DateFormat.is24HourFormat(context);
    }
}
