package ru.antonidos.gloveApp.ui;

import ru.antonidos.gloveApp.entity.MaterialEntity;
import ru.antonidos.gloveApp.manager.MaterialEntityManager;
import ru.antonidos.gloveApp.util.BaseForm;
import ru.antonidos.gloveApp.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class MaterialCreateForm extends BaseForm {
    private JTextField titleField;
    private JTextField unitField;
    private JTextField descriptionField;
    private JTextField costField;
    private JTextField imagePathField;
    private JTextField materialTypeField;
    private JButton backButton;
    private JButton addButton;
    private JPanel mainPanel;
    private JSpinner countInPackSpinner;
    private JSpinner countInStockSpinner;
    private JSpinner minCountSpinner;

    public MaterialCreateForm() {
        super(1200, 800);
        setContentPane(mainPanel);
        initButton();
        setVisible(true);
    }

    public void initButton() {
        backButton.addActionListener(e -> {
            dispose();
            new MaterialTableForm();
        });

        addButton.addActionListener(e -> {
            String title = titleField.getText();
            if (title.isEmpty() || title.length() > 100) {
                DialogUtil.showError(this, "название указано неверно");
                return;
            }

            int countInPack = (int) countInPackSpinner.getValue();
            if (countInPack < 0) {
                DialogUtil.showError(this, "количество в упаковке указано неверно");
                return;

            }

            String unit = unitField.getText();
            if (unit.isEmpty() || unit.length() > 5) {
                DialogUtil.showError(this, "единицы измерения указаны неверно");
                return;

            }

            int countInStock = (int) countInStockSpinner.getValue();
            if (countInStock < 0) {
                DialogUtil.showError(this, "количество на складе указано неверно");
                return;
            }

            int minCount = (int) minCountSpinner.getValue();
            if (minCount < 0) {
                DialogUtil.showError(this, "минимальное количество указано неверно");
                return;
            }

            String description = descriptionField.getText();
            if (description.isEmpty() || description.length() > 1000) {
                DialogUtil.showError(this, "описание указано неверно");
                return;
            }

            double cost = -1;
            try {
                cost = Double.parseDouble(costField.getText());
            } catch (Exception ex) {
                DialogUtil.showError(this, "цкена указана неверно");
                return;
            }
            if(cost <= 0){
                DialogUtil.showError(this, "цкена указана неверно");
                return;
            }

            String imagePath = imagePathField.getText();
            if (imagePath.isEmpty() || imagePath.length() > 100) {
                DialogUtil.showError(this, "путь до картинки указан неверно");
                return;
            }

            String materialType = materialTypeField.getText();
            if (materialType.isEmpty() || materialType.length() > 100) {
                DialogUtil.showError(this, "путь до картинки указан неверно");
                return;
            }

            MaterialEntity material = new MaterialEntity(title, countInPack, unit, countInStock, minCount, description, cost, imagePath, materialType);

            try {
                MaterialEntityManager.insert(material);
                DialogUtil.showInfo(this, "запись успешно добалена");
                dispose();
                new MaterialTableForm();
            } catch (SQLException ex) {
                DialogUtil.showError(this, "ошибка добавления данных");
                ex.printStackTrace();
            }
        });
    }
}
