package lishuai.common.db;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SqlCommand {

	// T0_DO 写日志位置

	/**
	 * 执行sql语句,返回执行结果影响的记录行数
	 * 
	 * @param sql
	 *            无返回结果的sql语句
	 * @return 影响记录行数 返回-1：数据库操作失败
	 */
	public static int ExecuteNoQuery(String sql) {
		int count = 0;
		Connection con = null;
		Statement st = null;
		try {
			con = DataSourceUtils.getConnection();
			st = con.createStatement();
			// 执行sql语句返回影响的数据库记录行数
			count = st.executeUpdate(sql);
			if (count != -1) {
				con.commit();
			} else {
				con.rollback();
			}
		} catch (SQLException e) {
			count = -1;
			// //.WriteErrLog("ExecuteNoQuery(String sql)执行sql语句失败::" + sql
			// + "异常:" + e.getMessage());
			DataSourceUtils.conRollback(con);
		} finally {
			DataSourceUtils.closeStatement(st);
			DataSourceUtils.closeConnection(con);
		}
		return count;
	}

	/**
	 * 执行sql语句,返回执行结果影响的记录行数
	 * 
	 * @param con
	 *            数据库连接
	 * @param sql
	 *            无返回结果的sql语句
	 * @return 影响记录行数 返回-1：数据库操作失败
	 */
	public static int ExecuteNoQuery(Connection con, String sql) {
		if (con == null) {
			return -1;
		}
		int count = 0;
		Statement st = null;
		try {
			st = con.createStatement();
			// 执行sql语句返回影响的数据库记录行数
			count = st.executeUpdate(sql);
		} catch (SQLException e) {
			count = -1;
			//
			// .WriteErrLog("ExecuteNoQuery(Connection con,String sql)执行sql语句失败::"
			// + sql + "异常:" + e.getMessage());
		} finally {
			DataSourceUtils.closeStatement(st);
		}
		return count;
	}
	
	/**
	 * 执行sql语句,返回执行结果影响的记录行数
	 * 
	 * @param con
	 *            数据库连接
	 * @param sql
	 *            无返回结果的sql语句
	 * @return 影响记录行数 返回-1：数据库操作失败
	 */
	public static int ExecuteNoQuery(Connection con, String sql,String[] parms) {
		if (con == null) {
			return -1;
		}
		int count = 0;
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			for(int i=1;i<=parms.length;i++){
			ps.setString(i,parms[i-1]);
			}
			// 执行sql语句返回影响的数据库记录行数
			count = ps.executeUpdate();
		} catch (SQLException e) {
			count = -1;
			//
			// .WriteErrLog("ExecuteNoQuery(Connection con,String sql)执行sql语句失败::"
			// + sql + "异常:" + e.getMessage());
		} finally {
			DataSourceUtils.closeStatement(ps);
		}
		return count;
	}

	/**
	 * 事务提交sql语句串
	 * 
	 * @param sql
	 *            sql语句串
	 * @return 返回false:执行失败；true:成功
	 * @throws SQLException
	 */
	public static boolean ExecuteNoQuery(ArrayList<String> sql) {
		boolean result = false;
		Connection con = null;
		Statement st = null;
		try {
			con = DataSourceUtils.getConnection();
			st = con.createStatement();
			
			Iterator<String> iter = sql.iterator();
			while (iter.hasNext()) {
				String tmp = iter.next().toString();
				int count = st.executeUpdate(tmp);
				st.executeBatch();
				if (count == -1) {
					// .WriteErrLog("执行sql语句出错:"+tmp);
				}
			}
			DataSourceUtils.conCommit(con);
			result = true;
		} catch (SQLException e) {
			result = false;
			//
			// .WriteErrLog("ExecuteNoQuery(ArrayList<String> sql)执行sql语句失败,条数::"
			// + sql.size() + "异常：" + e.getMessage());
			DataSourceUtils.conRollback(con);
		} finally {
			DataSourceUtils.closeStatement(st);
			DataSourceUtils.closeConnection(con);
		}
		return result;
	}

	/**
	 * 事务提交sql语句串
	 * 
	 * @param sql
	 *            sql语句串
	 * @return 返回false:执行失败；true:成功
	 * @throws SQLException
	 */
	public static boolean ExecuteNoQuery(Connection con, ArrayList<String> sql) {
		boolean result = false;
		Statement st = null;
		try {
			st = con.createStatement();
			Iterator<String> iter = sql.iterator();
			while (iter.hasNext()) {
				String tmp = iter.next().toString();
				int count = st.executeUpdate(tmp);
				if (count == -1) {
					// .WriteErrLog("执行sql语句出错:"+tmp);
					// 只要有一条记录执行失败，则返回供上一层回滚
					result = false;
				}
			}
			result = true;
		} catch (SQLException e) {
			result = false;
			//
			// .WriteErrLog("ExecuteNoQuery(ArrayList<String> sql)执行sql语句失败,条数::"
			// + sql.size() + "异常：" + e.getMessage());
		} finally {
			DataSourceUtils.closeStatement(st);
		}
		return result;
	}

	/**
	 * 执行sql语句,返回ArrayList结果集
	 * 
	 * @param sql
	 *            sql语句
	 * @return 返回null：操作失败
	 */
	public static ArrayList<Map<String, String>> ExecuteQuery(String sql) {
		ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 执行查询语句返回结果集
			con = DataSourceUtils.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			/* 循环处理JDBC结果集的数据 */
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				for (int i = 1; i <= count; i++) {
					String colName = rsmd.getColumnName(i).toLowerCase();
					map.put(colName,
							rs.getString(colName) == null ? "" : rs
									.getString(colName));
				}
				data.add(map);
			}
		} catch (SQLException e) {
			data = null;
			e.printStackTrace();
			// //.WriteErrLog("ExecuteQuery(String sql)执行sql语句失败::" + sql
			// + "异常：" + e.getMessage());
		} finally {
			DataSourceUtils.closeResultSet(rs);
			DataSourceUtils.closeStatement(st);
			DataSourceUtils.closeConnection(con);
		}
		return data;
	}

	/**
	 * 执行sql语句,返回ArrayList结果集
	 * 
	 * @param sql
	 *            sql语句 con 数据库连接
	 * @return 返回null：操作失败
	 */
	public static ArrayList<Map<String, String>> ExecuteQuery(Connection con,
			String sql) {
		ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Statement st = null;
		ResultSet rs = null;
		try {
			// 执行查询语句返回结果集
			// con = DataSourceUtils.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			/* 循环处理JDBC结果集的数据 */
			while (rs.next()) {
				Map<String, String> map = new HashMap<String, String>();
				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				for (int i = 1; i <= count; i++) {
					String colName = rsmd.getColumnName(i).toLowerCase();
					map.put(colName,
							rs.getString(colName) == null ? "" : rs
									.getString(colName));
				}
				data.add(map);
			}
		} catch (SQLException e) {
			data = null;
			// //.WriteErrLog("ExecuteQuery(String sql)执行sql语句失败::" + sql
			// + "异常：" + e.getMessage());
		} finally {
			DataSourceUtils.closeResultSet(rs);
			DataSourceUtils.closeStatement(st);
		}
		return data;
	}

	public static String[] ExecuteQueryMap(String sql) {
		List<String> result = new ArrayList<String>();
		String[] strArray = null;
		StringBuffer buffer = null;
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 执行查询语句返回结果集
			con = DataSourceUtils.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			/* 循环处理JDBC结果集的数据 */
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				buffer = new StringBuffer();
				for (int i = 1; i <= count; i++) {
					buffer.append((rs.getString(i) == null ? "" : rs
							.getString(i)) + "|");
				}
				result.add(buffer.toString().substring(0, buffer.length() - 1));
			}
			strArray = result.toArray(new String[0]);
		} catch (SQLException e) {
			result = null;
			// //.WriteErrLog("ExecuteQuery(String sql)执行sql语句失败::" + sql
			// + "异常：" + e.getMessage());
		} finally {
			DataSourceUtils.closeResultSet(rs);
			DataSourceUtils.closeStatement(st);
			DataSourceUtils.closeConnection(con);
		}
		return strArray;
	}

	public static String[] ExecuteQueryMap(Connection con, String sql) {
		List<String> result = new ArrayList<String>();
		String[] strArray = null;
		StringBuffer buffer = null;
		Statement st = null;
		ResultSet rs = null;
		try {
			// 执行查询语句返回结果集
			// con = DataSourceUtils.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			/* 循环处理JDBC结果集的数据 */
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				buffer = new StringBuffer();
				for (int i = 1; i <= count; i++) {
					buffer.append((rs.getString(i) == null ? "" : rs
							.getString(i)) + "|");
				}
				result.add(buffer.toString().substring(0, buffer.length() - 1));
			}
			strArray = new String[result.size()];
			int j = 0;
			for (String str : result) {
				strArray[j++] = str;
			}
		} catch (SQLException e) {
			result = null;
			// //.WriteErrLog("ExecuteQuery(String sql)执行sql语句失败::" + sql
			// + "异常：" + e.getMessage());
		} finally {
			DataSourceUtils.closeResultSet(rs);
			DataSourceUtils.closeStatement(st);
		}
		return strArray;
	}

	/**
	 * 填充或者更新表中Blob字段 如果想把Blob字段置空，data参数为 null 即可
	 * 
	 * @param tableName
	 *            表名
	 * @param columnName
	 *            Blob字段的名称
	 * @param where_clause
	 *            找到记录的依据 例如 " where pr_id = 123456789"
	 * @param data
	 *            填充数据
	 * @return true:成功 false:失败
	 * @author liye
	 */
	public static boolean setBlob(String tableName, String columnName,
			String where_clause, byte[] data) {
		boolean isSuccess = false;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuffer insertBlob = new StringBuffer();
		insertBlob.append(" select ");
		insertBlob.append(columnName);
		insertBlob.append(" from ");
		insertBlob.append(tableName);
		insertBlob.append(" where 1=1 ");
		if (!"".equals(where_clause)) {
			insertBlob.append(" and " + where_clause);
		}
		insertBlob.append(" for update");
		StringBuffer flushBloB = new StringBuffer();
		flushBloB.append(" update ");
		flushBloB.append(tableName);
		flushBloB.append(" set ");
		flushBloB.append(columnName);
		flushBloB.append(" = EMPTY_BLOB() ");
		flushBloB.append(" where 1=1 ");
		if (!"".equals(where_clause)) {
			flushBloB.append(" and " + where_clause);
		}
		try {
			con = DataSourceUtils.getConnection();
			ps = con.prepareStatement(flushBloB.toString());
			ps.executeUpdate();
			ps.clearParameters();
			if (data != null && data.length != 0) {
				ps = con.prepareStatement(insertBlob.toString());
				rs = ps.executeQuery();
				if (rs.next()) {
					Blob blob = rs.getBlob(1);
					blob.setBytes(1, data);
				}
			}
			DataSourceUtils.conCommit(con);
			isSuccess = true;
		} catch (SQLException e1) {
			isSuccess = false;
			// //
			// .WriteErrLog("setBlob(String tableName,String columnName,String where_clause,byte[] data)执行sql语句出错 ： "
			// + flushBloB.toString() + "异常：" + e1.getMessage());
			DataSourceUtils.conRollback(con);
		} finally {
			DataSourceUtils.closeResultSet(rs);
			DataSourceUtils.closePreparedStatement(ps);
			DataSourceUtils.closeConnection(con);
		}
		return isSuccess;
	}

	/**
	 * 填充或者更新表中Blob字段 如果想把Blob字段置空，data参数为 null 即可
	 * 
	 * @param con
	 *            数据库连接
	 * @param tableName
	 *            表名
	 * @param columnName
	 *            Blob字段的名称
	 * @param where_clause
	 *            找到记录的依据 例如 " where pr_id = 123456789"
	 * @param data
	 *            填充数据
	 * @return true:成功 false:失败
	 * @author liye
	 */
	public static boolean setBlob(Connection con, String tableName,
			String columnName, String where_clause, byte[] data) {
		if (con == null) {
			return false;
		}
		boolean isSuccess = false;
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuffer insertBlob = new StringBuffer();
		insertBlob.append(" select ");
		insertBlob.append(columnName);
		insertBlob.append(" from ");
		insertBlob.append(tableName);
		insertBlob.append(" where 1=1 ");
		if (!"".equals(where_clause)) {
			insertBlob.append(" and " + where_clause);
		}
		insertBlob.append(" for update");
		StringBuffer flushBloB = new StringBuffer();
		flushBloB.append(" update ");
		flushBloB.append(tableName);
		flushBloB.append(" set ");
		flushBloB.append(columnName);
		flushBloB.append(" = EMPTY_BLOB() ");
		flushBloB.append(" where 1=1 ");
		if (!"".equals(where_clause)) {
			flushBloB.append(" and " + where_clause);
		}
		try {
			ps = con.prepareStatement(flushBloB.toString());
			ps.executeUpdate();
			ps.clearParameters();
			if (data != null && data.length != 0) {
				ps = con.prepareStatement(insertBlob.toString());
				rs = ps.executeQuery();
				if (rs.next()) {
					Blob blob = rs.getBlob(1);
					blob.setBytes(1, data);
				}
			}
			isSuccess = true;
		} catch (SQLException e1) {
			isSuccess = false;
			// //
			// .WriteErrLog("setBlob(Connection con,String tableName,String columnName,String where_clause,byte[] data)执行sql语句出错 : "
			// + flushBloB.toString() + "异常：" + e1.getMessage());
		} finally {
			DataSourceUtils.closeResultSet(rs);
			DataSourceUtils.closePreparedStatement(ps);
		}
		return isSuccess;
	}

	/**
	 * 获得数据库表中blob字段数据
	 * 
	 * @param tableName
	 *            表名
	 * @param columnName
	 *            Blob字段的名称
	 * @param where_clause
	 *            找到记录的依据 例如 " where pr_id = 123456789"
	 * @return byte[] 数据 如果没数据 返回null
	 * @author liye
	 */
	public static byte[] getBlob(String tableName, String columnName,
			String where_clause) {
		byte[] data = null;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuffer insertBlob = new StringBuffer();
		insertBlob.append(" select ");
		insertBlob.append(columnName);
		insertBlob.append(" from ");
		insertBlob.append(tableName);
		insertBlob.append(" where 1=1 ");
		if (!"".equals(where_clause)) {
			insertBlob.append("and " + where_clause);
		}
		try {
			con = DataSourceUtils.getConnection();
			ps = con.prepareStatement(insertBlob.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				Blob blob = rs.getBlob(1);
				if (blob != null) {
					int length = (int) blob.length();
					data = blob.getBytes(1, length);
				}
			}
		} catch (SQLException e1) {
			// //
			// .WriteErrLog("getBlob(String tableName,String columnName,String where_clause)执行sql语句出错： "
			// + insertBlob.toString() + "异常：" + e1.getMessage());
		} finally {
			DataSourceUtils.closeResultSet(rs);
			DataSourceUtils.closePreparedStatement(ps);
			DataSourceUtils.closeConnection(con);
		}
		return data;
	}

	/**
	 * 获得数据库表中blob字段数据
	 * 
	 * @param con
	 *            数据库连接
	 * @param tableName
	 *            表名
	 * @param columnName
	 *            Blob字段的名称
	 * @param where_clause
	 *            找到记录的依据 例如 " where pr_id = 123456789"
	 * @return byte[] 数据 如果没数据 返回null
	 * @author liye
	 */
	public static byte[] getBlob(Connection con, String tableName,
			String columnName, String where_clause) {
		byte[] data = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		StringBuffer insertBlob = new StringBuffer();
		insertBlob.append(" select ");
		insertBlob.append(columnName);
		insertBlob.append(" from ");
		insertBlob.append(tableName);
		insertBlob.append(" where 1=1 ");
		if (!"".equals(where_clause)) {
			insertBlob.append("and " + where_clause);
		}
		try {
			// con = DataSourceUtils.getConnection();
			ps = con.prepareStatement(insertBlob.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				Blob blob = rs.getBlob(1);
				if (blob != null) {
					int length = (int) blob.length();
					data = blob.getBytes(1, length);
				}
			}
		} catch (SQLException e1) {
			// //
			// .WriteErrLog("getBlob(String tableName,String columnName,String where_clause)执行sql语句出错： "
			// + insertBlob.toString() + "异常：" + e1.getMessage());
		} finally {
			DataSourceUtils.closeResultSet(rs);
			DataSourceUtils.closePreparedStatement(ps);
		}
		return data;
	}

	/**
	 * 
	 * @param tableName
	 * @param columnName
	 * @param where_clause
	 * @return Dec 26, 2011 java.sql.Blob zhaojf
	 * @desc 获取blob类型的数据
	 */
	public static java.sql.Blob getBlob2(String tableName, String columnName,
			String where_clause) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		Blob blob = null;
		StringBuffer selectBlob = new StringBuffer();
		selectBlob.append(" select ");
		selectBlob.append(columnName);
		selectBlob.append(" from ");
		selectBlob.append(tableName);
		selectBlob.append(" " + where_clause);
		try {
			con = DataSourceUtils.getConnection();
			ps = con.prepareStatement(selectBlob.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				blob = rs.getBlob(1);
			}
		} catch (SQLException e1) {
			//
			// .WriteErrLog("getBlob2(String tableName,String columnName,String where_clause)执行sql语句出错： "
			// + selectBlob.toString() + "异常：" + e1.getMessage());
		} finally {
			DataSourceUtils.closeResultSet(rs);
			DataSourceUtils.closePreparedStatement(ps);
			DataSourceUtils.closeConnection(con);
		}
		return blob;
	}

	/**
	 * 
	 * @param tableName
	 * @param columnName
	 * @param where_clause
	 * @return Dec 26, 2011 java.sql.Blob zhaojf
	 * @desc 获取blob类型的数据
	 */
	public static java.sql.Blob getBlob2(Connection con, String tableName,
			String columnName, String where_clause) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		Blob blob = null;
		StringBuffer selectBlob = new StringBuffer();
		selectBlob.append(" select ");
		selectBlob.append(columnName);
		selectBlob.append(" from ");
		selectBlob.append(tableName);
		selectBlob.append(" " + where_clause);
		try {
			ps = con.prepareStatement(selectBlob.toString());
			rs = ps.executeQuery();
			if (rs.next()) {
				blob = rs.getBlob(1);
			}
		} catch (SQLException e1) {
			// .WriteErrLog("getBlob2(String tableName,String columnName,String where_clause)执行sql语句出错： "
			// + selectBlob.toString() + "异常：" + e1.getMessage());
		} finally {
			DataSourceUtils.closeResultSet(rs);
			DataSourceUtils.closePreparedStatement(ps);
		}
		return blob;
	}

	/**
	 * 执行sql语句,返回ArrayList结果集
	 * 
	 * @param sql语句
	 * @return 返回null：操作失败 author chenming
	 */
	public static ArrayList<String[]> ExecuteWithResult(String sql) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		try {
			// 执行查询语句返回结果集
			con = DataSourceUtils.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			/* 循环处理JDBC结果集的数据 */
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				String[] rowdata = new String[count];
				for (int i = 1; i <= count; i++) {
					rowdata[i - 1] = rs.getString(i) == null ? "" : rs
							.getString(i);
				}
				data.add(rowdata);
			}
		} catch (SQLException e) {
			data = null;
			// .WriteErrLog("ExecuteWithResult(String sql)执行sql语句失败::"
			// + sql + "异常：" + e.getMessage());
		} finally {
			DataSourceUtils.closeResultSet(rs);
			DataSourceUtils.closeStatement(st);
			DataSourceUtils.closeConnection(con);
		}
		return data;
	}

	/**
	 * 执行sql语句,返回ArrayList结果集
	 * 
	 * @param sql语句
	 * @param con
	 *            数据库链接
	 * @return 返回null：操作失败 author chenming
	 */
	public static ArrayList<String[]> ExecuteWithResult(Connection con,
			String sql) {
		ArrayList<String[]> data = new ArrayList<String[]>();
		Statement st = null;
		ResultSet rs = null;
		try {
			// 执行查询语句返回结果集
			// con = DataSourceUtils.getConnection();
			st = con.createStatement();
			rs = st.executeQuery(sql);
			/* 循环处理JDBC结果集的数据 */
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				String[] rowdata = new String[count];
				for (int i = 1; i <= count; i++) {
					rowdata[i - 1] = rs.getString(i) == null ? "" : rs
							.getString(i);
				}
				data.add(rowdata);
			}
		} catch (SQLException e) {
			data = null;
			// .WriteErrLog("ExecuteWithResult(Connection con,String sql)执行sql语句失败::"
			// + sql + "异常：" + e.getMessage());
		} finally {
			DataSourceUtils.closeResultSet(rs);
			DataSourceUtils.closeStatement(st);
		}
		return data;
	}

	/**
	 * 执行存储过程
	 * 
	 * @param connection
	 *            数据库连接
	 * @param procedureName
	 *            存储过程名称
	 * @param args
	 *            参数列表[可以为任何数据类型]
	 * @return true 执行成功,false 执行失败
	 * @author houwenhua
	 */
	public static boolean executeProcudure(Connection connection,
			String procedureName, String... args) {
		// .WriteDebugLog("开始执行存储过程...");
		boolean result = false;
		try {
			// .WriteDebugLog("拼凑sql语句");
			int length = args.length;
			StringBuffer sql = new StringBuffer();
			sql.append("{call " + procedureName);
			if (length > 0) {
				sql.append("(");
				for (int i = 0; i < length; i++) {
					if (i != (length - 1)) {
						sql.append("?,");
					} else {
						sql.append("?");
					}
				}
				sql.append(")");
			}
			sql.append("}");
			// .WriteDebugLog("sql语句为：" + sql);
			CallableStatement callStatement = connection.prepareCall(sql
					.toString());
			for (int i = 0; i < length; i++) {
				callStatement.setObject(i + 1, args[i]);
			}
			callStatement.execute();
			callStatement.getResultSet();
			result = true;
		} catch (Exception ex) {
			result = false;
			// .WriteErrLog("执行存储过程发生异常：" + ex.getMessage());
		}
		return result;
	}

	/**
	 * 执行存储过程[可返回结果集，如果没有返回结果集，则返回空]
	 * 
	 * @param connection
	 *            数据库连接
	 * @param procedureName
	 *            存储过程名称
	 * @param outParameterIndex
	 *            输出参数在存储过程中的索引号
	 * @param args
	 *            参数列表[可以为任务数据库类型]
	 * @return 非null 执行成功并返回存储过程中的输出参数,null 执行失败
	 * @author houwenhua
	 */
	public static String[] executeProcudureWithResult(Connection connection,
			String procedureName, int outParameterIndex, String... args) {
		// .WriteDebugLog("开始执行存储过程...");
		String[] strArray = null;
		StringBuffer buffer = null;
		List<String> result = new ArrayList<String>();
		ResultSet rs = null;
		try {
			// .WriteDebugLog("拼凑sql语句");
			StringBuffer sql = new StringBuffer();
			sql.append("{call " + procedureName + "(");
			if (args != null && args.length > 0) {
				int length = args.length;
				for (int i = 0; i < length; i++) {
					sql.append("?,");
				}
			}
			sql.append("?)}");
			// .WriteDebugLog("sql语句为：" + sql);
			CallableStatement callStatement = connection.prepareCall(sql
					.toString());
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					if ((i + 1) != outParameterIndex)
						callStatement.setString(i + 1, args[i]);
				}
			}
