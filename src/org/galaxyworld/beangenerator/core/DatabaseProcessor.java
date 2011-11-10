package org.galaxyworld.beangenerator.core;

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

import org.galaxyworld.beangenerator.data.FieldData;
import org.galaxyworld.beangenerator.data.JavaBeanData;
import org.galaxyworld.beangenerator.util.Constants;
import org.galaxyworld.beangenerator.util.JarLoader;
import org.galaxyworld.beangenerator.util.PropertiesUtils;

public class DatabaseProcessor {

	private Connection getConnection() throws DatabaseException {
		Connection conn = null;
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
		return conn;
	}
	
	private void closeConnection(Connection conn) {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private List<String> getTables(Connection conn) throws Exception {
	    DatabaseMetaData meta = conn.getMetaData();
	    ResultSet rs = meta.getTables(null, null, null, new String[] { "TABLE" });
	    List<String> tables = new ArrayList<String>();
	    while (rs.next()) {
	    	tables.add(rs.getString("TABLE_NAME"));
	    }
	    return tables;
	}
	
	private JavaBeanData getTableStructure(Connection conn, String name) throws Exception {
		JavaBeanData bean = new JavaBeanData();
		bean.setComment("Table name: " + name + ".");
		bean.setTableName(name);
		bean.setClassName(createJavaName(name));
		Statement statement = conn.createStatement();
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(name);
		sb.append(" where 1=2");
		String sql = sb.toString();
		ResultSet rs = statement.executeQuery(sql);
		ResultSetMetaData meta = rs.getMetaData();
		for (int i = 1, cols = meta.getColumnCount(); i <= cols; i++) {
			FieldData fd = new FieldData(createJavaName(meta.getColumnName(i)), meta.getColumnClassName(i));
			StringBuilder fieldCommentBuilder = new StringBuilder();
			fieldCommentBuilder.append("Field: ");
			fieldCommentBuilder.append(meta.getColumnName(i));
			fieldCommentBuilder.append("; Type: ");
			fieldCommentBuilder.append(meta.getColumnTypeName(i));
			fieldCommentBuilder.append(".");
			fd.setComment(fieldCommentBuilder.toString());
			bean.addField(fd);
		}
		rs.close();
		statement.close();
		return bean;
	}
	
	private String createJavaName(String dbName) {
		String[] subs = dbName.split("_");
		StringBuilder javaNameBuilder = new StringBuilder();
		for(String str : subs) {
			javaNameBuilder.append(str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase()));
		}
		return javaNameBuilder.toString();
	}
	
	public List<JavaBeanData> getJavaBeanData() throws Exception {
		Connection conn = getConnection();
		List<JavaBeanData> beans = new ArrayList<JavaBeanData>();
		List<String> tables = getTables(conn);
		for(String tableName : tables) {
			beans.add(getTableStructure(conn, tableName));
		}
		closeConnection(conn);
		return beans;
	}
}
