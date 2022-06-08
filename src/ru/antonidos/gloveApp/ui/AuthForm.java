package ru.antonidos.gloveApp.ui;

import ru.antonidos.gloveApp.App;
import ru.antonidos.gloveApp.manager.AuthManager;
import ru.antonidos.gloveApp.util.BaseForm;
import ru.antonidos.gloveApp.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class AuthForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField LoginField;
    private JPasswordField passwordField;
    private JButton guestButton;
    private JButton loginButton;


    public AuthForm() {
        super(400, 600);

        setContentPane(mainPanel);

        initButton();
        setVisible(true);
    }

    public void initButton(){
        loginButton.addActionListener(e -> {
            String login = LoginField.getText();
            String password = new String (passwordField.getPassword());

            try {
                String role = AuthManager.auth(login,password);
                if (role == null){
                    DialogUtil.showError(this,"Неверные данные пользователя");
                    return;
                }
                if(role.equalsIgnoreCase("Администратор")){
                    App.IS_ADMIN = true;
                }
                dispose();
                new MaterialTableForm();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        guestButton.addActionListener(e -> {
            dispose();
            new MaterialTableForm();
        });
    }
}
