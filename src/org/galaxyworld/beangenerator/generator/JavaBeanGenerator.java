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

package org.galaxyworld.beangenerator.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.galaxyworld.beangenerator.data.FieldData;
import org.galaxyworld.beangenerator.data.JavaBeanData;
import org.galaxyworld.beangenerator.event.GeneratorProcessEvent;
import org.galaxyworld.beangenerator.event.GeneratorProcessEvent.Phase;
import org.galaxyworld.beangenerator.util.AppContext;
import org.galaxyworld.beangenerator.util.AppException;
import org.galaxyworld.beangenerator.util.BeanMapUtils;
import org.galaxyworld.beangenerator.util.Constants;
import org.galaxyworld.beangenerator.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Template;

/**
 * JavaBean generator.
 * 
 * @author devbean
 * @version 0.0.1
 */
public class JavaBeanGenerator extends AbstractGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(JavaBeanGenerator.class);
	
	public JavaBeanGenerator() {
	}
	
	/**
	 * Creates directory structure according to package.
	 * 
	 * @return output file directory path
	 * @throws AppException if any problem
	 */
	private String createPackageFolders() throws AppException {
		AppContext ctx = AppContext.getInstance();
		String pn = ctx.getCommonDataValue(Constants.PACKAGE_NAME);
		try {
			String outputFilePath = AppContext.getInstance().getOutputPath();
			outputFilePath = outputFilePath + pn.replace(".", File.separator);
			FileUtils.forceMkdir(new File(outputFilePath));
			return outputFilePath;
		} catch (IOException e) {
			logger.error(e.getMessage());
			throw new AppException(AppException.FAILED_CREATE_PACKAGE_DIR);
		}
	}

	/**
	 * Generates JavaBean source code to disk.
	 * 
	 * @param data JavaBean data
	 */
	private void generateBean(JavaBeanData data) {
		try {
			Template temp = cfg.getTemplate("JavaBean.ftl");
			String packagePath = createPackageFolders();
			if(packagePath != null) {
				StringBuilder sb = new StringBuilder();
				sb.append(packagePath);
				sb.append(File.separator);
				sb.append(data.getClassName());
				sb.append(".java");
				String path =  sb.toString();
		        Writer out = new OutputStreamWriter(new FileOutputStream(new File(path)));
		        Map<String, Object> root = BeanMapUtils.toMap(data);
		        root.putAll(AppContext.getInstance().getCommonDataMap());
		        temp.process(root, out);
		        out.flush();
		        logger.info("Success! Location: " + path + "; data: " + root);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	@Override
	public void generate() throws Exception {
		DatabaseProcessor dp = new DatabaseProcessor();
		fireGeneratorProcessEvent(new GeneratorProcessEvent(Phase.Starting,
				"Start parsing database tables...\n", this));
		Connection conn = dp.getConnection();
		List<String> tables = dp.getTables(conn);
		fireGeneratorProcessEvent(new GeneratorProcessEvent(Phase.Started,
				"Finish parsed database.\n", this));
		for(String tableName : tables) {
			JavaBeanData bean = dp.getTableMetaData(conn, tableName);
			generateBean(bean);
			StringBuilder sb = new StringBuilder();
			sb.append("Finish generate table ");
			sb.append(bean.getComment());
			sb.append(".\n");
			fireGeneratorProcessEvent(new GeneratorProcessEvent(Phase.ItemFinished,
					sb.toString(), this));
		}
		dp.closeConnection(conn);
		fireGeneratorProcessEvent(new GeneratorProcessEvent(Phase.Finished,
				"Done.", this));
	}
	
	private class DatabaseProcessor {
		
		/**
		 * Gets tables list from database.
		 * 
		 * @param conn database connection
		 * @return table list
		 * @throws Exception if any problem
		 */
		public List<String> getTables(Connection conn) throws Exception {
		    DatabaseMetaData meta = conn.getMetaData();
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
		 * @param conn database connection
		 * @param name table name
		 * @return data needs to generate
		 * @throws Exception if any problem
		 */
		public JavaBeanData getTableMetaData(Connection conn, String name) throws Exception {
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
		 * 
		 * @param conn connection to close
		 */
		public void closeConnection(Connection conn) {
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
		public Connection getConnection() throws AppException {
			Properties props = ResourceUtils.read(AppContext.getInstance().getConfigFilePath());
			try {
				Driver driver = ResourceUtils.loadJarFile(new File(props.getProperty(Constants.PROP_JAR_PATH)),
						props.getProperty(Constants.PROP_DRIVER));
				return driver.connect(props.getProperty(Constants.PROP_URL), props);
			} catch (Exception e) {
				AppException ex = new AppException(AppException.FAILED_CREATE_CONNECTION);
				ex.fillInStackTrace();
				throw ex;
			}
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
	
}
