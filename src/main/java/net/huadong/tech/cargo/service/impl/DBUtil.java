package net.huadong.tech.cargo.service.impl;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.huadong.tech.cargo.entity.ShipMaster;
import net.huadong.tech.inter.entity.ShipIn;
import net.huadong.tech.inter.entity.ShipOut;

public class DBUtil {

    private static final Logger logger = LoggerFactory.getLogger(DBUtil.class);
    private static Connection connection = null;
    private static Statement statement = null;
    private static String username = "tjg_auto";
    private static String password = "auto_tjg";

    /**
     * @describe: 设置连接 * @params: 
     */
    public static void setConnection(String workTyp) {
        try {
//            声明驱动类型
            Class.forName("org.sqlite.JDBC");
//            设置sqlite db文件存放基本目录
//            String path = DBUtil.class.getClassLoader().getResource("").getPath() + "static/";
            
//            String path = "E:\\tjroro\\upload\\shipInOutFT\\";
            String path = ".//upload//ShipInOutDB//";
            File file = new File(path);
            if(!file.exists()) {
            	file.mkdirs();
            }
            if(workTyp.equals("SO")) {  //装船 shipIn.db
            	path += "info.db";
            }
            if(workTyp.equals("SI")) {  //卸船 shipOut.db
            	path += "info.db";
            }
//            String xxpath = "upload/ShipInOutDb/shinOut.db";
//            设置 sqlite文件路径
            String url = "jdbc:sqlite:" + path;
            
            
            
//            获取连接
            connection = DriverManager.getConnection(url, username, password);
//            声明
            statement = connection.createStatement();
        } catch (Exception e) {
            throw new RuntimeException("建立Sqlite连接失败");
        }
    }

    /**
     * @describe: 创建表
     * @params: tableName: 要创建的表的名称
     */
    public synchronized static void create(String tableName, String className, String workTyp) {
        try {
//        	statement.executeUpdate("DROP TABLE IF EXISTS " + tableName + ";");
        	statement.executeUpdate("DROP TABLE IF EXISTS " + "shipIn" + ";");
        	statement.executeUpdate("DROP TABLE IF EXISTS " + "shipOut" + ";");
        	statement.executeUpdate("DROP TABLE IF EXISTS " + "shipMaster" + ";");
        	String cSql = "";
        	if(workTyp.equals("SI")) {
//        		cSql += "create table shipOut (VC_VIN_NO varchar(255) ,VC_SITE varchar(255) ,D_CREATE_DATE varchar(255) ,VC_EXCEPTION varchar(255) ,VC_SHIP_ID varchar(255) );";
        		cSql += "create table shipOut (vcVinNo varchar(255) ,vcSite varchar(255) ,dCreateDate varchar(255) ,vcException varchar(255) ,vcShipId varchar(255) );";
        	}
            if(workTyp.equals("SO")) {
//            	cSql += "create table shipIn (VC_VIN_NO varchar(255) ,VC_PORT varchar(255) ,D_CREATE_DATE varchar(255) ,VC_START_SITE varchar(255) ,VC_SHIP_ID varchar(255) );";
//            	cSql += "create table shipIn (vcVinNo varchar(255) ,vcPort varchar(255) ,dCreateDate varchar(255) ,vcStartSite varchar(255) ,vcShipId varchar(255) );";
            	cSql += "create table shipIn (vcVinNo varchar(255) ,vcShipId varchar(255) ,dCreateDate varchar(255) ,vcPort varchar(255) ,vcStartSite varchar(255)  );";
            	String smSql = "create table shipMaster (vcShipID varchar(255) ,vcShipName varchar(255)  );";
            	statement.executeUpdate(smSql);
            }
            System.out.println(cSql);
            statement.executeUpdate(cSql);
            
        } catch (Exception e) {
            logger.error("建表失败：" + e);
            throw new RuntimeException("建表失败，表名称：" + tableName);
        }
    }

