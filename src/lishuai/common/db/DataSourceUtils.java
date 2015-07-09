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
 * <p> Title: ���ݿ����ӹ����� </p>
 * <p> Description: </p>
 * @���� TangChengLin
 * @����ʱ�� 2014-5-14
 * @�汾 1.00
 * @�޸ļ�¼
 * <pre>
 * �汾   �޸���    �޸�ʱ��    �޸���������
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
	 * ������������ȡ���ݿ�����
	 * �߼�����: 
	 * @author TangChengLin
	 * @param ��
	 * @return Connection  ���ݿ�����
	 * @throws ��
	 * @since Ver 1.00
	 */
	public static Connection getConnection() {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);//���Ϊfalseʱ���޷��ύ����Ҫ�ֶ��ύ
		} catch (SQLException e) {
			logger.error("���ݿ��ȡ���ӳ���" + e.getMessage());
		}
		return connection;
	}

	/**
	 * 
	 * closeConnection
	 * �����������ر����ݿ�����
	 * �߼�����: 
	 * @author TangChengLin
	 * @param Connection  ���ݿ�����
	 * @return ��
	 * @throws ��
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
			logger.error("�ر����ݿ����ӳ��ִ���" + e.getMessage());
		}
	}

	/**
	 * 
	 * closeStatement
	 * �����������ر�Statement
	 * �߼�����: 
	 * @author TangChengLin
	 * @param Statement  statement����
	 * @return ��
	 * @throws ��
	 * @since Ver 1.00
	 */
	public static void closeStatement(Statement statement) {
		if (statement == null) {
			return;
		} else {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.error("�ر�statement����" + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * closeResultSet
	 * �����������ر�ResultSet
	 * �߼�����: 
	 * @author TangChengLin
	 * @param ResultSet  resultSet����
	 * @return ��
	 * @throws ��
	 * @since Ver 1.00
	 */
	public static void closeResultSet(ResultSet resultSet) {
		if (resultSet == null) {
			return;
		} else {
			try {
				resultSet.close();
			} catch (SQLException e) {
				logger.error("�ر�ResultSet����" + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * closePreparedStatement
	 * �����������ر�PreparedStatement
	 * �߼�����: 
	 * @author TangChengLin
	 * @param PreparedStatement  preparedStatement����
	 * @return ��
	 * @throws ��
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
				logger.error("�ر�PreparedStatement����" + e.getMessage());
			}
		}
	}

	/**
	 * 
	 * conCommit
	 * �����������ύ����
	 * �߼�����: 
	 * @author TangChengLin
	 * @param Connection  ���ݿ�����
	 * @return ��
	 * @throws ��
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
			logger.error("���ݿ��ύʱ����" + e.getMessage());
		}
	}

	/**
	 * 
	 * conRollback
	 * ������������������쳣����Ҫ�ع�����
	 * �߼�����: 
	 * @author TangChengLin
	 * @param Connection  ���ݿ�����
	 * @return ��
	 * @throws ��
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
			logger.error("���ݻع�ʧ�ܣ�" + e.getMessage());
		}
	}

	/**
	 * 
	 * closeAll
	 * �����������ر���������
	 * �߼�����: 
	 * @author TangChengLin
	 * @param Connection  ���ݿ�����
	 * @param Statement statement����
	 * @param ResultSet resultSet����
	 * @return ��
	 * @throws ��
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