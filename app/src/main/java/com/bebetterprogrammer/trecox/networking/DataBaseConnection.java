package com.bebetterprogrammer.trecox.networking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBaseConnection {
    public static String user = "epiz_26523431";
    public static String password = "um4i5ui1CnI";
    public static String url = "jdbc:mysql://185.27.134.3/epiz_26523431_trecox";
    public static String classs = "com.mysql.jdbc.Driver"; //185.27.134.3

    public static String getName() {
        String companyEmail = "hap";
        try {
            Class.forName(classs);
            Connection connection = DriverManager.getConnection(url, user, password);
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from company where company_name = 'AnalogX'");
            companyEmail = rs.getString(3);
            if (companyEmail == null) {
                companyEmail = "qwe";
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return companyEmail;
    }
}
