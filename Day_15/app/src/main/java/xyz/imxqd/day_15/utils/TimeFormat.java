package xyz.imxqd.day_15.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by imxqd on 2016/12/10.
 * 时间格式化的工具类
 */

public class TimeFormat {

    public static String format(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM月dd日 HH:mm");
        return format.format(new Date(time));
    }
}
