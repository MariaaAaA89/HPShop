/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package Service;

/**
 *
 * @author Мария
 */
import model.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//
//public class Database {
//    private static final String DB_URL = "jdbc:sqlite:ollivanders.db";
//    
//    public Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(DB_URL);
//    }
//    
//   
//public void initializeDatabase() {
//    String[] createTables = {
//        "CREATE TABLE IF NOT EXISTS Components (" +
//        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//        "type TEXT NOT NULL CHECK(type IN ('wood', 'core'))," +  
//        "name TEXT NOT NULL UNIQUE," + 
//        "quantity INTEGER NOT NULL DEFAULT 0 CHECK(quantity >= 0))",  
//
//        "CREATE TABLE IF NOT EXISTS Wizards (" +
//        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//        "first_name TEXT NOT NULL," +
//        "last_name TEXT NOT NULL," +
//         
//        "school TEXT," +
//        "contact_info TEXT)",
//
//        "CREATE TABLE IF NOT EXISTS Wands (" +
//        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//        "creation_date DATE NOT NULL," +
//        "price REAL NOT NULL CHECK(price > 0)," +  // Добавлена проверка на положительную цену
//        "status TEXT NOT NULL DEFAULT 'available' CHECK(status IN ('available', 'sold'))," +  
//        "wood_id INTEGER NOT NULL," +
//        "core_id INTEGER NOT NULL," +
//        "wizard_id INTEGER," +
//        "sale_date DATE," +
//        "FOREIGN KEY(wood_id) REFERENCES components(id) ON DELETE RESTRICT," +  
//        "FOREIGN KEY(core_id) REFERENCES components(id) ON DELETE RESTRICT," +
//        "FOREIGN KEY(wizard_id) REFERENCES wizards(id) ON DELETE SET NULL)",  
//
//        "CREATE TABLE IF NOT EXISTS Deliveries (" +
//        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//        "delivery_date DATE NOT NULL," +
//        "supplier_name TEXT NOT NULL," +
//        "is_seasonal BOOLEAN NOT NULL DEFAULT FALSE)",
//
//        "CREATE TABLE IF NOT EXISTS delivery_items (" +
//        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//        "delivery_id INTEGER NOT NULL," +
//        "component_id INTEGER NOT NULL," +
//        "quantity INTEGER NOT NULL CHECK(quantity > 0)," +  
//        "unit_price REAL NOT NULL CHECK(unit_price > 0)," +  
//        "FOREIGN KEY(delivery_id) REFERENCES deliveries(id) ON DELETE CASCADE," +  
//        "FOREIGN KEY(component_id) REFERENCES components(id) ON DELETE RESTRICT)"  
//    };
//
//
//  
//      
//        try (Connection conn = getConnection();
//             Statement stmt = conn.createStatement()) {
//            
//                for (String sql : createTables) {
//                    stmt.execute(sql);
//                }
//            } catch (SQLException e) {
//                System.err.println("Ошибка при инициализации базы данных: " + e.getMessage());
//            }
//        }
//    
//        public void addComponent(WandComponent component) throws SQLException {
//            String sql = "INSERT INTO components (type, name, quantity) VALUES (?, ?, ?)";
//
//            try (Connection conn = getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//                stmt.setString(1, component.getType());
//                stmt.setString(2, component.getName());
//                stmt.setInt(3, component.getQuantity());
//
//                stmt.executeUpdate();
//
//                try (ResultSet rs = stmt.getGeneratedKeys()) {
//                    if (rs.next()) {
//                        component.setId(rs.getInt(1));
//                    }
//                }
//            }
//        }
//    
//        public List<WandComponent> getAllComponents() throws SQLException {
//            List<WandComponent> components = new ArrayList<>();
//            String sql = "SELECT * FROM components";
//
//            try (Connection conn = getConnection();
//                 Statement stmt = conn.createStatement();
//                 ResultSet rs = stmt.executeQuery(sql)) {
//
//                while (rs.next()) {
//                    WandComponent component = new WandComponent();
//                    component.setId(rs.getInt("id"));
//                    component.setType(rs.getString("type"));
//                    component.setName(rs.getString("name"));
//                    component.setQuantity(rs.getInt("quantity"));
//
//                    components.add(component);
//                }
//            }
//            return components;
//        }
//    
//        public void addWizard(WizardCustomer wizard) throws SQLException {
//            String sql = "INSERT INTO wizards (first_name, last_name, school, contact_info) " +
//                         "VALUES (?, ?, ?, ?)";
//
//            try (Connection conn = getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//                stmt.setString(1, wizard.getFirstName());
//                stmt.setString(2, wizard.getLastName());
//                
//                stmt.setString(3, wizard.getSchool());
//                stmt.setString(4, wizard.getContactInfo());
//
//                stmt.executeUpdate();
//
//                try (ResultSet rs = stmt.getGeneratedKeys()) {
//                    if (rs.next()) {
//                        wizard.setId(rs.getInt(1));
//                    }
//                }
//            }
//        }
//    
//        public List<WizardCustomer> getAllWizards() throws SQLException {
//            List<WizardCustomer> wizards = new ArrayList<>();
//            String sql = "SELECT * FROM wizards";
//
//            try (Connection conn = getConnection();
//                 Statement stmt = conn.createStatement();
//                 ResultSet rs = stmt.executeQuery(sql)) {
//
//                while (rs.next()) {
//                    WizardCustomer wizard = new WizardCustomer();
//                    wizard.setId(rs.getInt("id"));
//                    wizard.setFirstName(rs.getString("first_name"));
//                    wizard.setLastName(rs.getString("last_name"));
//                    
//                    wizard.setSchool(rs.getString("school"));
//                    wizard.setContactInfo(rs.getString("contact_info"));
//
//                    wizards.add(wizard);
//                }
//            }
//            return wizards;
//        }
//    
//        public void addWand(MagicWand wand) throws SQLException {
//            Connection conn = null;
//            try{
//                conn = getConnection();
//                conn.setAutoCommit(false);
//            
//            if (!areComponentsAvailable(wand.getWoodId(), wand.getCoreId())) {
//                throw new SQLException("Недостаточно компонентов для создания палочки");
//            }
//            String sql = "INSERT INTO wands (creation_date, price, status, wood_id, core_id) " +
//                         "VALUES (?, ?, ?, ?, ?)";
//
//            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//                stmt.setDate(1, Date.valueOf(wand.getCreationDate()));
//                stmt.setDouble(2, wand.getPrice());
//                stmt.setString(3, wand.getStatus());
//                stmt.setInt(4, wand.getWoodId());
//                stmt.setInt(5, wand.getCoreId());
//
//                stmt.executeUpdate();
//
//                try (ResultSet rs = stmt.getGeneratedKeys()) {
//                    if (rs.next()) {
//                        wand.setId(rs.getInt(1));
//                    }
//                }
//            }
//            
//            updateComponentQuantity(conn, wand.getWoodId(), -1);
//            updateComponentQuantity(conn, wand.getCoreId(), -1);
//            } catch (SQLException e) {
//            if (conn != null) conn.rollback();
//            throw e;
//            } finally {
//                if (conn != null) conn.setAutoCommit(true);
//            }
//        }
//    
//        public List<MagicWand> getAvailableWands() throws SQLException {
//            List<MagicWand> wands = new ArrayList<>();
//            String sql = "SELECT * FROM wands WHERE status = 'available'";
//
//            try (Connection conn = getConnection();
//                 Statement stmt = conn.createStatement();
//                 ResultSet rs = stmt.executeQuery(sql)) {
//
//                while (rs.next()) {
//                    MagicWand wand = new MagicWand();
//                    wand.setId(rs.getInt("id"));
//                    wand.setCreationDate(rs.getDate("creation_date").toLocalDate());
//                    wand.setPrice(rs.getDouble("price"));
//                    wand.setStatus(rs.getString("status"));
//                    wand.setWoodId(rs.getInt("wood_id"));
//                    wand.setCoreId(rs.getInt("core_id"));
//
//                    wands.add(wand);
//                }
//            }
//            return wands;
//        }
//    
//        public void sellWand(int wandId, int wizardId) throws SQLException {
//            String sql = "UPDATE wands SET status = 'sold', wizard_id = ?, sale_date = ? " +
//                         "WHERE id = ?";
//
//            try (Connection conn = getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                stmt.setInt(1, wizardId);
//                stmt.setDate(2, Date.valueOf(LocalDate.now()));
//                stmt.setInt(3, wandId);
//
//                stmt.executeUpdate();
//            }
//        }
//    
//        public void clearAllData() throws SQLException {
//            try (Connection conn = getConnection();
//                 Statement stmt = conn.createStatement()) {
//
//                stmt.execute("PRAGMA foreign_keys = OFF");
//                stmt.execute("DROP TABLE IF EXISTS wands");
//                stmt.execute("DROP TABLE IF EXISTS wizards");
//                stmt.execute("DROP TABLE IF EXISTS components");
//                stmt.execute("DROP TABLE IF EXISTS deliveries");
//                stmt.execute("DROP TABLE IF EXISTS delivery_items");
//                stmt.execute("PRAGMA foreign_keys = ON");
//                initializeDatabase();
//            }
//        }
//    
//        public List<MagicWand> getSoldWandsWithWizards() throws SQLException {
//            List<MagicWand> wands = new ArrayList<>();
//            String sql = "SELECT w.*, wz.first_name, wz.last_name, wz.school " +
//                         "FROM wands w " +
//                         "JOIN wizards wz ON w.wizard_id = wz.id " +
//                         "WHERE w.status = 'sold'";
//
//            try (Connection conn = getConnection();
//                 Statement stmt = conn.createStatement();
//                 ResultSet rs = stmt.executeQuery(sql)) {
//
//                while (rs.next()) {
//                    MagicWand wand = new MagicWand();
//                    wand.setId(rs.getInt("id"));
//                    wand.setCreationDate(rs.getDate("creation_date").toLocalDate());
//                    wand.setPrice(rs.getDouble("price"));
//                    wand.setStatus(rs.getString("status"));
//                    wand.setWoodId(rs.getInt("wood_id"));
//                    wand.setCoreId(rs.getInt("core_id"));
//                    wand.setWizardId(rs.getInt("wizard_id"));
//                    wand.setSaleDate(rs.getDate("sale_date").toLocalDate());
//
//                    WizardCustomer wizard = new WizardCustomer();
//                    wizard.setId(rs.getInt("wizard_id"));
//                    wizard.setFirstName(rs.getString("first_name"));
//                    wizard.setLastName(rs.getString("last_name"));
//                    wizard.setSchool(rs.getString("school"));
//
//                    wand.setOwner(wizard);
//
//                    wands.add(wand);
//                }
//            }
//            return wands;
//        }
//
//        public void addDelivery(ComponentDelivery delivery) throws SQLException {
//            String sql = "INSERT INTO deliveries (delivery_date, supplier_name, is_seasonal) VALUES (?, ?, ?)";
//
//            try (Connection conn = getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//                stmt.setDate(1, Date.valueOf(delivery.getDeliveryDate()));
//                stmt.setString(2, delivery.getSupplierName());
//                stmt.setBoolean(3, delivery.isSeasonal());
//
//                stmt.executeUpdate();
//
//                try (ResultSet rs = stmt.getGeneratedKeys()) {
//                    if (rs.next()) {
//                        delivery.setId(rs.getInt(1));
//                    }
//                }
//
//                addDeliveryItems(delivery.getId(), delivery.getItems());
//            }
//        }
//
//        private void addDeliveryItems(int deliveryId, List<DeliveryItem> items) throws SQLException {
//            String sql = "INSERT INTO delivery_items (delivery_id, component_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
//
//            try (Connection conn = getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                for (DeliveryItem item : items) {
//                    stmt.setInt(1, deliveryId);
//                    stmt.setInt(2, item.getComponentId());
//                    stmt.setInt(3, item.getQuantity());
//                    stmt.setDouble(4, item.getUnitPrice());
//                    stmt.addBatch();
//
//                    updateComponentQuantity(getConnection(), item.getComponentId(), item.getQuantity());
//                }
//
//                stmt.executeBatch();
//            }
//        }
//    
//    
//        public List<DeliveryItem> getDeliveryItems(int deliveryId) throws SQLException {
//            List<DeliveryItem> items = new ArrayList<>();
//            String sql = "SELECT * FROM delivery_items WHERE delivery_id = ?";
//
//            try (Connection conn = getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                stmt.setInt(1, deliveryId);
//                try (ResultSet rs = stmt.executeQuery()) {
//                    while (rs.next()) {
//                        DeliveryItem item = new DeliveryItem();
//                        item.setId(rs.getInt("id"));
//                        item.setDeliveryId(rs.getInt("delivery_id"));
//                        item.setComponentId(rs.getInt("component_id"));
//                        item.setQuantity(rs.getInt("quantity"));
//                        item.setUnitPrice(rs.getDouble("unit_price"));
//
//                        items.add(item);
//                    }
//                }
//            }
//            return items;
//        }
//    
//        public boolean hasDeliveryThisWeek() throws SQLException {
//           String sql = "SELECT COUNT(*) FROM deliveries WHERE delivery_date BETWEEN ? AND ?";
//           LocalDate today = LocalDate.now();
//           LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);
//
//           try (Connection conn = getConnection();
//                PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//               stmt.setDate(1, Date.valueOf(startOfWeek));
//               stmt.setDate(2, Date.valueOf(today));
//
//               try (ResultSet rs = stmt.executeQuery()) {
//                   return rs.next() && rs.getInt(1) > 0;
//               }
//           }
//       }
//     
//        public List<WandComponent> getComponentsLowStock(int threshold) throws SQLException {
//            List<WandComponent> components = new ArrayList<>();
//            String sql = "SELECT * FROM components WHERE quantity < ?";
//
//            try (Connection conn = getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                stmt.setInt(1, threshold);
//                try (ResultSet rs = stmt.executeQuery()) {
//                    while (rs.next()) {
//                        WandComponent component = new WandComponent();
//                        component.setId(rs.getInt("id"));
//                        component.setType(rs.getString("type"));
//                        component.setName(rs.getString("name"));
//                        component.setQuantity(rs.getInt("quantity"));
//
//                        components.add(component);
//                    }
//                }
//            }
//            return components;
//        }
//    
//        public List<WandComponent> getPopularComponents(String type, int limit) throws SQLException {
//            List<WandComponent> components = new ArrayList<>();
//            String sql = "SELECT c.* FROM components c " +
//                         "JOIN (SELECT wood_id AS comp_id, COUNT(*) AS cnt FROM wands GROUP BY wood_id " +
//                         "      UNION ALL " +
//                         "      SELECT core_id AS comp_id, COUNT(*) AS cnt FROM wands GROUP BY core_id) stats " +
//                         "ON c.id = stats.comp_id " +
//                         "WHERE c.type = ? " +
//                         "ORDER BY stats.cnt DESC LIMIT ?";
//
//            try (Connection conn = getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                stmt.setString(1, type);
//                stmt.setInt(2, limit);
//                try (ResultSet rs = stmt.executeQuery()) {
//                    while (rs.next()) {
//                        WandComponent component = new WandComponent();
//                        component.setId(rs.getInt("id"));
//                        component.setType(rs.getString("type"));
//                        component.setName(rs.getString("name"));
//                        component.setQuantity(rs.getInt("quantity"));
//
//                        components.add(component);
//                    }
//                }
//            }
//            return components;
//        }
//
//        public double getComponentPrice(int componentId) throws SQLException {
//            String sql = "SELECT unit_price FROM delivery_items " +
//                         "WHERE component_id = ? ORDER BY delivery_id DESC LIMIT 1";
//
//            try (Connection conn = getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                stmt.setInt(1, componentId);
//                try (ResultSet rs = stmt.executeQuery()) {
//                    return rs.next() ? rs.getDouble(1) : 10.0; 
//                }
//            }
//        }
//
//        public List<ComponentDelivery> getAllDeliveries() throws SQLException {
//                List<ComponentDelivery> deliveries = new ArrayList<>();
//                String sql = "SELECT * FROM deliveries ORDER BY delivery_date DESC";
//
//                try (Connection conn = getConnection();
//                     Statement stmt = conn.createStatement();
//                     ResultSet rs = stmt.executeQuery(sql)) {
//
//                    while (rs.next()) {
//                        ComponentDelivery delivery = new ComponentDelivery();
//                        delivery.setId(rs.getInt("id"));
//                        delivery.setDeliveryDate(rs.getDate("delivery_date").toLocalDate());
//                        delivery.setSupplierName(rs.getString("supplier_name"));
//                        delivery.setSeasonal(rs.getBoolean("is_seasonal"));
//
//                        delivery.setItems(getDeliveryItems(delivery.getId()));
//
//                        deliveries.add(delivery);
//                    }
//                }
//                return deliveries;
//            }
//        
//            public void updateComponentQuantity(Connection conn, int componentId, int delta) throws SQLException {
//                String sql = "UPDATE components SET quantity = quantity + ? WHERE id = ?";
//
//                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                    stmt.setInt(1, delta);
//                    stmt.setInt(2, componentId);
//                    stmt.executeUpdate();
//                }
//            }
//            
//        public boolean areComponentsAvailable(int woodId, int coreId) throws SQLException {
//            String sql = "SELECT COUNT(*) FROM components WHERE id IN (?, ?) AND quantity > 0";
//
//            try (Connection conn = getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                stmt.setInt(1, woodId);
//                stmt.setInt(2, coreId);
//
//                try (ResultSet rs = stmt.executeQuery()) {
//                    return rs.next() && rs.getInt(1) == 2;
//                }
//            }
//        }
//    
//        public List<WandComponent> getAvailableComponents(String type) throws SQLException {
//            String sql = "SELECT * FROM components WHERE type = ? AND quantity > 0";
//
//            List<WandComponent> components = new ArrayList<>();
//            try (Connection conn = getConnection();
//                 PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//                stmt.setString(1, type);
//                try (ResultSet rs = stmt.executeQuery()) {
//                    while (rs.next()) {
//                        WandComponent component = new WandComponent();
//                        component.setId(rs.getInt("id"));
//                        component.setType(rs.getString("type"));
//                        component.setName(rs.getString("name"));
//                        component.setQuantity(rs.getInt("quantity"));
//                        components.add(component);
//                    }
//                }
//            }
//            return components;
//        }
//
//    public void closeConnection() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    public void restoreFromBackup() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    public void createBackup() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//
//    List<ComponentDelivery> getDeliveriesAfterDate(LocalDate cutoffDate) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
//}
//
//
public class Database {
    private static final String DB_URL = "jdbc:sqlite:ollivanders.db";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }


    //public void initializeDatabase() {
