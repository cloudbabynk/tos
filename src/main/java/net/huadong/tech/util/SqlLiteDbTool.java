package net.huadong.tech.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import net.huadong.tech.util.HdUtils;
import net.huadong.tech.base.bean.HdRunTimeException;
import net.huadong.tech.dao.JpaUtils;
import net.huadong.tech.inter.entity.ShipInOutCheck;
import net.huadong.tech.inter.entity.YardIn;
import net.huadong.tech.ship.entity.Shipcount;

public class SqlLiteDbTool {
	private Connection connect(String fileNam) {
		Connection conn = null;
		String url1 = ".//upload//ShipInOutCheck//" + fileNam + ".upload";
		String url2 = ".//upload//ShipInOutCheck//" + fileNam + ".db";
		try {
			FileInputStream in = new FileInputStream(new File(url1));
			FileOutputStream out = new FileOutputStream(new File(url2));
			byte[] buff = new byte[1024];
			int n = 0;
			while ((n = in.read(buff)) != -1) {
				out.write(buff, 0, n);
			}
			out.flush();
			in.close();
			out.close();
			System.out.println("复制完成");
		} catch (Exception e) {
			System.out.println("复制失败");
		}
		// 定义数据库连接对象
		try {
			String url = "jdbc:sqlite:upload/ShipInOutCheck/" + fileNam + ".db";
			// 定义连接数据库的url(url:访问数据库的URL路径),test为数据库名称
			Class.forName("org.sqlite.JDBC");
			// 加载数据库驱动
			conn = DriverManager.getConnection(url);
			// 获取数据库连接
			System.out.println("数据库连接成功！\n");
			// 数据库连接成功输出提示
		} catch (ClassNotFoundException | SQLException e) {
			throw new HdRunTimeException("数据库连接失败！" + e.getMessage());
		}
		return conn;
		// 返回一个连接
	}

	@Transactional
	public void selectAll(String type, String fileNam, String shipNo) {
		// 选择 文本区 中的所有文本。在 null 或空文档上不执行任何操作。
		String sql = "";
		if ("SO".equals(type)) {
			sql = "select a.* from shipInCheck a";
		} else if ("SI".equals(type)) {
			sql = "select a.* from shipOutCheck a";
		}

		// 将从表中查询到的的所有信息存入sql
		try {
			Connection conn = this.connect(fileNam);
			Statement stmt = conn.createStatement();
			// 得到Statement实例
			ResultSet rs = stmt.executeQuery(sql);
			// 执行SQL语句返回结果集 //输出查询到的记录的内容（表头）
			// 当返回的结果集不为空时，并且还有记录时，循环输出记录
			while (rs.next()) {
				ShipInOutCheck bean = new ShipInOutCheck();
				bean.setCheckId(HdUtils.generateUUID());
				bean.setShipNo(shipNo);
				if (HdUtils.strNotNull(rs.getString("vcVinNo")))
					bean.setVcVinNo(rs.getString("vcVinNo"));
				
				bean.setWorkTyp(type);
				
				if ("SI".equals(type)) {
					if (HdUtils.strNotNull(rs.getString("vcPosition")))
						bean.setVcPort(rs.getString("vcPosition"));
					if (HdUtils.strNotNull(rs.getString("vcGarage"))) {
						bean.setVcGarage(rs.getString("vcGarage"));
						String areaRow = rs.getString("vcGarage");
						bean.setCyAreaNo("HQ" + areaRow.split("-")[0]);
						bean.setCyRowNo(areaRow.split("-")[1]);
					}
				} else if ("SO".equals(type)){
					if (HdUtils.strNotNull(rs.getString("vcPort")))
						bean.setVcPort(rs.getString("vcPort"));
				}
				JpaUtils.save(bean);
			}
			
			//解析yardIn数据
			String querySql = "select a.* from YardIn a";
			ResultSet yardInData = stmt.executeQuery(querySql);
			while (yardInData.next()) {
				YardIn yardIn = new YardIn();
				yardIn.setInId(HdUtils.generateUUID());
				yardIn.setShipNo(shipNo);
				if (HdUtils.strNotNull(rs.getString("vcVinNo")))
					yardIn.setVcVinNo(rs.getString("vcVinNo"));
				if (HdUtils.strNotNull(rs.getString("vcSiteId")))
					yardIn.setVcSite(rs.getString("vcSiteId"));
				if (HdUtils.strNotNull(rs.getString("dYardInTime")))
					yardIn.setdYardInTime(rs.getString("dYardInTime"));
				JpaUtils.save(yardIn);
			}
		} catch (SQLException e) {
			throw new HdRunTimeException("查询数据时出错！" + e.getMessage());
		}
	}

}
