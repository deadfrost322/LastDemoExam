package ru.antonidos.gloveApp.ui;

import ru.antonidos.gloveApp.App;
import ru.antonidos.gloveApp.entity.MaterialEntity;
import ru.antonidos.gloveApp.manager.MaterialEntityManager;
import ru.antonidos.gloveApp.util.BaseForm;
import ru.antonidos.gloveApp.util.CustomTableModel;
import ru.antonidos.gloveApp.util.DialogUtil;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class MaterialTableForm extends BaseForm {
    private JPanel mainPanel;
    private JTable table;
    private JButton addButton;

    CustomTableModel<MaterialEntity> model;
    public MaterialTableForm() {
        super(1200, 800);
        setContentPane(mainPanel);
        initButton();
        initTable();
        setVisible(true);
    }

    public void initTable(){
        table.getTableHeader().setReorderingAllowed(false);
        table.setRowHeight(50);
        try {
            model = new CustomTableModel<>(
                    MaterialEntity.class,
                    new String[] {"ID", "Title", "CountInPack", "Unit", "CountInStock", "MinCount", "Description", "Cost", "ImagePath", "MaterialType", "Image"},
                    MaterialEntityManager.selectAll()
            );
        } catch (SQLException e) {
            DialogUtil.showError(this, "Ошибка загрузки данных");
            e.printStackTrace();
        }
        table.setModel(model);

        TableColumn c = table.getColumn("ImagePath");
        c.setPreferredWidth(0);
        c.setMinWidth(0);
        c.setMaxWidth(0);

        if (App.IS_ADMIN){
            table.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        int row = table.rowAtPoint(e.getPoint());
                        if (row != -1) {
                            dispose();
                            new MaterialEditForm(model.getRows().get(row));
                        }
                    }
                }
            });
        }
    }

    public void initButton(){
        addButton.setEnabled(false);
        if(App.IS_ADMIN){
            addButton.setEnabled(true);
            addButton.addActionListener(e -> {
                dispose();
                new MaterialCreateForm();
            });
        }
    }
}
