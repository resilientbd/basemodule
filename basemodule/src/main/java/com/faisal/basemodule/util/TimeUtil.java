package com.faisal.basemodule.util;

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
   public static long GET_TIME_MILLIS(String formatInput,String inptDateString)//"DD-MM-yyyy HH:mm:ss"
   {

       try {
           Date date1 = null;

           date1 = new SimpleDateFormat(formatInput).parse(inptDateString);

           Calendar calendar = Calendar.getInstance();
           calendar.setTime(Objects.requireNonNull(date1));


           //SimpleDateFormat df = new SimpleDateFormat(formatOutput);

           return calendar.getTimeInMillis();
       } catch (ParseException e) {
           e.printStackTrace();
       }
       return 0;
   }
    public static String GetMinSecFromMilli(int milliseconds) {
        int Minutes = (milliseconds / 1000) / 60;
        int Seconds = (milliseconds / 1000) % 60;
        if (Seconds < 59) {
            if (Seconds < 10) {
                return "0" + Minutes + ".0" + Seconds;
            } else {
                return "0" + Minutes + "." + Seconds;
            }
        } else {
            return "0" + Minutes + "." + Seconds;
        }


    }

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
