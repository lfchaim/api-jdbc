package com.whs.apijdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BaseDAO {

	public Connection getConnection(String dataSourceName) throws Exception {
		Connection conn = null;
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup(dataSourceName);
			conn = ds.getConnection();
			
			//conn = ((DataSource) ServiceLocator.getInstance().getDataSource(dataSourceName)).getConnection();
			return conn;
		}
		catch (NamingException e) {
			System.err.println("BaseDAO.getConnection\n"+Util.getStackTraceInformation(e,false));
			throw new Exception("NamingException: " + dataSourceName, e);
		}
		catch (SQLException e) {
			System.err.println("BaseDAO.getConnection\n"+Util.getStackTraceInformation(e,false));
			throw new Exception("NamingException: " + dataSourceName, e);
		}
	}

	public Connection getConnection(String[] conf){
		Connection ret = null;
		try{
			Class.forName(conf[0]);
			ret = DriverManager.getConnection(conf[1],conf[2],conf[3]);
		}catch(Exception e){
			e.printStackTrace();
		}
		return ret;
	}
	
	public Connection getConnection(String dataSourceName, String defaultDataSource, Map<String,String[]> mapConnManual) throws Exception {
		Connection ret = null;
		if( dataSourceName == null )
			dataSourceName = defaultDataSource;
		if( mapConnManual != null && mapConnManual.size() > 0 ){
			String[] conf = mapConnManual.get(dataSourceName);
			try{
				Class.forName(conf[0]);
				ret = DriverManager.getConnection(conf[1],conf[2],conf[3]);
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			ret = getConnection(dataSourceName);
		}
		return ret;
	}

	public void close(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		}
		catch (SQLException e) {

		}
	}

	public void close(Statement st) {
		try {
			if (st != null)
				st.close();
		}
		catch (SQLException e) {

		}
	}
	
	protected void close(Connection con, PreparedStatement ps, ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (ps != null) {
			try {
				ps.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			ps = null;
		}
		if (con != null) {
			try {
				con.close();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			con = null;
		}
	}


	public void close(Connection conn, Statement st) {
		try {
			if (conn != null)
				conn.close();
		}
		catch (SQLException e) {

		}
		try {
			if (st != null)
				st.close();
		}
		catch (SQLException e) {

		}
	}

	public void setLong(PreparedStatement ps, int index, Long value) throws SQLException {
		if (value != null)
			ps.setLong(index, value.longValue());
		else
			ps.setNull(index, Types.NUMERIC);
	}

	public void setInt(PreparedStatement ps, int index, Integer value) throws SQLException {
		if (value != null)
			ps.setInt(index, value.intValue());
		else
			ps.setNull(index, Types.NUMERIC);
	}

	public void setInteger(PreparedStatement ps, int index, Integer value) throws SQLException {
		if (value != null)
			ps.setInt(index, value.intValue());
		else
			ps.setNull(index, Types.NUMERIC);
	}

	public List getColumNames(ResultSet rs) throws SQLException {
		List ret = new ArrayList();
		ResultSetMetaData meta = rs.getMetaData();
		int cols = meta.getColumnCount();
		for (int i = 0; i < cols; i++) {
			String colName = meta.getColumnName(i + 1);
			if (!ret.contains(colName))
				ret.add(colName);
		}
		return ret;
	}

	public void setString(PreparedStatement ps, int index, String value) throws SQLException {
		if ( value != null )
			ps.setString(index, value);
		else
			ps.setNull(index, Types.VARCHAR);
	}

	public void setDouble(PreparedStatement ps, int index, Double value) throws SQLException {
		if (value != null)
			ps.setDouble(index, value.doubleValue());
		else
			ps.setNull(index, Types.DOUBLE);
	}

	public void setFloat(PreparedStatement ps, int index, Float value) throws SQLException {
		if (value != null)
			ps.setDouble(index, value.floatValue());
		else
			ps.setNull(index, Types.FLOAT);
	}

	public void setBoolean(PreparedStatement ps, int index, Boolean value) throws SQLException {
		if (value != null)
			ps.setBoolean(index, value.booleanValue());
		else
			ps.setNull(index, Types.BOOLEAN);
	}

	public void setTimestamp(PreparedStatement ps, int index, Date value) throws SQLException {
		if (value != null)
			ps.setTimestamp(index, new Timestamp(value.getTime()));
		else
			ps.setNull(index, Types.DATE);
	}

	public void setDate(PreparedStatement ps, int index, Date value) throws SQLException {
		if (value != null)
			ps.setDate(index, new java.sql.Date(value.getTime()));
		else
			ps.setNull(index, Types.DATE);
	}

	public StringBuffer getPreparedSelectSQL(String tableName, String[] primaryKey, String[] columns) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
		int i = 0;
		for (; i < primaryKey.length; i++) {
			if (i > 0)
				sql.append(",");
			sql.append(primaryKey[i]);
		}
		for (int j = 0; columns != null && j < columns.length; j++) {
			if (i++ > 0)
				sql.append(",");
			sql.append(columns[j]);
		}
		sql.append(" FROM ");
		sql.append(tableName);
		return sql;
	}

	public StringBuffer getPreparedSelectSQL(String tableName, String[] primaryKey, String[] columns, String[] where) {
		StringBuffer sql = getPreparedSelectSQL(tableName, primaryKey, columns);
		sql.append(" WHERE 1=1 ");
		for (int i = 0; i < where.length; i++) {
			sql.append(where[i]).append(" ");
		}
		return sql;
	}

	public StringBuffer getPreparedInsertSQL(String tableName, String[] columns) {
		return getPreparedInsertSQL(tableName,null,columns,null);
	}
	
	public StringBuffer getPreparedInsertSQL(String tableName, String[] primaryKey, String[] columns) {
		return getPreparedInsertSQL(tableName, primaryKey, columns, null);
	}
	
	public StringBuffer getPreparedInsertSQL(String tableName, String[] primaryKey, String[] columns, String identityColumn) {
		StringBuffer sql = new StringBuffer();
		sql.append("INSERT INTO ");
		sql.append(tableName);
		sql.append(" ( ");
		int i = 0;
		for (; primaryKey != null && i < primaryKey.length; i++) {
			if (i > 0)
				sql.append(",");
			sql.append(primaryKey[i]);
		}
		for (int j = 0; columns != null && j < columns.length; j++) {
			if (i++ > 0)
				sql.append(",");
			sql.append(columns[j]);
		}
		sql.append(" ) ");
		sql.append("VALUES");
		sql.append(" ( ");
		for (int j = 0; j < i; j++) {
			if (j > 0)
				sql.append(",");
			sql.append("?");
		}
		sql.append(" ) ");
		return sql;
	}

	public StringBuffer getPreparedUpdateSQL(String tableName, String[] primaryKey, String[] columns) {
		StringBuffer sql = new StringBuffer();
		sql.append("UPDATE ");
		sql.append(tableName);
		sql.append(" SET ");
		for (int i = 0; i < columns.length; i++) {
			if (i > 0)
				sql.append(",");
			sql.append(columns[i]).append(" = ?");
		}
		sql.append(" WHERE ");
		for (int i = 0; i < primaryKey.length; i++) {
			if (i > 0)
				sql.append(" AND ");
			sql.append(primaryKey[i]).append(" = ?");
		}
		return sql;
	}

	public StringBuffer getPreparedDeleteSQL(String tableName, String[] primaryKey) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ");
		sql.append(tableName);
		sql.append(" WHERE ");
		for (int i = 0; i < primaryKey.length; i++) {
			if (i > 0)
				sql.append(" AND ");
			sql.append(primaryKey[i]).append(" = ?");
		}
		return sql;
	}

	public StringBuffer getPreparedDeleteSQL(String tableName, String[] primaryKey, int count) {
		StringBuffer sql = new StringBuffer();
		sql.append("DELETE FROM ");
		sql.append(tableName);
		sql.append(" WHERE 1=1 ");
		sql.append(" AND ( ");
		for (int i = 0; i < count; i++) {
			if (i > 0)
				sql.append(" OR ");
			sql.append(" ( ");
			for (int j = 0; j < primaryKey.length; j++) {
				if (j > 0)
					sql.append(" AND ");
				sql.append(primaryKey[j]).append(" = ? ");
			}
			sql.append(" ) ");
		}
		sql.append(" ) ");
		return sql;
	}

	public Long getNextSequenceId(Connection conn, String sequenceName) throws SQLException {
		PreparedStatement ps = null;
		Long retVal = null;
		try {
			ps = conn.prepareStatement("SELECT " + sequenceName + ".NEXTVAL FROM dual ");
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				retVal = new Long(rs.getLong(1));
			rs.close();
			return retVal;
		}
		catch (SQLException e) {
			throw e;
		}
		finally {
			close(ps);
		}
	}

	public Object getObject(String colName, ResultSet rs) throws SQLException {
		Object retVal = null;
		ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		for (int i = 1; i <= cols; i++) {
			if (rsmd.getColumnName(i).equals(colName)) {
				int type = rsmd.getColumnType(i);
				switch (type) {
					case Types.DECIMAL:
					case Types.NUMERIC:
						if (rsmd.getScale(i) > 0) {
							retVal = new Double(rs.getDouble(i));
						}
						else {
							retVal = new Long(rs.getLong(i));
						}
						break;
					case Types.DOUBLE:
					case Types.FLOAT:
						retVal = new Double(rs.getDouble(i));
						break;
					case Types.BIGINT:
						retVal = new Long(rs.getLong(i));
						break;
					case Types.INTEGER:
						retVal = new Integer(rs.getInt(i));
						break;
					case Types.SMALLINT:
					case Types.TINYINT:
						retVal = new Integer(rs.getShort(i));
						break;
					case Types.CHAR:
					case Types.LONGVARCHAR:
					case Types.VARCHAR:
						retVal = rs.getString(i);
						break;
					case Types.BOOLEAN:
						retVal = new Boolean(rs.getBoolean(i));
						break;
					case Types.TIMESTAMP:
						retVal = new Date(rs.getTimestamp(i).getTime());
						break;
					case Types.DATE:
						retVal = new Date(rs.getTime(i).getTime());
						break;
					default:
						retVal = rs.getString(i);
						break;
				}
			}
		}
		return retVal;
	}

	public String[] getAndPK(String[] pk) {
		String[] ret = new String[pk.length];
		for (int i = 0; i < pk.length; i++) {
			ret[i] = "AND " + pk[i] + " = ? ";
		}
		return ret;
	}
	
	public synchronized int getNextId(Connection conn) throws Exception {
		StringBuffer sql 	= new StringBuffer();
		Statement stmt 	= null;
 		ResultSet rs 		= null;
		int retVal = 0;
		try{
			sql.append("SELECT @@identity as ID");
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql.toString());
			if( rs.next() ){
				retVal = rs.getInt("ID");
			}
			rs.close();
			if( retVal < 1 ) retVal = 1;
			return retVal;
		}catch( SQLException e ){
			throw new Exception(e);
		}finally{
			close(conn,stmt);
		}
	}

	public List listMapByQuery(String dsName, Map<String,String[]> map, String sql){
		Connection conn = null;
		PreparedStatement ps = null;
		List ret = null;
		try{
			conn = getConnection(map.get(dsName));
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			String[] cols = new String[meta.getColumnCount()];
			for( int i = 0; i < meta.getColumnCount(); i++ ){
				cols[i] = meta.getColumnName(i+1);
			}
			while( rs.next() ){
				if( ret == null )
					ret = new ArrayList();
				Map mapData = new HashMap();
				for( int i = 0; i < cols.length; i++ ){
					if( rs.getString(cols[i]) == null )
						mapData.put(cols[i], "");
					else
						mapData.put(cols[i], rs.getObject(cols[i]));
				}
				ret.add(mapData);
			}
		}catch( Exception e ){
			e.printStackTrace();
		}finally{
			try{ps.close();}catch(Exception e){}
			try{conn.close();}catch(Exception e){}
		}
		return ret;
	}

	public String semAcento(String text) {
		text = Normalizer.normalize(text, Normalizer.Form.NFD);
		text = text.replaceAll("[^\\p{ASCII}]", "");
		return text;
	}
}

