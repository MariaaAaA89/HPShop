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
import model.WandComponent;
import model.MagicWand;
import model.WizardCustomer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
//
//public class Wands extends JPanel {
//    private final Database dbManager;
//    private JTable wandsTable;
//    
//    public Wands(Database dbManager) {
//        this.dbManager = dbManager;
//        initializeUI();
//        loadWands();
//    }
//    
//    private void initializeUI() {
//        setLayout(new BorderLayout());
//        
//        // Панель с кнопками
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
//        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        
//        JButton addButton = createStyledButton("Добавить палочку");
//        addButton.addActionListener(e -> showAddWandDialog());
//        
//        JButton sellButton = createStyledButton("Продать палочку");
//        sellButton.addActionListener(e -> showSellWandDialog());
//        
//        JButton refreshButton = createStyledButton("Обновить");
//        refreshButton.addActionListener(e -> loadWands());
//     
//        buttonPanel.add(addButton);
//        buttonPanel.add(sellButton);
//        buttonPanel.add(refreshButton);
//        
//        add(buttonPanel, BorderLayout.NORTH);
//        
//        // Таблица с палочками
//        wandsTable = new JTable();
//        wandsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        JScrollPane scrollPane = new JScrollPane(wandsTable);
//        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        
//        add(scrollPane, BorderLayout.CENTER);
//    }
//    
//    private JButton createStyledButton(String text) {
//        JButton button = new JButton(text);
//        button.setBackground(new Color(0, 120, 215)); // Синий цвет
//        button.setForeground(Color.WHITE);
//        button.setFocusPainted(false);
//        button.setFont(new Font("Arial", Font.BOLD, 14));
//        button.setPreferredSize(new Dimension(180, 35));
//        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
//        return button;
//    }
//    
//    private JTextField createWideTextField() {
//        JTextField textField = new JTextField();
//        textField.setPreferredSize(new Dimension(250, 30));
//        textField.setFont(new Font("Arial", Font.PLAIN, 14));
//        return textField;
//    }
//    
//    private void loadWands() {
//        try {
//            List<MagicWand> wands = dbManager.getAvailableWands();
//            
//            String[] columnNames = {"Идентификатор", "Дата создания", "Цена", "Статус", "ID древесины", "ID сердцевины"};
//            Object[][] data = new Object[wands.size()][6];
//            
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//            
//            for (int i = 0; i < wands.size(); i++) {
//                MagicWand wand = wands.get(i);
//                data[i][0] = wand.getId();
//                data[i][1] = wand.getCreationDate().format(formatter);
//                data[i][2] = wand.getPrice();
//                data[i][3] = wand.getStatus();
//                data[i][4] = wand.getWoodId();
//                data[i][5] = wand.getCoreId();
//            }
//            
//            wandsTable.setModel(new DefaultTableModel(data, columnNames) {
//                @Override
//                public boolean isCellEditable(int row, int column) {
//                    return false;
//                }
//            });
//            
//            // Установка стилей для таблицы
//            wandsTable.setFont(new Font("Bahnschrift SemiBold SemiConden", Font.PLAIN, 14));
//            wandsTable.setRowHeight(25);
//            wandsTable.getTableHeader().setFont(new Font("Bahnschrift SemiBold SemiConden", Font.BOLD, 14));
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(
//                this,
//                "Ошибка загрузки данных: " + e.getMessage(),
//                "Ошибка",
//                JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }
//    
//    private void showAddWandDialog() {
//        try {
//            List<WandComponent> availableWoods = dbManager.getAvailableComponents("wood");
//            List<WandComponent> availableCores = dbManager.getAvailableComponents("core");
//            
//            if (availableWoods.isEmpty() || availableCores.isEmpty()) {
//                JOptionPane.showMessageDialog(
//                    this,
//                    "Нет доступных компонентов для создания палочки",
//                    "Ошибка",
//                    JOptionPane.ERROR_MESSAGE
//                );
//                return;
//            }
//            
//            JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Добавить палочку", true);
//            dialog.setLayout(new GridBagLayout());
//            GridBagConstraints gbc = new GridBagConstraints();
//            gbc.insets = new Insets(10, 10, 10, 10);
//            gbc.anchor = GridBagConstraints.WEST;
//            gbc.fill = GridBagConstraints.HORIZONTAL;
//            
//            JPanel contentPanel = new JPanel(new GridBagLayout());
//            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//            
//            JLabel dateLabel = new JLabel(LocalDate.now().toString());
//            dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
//            
//            JTextField priceField = createWideTextField();
//            
//            JComboBox<WandComponent> woodCombo = new JComboBox<>(availableWoods.toArray(new WandComponent[0]));
//            woodCombo.setPreferredSize(new Dimension(250, 30));
//            woodCombo.setFont(new Font("Arial", Font.PLAIN, 14));
//            
//            JComboBox<WandComponent> coreCombo = new JComboBox<>(availableCores.toArray(new WandComponent[0]));
//            coreCombo.setPreferredSize(new Dimension(250, 30));
//            coreCombo.setFont(new Font("Arial", Font.PLAIN, 14));
//            
//            // Добавление компонентов
//            gbc.gridx = 0;
//            gbc.gridy = 0;
//            contentPanel.add(new JLabel("Дата создания:"), gbc);
//            
//            gbc.gridx = 1;
//            contentPanel.add(dateLabel, gbc);
//            
//            gbc.gridx = 0;
//            gbc.gridy = 1;
//            contentPanel.add(new JLabel("Цена:"), gbc);
//            
//            gbc.gridx = 1;
//            contentPanel.add(priceField, gbc);
//            
//            gbc.gridx = 0;
//            gbc.gridy = 2;
//            contentPanel.add(new JLabel("Древесина:"), gbc);
//            
//            gbc.gridx = 1;
//            contentPanel.add(woodCombo, gbc);
//            
//            gbc.gridx = 0;
//            gbc.gridy = 3;
//            contentPanel.add(new JLabel("Сердцевина:"), gbc);
//            
//            gbc.gridx = 1;
//            contentPanel.add(coreCombo, gbc);
//            
//            JButton saveButton = createStyledButton("Сохранить");
//            saveButton.addActionListener(e -> {
//                try {
//                    WandComponent selectedWood = (WandComponent)woodCombo.getSelectedItem();
//                    WandComponent selectedCore = (WandComponent)coreCombo.getSelectedItem();
//
//                    double price = Double.parseDouble(priceField.getText());
//                    if(price < 0){
//                        JOptionPane.showMessageDialog(
//                                dialog,
//                                "Цена может быть только положительным числом",
//                                "Ошибка",
//                                JOptionPane.ERROR_MESSAGE
//                        );
//                        return;
//                    }
//                    MagicWand wand = new MagicWand(
//                        LocalDate.now(),
//                            price,
//                        selectedWood.getId(),
//                        selectedCore.getId()
//                    );
//                    
//                    dbManager.addWand(wand);
//                    loadWands();
//                    dialog.dispose();
//                    
//                    JOptionPane.showMessageDialog(
//                        this,
//                        "Палочка успешно создана!",
//                        "Успех",
//                        JOptionPane.INFORMATION_MESSAGE
//                    );
//                   
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(
//                        dialog,
//                        "Введите корректную цену",
//                        "Ошибка",
//                        JOptionPane.ERROR_MESSAGE
//                    );
//                } catch (SQLException ex) {
//                    JOptionPane.showMessageDialog(
//                        dialog,
//                        "Ошибка при создании палочки: " + ex.getMessage(),
//                        "Ошибка",
//                        JOptionPane.ERROR_MESSAGE
//                    );
//                }
//            });
//            
//            gbc.gridx = 0;
//            gbc.gridy = 4;
//            gbc.gridwidth = 2;
//            gbc.anchor = GridBagConstraints.CENTER;
//            contentPanel.add(saveButton, gbc);
//            
//            dialog.add(contentPanel);
//            dialog.pack();
//            dialog.setMinimumSize(new Dimension(500, 350));
//            dialog.setLocationRelativeTo(this);
//            dialog.setVisible(true);
//            
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(
//                this,
//                "Ошибка загрузки компонентов: " + e.getMessage(),
//                "Ошибка",
//                JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }
//    
//    private void showSellWandDialog() {
//        try {
//            List<MagicWand> wands = dbManager.getAvailableWands();
//            List<WizardCustomer> wizards = dbManager.getAllWizards();
//            
//            if (wands.isEmpty() || wizards.isEmpty()) {
//                JOptionPane.showMessageDialog(
//                    this,
//                    "Нет доступных палочек или покупателей",
//                    "Ошибка",
//                    JOptionPane.ERROR_MESSAGE
//                );
//                return;
//            }
//            
//            JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Продать палочку", true);
//            dialog.setLayout(new GridBagLayout());
//            GridBagConstraints gbc = new GridBagConstraints();
//            gbc.insets = new Insets(10, 10, 10, 10);
//            gbc.anchor = GridBagConstraints.WEST;
//            gbc.fill = GridBagConstraints.HORIZONTAL;
//            
//            JPanel contentPanel = new JPanel(new GridBagLayout());
//            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//            
//            JComboBox<MagicWand> wandCombo = new JComboBox<>(wands.toArray(new MagicWand[0]));
//            wandCombo.setPreferredSize(new Dimension(250, 30));
//            wandCombo.setFont(new Font("Arial", Font.PLAIN, 14));
//            
//            JComboBox<WizardCustomer> wizardCombo = new JComboBox<>(wizards.toArray(new WizardCustomer[0]));
//            wizardCombo.setPreferredSize(new Dimension(250, 30));
//            wizardCombo.setFont(new Font("Arial", Font.PLAIN, 14));
//            
//            // Добавление компонентов
//            gbc.gridx = 0;
//            gbc.gridy = 0;
//            contentPanel.add(new JLabel("Палочка:"), gbc);
//            
//            gbc.gridx = 1;
//            contentPanel.add(wandCombo, gbc);
//            
//            gbc.gridx = 0;
//            gbc.gridy = 1;
//            contentPanel.add(new JLabel("Покупатель:"), gbc);
//            
//            gbc.gridx = 1;
//            contentPanel.add(wizardCombo, gbc);
//            
//            JButton sellButton = createStyledButton("Продать");
//            sellButton.addActionListener(e -> {
//                try {
//                    MagicWand selectedWand = (MagicWand)wandCombo.getSelectedItem();
//                    WizardCustomer selectedWizard = (WizardCustomer)wizardCombo.getSelectedItem();
//                    
//                    dbManager.sellWand(selectedWand.getId(), selectedWizard.getId());
//                    loadWands();
//                    dialog.dispose();
//                    
//                    JOptionPane.showMessageDialog(
//                        this,
//                        "Палочка успешно продана!",
//                        "Успех",
//                        JOptionPane.INFORMATION_MESSAGE
//                    );
//                } catch (SQLException ex) {
//                    JOptionPane.showMessageDialog(
//                        dialog,
//                        "Ошибка: " + ex.getMessage(),
//                        "Ошибка",
//                        JOptionPane.ERROR_MESSAGE
//                    );
//                }
//            });
//            
//            gbc.gridx = 0;
//            gbc.gridy = 2;
//            gbc.gridwidth = 2;
//            gbc.anchor = GridBagConstraints.CENTER;
//            contentPanel.add(sellButton, gbc);
//            
//            dialog.add(contentPanel);
//            dialog.pack();
//            dialog.setMinimumSize(new Dimension(500, 250));
//            dialog.setLocationRelativeTo(this);
//            dialog.setVisible(true);
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(
//                this,
//                "Ошибка загрузки данных: " + e.getMessage(),
//                "Ошибка",
//                JOptionPane.ERROR_MESSAGE
//            );
//        }
//    }
//}
public class Wands extends JPanel {
    private final Database dbManager;
    private JTable wandsTable;
    
    public Wands(Database dbManager) {
        this.dbManager = dbManager;
        initializeUI();
        loadWands();
    }
    
    private void initializeUI() {
        setLayout(new BorderLayout());
        
        // Панель с кнопками (остается без изменений)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton addButton = createStyledButton("Добавить палочку");
        addButton.addActionListener(e -> showAddWandDialog());
        
        JButton sellButton = createStyledButton("Продать палочку");
        sellButton.addActionListener(e -> showSellWandDialog());
        
        JButton refreshButton = createStyledButton("Обновить");
        refreshButton.addActionListener(e -> loadWands());
     
        buttonPanel.add(addButton);
        buttonPanel.add(sellButton);
        buttonPanel.add(refreshButton);
        
        add(buttonPanel, BorderLayout.NORTH);
        
        // Таблица с палочками (остается без изменений)
        wandsTable = new JTable();
        wandsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(wandsTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(scrollPane, BorderLayout.CENTER);
    }
    
    // Методы createStyledButton и createWideTextField остаются без изменений
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 120, 215));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(180, 35));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return button;
    }
    
    private JTextField createWideTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(250, 30));
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }
    
    // Метод loadWands остается без изменений
    private void loadWands() {
        try {
            List<MagicWand> wands = dbManager.getAvailableWands();
            
            String[] columnNames = {"Идентификатор", "Дата создания", "Цена", "Статус", "ID древесины", "ID сердцевины"};
            Object[][] data = new Object[wands.size()][6];
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            
            for (int i = 0; i < wands.size(); i++) {
                MagicWand wand = wands.get(i);
                data[i][0] = wand.getId();
                data[i][1] = wand.getCreationDate().format(formatter);
                data[i][2] = wand.getPrice();
                data[i][3] = wand.getStatus();
                data[i][4] = wand.getWoodId();
                data[i][5] = wand.getCoreId();
            }
            
            wandsTable.setModel(new DefaultTableModel(data, columnNames) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            });
            
            wandsTable.setFont(new Font("Bahnschrift SemiBold SemiConden", Font.PLAIN, 14));
            wandsTable.setRowHeight(25);
            wandsTable.getTableHeader().setFont(new Font("Bahnschrift SemiBold SemiConden", Font.BOLD, 14));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Ошибка загрузки данных: " + e.getMessage(),
                "Ошибка",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void showAddWandDialog() {
        try {
            List<WandComponent> availableWoods = dbManager.getAvailableComponents("wood");
            List<WandComponent> availableCores = dbManager.getAvailableComponents("core");
            
            if (availableWoods.isEmpty() || availableCores.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Нет доступных компонентов для создания палочки",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Добавить палочку", true);
            dialog.setLayout(new BorderLayout());
            
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Панель для даты создания
            JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            datePanel.add(new JLabel("Дата создания:"));
            JLabel dateLabel = new JLabel(LocalDate.now().toString());
            dateLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            datePanel.add(dateLabel);
            mainPanel.add(datePanel);
            
            mainPanel.add(Box.createVerticalStrut(15));
            
            // Панель для цены
            JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            pricePanel.add(new JLabel("Цена:"));
            JTextField priceField = createWideTextField();
            pricePanel.add(priceField);
            mainPanel.add(pricePanel);
            
            mainPanel.add(Box.createVerticalStrut(15));
            
            // Панель для древесины
            JPanel woodPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            woodPanel.add(new JLabel("Древесина:"));
            JComboBox<WandComponent> woodCombo = new JComboBox<>(availableWoods.toArray(new WandComponent[0]));
            woodCombo.setPreferredSize(new Dimension(250, 30));
            woodCombo.setFont(new Font("Arial", Font.PLAIN, 14));
            woodPanel.add(woodCombo);
            mainPanel.add(woodPanel);
            
            mainPanel.add(Box.createVerticalStrut(15));
            
            // Панель для сердцевины
            JPanel corePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            corePanel.add(new JLabel("Сердцевина:"));
            JComboBox<WandComponent> coreCombo = new JComboBox<>(availableCores.toArray(new WandComponent[0]));
            coreCombo.setPreferredSize(new Dimension(250, 30));
            coreCombo.setFont(new Font("Arial", Font.PLAIN, 14));
            corePanel.add(coreCombo);
            mainPanel.add(corePanel);
            
            mainPanel.add(Box.createVerticalStrut(25));
            
            // Кнопка сохранения
            JPanel buttonPanel = new JPanel();
            JButton saveButton = createStyledButton("Сохранить");
            saveButton.addActionListener(e -> {
                try {
                    WandComponent selectedWood = (WandComponent)woodCombo.getSelectedItem();
                    WandComponent selectedCore = (WandComponent)coreCombo.getSelectedItem();

                    double price = Double.parseDouble(priceField.getText());
                    if(price < 0){
                        JOptionPane.showMessageDialog(
                                dialog,
                                "Цена может быть только положительным числом",
                                "Ошибка",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    MagicWand wand = new MagicWand(
                        LocalDate.now(),
                        price,
                        selectedWood.getId(),
                        selectedCore.getId()
                    );
                    
                    dbManager.addWand(wand);
                    loadWands();
                    dialog.dispose();
                    
                    JOptionPane.showMessageDialog(
                        this,
                        "Палочка успешно создана!",
                        "Успех",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                        dialog,
                        "Введите корректную цену",
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE
                    );
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(
                        dialog,
                        "Ошибка при создании палочки: " + ex.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            });
            buttonPanel.add(saveButton);
            mainPanel.add(buttonPanel);
            
            dialog.add(mainPanel, BorderLayout.CENTER);
            dialog.pack();
            dialog.setMinimumSize(new Dimension(500, 350));
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Ошибка загрузки компонентов: " + e.getMessage(),
                "Ошибка",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void showSellWandDialog() {
        try {
            List<MagicWand> wands = dbManager.getAvailableWands();
            List<WizardCustomer> wizards = dbManager.getAllWizards();
            
            if (wands.isEmpty() || wizards.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Нет доступных палочек или покупателей",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            
            JDialog dialog = new JDialog((Frame)SwingUtilities.getWindowAncestor(this), "Продать палочку", true);
            dialog.setLayout(new BorderLayout());
            
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Панель для выбора палочки
            JPanel wandPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            wandPanel.add(new JLabel("Палочка:"));
            JComboBox<MagicWand> wandCombo = new JComboBox<>(wands.toArray(new MagicWand[0]));
            wandCombo.setPreferredSize(new Dimension(250, 30));
            wandCombo.setFont(new Font("Arial", Font.PLAIN, 14));
            wandPanel.add(wandCombo);
            mainPanel.add(wandPanel);
            
            mainPanel.add(Box.createVerticalStrut(20));
            
            // Панель для выбора покупателя
            JPanel wizardPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            wizardPanel.add(new JLabel("Покупатель:"));
            JComboBox<WizardCustomer> wizardCombo = new JComboBox<>(wizards.toArray(new WizardCustomer[0]));
            wizardCombo.setPreferredSize(new Dimension(250, 30));
            wizardCombo.setFont(new Font("Arial", Font.PLAIN, 14));
            wizardPanel.add(wizardCombo);
            mainPanel.add(wizardPanel);
            
            mainPanel.add(Box.createVerticalStrut(25));
            
            // Кнопка продажи
            JPanel buttonPanel = new JPanel();
            JButton sellButton = createStyledButton("Продать");
            sellButton.addActionListener(e -> {
                try {
                    MagicWand selectedWand = (MagicWand)wandCombo.getSelectedItem();
                    WizardCustomer selectedWizard = (WizardCustomer)wizardCombo.getSelectedItem();
                    
                    dbManager.sellWand(selectedWand.getId(), selectedWizard.getId());
                    loadWands();
                    dialog.dispose();
                    
                    JOptionPane.showMessageDialog(
                        this,
                        "Палочка успешно продана!",
                        "Успех",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(
                        dialog,
                        "Ошибка: " + ex.getMessage(),
                        "Ошибка",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            });
            buttonPanel.add(sellButton);
            mainPanel.add(buttonPanel);
            
            dialog.add(mainPanel, BorderLayout.CENTER);
            dialog.pack();
            dialog.setMinimumSize(new Dimension(500, 250));
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                this,
                "Ошибка загрузки данных: " + e.getMessage(),
                "Ошибка",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}

