package Frames;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Мария
 */

import GUI.Components;
import GUI.Delivery;
import GUI.Sales;
import GUI.Wands;
import GUI.Wizards;
import Service.Database;
import javax.swing.*;
import java.sql.SQLException;

public class MainFrame extends JFrame {
    private final Database dbManager;
    
    public MainFrame(Database dbManager) {
        this.dbManager = dbManager;
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("Магазин волшебных палочек Олливандеры");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        tabbedPane.addTab("Волшебные Палочки", new Wands(dbManager));
        tabbedPane.addTab("Клиенты", new Wizards(dbManager));
        tabbedPane.addTab("Компоненты", new Components(dbManager));
        tabbedPane.addTab("Продажи", new Sales(dbManager));
        tabbedPane.addTab("Отгрузка", new Delivery(dbManager));
        
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Удалить всё");
        
        JMenuItem clearDataItem = new JMenuItem("Удалить все данные");
        clearDataItem.addActionListener(e -> clearAllData());
        
        fileMenu.add(clearDataItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        
        add(tabbedPane);
    }
    
    private void clearAllData() {
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Удалить всё ?",
            "Подтверждение",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                dbManager.clearAllData();
                JOptionPane.showMessageDialog(
                    this,
                    "Все данные успешно удалены",
                    "Успех",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                    this,
                    "Ошибка при удаление данных: " + e.getMessage(),
                    "Упс, Ошибка",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
