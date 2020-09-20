package guperstudy;

import guperstudy.db.pool.DbConnectPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException {
        // System.out.println( "Hello World!" );
        DbConnectPool pool = DbConnectPool.getInstance();
        Connection connection = pool.getConnection();
        if (connection.isClosed()) {
            System.out.println("有效连接");
        }
        pool.freeConnection(connection);
    }
}
