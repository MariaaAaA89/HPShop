/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Basic;

/**
 *
 * @author Мария
 */

//hdfhhsfhjjkfk

import Frames.MainFrame ;
import Service.Database;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            Database dbManager = new Database();
            dbManager.initializeDatabase();
            try {
                dbManager.initializeDatabase();
            } catch (Exception e) {
                System.err.println("Ошибка при инициализации поставок: " + e.getMessage());
            }
            new MainFrame(dbManager).setVisible(true);
        });
    }
}
