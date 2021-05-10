/**
 * 
 */
package net.huadong.tech.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



/**
 * @yl 
 */
public class CommonUtil {
	/**  
     * 生成主键(16位数字) 
     * 主键生成方式,年月日时分秒毫秒的时间戳+四位随机数保证不重复 
     */    
    public static  String getId() {  
        //当前时间精确到秒  
    	String datetime = new SimpleDateFormat("yyyyMMddHHmmss")  
    	        .format(new Date());   
        return datetime;    
    }
	/**
	 * 判断字符串是否为空。
	 * 
	 * @param str
	 * @return
	 */
	public static boolean strIsNull(String str) {
		boolean flag = false;
		if (str == null || str.trim().length() <= 0) {
			return true;
		}
		return flag;
	}

	public static boolean strNotNull(String str) {

		return !strIsNull(str);
	}
    public  static boolean strIsEmpty(String strObj){
        if(strObj==null||strObj.equals("")||strObj.equals("null")){
            return true;
        }else
            return false;
    }

	/**
	 * 字符串转日期到分
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date strToDateTime(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;

		}

	}

	/**
	 * 日期处理
	 * 
	 * @param old
	 * @param num
	 *            增加的天数,可为负数
	 * @return
	 */
	public static Date addDay(Date old, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(old);
		cal.add(Calendar.DATE, num);
		return cal.getTime();
	}

	/**
	 * 字符串转日期
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date strToDate(String dateStr) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			System.out.println(format.parse(dateStr));
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;

		}

	}

	/**
	 * 获取日期字符串形式
	 * 
	 * @return
	 */
	public static String getDateStr() {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(getDate());
	}

	public static String getDateTimeStr() {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(getDate());
	}

	/**
	 * 获取日期
	 * 
	 * @return
	 */
	public static Timestamp getDate() {
		TimeZone time = TimeZone.getTimeZone("GMT+8"); //设置为东八区
		TimeZone.setDefault(time);
		Calendar calendar = Calendar.getInstance();
		return new Timestamp(calendar.getTime().getTime());

	}

	/**
	 * 生成uuid
	 * 
	 * @return
	 */
	public static String genUuid() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	public static HttpSession getHttpSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		return request.getSession();
	}

	// /**
	// * 获取当前用户信息
	// *
	// * @return
	// */
	// public static AuthUser getCurUser() {
	// return (AuthUser)
	// getSession().getAttribute(PrivilegeResource.SESSION_USER);
	// }
	//
	// public static Response genSuccessMsg() {
	// HdMessageCode msg =
	// HdMessageFactory.getInstance().getHdMessageCode("dbPersistSuccess");
	// return Response.ok(msg, "application/json").build();
	// }
	// public static Response genErrMsg(Exception e) {
	// HdMessageCode msg = HdMessageFactory.getInstance().getHdMessageCode(e);
	// return Response.ok(msg, "application/json").build();
	// }
}
