package org.galaxyworld.beangenerator.util;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.galaxyworld.beangenerator.core.Config;
import org.galaxyworld.beangenerator.core.DatabaseException;

public class DatabaseUtils {
	
	private static Connection conn = null;

	private static Connection getConnection() throws DatabaseException {
		if(conn == null) {
			Properties props = PropertiesUtils.read(Config.getInstance().getConfigFilePath());
			try {
				Driver driver = JarLoader.loadJarFile(new File(props.getProperty(Constants.PROP_JAR_PATH)), props.getProperty(Constants.PROP_DRIVER));
				conn = driver.connect(props.getProperty(Constants.PROP_URL), props);
			} catch (Exception e) {
				e.printStackTrace();
				DatabaseException ex = new DatabaseException(DatabaseException.FAILED_CREATE_CONNECTION);
				ex.fillInStackTrace();
				throw ex;
			}
		}
		return conn;
	}
	
	private static void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static List<String> getTables() throws Exception {
	    DatabaseMetaData meta = getConnection().getMetaData();
	    ResultSet rs = meta.getTables(null, null, null, new String[] { "TABLE" });
	    List<String> tables = new ArrayList<String>();
	    while (rs.next()) {
	    	tables.add(rs.getString("TABLE_NAME"));
	    }
	    return tables;
	}
	
	private static void getTableStructure(String name) throws Exception {
		Statement statement = getConnection().createStatement();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(name);
		sb.append(" where 1=2");
		String sql = sb.toString();
		ResultSet rs = statement.executeQuery(sql);
		ResultSetMetaData meta = rs.getMetaData();
		for (int i = 1, cols = meta.getColumnCount(); i <= cols; i++) {
			System.out.print(String.format("列%1$s的信息--", i));
			System.out.print(String.format("名称:%1$s,", meta.getColumnName(i)));
			System.out.print(String.format("java类型名称:%1$s,", meta    .getColumnClassName(i)));
			System.out.print(String.format("sql类型名称:%1$s,", meta.getColumnTypeName(i)));
			System.out.print(String.format("显示需要的最大长度:%1$s,", meta    .getColumnDisplaySize(i)));
			System.out.println();
		}
		rs.close();
		statement.close();
	}
	
	private static void process() throws Exception {
		List<String> tables = getTables();
		for(String tableName : tables) {
			getTableStructure(tableName);
		}
	}
	
	public static void main(String[] args) throws Exception {
		process();
		closeConnection();
	}
	
}
