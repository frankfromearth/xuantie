package com.xuantie.qidaren.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 时间工具类
 * 
 * @author xuhh
 *
 * 2018/4/30
 */
public class DateUtil {

    public static Long getDiffMin(Date date1, Date date2) {
        Long diffMS = getDiffMS(date1, date2);
        if (diffMS > 0) {
            return diffMS / 1000 / 60;
        } else {
            return 0L;
        }
    }

    public static Long getDiffMS(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return 0L;
        }

        return Math.abs(date1.getTime() - date2.getTime());
    }
    
    /**
	 * 获取当前时间
	 * @param dateformat
	 * @return
	 */
	public static String getNowTime(String dateformat) {
		try {
			Date now = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);
			String nowTime = dateFormat.format(now);
			return nowTime;
		} catch (Exception e) {
            Logger.error("getNowTime Exception：" + e.getMessage(), e);
        }
        return null;
	}

    /**
     * 获取当前时间
     * 
     * @param pattern
     * @return
     */
    public static String getNow(String... pattern) {
        try {
            if (pattern != null && pattern.length > 0) {
                return date2String(new Date(), pattern[0]);
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            return sdf.format(new Date());
        } catch (Exception e) {
            Logger.error("获取当前时间异常：", e);
        }
        return null;
    }

    /**
     * 日期格式化成字符串,格式自定义
     * 
     * @author xuhh
     * @date 2018/4/30
     * @param date 日期
     * @param pattern 日期格式
     * @return
     */
    public static String date2String(Date date, String pattern) {
        try {
        	if(date != null){
        		// 使用已有的format
                if ("yyyy-MM-dd".equals(pattern)) {
                    SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                    return sdf3.format(date);
                } else if ("yyyy-MM-dd HH:mm:ss".equals(pattern)) {
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return sdf2.format(date);
                } else if ("yyyy-MM-dd HH:mm:ss.SSS".equals(pattern)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    return sdf.format(date);
                }
                // 根据入参创建format
                else {
                    SimpleDateFormat format = new SimpleDateFormat(pattern);
                    return format.format(date);
                }
        	}
        } catch (Exception e) {
            Logger.error("日期格式化成字符串异常：", e);
        }
        return null;
    }

    /**
     * 检查一个日期字符串是否能正确转换成给定的格式
     * 
     * @author xuhh
     * @date 2018/4/30
     * @param dateStr 日期字符串
     * @param pattern 日期格式 例如 "yyyy-MM-dd HH:mm:ss.SSS"
     * @return
     */
    public static boolean checkDateFormat(String dateStr, String pattern) {
        try {
            // 使用已有的format
            if ("yyyy-MM-dd".equals(pattern)) {
                SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                sdf3.parse(dateStr);
            } else if ("yyyy-MM-dd HH:mm:ss".equals(pattern)) {
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                sdf2.parse(dateStr);
            } else if ("yyyy-MM-dd HH:mm:ss.SSS".equals(pattern)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                sdf.parse(dateStr);
            }
            // 根据入参创建format
            else {
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                format.parse(dateStr);
            }
        } catch (Exception e) {
            Logger.error("检查一个日期字符串是否能正确转换成给定的格式异常：", e);
            return false;
        }
        return true;
    }

    /**
     * 字符串转日期类型
     * 
     * @author pansq
     * @date 2017年2月19日 下午3:56:59
     * @param dateStr 日期字符串
     * @param pattern 日期格式
     * @return
     * @throws ParseException 转换异常
     */
    public static Date string2Date(String dateStr, String pattern) {
        try {
            // 使用已有的format
            if ("yyyy-MM-dd".equals(pattern)) {
                SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
                return sdf3.parse(dateStr);
            } else if ("yyyy-MM-dd HH:mm:ss".equals(pattern)) {
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return sdf2.parse(dateStr);
            } else if ("yyyy-MM-dd HH:mm:ss.SSS".equals(pattern)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                return sdf.parse(dateStr);
            }
            // 根据入参创建format
            else {
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                return format.parse(dateStr);
            }
        } catch (Exception e) {
            Logger.error("字符串转日期类型异常：", e);
        }
        return null;
    };

    /**
     * 给定一个日期,添加天数
     * 
     * @author pansq
     * @date 2017年2月19日 下午3:53:04
     * @param dateStr 日期字符串
     * @param addDays 增加天数
     * @return
     */
    public static String addDay(String dateStr, String pattern, int addDays) {
        try {
            Date date = string2Date(dateStr, pattern);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, addDays);
            return date2String(calendar.getTime(), pattern);
        } catch (Exception e) {
            return dateStr;
        }
    }

    /**
     * 给定一个日期,添加天数
     * 
     * @author pansq
     * @date 2017年3月5日 下午4:08:49
     * @param date 日期
     * @param addDays 增加天数
     * @return
     */
    public static Date addDay(Date date, int addDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, addDays);
        return calendar.getTime();
    }

    /**
     * 给定一个日期,添加小时数
     * 
     * @author pansq
     * @date 2017年3月7日 下午2:48:35
     * @param date
     * @param addHours
     * @return
     */
    public static Date addHour(Date date, int addHours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, addHours);
        return calendar.getTime();
    }

    /**
     * 给定一个日期,添加分钟数
     * 
     * @author pansq
     * @date 2017年3月7日 下午2:48:35
     * @param date
     * @param minutes
     * @return
     */
    public static Date addMinute(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 获取时间是一年中的第几天
     * 
     * @author pansq
     * @date 2017年3月10日 上午10:40:22
     * @param date
     * @return
     */
    public static int getDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 获取时间是一天中的第几个小时
     * 
     * @author pansq
     * @date 2017年3月15日 上午10:48:46
     * @param date
     * @return
     */
    public static int getHourOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 计算当前时间与时间戳相差秒数
     * 
     * @description:
     * @param timestamp 时间字符串 例：2017-03-29 12:12:12
     * @param pattern 模板
     * @return long 秒
     *
     */
    public static long currentMinusTimestamp(String timestamp, String pattern) {
        try {
            if (StringUtils.isBlank(timestamp)) {
                return -1;
            }
            Date string2Date = string2Date(timestamp, pattern);
            Date date = new Date();
            return (date.getTime() - string2Date.getTime()) / 1000;
        } catch (Exception e) {
            Logger.error("计算当前时间与时间戳相差秒数异常：", e);
        }
        return -1;

    }

    /**
     * 比较两个日期大小
     * 
     * @description:日期1>日期2返回1;日期1<日期2返回-1;日期1=日期2返回0
     * @description:异常返回-2
     * @param date1 日期1
     * @param date2 日期2
     * @param templet 模板 默认格式"yyyy-MM-dd HH:mm:ss"
     * @return int
     *
     */
    public static int compareDate(String date1, String date2, String templet) {

        try {
            if (StringUtils.isBlank(date1) || StringUtils.isBlank(date2)) {
                return -2;
            }
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (StringUtils.isNotBlank(templet)) {
                df = new SimpleDateFormat(templet);
            }
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            Logger.error("比较两个日期大小异常：", e);
        }
        return -2;
    }
}
