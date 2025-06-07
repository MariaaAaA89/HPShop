/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GUI;

/**
 *
 * @author Мария
 */
 

import Service.Database;
import model.MagicWand;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Sales extends JPanel {
    private final Database dbManager;
    private JTable salesTable;
    
    public Sales(Database dbManager) {
        this.dbManager = dbManager;
        initializeUI();
        loadSales();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Панель кнопок с отступами
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Стилизованная кнопка "Обновить"
        JButton refreshButton = createStyledButton("Обновить");
        refreshButton.addActionListener(e -> loadSales());
        
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.NORTH);
        
        // Настройка таблицы
        salesTable = new JTable() {
            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(super.getPreferredScrollableViewportSize().width, getRowHeight() * 10);
            }
        };
        
        salesTable.setRowHeight(40); // Увеличенная высота строк
        salesTable.setFont(new Font("Bahnschrift SemiBold SemiConden", Font.PLAIN, 14));
        salesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        salesTable.setIntercellSpacing(new Dimension(10, 5));
        salesTable.setShowGrid(false);
        
        JScrollPane scrollPane = new JScrollPane(salesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // Метод для создания стилизованных кнопок
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 120, 215)); // Синий цвет
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(0, 80, 180)), 
            BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        
        // Эффекты при наведении
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 100, 190));
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 120, 215));
            }
            
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 80, 160));
            }
            
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 100, 190));
            }
        });
        
        return button;
    }
    
    private void loadSales() {
        try {
            List<MagicWand> soldWands = dbManager.getSoldWandsWithWizards();
            
            String[] columnNames = {
                "ID палочки", "Дата создания", "Цена", 
                "Дата продажи", "Владелец", "Школа"
            };
            
            Object[][] data = new Object[soldWands.size()][6];
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            
            for (int i = 0; i < soldWands.size(); i++) {
                MagicWand wand = soldWands.get(i);
                data[i][0] = wand.getId();
                data[i][1] = wand.getCreationDate().format(formatter);
                data[i][2] = String.format("%.2f", wand.getPrice()); // Форматирование цены
                data[i][3] = wand.getSaleDate().format(formatter);
                data[i][4] = wand.getOwner().getFirstName() + " " + wand.getOwner().getLastName();
                data[i][5] = wand.getOwner().getSchool();
            }
            
            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            salesTable.setModel(model);
            
            // Настройка ширины столбцов
            salesTable.getColumnModel().getColumn(0).setPreferredWidth(100);  // ID
            salesTable.getColumnModel().getColumn(1).setPreferredWidth(120); // Дата создания
            salesTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Цена
            salesTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Дата продажи
            salesTable.getColumnModel().getColumn(4).setPreferredWidth(200); // Владелец
            salesTable.getColumnModel().getColumn(5).setPreferredWidth(150); // Школа
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Ошибка загрузки данных о продажах: " + e.getMessage(),
                "Ошибка",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}