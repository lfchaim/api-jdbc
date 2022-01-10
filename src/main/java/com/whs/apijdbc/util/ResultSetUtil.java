package com.whs.apijdbc.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResultSetUtil extends BaseDAO {
	
	public static final long serialVersionUID = 1l;

	private String defaultDataSource = null;
	
	private Map<String,String[]> mapConnManual;
	
	public ResultSetUtil(Map<String,String[]> mapConnManual){
		this.mapConnManual = mapConnManual;
	}

	public List<Map> executeQuery( String dataSourceName, String sql, List parameter ) throws Exception {
		Connection conn = null;
		try{
			conn = getConnection(dataSourceName,defaultDataSource,mapConnManual);
			return executeQuery(conn, sql, parameter);
		}catch( Exception e ){
			throw e;
		}finally{
			close(conn);
		}
	}
	public List<Map> executeQuery( Connection conn, String sql, List parameter ) throws Exception {
		PreparedStatement ps = null;
		List<Map> ret = null;
		try{
			ps = conn.prepareStatement(sql);
			int idx = 1;
			if( parameter != null ){
				for( int i = 0; i < parameter.size(); i++ ){
					Object objParam = parameter.get(i);
					if( objParam instanceof Date ){
						setTimestamp(ps,idx++,((Date)objParam));
					}else if( objParam instanceof Integer ){
						setInteger(ps,idx++,(Integer)objParam);
					}else if( objParam instanceof Long ){
						setLong(ps,idx++,(Long)objParam);
					}else if( objParam instanceof String ){
						setString(ps,idx++,(String)objParam);
					}else{
						ps.setObject(idx++,objParam);
					}
				}
			}
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			while( rs.next() ){
				if( ret == null )
					ret = new ArrayList<Map>();
				Map map = new LinkedHashMap();
				for( int i = 0; i < rsmd.getColumnCount(); i++ ){
					//String name = rsmd.getColumnName(i+1);
					String name = rsmd.getColumnLabel(i+1);
		    		if( rs.getString(name) == null ){
		    			map.put(name,null);
		    		}
		    		else{
			    		switch(rsmd.getColumnType(i+1)){
			    			case Types.VARCHAR:
			    				map.put(name,rs.getString(name));
			    				break;
			    			case Types.NUMERIC:
			    				if(rsmd.getScale(i+1)>0)
			    					map.put(name,rs.getBigDecimal(name));
			    				else
			    					map.put(name,new Long(rs.getLong(name)));
			    				break;
			    			case Types.INTEGER:
			    				map.put(name,new Integer(rs.getInt(name)));
			    				break;
			    			case Types.BIGINT:
			    				map.put(name,new Long(rs.getLong(name)));
			    				break;
							case Types.DECIMAL:
							case Types.DOUBLE:
							case Types.REAL:
								map.put(name, new Double(rs.getDouble(name)));
								break;
							case Types.FLOAT:
								map.put(name, new Float(rs.getFloat(name)));
								break;
			    			case Types.DATE:
			    			case Types.TIME:
			    			case Types.TIMESTAMP:
			    				map.put(name,new java.util.Date(rs.getTimestamp(name).getTime()));
		    					break;
							case Types.CHAR:
							case Types.CLOB:
							case Types.LONGVARCHAR:
							case Types.BLOB:
							case Types.LONGVARBINARY:
							case Types.VARBINARY:
								map.put(name, rs.getString(name));
								break;
							case Types.BIT:
								map.put(name, new Boolean(rs.getBoolean(name)));
								break;
			    		    default:
			    		    	map.put(name,rs.getString(name));
			    		    	break;
			    		}
		    		}
				}
				ret.add(map);
			}
			return ret;
		}catch( SQLException e ){
			System.err.println(Util.getStackTraceInformation(e,false));
			throw new Exception(e);
		}finally{
			close(ps);
		}
	}
	
	public List<String[]> executeQueryString( String dataSourceName, String sql, List parameter ) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		List<String[]> ret = null;
		try{
			conn = getConnection(dataSourceName,defaultDataSource,mapConnManual);
			
			ps = conn.prepareStatement(sql);
	
			int idx = 1;
			if( parameter != null ){
				for( int i = 0; i < parameter.size(); i++ ){
					Object objParam = parameter.get(i);
					if( objParam instanceof Date ){
						setTimestamp(ps,idx++,((Date)objParam));
					}else if( objParam instanceof Integer ){
						setInteger(ps,idx++,(Integer)objParam);
					}else if( objParam instanceof Long ){
						setLong(ps,idx++,(Long)objParam);
					}else if( objParam instanceof String ){
						setString(ps,idx++,(String)objParam);
					}else{
						ps.setObject(idx++,objParam);
					}
				}
			}
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			if( ret == null )
				ret = new ArrayList<String[]>();
			String[] line = new String[rsmd.getColumnCount()];
			for( int i = 0; i < rsmd.getColumnCount(); i++ ){
				line[i] = rsmd.getColumnName(i+1);
			}
			ret.add(line);
			while( rs.next() ){
				String[] lineData = new String[rsmd.getColumnCount()];
				for( int i = 0; i < rsmd.getColumnCount(); i++ ){
					String name = rsmd.getColumnName(i+1);
					String value = rs.getString(rsmd.getColumnName(i+1));
					lineData[i] = value;
				}
				ret.add(lineData);
			}
			return ret;
		}catch( Exception e ){
			System.err.println(Util.getStackTraceInformation(e,false));
			throw new Exception(e);
		}finally{
			close(conn,ps);
		}
	}

	public Map executeQueryUnique( String dataSourceName, String sql, List parameter ) throws Exception {
		Map ret = null;
		List<Map> list = executeQuery(dataSourceName, sql, parameter);
		if( list != null && list.size() > 0 )
			ret = list.get(0);
		return ret;
	}
	
	public String executeUpdate( String dataSourceName, String sql ) throws Exception {
		return executeUpdate(dataSourceName, sql, null);
	}

	public String executeUpdate( String dataSourceName, String sql, String[] values ) throws Exception {
		Connection conn = null;
		try{
			conn = getConnection(dataSourceName,defaultDataSource,mapConnManual);
			return executeUpdate(conn, sql, values);
		}catch( Exception e ){
			throw e;
		}finally{
			close(conn);
		}
	}
	
	public String executeUpdate( Connection conn, String sql, String[] values ) throws Exception {
		PreparedStatement ps = null;
		String ret = "";
		try{
			ps = conn.prepareStatement(sql);
			if( values != null ){
				for( int i = 0; i < values.length; i++ ){
					ps.setString((i+1), values[i]);
				}
			}
			
			ret = ""+ps.executeUpdate();
		}catch( SQLException e ){
			System.err.println(Util.getStackTraceInformation(e,false));
			ret = Util.getStackTraceInformation(e,false);
			//throw new Exception(e);
		}finally{
			close(ps);
		}
		return ret;
	}

	public String insert( String dataSourceName, String tableName, Map map ) throws Exception {
		Connection conn = null;
		try{
			conn = getConnection(dataSourceName,defaultDataSource,mapConnManual);
			return insert(conn, tableName, map);
		}catch( Exception e ){
			throw e;
		}finally{
			close(conn);
		}
	}
	public String insert( Connection conn, String tableName, Map map ) throws Exception {
		PreparedStatement ps = null;
		String ret = "0";
		StringBuffer sql = new StringBuffer();
		try{
			sql.append("insert into ").append(tableName).append(" ");
			List<String> listColumn = listColumns(conn, tableName);
			Iterator it = map.keySet().iterator();
			List<String> listKey = new ArrayList<String>();
			while( it.hasNext() ){
				String keyCol = (String)it.next();
				if( listColumn.contains(keyCol) )
					listKey.add(keyCol);

			}
			// Tratamento de campos
			sql.append("(");
			for( int i = 0; i < listKey.size(); i++ ){
				if( i > 0 )
					sql.append(",");
				sql.append(listKey.get(i));
			}
			sql.append(") ");
			
			sql.append("values ");
			
			sql.append("(");
			for( int i = 0; i < listKey.size(); i++ ){
				if( i > 0 )
					sql.append(",");
				sql.append("?");
			}
			sql.append(") ");
			
			ps = conn.prepareStatement(sql.toString());

			int idx = 1;
			for( int i = 0; i < listKey.size(); i++ ){
				ps.setObject(idx++,map.get(listKey.get(i)));
			}
			
			ret = ""+ps.executeUpdate();
		}catch( SQLException e ){
			System.err.println(Util.getStackTraceInformation(e,false));
			ret = Util.getStackTraceInformation(e,false);
			System.err.println("SQL: "+sql.toString());
			System.err.println("Map: "+map.toString());
			//throw new Exception(e);
		}finally{
			close(ps);
		}
		return ret;
	}
	
	public List<String[]> listAllBySQL( String dataSourceName, String sql, Boolean insertHeader ) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		List<String[]> ret = null;
		try{
			conn = getConnection(dataSourceName,defaultDataSource,mapConnManual);
			
			ps = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);

			ResultSet rs = ps.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			String[] cols = new String[meta.getColumnCount()];
			for( int i = 0; i < meta.getColumnCount(); i++ ){
				cols[i] = meta.getColumnName(i+1);
			}
			if( insertHeader ){
				if( ret == null )
					ret = new ArrayList<String[]>();
				ret.add(cols);
			}
			while(rs.next()){
				if( ret == null ){
					ret = new ArrayList<String[]>();
				}
				String[] line = new String[cols.length];
				for( int i = 0; i < cols.length; i++ ){
					line[i] = rs.getString(cols[i]);
				}
				ret.add(line);
			}
			rs.close();
			return ret;
		}catch( Exception e ){
			System.err.println(Util.getStackTraceInformation(e,false));
			throw new Exception(e);
		}finally{
			close(conn,ps);
		}
	}

	public List<String[]> listAllByTable( String dataSourceName, String tableName, Boolean insertHeader ) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(tableName);
		return listAllBySQL(dataSourceName,sql.toString(),insertHeader);
	}
	
	public List<String> listColumns(String dataSourceName, String tableName){
		Connection conn = null;
		List<String> ret = new ArrayList();
		try{
			conn = getConnection(dataSourceName,defaultDataSource,mapConnManual);
			ret = listColumns(conn, tableName);
		}catch( Exception e ){
			e.printStackTrace();
		}finally{
			close(conn);
		}
		return ret;
	}
	public List<String> listColumns(Connection conn, String tableName){
		PreparedStatement ps = null;
		List<String> ret = new ArrayList();
		try{
			ps = conn.prepareStatement("select * from "+tableName+" where 1 <> 1");
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			for( int i = 0; i < meta.getColumnCount(); i++ ){
				ret.add(meta.getColumnLabel(i+1));
			}
			rs.close();
		}catch( Exception e ){
			e.printStackTrace();
		}finally{
			close(ps);
		}
		return ret;
	}

	public String update( String dataSourceName, String tableName, List<String> primaryKeyCols, Map map ) throws Exception {
		Connection conn = null;
		String ret = null;
		try{
			conn = getConnection(dataSourceName,defaultDataSource,mapConnManual);
			ret = update(conn, tableName, primaryKeyCols, map);
		}catch( Exception e ){
			e.printStackTrace();
		}finally{
			close(conn);
		}
		return ret;
	}
	public String update( Connection conn, String tableName, List<String> primaryKeyCols, Map map ) throws Exception {
		PreparedStatement ps = null;
		String ret = "0";
		StringBuffer sql = new StringBuffer();
		try{
			sql.append("update ").append(tableName).append(" set ");
			List<String> listColumn = listColumns(conn, tableName);
			Iterator it = map.keySet().iterator();
			List<String> listKey = new ArrayList<String>();
			while( it.hasNext() ){
				String keyCol = (String)it.next();
				if( listColumn.contains(keyCol) )
					listKey.add(keyCol);
			}

			// Tratamento de campos set
			int count = 0;
			for( int i = 0; i < listKey.size(); i++ ){
				if( !primaryKeyCols.contains(listKey.get(i)) ){
					if( count > 0 )
						sql.append(",");
					sql.append(listKey.get(i)).append(" = ? ");
					count++;
				}
			}
			
			sql.append(" where 1=1 ");

			// Tratamento de campos PK
			for( int i = 0; i < listKey.size(); i++ ){
				if( primaryKeyCols.contains(listKey.get(i)) ){
					sql.append(" and ").append(listKey.get(i)).append(" = ? ");
				}
			}
			ps = conn.prepareStatement(sql.toString());

			int idx = 1;
			for( int i = 0; i < listKey.size(); i++ ){
				if( !primaryKeyCols.contains(listKey.get(i)) ){
					ps.setObject(idx++,map.get(listKey.get(i)));
				}
			}
			for( int i = 0; i < listKey.size(); i++ ){
				if( primaryKeyCols.contains(listKey.get(i)) ){
					ps.setObject(idx++,map.get(listKey.get(i)));
				}
			}
			
			ret = ""+ps.executeUpdate();
		}catch( SQLException e ){
			System.err.println(Util.getStackTraceInformation(e,false));
			ret = Util.getStackTraceInformation(e,false);
			e.printStackTrace();
			System.err.println("SQL: "+sql.toString());
			System.err.println("Map: "+map.toString());
			//throw new Exception(e);
		}finally{
			close(ps);
		}
		return ret;
	}

}

