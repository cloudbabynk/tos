package net.huadong.tech.util;

import org.apache.commons.lang.StringUtils;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.springframework.stereotype.Repository;

import net.huadong.tech.dao.JpaUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JpaProcUtile {
	
    public static Object executeOracleProcWithResult(String procName, List<Object> inParamLs, List<String> outParamLs, int paramSize) {
			String sql = "{call " + procName + " (" + StringUtils.leftPad("", paramSize * 2 - 1, "?,") + ")}";
			
		Connection con = JpaUtils.getEntityManager().unwrap(Connection.class);
		CallableStatement callableStatement = null;
			
		try {
			callableStatement = con.prepareCall(sql);
			for (int i = 0; i < inParamLs.size(); i++) {
				Object o = inParamLs.get(i);
				if (o instanceof String) {
					callableStatement.setString(i + 1, (String) o);
				} else if (o instanceof Integer) {
					callableStatement.setInt(i + 1, (Integer) o);
				} else if (o instanceof BigDecimal) {
					callableStatement.setBigDecimal(i + 1, (BigDecimal) o);
				} else if (o instanceof Timestamp) {
					callableStatement.setTimestamp(i + 1, (Timestamp) o);
				} else if (o instanceof Date) {
					callableStatement.setDate(i + 1, (Date) o);
				}
			}
			for (int i = inParamLs.size(); i < paramSize; i++) {
				callableStatement.registerOutParameter(i + 1, Types.VARCHAR);
			}
			callableStatement.execute();
			for (int i = inParamLs.size(); i < paramSize; i++) {
				outParamLs.add(callableStatement.getString(i + 1));
			}	
			return callableStatement.getResultSet();
		} catch (SQLException e) {
				throw new RuntimeException(e);
		} finally {
			try {
				if(callableStatement != null) {
					callableStatement.close();
				}
			} catch (SQLException e) {
			e.printStackTrace();
			}
		}
	}
}
