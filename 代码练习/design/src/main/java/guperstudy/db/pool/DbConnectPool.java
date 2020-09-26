package guperstudy.db.pool;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;

/**
 * @author joe
 * @program: design
 * @description: 数据库连接池对象
 * @date 2020-09-13 15:18:38
 * 作用：解析配置文件、连接对象存储、最大连接数、空闲连接数、当前工作连接数
 */
public class DbConnectPool {

    private static final DbConnectPool POOL;

    private String url;

    private String name;

    private String password;

    private String jdbcDriverName;

    /**
     * 最大连接数
     */
    private int maxConnect;
    /**
     * 当前被取出的连接数
     */
    private int checkoutConnect = 0;

    private static final String CONFIG_PATH = "dbconfig.properties";

    private Vector<Connection> freeConnects = new Vector<Connection>();
    
    static {
        // NOTE: 静态代码块天然线程加锁，多线程也只会执行一次
        POOL = new DbConnectPool();
        POOL.init();
    }
    
    public static DbConnectPool getInstance() {
        return POOL;
    }

    public int getFreeConnectCount() {
        return freeConnects.size();
    }

    public int getCheckoutConnect() {
        return checkoutConnect;
    }

    private synchronized void init() {
        // 解析配置文件，尝试链接
        InputStream is = DbConnectPool.class.getResourceAsStream(CONFIG_PATH);
        Properties config = new Properties();
        try {
            config.load(is);
            this.url = config.getProperty("connecturl");
            this.name = config.getProperty("username");
            this.password = config.getProperty("password");
            this.jdbcDriverName = config.getProperty("jdbc");
            // 连接池配置
            this.maxConnect = Integer.parseInt(config.getProperty("maxConnect", "20"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            // 初始化driver
            Driver driver = (Driver) Class.forName(jdbcDriverName).newInstance();

            // NOTE: 注册实际的jdbc实现
            DriverManager.registerDriver(driver);
            System.out.println("成功注册 JDBC 驱动程序" + jdbcDriverName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("注册 JDBC 驱动程序失败" + jdbcDriverName);
        }
        
        // 按照最大连接数创建连接对象
        for (int i = 0; i < maxConnect; i++) {
            Connection connection = createConnection();
            if (connection != null) {
                freeConnects.add(connection);
            }
        }

    }


    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, name, password);
            System.out.println("连接池创建一个新的连接");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接池创建新的连接失败");
        }
        return connection;
    }

    /**
     * 获取一个数据库链接 NOTE: 为了避免多个线程同时取到同一个链接对象，需要线程同步
     * @return 数据库链接
     */
    public synchronized Connection getConnection() throws SQLException {
        Connection connection = null;
        if (freeConnects.size() > 0) {
            // 还有空闲的连接，直接取出
            connection = freeConnects.firstElement();
            freeConnects.removeElementAt(0);
            if (connection.isClosed()) {
                // 如果当前连接已经关闭，则重新取一个连接
                return getConnection();
            }
        } else if (maxConnect == 0 || checkoutConnect < maxConnect) {
            // maxConnect 为0代表没有最大限制 或者 当前取出的连接数还没有到达最大连接数
            // 此时创建一个新的链接
            connection = createConnection();
        }
        if (connection != null) {
            // 记录取出的连接数
            checkoutConnect++;
        }
        return connection;
    }

    /**
     * 释放一个连接对象，放回连接池数组
     * @param connection 释放的链接对象
     */
    public synchronized void freeConnection(Connection connection) {
        System.out.println("释放链接到连接池");
        if (!freeConnects.contains(connection)) {
            freeConnects.add(connection);
            checkoutConnect--;
        }
    }

    /**
     * 释放连接池中的所有空闲链接
     */
    public synchronized void releaseAll() {
        if (freeConnects.size() == 0) {
            return;
        }
        try {
            for (int i = 0; i < freeConnects.size(); i++) {
                Connection connection = freeConnects.get(i);
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
