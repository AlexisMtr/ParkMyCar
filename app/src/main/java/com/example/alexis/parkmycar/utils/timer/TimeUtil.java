package com.example.alexis.parkmycar.utils.timer;

/**
 * Created by Alexis on 12/04/2017.
 */

public class TimeUtil
{
    public static final int SECOND = 0;
    public static final int MINUTE = 1;

    public static Integer[] getMinSec(long timeInMs){
        int secs = (int) (timeInMs / 1000);
        int mins = secs / 60;
        secs = secs % 60;
        return new Integer[]{secs, mins};
    }

    public static long toMs(int minute, int seconde){
        return (minute * 60 + seconde) * 1000;
    }

    public static String toHoursMin(int minutes)
    {
        String hours = minutes/60 + "";
        hours = hours.length() == 2 ? hours : "0" + hours;
        String min = minutes%60 + "";
        min = min.length() == 2 ? min : "0" + min;

        return hours + ":" + min + ":00";
    }
}