    /**
     * @describe: 表中插入数据
     * @params: tableName：表名 list:待插入的对象集合 需要注意的是插入的对象要跟表名对应
     */
    public synchronized static <T> int insert(String tableName, List<ShipOut> list, String workTyp) {
        StringBuffer declaration = new StringBuffer();
        List<String> dataSqlArray = new ArrayList<>();
        int count = 0;
        
        try {
//        	statement.executeUpdate("DROP TABLE IF EXISTS " + tableName + ";");
        	//sqllite表插入数据
    		StringBuffer oneData = new StringBuffer();
            String reg = "";
            for(int i=0; i<list.size(); i++) {
            	ShipOut shipOut = list.get(i);
            	oneData.append(" (");
            	oneData.append(reg + "\'" + shipOut.getVcVinNo() + "\',");
            	oneData.append(reg + "\'" + shipOut.getVcSite() + "\',");
            	oneData.append(reg + "\'" + shipOut.getVcCreateDate() + "\',");
            	oneData.append(reg + "\'" + shipOut.getVcException() + "\',");
            	oneData.append(reg + "\'" + shipOut.getVcShipId() + "\'");
            	if(i==(list.size()-1)) {
            		oneData.append(" )");
            	} else {
            		oneData.append(" ),");
            	}
            }
            dataSqlArray.add(oneData.toString());
            String dataSql = StringUtils.join(dataSqlArray, ",");
//            String retSQL = "INSERT INTO shipOut (VC_VIN_NO, VC_SITE, D_CREATE_DATE, VC_EXCEPTION, VC_SHIP_ID)" + " VALUES " + dataSql + ";";
            String retSQL = "INSERT INTO shipOut (vcVinNo, vcSite, dCreateDate, vcException, vcShipId)" + " VALUES " + dataSql + ";";
            System.out.println(retSQL);
            PreparedStatement prep = connection.prepareStatement(retSQL);
////        设置自动提交
	        connection.setAutoCommit(true);
	        count = prep.executeUpdate();
        } catch (Exception e) {
          logger.error("插入失败：" + e);
          e.printStackTrace();
      }
        return count;
    }
    
    public synchronized static <T> int insertShipIn(String tableName, List<ShipIn> list, ShipMaster smList, String workTyp) {
    	StringBuffer declaration = new StringBuffer();
        List<String> dataSqlArray = new ArrayList<>();
        int count = 0;
        
        try {
        	//sqllite表插入数据
    		StringBuffer oneData = new StringBuffer();
            String reg = "";
            
            for(int i=0; i<list.size(); i++) {
            	ShipIn shipIn = list.get(i);
            	oneData.append(" (");
            	oneData.append(reg + "\'" + shipIn.getVcVinNo() + "\',");
            	oneData.append(reg + "\'" + shipIn.getVcShipId() + "\',");
            	oneData.append(reg + "\'" + shipIn.getVcCreateDate() + "\',");
            	oneData.append(reg + "\'" + shipIn.getVcPort() + "\',");
            	oneData.append(reg + "\'" + shipIn.getVcStartSite() + "\'");
            	
            	if(i==(list.size()-1)) {
            		oneData.append(" )");
            	} else {
            		oneData.append(" ),");
            	}
            }
            
            dataSqlArray.add(oneData.toString());
            String dataSql = StringUtils.join(dataSqlArray, ",");
//            String retSQL = "INSERT INTO shipIn (VC_VIN_NO, VC_PORT, D_CREATE_DATE, VC_START_SITE,  VC_SHIP_ID)" + " VALUES " + dataSql + ";";
            String retSQL = "INSERT INTO shipIn (vcVinNo, vcShipId, dCreateDate, vcPort, vcStartSite)" + " VALUES " + dataSql + ";";
            
            //插入shipMaster
            String smSQL = "INSERT INTO shipMaster (vcShipID, vcShipName)" + " VALUES ('" + smList.getVcShipID() + "', '" + smList.getVcShipName() + "');";
            System.out.println(smSQL);
            System.out.println(retSQL);
            PreparedStatement smPrep = connection.prepareStatement(smSQL);
            PreparedStatement prep = connection.prepareStatement(retSQL);
////        设置自动提交
	        connection.setAutoCommit(true);
	        count = smPrep.executeUpdate();
	        count = prep.executeUpdate();
        } catch (Exception e) {
          logger.error("插入失败：" + e);
          e.printStackTrace();
      }
        return count;
    }


    /**
     * @describe: 关闭链接
     * @params:
     */
    public static void endConnection() {
        try {

            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * URL地址
     */
    public static String getURL(String workTyp) {
    	String path = DBUtil.class.getClassLoader().getResource("").getPath() + "static/" + "data.db";
    	return path;
    }

}