/*			callStatement.registerOutParameter(outParameterIndex,
					oracle.jdbc.OracleTypes.CURSOR);*/
			callStatement.execute();
			rs = (ResultSet) callStatement.getObject(outParameterIndex);
			while (rs.next()) {
				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				buffer = new StringBuffer();
				for (int i = 1; i <= count; i++) {
					buffer.append((rs.getString(i) == null ? "" : rs
							.getString(i)) + "|");
				}
				result.add(buffer.toString().substring(0, buffer.length() - 1));
			}
			strArray = new String[result.size()];
			int j = 0;
			for (String str : result) {
				strArray[j++] = str;
			}
		} catch (Exception ex) {
			// .WriteErrLog("执行存储过程发生异常：" + ex.getMessage());
		}
		return strArray;
	}
	
	/**
	 * 事务提交sql语句串
	 * 
	 * @param sql
	 *            sql语句串
	 * @return 返回false:执行失败；true:成功
	 * @throws SQLException
	 */
	public static boolean BatchExecute(List<String> sqllist,List<String[]> pram) {
		boolean result = false;
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DataSourceUtils.getConnection();
			for(int i=0;i<sqllist.size();i++){
			System.out.println(sqllist.get(i));
			ps = con.prepareStatement(sqllist.get(i));
			String[] str=pram.get(i);
			for(int j=1;j<=str.length;j++){
				ps.setString(j, str[j-1]);
				System.out.println(str[j-1]);
			}
			ps.executeUpdate();
			}
			DataSourceUtils.conCommit(con);
			result = true;
		} catch (SQLException e) {
			result = false;
			//
			// .WriteErrLog("ExecuteNoQuery(ArrayList<String> sql)执行sql语句失败,条数::"
			// + sql.size() + "异常：" + e.getMessage());
			DataSourceUtils.conRollback(con);
		} finally {
			DataSourceUtils.closePreparedStatement(ps);
			DataSourceUtils.closeConnection(con);
		}
		return result;
	}
}