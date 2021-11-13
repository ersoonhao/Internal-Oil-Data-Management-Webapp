package com.oop.project;

import org.springframework.beans.factory.annotation.Value;
import java.sql.*;
import com.oop.project.model.ChinaData;
import org.springframework.stereotype.Repository;

@Repository
public class Database {
    private static String dbURL = "jdbc:mysql://oop.czmzwyti4icp.ap-southeast-1.rds.amazonaws.com:3306/testdb";

    @Value("${spring.datasource.url}")
    public void setdbURL(String value) {
        dbURL = value;
    }

    private static String username = "oopadmin";

    @Value("${spring.datasource.username}")
    public void setdbUser(String value) {
        username = value;
    }

    private static String password = "ooppassword";

    @Value("${spring.datasource.password}")
    public void setdbPass(String value) {
        password = value;
    }

    public static String deleteAll() {
        System.out.println(dbURL);

        String sql = "DELETE FROM `china_data`";
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.executeUpdate();

        } catch (SQLException e) {
            return e.getMessage();
        }
        return "Success";
    }

    public static String insert(ChinaData chinaData) {

        String sql = "INSERT INTO `china_data`(`category`,`type`,`year`,`month`,`quantity_unit`,`raw_quantity`,`final_quantity`,`value`,`price`) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(dbURL, username, password);
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            
            stmt.setString(1, chinaData.getCategory());
            stmt.setString(2, chinaData.getType());
            stmt.setInt(3, chinaData.getYear());
            stmt.setInt(4, chinaData.getMonth());
            stmt.setInt(5, chinaData.getQuantity_unit());
            stmt.setInt(6, chinaData.getRaw_quantity());
            stmt.setDouble(7, chinaData.getFinal_quantity());
            stmt.setInt(8, chinaData.getValue());
            stmt.setDouble(9, chinaData.getPrice());
            stmt.executeUpdate();

        } catch (SQLException e) {
            return e.getMessage();
        }
        return "Success";
    }
}
