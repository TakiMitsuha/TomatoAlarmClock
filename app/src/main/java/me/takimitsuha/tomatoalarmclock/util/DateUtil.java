package me.takimitsuha.tomatoalarmclock.util;

import java.util.Calendar;
import java.util.Date;

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
}
