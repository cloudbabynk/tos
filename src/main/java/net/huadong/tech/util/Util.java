package net.huadong.tech.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static LocalDateTime getLocalDateTimeByStr(String str) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = null;
        try {
            dateTime = LocalDateTime.parse(str, formatter);
        } catch (Exception e) {
            return null;
        }
        return dateTime;
    }
    
    public static Date strFormateDate(String strDate,String format) {
    	//yyyy-MM-dd HH:mm:ss"
	   SimpleDateFormat formatter = new SimpleDateFormat(format);
	   ParsePosition pos = new ParsePosition(0);
	   Date strtodate = formatter.parse(strDate, pos);
	   return strtodate;
	  }
    
    

    public static Timestamp getTimestampByStr(String str) {
        LocalDateTime dateTime = getLocalDateTimeByStr(str);
        if (dateTime!=null) {
            return Timestamp.valueOf(dateTime);
        }
        return null;
    }

    public static boolean isNotNullOrZero(Object object) {
        if (object == null) {
            return false;
        }
        String str = object.toString();
        if ("".equals(str)) {
            return false;
        }
        try {
            int i = Integer.valueOf(str);
            if (i == 0) {
                return false;
            }
        } catch (Exception e) {
        }
        return true;
    }

    /**
     * 下划线转驼峰
     *
     * @param line
     * @param smallCamel
     * @return
     */
    public static String underline2Camel(String line, boolean smallCamel) {
        if (line == null || "".equals(line)) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            String word = matcher.group();
            sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0))
                    : Character.toUpperCase(word.charAt(0)));
            int index = word.lastIndexOf('_');
            if (index > 0) {
                sb.append(word.substring(1, index).toLowerCase());
            } else {
                sb.append(word.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
    
    public static Long getMonthBegin(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		//设置为1号,当前日期既为本月第一天  
		c.set(Calendar.DAY_OF_MONTH, 1);
		//将小时至0  
		c.set(Calendar.HOUR_OF_DAY, 0);  
		//将分钟至0  
		c.set(Calendar.MINUTE, 0);  
		//将秒至0  
		c.set(Calendar.SECOND,0);  
		//将毫秒至0  
		c.set(Calendar.MILLISECOND, 0);  
		// 获取本月第一天的时间戳  
		return c.getTimeInMillis();  
	}
    
    
    public static Long getMonthEnd(Date date) {
		Calendar c = Calendar.getInstance();  
		c.setTime(date);
		
		//设置为当月最后一天
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));  
		//将小时至23
		c.set(Calendar.HOUR_OF_DAY, 23);  
		//将分钟至59
		c.set(Calendar.MINUTE, 59);  
		//将秒至59
		c.set(Calendar.SECOND,59);  
		//将毫秒至999
		c.set(Calendar.MILLISECOND, 999);  
		// 获取本月最后一天的时间戳  
		return c.getTimeInMillis();
	}
    /**
     	获取两个日期相差几日子
     * @param time1
     * @param time2
     * @return
     */
    public int  getChaDay(String time1,String time2)
	{
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	long days=0;
    	try
    	{
    	    Date d1 = df.parse(time1);
    	    Date d2 = df.parse(time2);
    	    long diff = d1.getTime() - d2.getTime();
    	     days = diff / (1000 * 60 * 60 * 24);
    	   
    	}
    	catch (Exception e)
    	{
    	}
    	 return new Long(days).intValue();

	}
    /**
                获取两个日期相差几个月
     * @param start
     * @param end
     * @return
     */
    public static int getChaMonth(String time1,String time2) {
    	
    	try {
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	
	    	Date start = df.parse(time1);
	
	    	Date end= df.parse(time2);
	    	
	        if (start.after(end)) {
	            Date t = start;
	            start = end;
	            end = t;
	        }
	        Calendar startCalendar = Calendar.getInstance();
	        startCalendar.setTime(start);
	        Calendar endCalendar = Calendar.getInstance();
	        endCalendar.setTime(end);
	        Calendar temp = Calendar.getInstance();
	        temp.setTime(end);
	        temp.add(Calendar.DATE, 1);
	        int year = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
	        int month = endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
	        if ((startCalendar.get(Calendar.DATE) == 1)&& (temp.get(Calendar.DATE) == 1)) {
	            return year * 12 + month + 1;
	        } else if ((startCalendar.get(Calendar.DATE) != 1) && (temp.get(Calendar.DATE) == 1)) {
	            return year * 12 + month;
	        } else if ((startCalendar.get(Calendar.DATE) == 1) && (temp.get(Calendar.DATE) != 1)) {
	            return year * 12 + month;
	        } else {
	            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
	        }
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return 0;
    }
}
