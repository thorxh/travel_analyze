package com.bonc.usdp.util;

import java.sql.*;

/**
 * <br>Created by liyanjun@bonc.com.cn on 2017/9/21.<br><br>
 */
public class DbUtil {

    public static Connection getConn(String url, String driver, String userName, String password) {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("can not load class " + driver, e);
        } catch (SQLException e) {
            throw new RuntimeException("can not get a connection, please check db params", e);
        }
    }

    public static void close(Connection conn, Statement stat, ResultSet resultSet) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (stat != null) {
                    try {
                        stat.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (resultSet != null) {
                            try {
                                resultSet.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

}
