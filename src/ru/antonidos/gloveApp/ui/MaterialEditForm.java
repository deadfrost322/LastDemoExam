package ru.antonidos.gloveApp.ui;

import ru.antonidos.gloveApp.entity.MaterialEntity;
import ru.antonidos.gloveApp.manager.MaterialEntityManager;
import ru.antonidos.gloveApp.util.BaseForm;
import ru.antonidos.gloveApp.util.DialogUtil;

import javax.swing.*;
import java.sql.SQLException;

public class MaterialEditForm extends BaseForm {
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
    private JTextField idField;

    private MaterialEntity material;

    public MaterialEditForm(MaterialEntity material) {
        super(1200, 800);
        this.material = material;
        setContentPane(mainPanel);
        initFields();
        initButton();
        setVisible(true);
    }

    public void initFields(){
        idField.setText(String.valueOf(material.getId()));
        idField.setEnabled(false);
        titleField.setText(material.getTitle());
        countInPackSpinner.setValue(material.getCountInPack());
        unitField.setText(material.getUnit());
        countInStockSpinner.setValue(material.getCountInStock());
        minCountSpinner.setValue(material.getMinCount());
        descriptionField.setText(material.getDescription());
        costField.setText(String.valueOf(material.getCost()));
        imagePathField.setText(material.getImagePath());
        materialTypeField.setText(material.getMaterialType());
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

            material.setTitle(title);
            material.setCountInPack(countInPack);
            material.setCountInStock(countInStock);
            material.setUnit(unit);
            material.setMaterialType(materialType);
            material.setCost(cost);
            material.setDescription(description);
            material.setImagePath(imagePath);
            material.setMinCount(minCount);


            try {
                MaterialEntityManager.update(material);
                DialogUtil.showInfo(this, "запись успешно изменена");
                dispose();
                new MaterialTableForm();
            } catch (SQLException ex) {
                DialogUtil.showError(this, "ошибка изменения данных");
                ex.printStackTrace();
            }
        });
    }
}
