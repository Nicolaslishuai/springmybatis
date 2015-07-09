package lishuai.common.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * 
 * <p> Title: 数据库连接工具类 </p>
 * <p> Description: </p>
 * @作者 TangChengLin
 * @创建时间 2014-5-14
 * @版本 1.00
 * @修改记录
 * <pre>
 * 版本   修改人    修改时间    修改内容描述
 * ----------------------------------------
 * 
 * </pre>
 */
public class DataSourceUtils {
	private static Logger logger = Logger.getLogger(DataSourceUtils.class);
	private static DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * 
	 * getConnection
	 * 功能描述：获取数据库连接
	 * 逻辑描述: 
	 * @author TangChengLin
	 * @param 无
	 * @return Connection  数据库连接
	 * @throws 无
	 * @since Ver 1.00
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);//如果为false时会无法提交，需要手动提交
		} catch (SQLException e) {
			logger.error("数据库获取连接出错：" + e.getMessage());
		}
		return connection;
	}

	/**
	 * 
	 * closeConnection
	 * 功能描述：关闭数据库连接
	 * 逻辑描述: 
	 * @author TangChengLin
	 * @param Connection  数据库连接
	 * @return 无
	 * @throws 无
	 * @since Ver 1.00
	 */
	public static void closeConnection(Connection connection) {
		try {
			if (connection == null || connection.isClosed()) {
				return;
			}
			connection.close();
			connection = null;
		} catch (SQLException e) {
			logger.error("关闭数据库连接出现错误：" + e.getMessage());
		}
	}

	/**
	 * 
	 * closeStatement
	 * 功能描述：关闭Statement
	 * 逻辑描述: 
	 * @author TangChengLin
	 * @param Statement  statement对象
	 * @return 无
	 * @throws 无
	 * @since Ver 1.00
	 */
	public static void closeStatement(Statement statement) {
		if (statement == null) {
			return;
		} else {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error("关闭statement出错：" + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * closeResultSet
	 * 功能描述：关闭ResultSet
	 * 逻辑描述: 
	 * @author TangChengLin
	 * @param ResultSet  resultSet对象
	 * @return 无
	 * @throws 无
	 * @since Ver 1.00
	 */
	public static void closeResultSet(ResultSet resultSet) {
		if (resultSet == null) {
			return;
		} else {
			try {
				resultSet.close();
			} catch (SQLException e) {
				logger.error("关闭ResultSet出错：" + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * closePreparedStatement
	 * 功能描述：关闭PreparedStatement
	 * 逻辑描述: 
	 * @author TangChengLin
	 * @param PreparedStatement  preparedStatement对象
	 * @return 无
	 * @throws 无
	 * @since Ver 1.00
	 */
	public static void closePreparedStatement(
			PreparedStatement preparedStatement) {
		if (preparedStatement == null) {
			return;
		} else {
			try {
				preparedStatement.close();
			} catch (SQLException e) {
				logger.error("关闭PreparedStatement出错：" + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * conCommit
	 * 功能描述：提交连接
	 * 逻辑描述: 
	 * @author TangChengLin
	 * @param Connection  数据库连接
	 * @return 无
	 * @throws 无
	 * @since Ver 1.00
	 */
	public static void conCommit(Connection connection) {
		try {
			if (connection == null || connection.isClosed()) {
				return;
			} else {
				connection.commit();
			}
		} catch (SQLException e) {
			logger.error("数据库提交时出错：" + e.getMessage());
		}
	}

	/**
	 * 
	 * conRollback
	 * 功能描述：如果出现异常后需要回滚操作
	 * 逻辑描述: 
	 * @author TangChengLin
	 * @param Connection  数据库连接
	 * @return 无
	 * @throws 无
	 * @since Ver 1.00
	 */
	public static void conRollback(Connection connection) {
		try {
			if (connection == null || connection.isClosed()) {
				return;
			} else {
				connection.rollback();
			}
		} catch (SQLException e) {
			logger.error("数据回滚失败：" + e.getMessage());
		}
	}

	/**
	 * 
	 * closeAll
	 * 功能描述：关闭所有连接
	 * 逻辑描述: 
	 * @author TangChengLin
	 * @param Connection  数据库连接
	 * @param Statement statement对象
	 * @param ResultSet resultSet对象
	 * @return 无
	 * @throws 无
	 * @since Ver 1.00
	 */
	public static void closeAll(Connection connection, Statement st,
			ResultSet rs) {
		if (null != connection) {
			closeConnection(connection);
		}
		if (null != st) {
			closeStatement(st);
		}
		if (null != rs) {
			closeResultSet(rs);
		}
	}
}