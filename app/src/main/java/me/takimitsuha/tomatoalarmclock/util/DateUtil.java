package me.takimitsuha.tomatoalarmclock.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Taki on 2017/2/7.
 */
public class DateUtil {

    public static Integer[] getXData() {
        Integer[] dates = new Integer[7];
        Calendar calendar = Calendar.getInstance();
        //向前推算6天
        for (int i = 6; i > 0; i--) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DATE, -i);
//            if (i == 6) {
//                Date date = calendar.getTime();
//                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日", Locale.CHINA);
//                dates[0] = sdf.format(date);
//            } else {
            dates[6 - i] = calendar.get(Calendar.DAY_OF_MONTH);
//            }
        }
        dates[6] = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return dates;
    }

    public static Long getStartTime() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        return currentDate.getTime().getTime();
    }

    public static Long getEndTime() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        return currentDate.getTime().getTime();
    }

    public static Long getStartTimeByParam(int param) {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.add(Calendar.DATE, -param);
        return currentDate.getTime().getTime();
    }

    public static Long getEndTimeByParam(int param) {
        Calendar currentDate = new GregorianCalendar();
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.add(Calendar.DATE, -param);
        return currentDate.getTime().getTime();
    }
}
