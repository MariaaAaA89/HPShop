/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 * @author Мария
 */


import Service.Database;
import model.MagicWand;
import model.WandComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Components extends JPanel {
    private final Database dbManager;
    private JTable componentsTable;

    public Components(Database dbManager) {
        this.dbManager = dbManager;
        initializeUI();
        loadComponents();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshButton = createStyledButton("Обновить");
        refreshButton.addActionListener(e -> loadComponents());
        buttonPanel.add(refreshButton);
        JButton addButton = createStyledButton("Добавить компонент");
        addButton.addActionListener(e -> showAddComponentDialog());
        buttonPanel.add(addButton);
        add(buttonPanel, BorderLayout.NORTH);

        componentsTable = new JTable() {
            // Увеличиваем высоту строк
            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(super.getPreferredScrollableViewportSize().width, getRowHeight() * 10);
            }
        };

        // Устанавливаем высоту строк
        componentsTable.setRowHeight(40); // Увеличиваем высоту строк до 40 пикселей

        // Устанавливаем шрифт большего размера для лучшей читаемости
        componentsTable.setFont(new Font("Bahnschrift SemiBold SemiConden", Font.PLAIN, 14));

        componentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(componentsTable);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JTextField createWideTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 30));
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }



    
    private void showAddComponentDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Добавить компонент", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel contentPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel dateLabel = new JLabel(LocalDate.now().toString());
        dateLabel.setFont(new Font("Bahnschrift SemiBold SemiConden", Font.PLAIN, 14));

        JTextField quantityField = createWideTextField();
        JTextField nameField = createWideTextField();

        List<String> types = new ArrayList<>(Arrays.asList("wood", "core"));
        JComboBox<String> componentType = new JComboBox<>(types.toArray(new String[0]));
        componentType.setPreferredSize(new Dimension(250, 30));
        componentType.setFont(new Font("Bahnschrift SemiBold SemiConden", Font.PLAIN, 14));

        // Добавление компонентов с метками
        contentPanel.add(new JLabel("Наименование:"));
        contentPanel.add(nameField);
        
        contentPanel.add(new JLabel("Тип компонента:"));
        contentPanel.add(componentType);
        
        contentPanel.add(new JLabel("Количество:"));
        contentPanel.add(quantityField);
        
        // Пустые ячейки для выравнивания
        contentPanel.add(new JLabel());
        contentPanel.add(new JLabel());

        JButton saveButton = createStyledButton("Сохранить");
        saveButton.addActionListener(e -> {
            try {
                String selectedWood = (String) componentType.getSelectedItem();

                WandComponent component = new WandComponent(
                        selectedWood,
                        nameField.getText(),
                        Integer.parseInt(quantityField.getText())
                );

                dbManager.addComponent(component);
                loadComponents();
                dialog.dispose();

                JOptionPane.showMessageDialog(
                        this,
                        "Компонент успешно создан!",
                        "Урааааааааааа",
                        JOptionPane.INFORMATION_MESSAGE
                );

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                        dialog,
                        "Введите корректное количество",
                        "Упс",
                        JOptionPane.ERROR_MESSAGE
                );
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                        dialog,
                        "Ошибка при создании компонента: " + ex.getMessage(),
                        "Упссс",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(saveButton);
        
        dialog.add(contentPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.setSize(500, 350);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void loadComponents() {
        try {
            if (dbManager.getAllComponents().isEmpty()) {
                createInitialComponentsWithZeroQuantity();
            }
            List<WandComponent> components = dbManager.getAllComponents();

            String[] columnNames = {"Идентификатор", "Тип", "Наименование", "Количество"};
            Object[][] data = new Object[components.size()][4];

            for (int i = 0; i < components.size(); i++) {
                WandComponent component = components.get(i);
                data[i][0] = component.getId();
                data[i][1] = component.getType();
                data[i][2] = component.getName();
                data[i][3] = component.getQuantity();
            }

            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            componentsTable.setModel(model);

            // Настраиваем ширину столбцов
            setColumnWidths();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ошибка загрузки данных: " + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void setColumnWidths() {
        // Устанавливаем предпочтительные ширины столбцов
        componentsTable.getColumnModel().getColumn(0).setPreferredWidth(100); // ID
        componentsTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Тип
        componentsTable.getColumnModel().getColumn(2).setPreferredWidth(300); // Наименование
        componentsTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Количество

        // Разрешаем растягивание только последнего столбца
        componentsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }

    // Остальные методы без изменений
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        button.setFont(button.getFont().deriveFont(Font.BOLD));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 100, 190));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 215));
            }
        });

        return button;
    }

    private void createInitialComponentsWithZeroQuantity() throws SQLException {
        WandComponent[] initialComponents = {
                new WandComponent("wood", "Дуб", 0),
                new WandComponent("wood", "Ясень", 0),
                new WandComponent("core", "Перо феникса", 0),
                new WandComponent("core", "Волос из хвоста единорога", 0)
        };

        for (WandComponent component : initialComponents) {
            dbManager.addComponent(component);
        }
    }
}  