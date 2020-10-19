package tjoker.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.util.JdbcUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @program: Ajax
 * @description: 数据库连接工具类
 * @author: 十字街头的守候
 * @create: 2019-10-17 18:41
 **/
public class JDBCUtils {
    private static DataSource ds;
    private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

    static {
        Properties pro=new Properties();

        try {
            pro.load(JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties"));
            ds= DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回连接池对象
     * @return dataSource
     */
    public static DataSource getDataSource() {
        return ds;
    }

    /**
     * @return 获取连接对象connection
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = tl.get();
        if (connection != null) {
            return connection;
        }
        return ds.getConnection();
    }
    /**
     * 开启事务。 1.获取一个Connection，2.保证dao中使用的Connection一致
     * @throws SQLException
     */
    public static void beginTransaction() throws SQLException {
        Connection connection = tl.get();
        if (connection != null){
            throw new SQLException("事务已开启!\n");
        }
        connection = getConnection();
        connection.setAutoCommit(false);
        tl.set(connection);
    }

    /**
     * 提交事务。使用beginTransaction的Connection。
     * @throws SQLException
     */
    public static void commitTransaction() throws SQLException {
        // 获取当前线程的专用连接
        Connection connection = tl.get();
        if (connection == null) {
            throw new SQLException("事务未开启!\n");
        }
        connection.commit();
        connection.close();
        tl.remove();// 移除连接
    }

    /**
     * 回滚事务
     * @throws SQLException
     */
    public static void rollbackTransaction() throws SQLException {
        Connection connection = tl.get();
        if (connection == null) {
            throw new SQLException("事务未开启!\n");
        }
        connection.rollback();
        connection.close();
        tl.remove();// 移除连接
    }
    /**
     * 释放连接回连接池
     * @param con
     * @throws SQLException
     */
    public static void releaseConnection(Connection con) throws SQLException {
        Connection connection = tl.get();
        /*
         * 判断是否开启了事务
         */
        // connection == null说明没有开启事务,con不是事务使用的要关闭
        if (connection == null) {
            con.close();}
        // connection!=null 有事务.判断参数连接是否与connection相等，!=说明连接不是事务的。
        if (connection != con){
            con.close();}
    }



}
