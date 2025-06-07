package GUI;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Мария
 */

import Service.Database;
import model.WizardCustomer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class Wizards extends JPanel {
    private final Database dbManager;
    private JTable wizardsTable;
    
    public Wizards(Database dbManager) {
        this.dbManager = dbManager;
        initializeUI();
        loadWizards();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Панель с кнопками
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton addButton = createStyledButton("Добавить покупателя");
        addButton.addActionListener(e -> showAddWizardDialog());
        
        JButton refreshButton = createStyledButton("Обновить");
        refreshButton.addActionListener(e -> loadWizards());
        
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        
        add(buttonPanel, BorderLayout.NORTH);
        
        // Таблица с покупателями
        wizardsTable = new JTable();
        wizardsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(wizardsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Bahnschrift SemiBold SemiConden", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(180, 35));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }
    
    private void loadWizards() {
        try {
            List<WizardCustomer> wizards = dbManager.getAllWizards();
            
            String[] columnNames = {"ID", "Имя", "Фамилия", "Школа", "Контакты"};
            Object[][] data = new Object[wizards.size()][columnNames.length];
            
            for (int i = 0; i < wizards.size(); i++) {
                WizardCustomer wizard = wizards.get(i);
                data[i][0] = wizard.getId();
                data[i][1] = wizard.getFirstName();
                data[i][2] = wizard.getLastName();
                data[i][3] = wizard.getSchool();
                data[i][4] = wizard.getContactInfo();
            }
            
            wizardsTable.setModel(new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
            
            // Настройка стилей таблицы
            wizardsTable.setFont(new Font("Bahnschrift SemiBold SemiConden", Font.PLAIN, 14));
            wizardsTable.setRowHeight(25);
            wizardsTable.getTableHeader().setFont(new Font("Bahnschrift SemiBold SemiConden", Font.BOLD, 14));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Ошибка загрузки данных: " + e.getMessage(),
                "Упс",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void showAddWizardDialog() {
        JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Добавить покупателя", true);
        dialog.setLayout(new BorderLayout());
        
        // Основная панель с полями ввода
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Поля ввода
        JLabel firstNameLabel = new JLabel("Имя:");
        JTextField firstNameField = new JTextField();
        
        JLabel lastNameLabel = new JLabel("Фамилия:");
        JTextField lastNameField = new JTextField();
        
        JLabel schoolLabel = new JLabel("Школа:");
        JTextField schoolField = new JTextField();
        
        JLabel contactLabel = new JLabel("Данные:");
        JTextField contactField = new JTextField();
        
        // Добавляем компоненты на панель
        inputPanel.add(firstNameLabel);
        inputPanel.add(firstNameField);
        inputPanel.add(lastNameLabel);
        inputPanel.add(lastNameField);
        inputPanel.add(schoolLabel);
        inputPanel.add(schoolField);
        inputPanel.add(contactLabel);
        inputPanel.add(contactField);
        
        // Панель с кнопкой
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        JButton saveButton = createStyledButton("Сохранить");
        saveButton.addActionListener(e -> {
            try {
                if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()) {
                    throw new IllegalArgumentException("Имя и фамилия обязательны для заполнения");
                }
                
                WizardCustomer wizard = new WizardCustomer();
                wizard.setFirstName(firstNameField.getText());
                wizard.setLastName(lastNameField.getText());
                wizard.setSchool(schoolField.getText());
                wizard.setContactInfo(contactField.getText());
                
                dbManager.addWizard(wizard);
                loadWizards();
                dialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    dialog,
                    "Ошибка: " + ex.getMessage(),
                    "Упс",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });
        
        buttonPanel.add(saveButton);
        
        // Добавляем все на диалог
        dialog.add(inputPanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.pack();
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }
}