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
import Service.DeliveryProcessor;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Delivery extends JPanel {
    private final Database dbManager;
    private final DeliveryProcessor deliveryManager;
    private JTable deliveriesTable;
    
    public Delivery(Database dbManager) {
        this.dbManager = dbManager;
        this.deliveryManager = new DeliveryProcessor(dbManager);
        initializeUI();
        loadDeliveries();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Создаем стилизованные кнопки
        JButton weeklyDeliveryBtn = createStyledButton("Создать недельную поставку");
        weeklyDeliveryBtn.addActionListener(e -> createWeeklyDelivery());
        
        JButton seasonalDeliveryBtn = createStyledButton("Создать дополнительную поставку");
        seasonalDeliveryBtn.addActionListener(e -> createSeasonalDelivery());
        
        JButton refreshButton = createStyledButton("Обновить");
        refreshButton.addActionListener(e -> loadDeliveries());
        
        buttonPanel.add(weeklyDeliveryBtn);
        buttonPanel.add(seasonalDeliveryBtn);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.NORTH);
        
        deliveriesTable = new JTable() {
            @Override
            public Dimension getPreferredScrollableViewportSize() {
                return new Dimension(super.getPreferredScrollableViewportSize().width, getRowHeight() * 10);
            }
        };
        
        // Настройки таблицы
        deliveriesTable.setRowHeight(40); // Увеличенная высота строк
        deliveriesTable.setFont(new Font("Bahnschrift SemiBold SemiConden", Font.PLAIN, 14));
        deliveriesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        deliveriesTable.setIntercellSpacing(new Dimension(10, 5));
        deliveriesTable.setShowGrid(false);
        
        JScrollPane scrollPane = new JScrollPane(deliveriesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);
        
        deliveriesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = deliveriesTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int deliveryId = (int) deliveriesTable.getValueAt(selectedRow, 0);
                    showDeliveryDetailsDialog(deliveryId);
                }
            }
        });
    }

  private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setBackground(new Color(0, 120, 215)); // Синий цвет
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setFont(new Font("Bahnschrift SemiBold SemiConden", Font.BOLD, 14));
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(0, 80, 180)), 
        BorderFactory.createEmptyBorder(10, 20, 10, 20)
    ));
    
    // Эффекты при наведении и нажатии
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

    private void showDeliveryDetailsDialog(int deliveryId) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Детали поставки", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(800, 500); // Увеличенный размер диалога
        dialog.setLocationRelativeTo(this);
        
        JTable itemsTable = new JTable();
        itemsTable.setRowHeight(35);
        itemsTable.setFont(new Font("Bahnschrift SemiBold SemiConden", Font.PLAIN, 14));
        itemsTable.setFillsViewportHeight(true);
        itemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
       
        itemsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        showDeliveryItems(deliveryId, itemsTable);
        
        // Установка ширины столбцов для таблицы деталей
        itemsTable.getColumnModel().getColumn(0).setPreferredWidth(200); // Компонент
        itemsTable.getColumnModel().getColumn(1).setPreferredWidth(100); // Тип
        itemsTable.getColumnModel().getColumn(2).setPreferredWidth(100); // Количество
        itemsTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Цена
        itemsTable.getColumnModel().getColumn(4).setPreferredWidth(150); // Стоимость
        
        JScrollPane scrollPane = new JScrollPane(itemsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    private void loadDeliveries() {
        try {
            List<ComponentDelivery> deliveries = dbManager.getAllDeliveries();
            
            String[] columnNames = {"ID", "Дата поставки", "Поставщик", "Тип", "Кол-во позиций"};
            Object[][] data = new Object[deliveries.size()][5];
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            
            for (int i = 0; i < deliveries.size(); i++) {
                ComponentDelivery delivery = deliveries.get(i);
                data[i][0] = delivery.getId();
                data[i][1] = delivery.getDeliveryDate().format(formatter);
                data[i][2] = delivery.getSupplierName();
                data[i][3] = delivery.isSeasonal() ? "Добавочная" : "Обычная";
                data[i][4] = delivery.getItems().size();
            }
            
            DefaultTableModel model = new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
            deliveriesTable.setModel(model);
            
            // Установка ширины столбцов
            deliveriesTable.getColumnModel().getColumn(0).setPreferredWidth(80);  // ID
            deliveriesTable.getColumnModel().getColumn(1).setPreferredWidth(150); // Дата
            deliveriesTable.getColumnModel().getColumn(2).setPreferredWidth(250); // Поставщик
            deliveriesTable.getColumnModel().getColumn(3).setPreferredWidth(120); // Тип
            deliveriesTable.getColumnModel().getColumn(4).setPreferredWidth(120); // Кол-во
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Ошибка загрузки поставок: " + e.getMessage(),
                "Упс",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Остальные методы без изменений
    private void createWeeklyDelivery() {
        try {
            boolean deliveryCreated = deliveryManager.processWeeklyDelivery(
                (JFrame)SwingUtilities.getWindowAncestor(this));

            if (deliveryCreated) {
                loadDeliveries();
                JOptionPane.showMessageDialog(this, 
                    "Недельная поставка создана!",
                    "Ура",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Ошибка при создании поставки: " + e.getMessage(),
                "Ура",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createSeasonalDelivery() {
        try {
            if (!deliveryManager.isSummerSeason()) {
                int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Сейчас не лето. Вы уверены, что хотите создать дополнительную поставку?",
                    "Подтверждение",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (confirm != JOptionPane.YES_OPTION) {
                    return;
                }
            }
            
            deliveryManager.processSeasonalDelivery();
            loadDeliveries();
            JOptionPane.showMessageDialog(this, "Дополнительная поставка создана!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Ошибка: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void showDeliveryItems(int deliveryId, JTable itemsTable) {
        try {
            List<DeliveryItem> items = dbManager.getDeliveryItems(deliveryId);
            List<WandComponent> components = dbManager.getAllComponents();
            
            String[] columnNames = {"Компонент", "Тип", "Количество", "Цена за единицу", "Общая стоимость"};
            Object[][] data = new Object[items.size()][5];
            
            for (int i = 0; i < items.size(); i++) {
                DeliveryItem item = items.get(i);
                WandComponent component = findComponent(components, item.getComponentId());
                
                data[i][0] = component != null ? component.getName() : "Неизвестно";
                data[i][1] = component != null ? component.getType() : "-";
                data[i][2] = item.getQuantity();
                data[i][3] = String.format("%.2f", item.getUnitPrice());
                data[i][4] = String.format("%.2f", item.getQuantity() * item.getUnitPrice());
            }
            
            itemsTable.setModel(new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Ошибка загрузки позиций поставки: " + e.getMessage(),
                "Упс",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private WandComponent findComponent(List<WandComponent> components, int componentId) {
        for (WandComponent component : components) {
            if (component.getId() == componentId) {
                return component;
            }
        }
        return null;
    }
}