//    String[] createTables = {
//        "CREATE TABLE IF NOT EXISTS Components (" +
//        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//        "type TEXT NOT NULL CHECK(type IN ('wood', 'core'))," +  // Добавлено NOT NULL
//        "name TEXT NOT NULL UNIQUE," +  // Добавлено UNIQUE для избежания дубликатов
//        "quantity INTEGER NOT NULL DEFAULT 0 CHECK(quantity >= 0))",  // Добавлена проверка на отрицательные значения
//
//        "CREATE TABLE IF NOT EXISTS Wizards (" +
//        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//        "first_name TEXT NOT NULL," +
//        "last_name TEXT NOT NULL," +
//         
//        "school TEXT," +
//        "contact_info TEXT)",
//
//        "CREATE TABLE IF NOT EXISTS Wands (" +
//        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//        "creation_date DATE NOT NULL," +
//        "price REAL NOT NULL CHECK(price > 0)," +  // Добавлена проверка на положительную цену
//        "status TEXT NOT NULL DEFAULT 'available' CHECK(status IN ('available', 'sold'))," +  // Добавлено NOT NULL
//        "wood_id INTEGER NOT NULL," +
//        "core_id INTEGER NOT NULL," +
//        "wizard_id INTEGER," +
//        "sale_date DATE," +
//        "FOREIGN KEY(wood_id) REFERENCES components(id) ON DELETE RESTRICT," +  // Добавлено действие при удалении
//        "FOREIGN KEY(core_id) REFERENCES components(id) ON DELETE RESTRICT," +
//        "FOREIGN KEY(wizard_id) REFERENCES wizards(id) ON DELETE SET NULL)",  // Добавлено действие при удалении
//
//        "CREATE TABLE IF NOT EXISTS Deliveries (" +
//        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//        "delivery_date DATE NOT NULL," +
//        "supplier_name TEXT NOT NULL," +
//        "is_seasonal BOOLEAN NOT NULL DEFAULT FALSE)",
//
//        "CREATE TABLE IF NOT EXISTS delivery_items (" +
//        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//        "delivery_id INTEGER NOT NULL," +
//        "component_id INTEGER NOT NULL," +
//        "quantity INTEGER NOT NULL CHECK(quantity > 0)," +  // Добавлена проверка на положительное количество
//        "unit_price REAL NOT NULL CHECK(unit_price > 0)," +  // Добавлена проверка на положительную цену
//        "FOREIGN KEY(delivery_id) REFERENCES deliveries(id) ON DELETE CASCADE," +  // Добавлено действие при удалении
//        "FOREIGN KEY(component_id) REFERENCES components(id) ON DELETE RESTRICT)"  // Добавлено действие при удалении
//    };
    public void initializeDatabase() {
        String[] createTables = {
                "CREATE TABLE IF NOT EXISTS Components (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "type TEXT NOT NULL CHECK(type IN ('wood', 'core'))," +  // Добавлено NOT NULL
                        "name TEXT NOT NULL UNIQUE," +  // Добавлено UNIQUE для избежания дубликатов
                        "quantity INTEGER NOT NULL DEFAULT 0 CHECK(quantity >= 0))",  // Добавлена проверка на отрицательные значения

                "CREATE TABLE IF NOT EXISTS Wizards (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "first_name TEXT NOT NULL," +
                        "last_name TEXT NOT NULL," +

                        "school TEXT," +
                        "contact_info TEXT)",

                "CREATE TABLE IF NOT EXISTS Wands (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "creation_date DATE NOT NULL," +
                        "price REAL NOT NULL CHECK(price > 0)," +  // Добавлена проверка на положительную цену
                        "status TEXT NOT NULL DEFAULT 'available' CHECK(status IN ('available', 'sold'))," +  // Добавлено NOT NULL
                        "wood_id INTEGER NOT NULL," +
                        "core_id INTEGER NOT NULL," +
                        "wizard_id INTEGER," +
                        "sale_date DATE," +
                        "FOREIGN KEY(wood_id) REFERENCES components(id) ON DELETE RESTRICT," +  // Добавлено действие при удалении
                        "FOREIGN KEY(core_id) REFERENCES components(id) ON DELETE RESTRICT," +
                        "FOREIGN KEY(wizard_id) REFERENCES wizards(id) ON DELETE SET NULL)",  // Добавлено действие при удалении

                "CREATE TABLE IF NOT EXISTS Deliveries (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "delivery_date DATE NOT NULL," +
                        "supplier_name TEXT NOT NULL," +
                        "is_seasonal BOOLEAN NOT NULL DEFAULT FALSE)",

                "CREATE TABLE IF NOT EXISTS delivery_items (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "delivery_id INTEGER NOT NULL," +
                        "component_id INTEGER NOT NULL," +
                        "quantity INTEGER NOT NULL CHECK(quantity > 0)," +  // Добавлена проверка на положительное количество
                        "unit_price REAL NOT NULL CHECK(unit_price > 0)," +  // Добавлена проверка на положительную цену
                        "FOREIGN KEY(delivery_id) REFERENCES deliveries(id) ON DELETE CASCADE," +  // Добавлено действие при удалении
                        "FOREIGN KEY(component_id) REFERENCES components(id) ON DELETE RESTRICT)"  // Добавлено действие при удалении
        };


        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            for (String sql : createTables) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при инициализации базы данных: " + e.getMessage());
        }
    }

    public void addComponent(WandComponent component) throws SQLException {
        String sql = "INSERT INTO components (type, name, quantity) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, component.getType());
            stmt.setString(2, component.getName());
            stmt.setInt(3, component.getQuantity());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    component.setId(rs.getInt(1));
                }
            }
        }
    }

    public List<WandComponent> getAllComponents() throws SQLException {
        List<WandComponent> components = new ArrayList<>();
        String sql = "SELECT * FROM components";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                WandComponent component = new WandComponent();
                component.setId(rs.getInt("id"));
                component.setType(rs.getString("type"));
                component.setName(rs.getString("name"));
                component.setQuantity(rs.getInt("quantity"));

                components.add(component);
            }
        }
        return components;
    }

    public void addWizard(WizardCustomer wizard) throws SQLException {
        String sql = "INSERT INTO wizards (first_name, last_name, school, contact_info) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, wizard.getFirstName());
            stmt.setString(2, wizard.getLastName());

            stmt.setString(3, wizard.getSchool());
            stmt.setString(4, wizard.getContactInfo());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    wizard.setId(rs.getInt(1));
                }
            }
        }
    }

    public void deleteWizard(int wizardId) {
        String sql = "DELETE FROM wizards WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, wizardId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<WizardCustomer> getAllWizards() throws SQLException {
        List<WizardCustomer> wizards = new ArrayList<>();
        String sql = "SELECT * FROM wizards";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                WizardCustomer wizard = new WizardCustomer();
                wizard.setId(rs.getInt("id"));
                wizard.setFirstName(rs.getString("first_name"));
                wizard.setLastName(rs.getString("last_name"));

                wizard.setSchool(rs.getString("school"));
                wizard.setContactInfo(rs.getString("contact_info"));

                wizards.add(wizard);
            }
        }
        return wizards;
    }

    public void addWand(MagicWand wand) throws SQLException {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false);

            if (!areComponentsAvailable(wand.getWoodId(), wand.getCoreId())) {
                throw new SQLException("Недостаточно компонентов для создания палочки");
            }
            String sql = "INSERT INTO wands (creation_date, price, status, wood_id, core_id) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                stmt.setDate(1, Date.valueOf(wand.getCreationDate()));
                stmt.setDouble(2, wand.getPrice());
                stmt.setString(3, wand.getStatus());
                stmt.setInt(4, wand.getWoodId());
                stmt.setInt(5, wand.getCoreId());

                stmt.executeUpdate();

                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        wand.setId(rs.getInt(1));
                    }
                }
            }

            updateComponentQuantity(conn, wand.getWoodId(), -1);
            updateComponentQuantity(conn, wand.getCoreId(), -1);
        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.setAutoCommit(true);
        }
    }

    public List<MagicWand> getAvailableWands() throws SQLException {
        List<MagicWand> wands = new ArrayList<>();
        String sql = "SELECT * FROM wands WHERE status = 'available'";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MagicWand wand = new MagicWand();
                wand.setId(rs.getInt("id"));
                wand.setCreationDate(rs.getDate("creation_date").toLocalDate());
                wand.setPrice(rs.getDouble("price"));
                wand.setStatus(rs.getString("status"));
                wand.setWoodId(rs.getInt("wood_id"));
                wand.setCoreId(rs.getInt("core_id"));

                wands.add(wand);
            }
        }
        return wands;
    }

    public void sellWand(int wandId, int wizardId) throws SQLException {
        String sql = "UPDATE wands SET status = 'sold', wizard_id = ?, sale_date = ? " +
                "WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wizardId);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setInt(3, wandId);

            stmt.executeUpdate();
        }
    }

    public void clearAllData() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = OFF");
            stmt.execute("DROP TABLE IF EXISTS wands");
            stmt.execute("DROP TABLE IF EXISTS wizards");
            stmt.execute("DROP TABLE IF EXISTS components");
            stmt.execute("DROP TABLE IF EXISTS deliveries");
            stmt.execute("DROP TABLE IF EXISTS delivery_items");
            stmt.execute("PRAGMA foreign_keys = ON");
            initializeDatabase();
        }
    }

    public List<MagicWand> getSoldWandsWithWizards() throws SQLException {
        List<MagicWand> wands = new ArrayList<>();
        String sql = "SELECT w.*, wz.first_name, wz.last_name, wz.school " +
                "FROM wands w " +
                "JOIN wizards wz ON w.wizard_id = wz.id " +
                "WHERE w.status = 'sold'";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MagicWand wand = new MagicWand();
                wand.setId(rs.getInt("id"));
                wand.setCreationDate(rs.getDate("creation_date").toLocalDate());
                wand.setPrice(rs.getDouble("price"));
                wand.setStatus(rs.getString("status"));
                wand.setWoodId(rs.getInt("wood_id"));
                wand.setCoreId(rs.getInt("core_id"));
                wand.setWizardId(rs.getInt("wizard_id"));
                wand.setSaleDate(rs.getDate("sale_date").toLocalDate());

                WizardCustomer wizard = new WizardCustomer();
                wizard.setId(rs.getInt("wizard_id"));
                wizard.setFirstName(rs.getString("first_name"));
                wizard.setLastName(rs.getString("last_name"));
                wizard.setSchool(rs.getString("school"));

                wand.setOwner(wizard);

                wands.add(wand);
            }
        }
        return wands;
    }

    public void addDelivery(ComponentDelivery delivery) throws SQLException {
        String sql = "INSERT INTO deliveries (delivery_date, supplier_name, is_seasonal) VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, Date.valueOf(delivery.getDeliveryDate()));
            stmt.setString(2, delivery.getSupplierName());
            stmt.setBoolean(3, delivery.isSeasonal());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    delivery.setId(rs.getInt(1));
                }
            }

            addDeliveryItems(delivery.getId(), delivery.getItems());
        }
    }

    private void addDeliveryItems(int deliveryId, List<DeliveryItem> items) throws SQLException {
        String sql = "INSERT INTO delivery_items (delivery_id, component_id, quantity, unit_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (DeliveryItem item : items) {
                stmt.setInt(1, deliveryId);
                stmt.setInt(2, item.getComponentId());
                stmt.setInt(3, item.getQuantity());
                stmt.setDouble(4, item.getUnitPrice());
                stmt.addBatch();

                updateComponentQuantity(getConnection(), item.getComponentId(), item.getQuantity());
            }

            stmt.executeBatch();
        }
    }


    public List<DeliveryItem> getDeliveryItems(int deliveryId) throws SQLException {
        List<DeliveryItem> items = new ArrayList<>();
        String sql = "SELECT * FROM delivery_items WHERE delivery_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, deliveryId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DeliveryItem item = new DeliveryItem();
                    item.setId(rs.getInt("id"));
                    item.setDeliveryId(rs.getInt("delivery_id"));
                    item.setComponentId(rs.getInt("component_id"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setUnitPrice(rs.getDouble("unit_price"));

                    items.add(item);
                }
            }
        }
        return items;
    }

    public boolean hasDeliveryThisWeek() throws SQLException {
        String sql = "SELECT COUNT(*) FROM deliveries WHERE delivery_date BETWEEN ? AND ?";
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.minusDays(today.getDayOfWeek().getValue() - 1);

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(startOfWeek));
            stmt.setDate(2, Date.valueOf(today));

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    public List<WandComponent> getComponentsLowStock(int threshold) throws SQLException {
        List<WandComponent> components = new ArrayList<>();
        String sql = "SELECT * FROM components WHERE quantity < ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, threshold);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    WandComponent component = new WandComponent();
                    component.setId(rs.getInt("id"));
                    component.setType(rs.getString("type"));
                    component.setName(rs.getString("name"));
                    component.setQuantity(rs.getInt("quantity"));

                    components.add(component);
                }
            }
        }
        return components;
    }

    public List<WandComponent> getPopularComponents(String type, int limit) throws SQLException {
        List<WandComponent> components = new ArrayList<>();
        String sql = "SELECT id, type, name, quantity FROM components WHERE type = ? ORDER BY quantity ASC LIMIT ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            stmt.setInt(2, limit);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    WandComponent component = new WandComponent();
                    component.setId(rs.getInt("id"));
                    component.setType(rs.getString("type"));
                    component.setName(rs.getString("name"));
                    component.setQuantity(rs.getInt("quantity"));

                    components.add(component);
                }
            }
        }
        return components;
    }

    public double getComponentPrice(int componentId) throws SQLException {
        String sql = "SELECT unit_price FROM delivery_items " +
                "WHERE component_id = ? ORDER BY delivery_id DESC LIMIT 1";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, componentId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getDouble(1) : 10.0;
            }
        }
    }

    public List<ComponentDelivery> getAllDeliveries() throws SQLException {
        List<ComponentDelivery> deliveries = new ArrayList<>();
        String sql = "SELECT * FROM deliveries ORDER BY delivery_date DESC";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ComponentDelivery delivery = new ComponentDelivery();
                delivery.setId(rs.getInt("id"));
                delivery.setDeliveryDate(rs.getDate("delivery_date").toLocalDate());
                delivery.setSupplierName(rs.getString("supplier_name"));
                delivery.setSeasonal(rs.getBoolean("is_seasonal"));

                delivery.setItems(getDeliveryItems(delivery.getId()));

                deliveries.add(delivery);
            }
        }
        return deliveries;
    }

    public void updateComponentQuantity(Connection conn, int componentId, int delta) throws SQLException {
        String sql = "UPDATE components SET quantity = quantity + ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, delta);
            stmt.setInt(2, componentId);
            stmt.executeUpdate();
        }
    }

    public boolean areComponentsAvailable(int woodId, int coreId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM components WHERE id IN (?, ?) AND quantity > 0";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, woodId);
            stmt.setInt(2, coreId);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() && rs.getInt(1) == 2;
            }
        }
    }

    public List<WandComponent> getAvailableComponents(String type) throws SQLException {
        String sql = "SELECT * FROM components WHERE type = ? AND quantity > 0";

        List<WandComponent> components = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, type);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    WandComponent component = new WandComponent();
                    component.setId(rs.getInt("id"));
                    component.setType(rs.getString("type"));
                    component.setName(rs.getString("name"));
                    component.setQuantity(rs.getInt("quantity"));
                    components.add(component);
                }
            }
        }
        return components;
    }

    public void closeConnection() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void restoreFromBackup() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void createBackup() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    List<ComponentDelivery> getDeliveriesAfterDate(LocalDate cutoffDate) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

