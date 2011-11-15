/*
 * BeanGenerator
 * 
 * Copyright (C) 2011 galaxyworld.org
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
import org.galaxyworld.beangenerator.util.ResourceUtils;

/**
 * Supports database operations.
 * 
 * @author devbean
 * @version 0.0.1
 */
public class DatabaseProcessor {
	
	private static Connection conn;
	
	/**
	 * Gets tables list from database.
	 * 
	 * @return table list
	 * @throws Exception if any problem
	 */
	public List<String> getTables() throws Exception {
	    DatabaseMetaData meta = getConnection().getMetaData();
	    ResultSet rs = meta.getTables(null, null, null, new String[] { "TABLE" });
	    List<String> tables = new ArrayList<String>();
	    while (rs.next()) {
	    	tables.add(rs.getString("TABLE_NAME"));
	    }
	    return tables;
	}
	
	/**
	 * Gets table's meta-data.
	 * 
	 * @param name table name
	 * @return data needs to generate
	 * @throws Exception if any problem
	 */
	public JavaBeanData getTableMetaData(String name) throws Exception {
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
	
	/**
	 * Closes connection.
	 */
	public void closeConnection() {
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Creates database connection. JDBC library, driver class name and database URL should be defined
	 * in application configuration file or there will be an exception.
	 * 
	 * @return database connection
	 * @throws AppException if creates fails
	 */
	private Connection getConnection() throws AppException {
		if(conn == null) {
			Properties props = ResourceUtils.read(Config.getInstance().getConfigFilePath());
			try {
				Driver driver = ResourceUtils.loadJarFile(new File(props.getProperty(Constants.PROP_JAR_PATH)),
						props.getProperty(Constants.PROP_DRIVER));
				conn = driver.connect(props.getProperty(Constants.PROP_URL), props);
			} catch (Exception e) {
				AppException ex = new AppException(AppException.FAILED_CREATE_CONNECTION);
				ex.fillInStackTrace();
				throw ex;
			}
		}
		return conn;
	}
	
	/**
	 * Supposes the name in databases is split with '_'. This method try to convert this kind
	 * of name into Java-style.
	 * 
	 * @param dbName name of database-style
	 * @return name in Java-style
	 */
	private String createJavaName(String dbName) {
		String[] subs = dbName.split("_");
		StringBuilder javaNameBuilder = new StringBuilder();
		for(String str : subs) {
			javaNameBuilder.append(str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase()));
		}
		return javaNameBuilder.toString();
	}
	
}
