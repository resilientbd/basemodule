package com.faisal.basemodule;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class TimeUtil {
   /* private static RxTime rxTime;
    public static RxTime getRxTime(){
        if (rxTime==null){
            rxTime=new RxTime();
        }
        return rxTime;
    }*/

    public static String GET_TIME(String formatInput, String formatOutput, String inptDateString)//"DD-MM-yyyy HH:mm:ss"
    {

        try {
            Date date1 = null;

            date1 = new SimpleDateFormat(formatInput).parse(inptDateString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Objects.requireNonNull(date1));


            SimpleDateFormat df = new SimpleDateFormat(formatOutput);
            return df.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTimeFromUtcStringFormat(String time, String outputFormat) {
        Log.d("chkutctime", "utc time:" + time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat output = new SimpleDateFormat(outputFormat);

        Date d = null;
        try {
            d = sdf.parse(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("chkutctime", "local time:" + output.format(d));
        return output.format(d);
    }

    public static Date dateToUTC(Date date) {
        return new Date(date.getTime() - Calendar.getInstance().getTimeZone().getOffset(date.getTime()));
    }

    public static String ConvertMilliSecondsToFormattedDate(String milliSeconds, String dateformat) {//ex format: "DD-MM-yyyy HH:mm:ss"
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateformat);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(milliSeconds));
        return simpleDateFormat.format(calendar.getTime());
    }
}
