package net.huadong.tech.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

public class beanUtil {

	public static Map<String, Object> convertBean(Object obj) { 
        Map<String, Object> params = new HashMap<String, Object>(0); 
        try { 
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
            PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
            for (int i = 0; i < descriptors.length; i++) { 
                String name = descriptors[i].getName(); 
                if (!"class".equals(name)) { 
                    params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                } 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
        return params; 
	}
	
    public static <T> T convertMap(Map<String, Object> map, Class<T> clazz) {
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         
         if (map == null) {
             return null;
         }
         T obj = null;
         try {
             // 使用newInstance来创建对象
             obj = clazz.newInstance();
             // 获取类中的所有字段
             Field[] fields = obj.getClass().getDeclaredFields();
             for (Field field : fields) {
                 int mod = field.getModifiers();
                 // 判断是拥有某个修饰符
                 if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                     continue;
                 }
                 // 当字段使用private修饰时，需要加上
                 field.setAccessible(true);
                 // 获取参数类型名字
                 String filedTypeName = field.getType().getName();
                 // 判断是否为时间类型，使用equalsIgnoreCase比较字符串，不区分大小写
                 // 给obj的属性赋值
                 if (filedTypeName.equalsIgnoreCase("java.util.date")) {
                     String datetimestamp = (String) map.get(field.getName());
                     if (datetimestamp.equalsIgnoreCase("null")) {
                         field.set(obj, null);
                     } else {
                         field.set(obj, sdf.parse(datetimestamp));
                     }
                 }else if (filedTypeName.equalsIgnoreCase("java.math.BigDecimal")) {
                	 if(map.get(field.getName())==null)  field.set(obj, null);
                	 else {
                         String datetimestamp = map.get(field.getName()).toString();
                         if (datetimestamp.equalsIgnoreCase("null")) {
                             field.set(obj, null);
                         } else {
                             field.set(obj, new  BigDecimal(datetimestamp));
                         }
                	 }
                 }else {
                     field.set(obj, map.get(field.getName()));
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
         return obj;
    }

}
