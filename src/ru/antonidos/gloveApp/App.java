package ru.antonidos.gloveApp;

import ru.antonidos.gloveApp.ui.AuthForm;
import ru.antonidos.gloveApp.ui.MaterialTableForm;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

    public static boolean IS_ADMIN = false;

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new AuthForm();
    }

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/demotest5", "root", "1234");
    }
}
