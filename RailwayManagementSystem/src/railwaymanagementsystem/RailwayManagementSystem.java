package railwaymanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Vector;

public class RailwayManagementSystem extends JFrame {
    Connection conn;
    private JTabbedPane tabbedPane;

    public RailwayManagementSystem() {
        connectDatabase();
        initializeUI();
    }

    private void connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/railway_db", "root", "Kartik@2005"
            );
            System.out.println("Connected to Database!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initializeUI() {
        setTitle("Railway Management System");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 14));
        
        // Passenger Panel
        JPanel passengerPanel = new JPanel(new GridLayout(3, 1, 10, 30));
        passengerPanel.setBorder(BorderFactory.createEmptyBorder(90, 180, 90, 180));
        passengerPanel.setBackground(Color.LIGHT_GRAY);
        
        JButton btnAddPassenger = createButton("Add Passenger");
        JButton btnViewPassengers = createButton("View Passengers");
        JButton btnDeletePassenger = createButton("Delete Passenger");
        
        btnAddPassenger.addActionListener(e -> showAddPassenger());
        btnViewPassengers.addActionListener(e -> viewPassengers());
        btnDeletePassenger.addActionListener(e -> deletePassenger());
        
        passengerPanel.add(btnAddPassenger);
        passengerPanel.add(btnViewPassengers);
        passengerPanel.add(btnDeletePassenger);
        
        // Train Panel
        JPanel trainPanel = new JPanel(new GridLayout(3, 1, 10, 30));
        trainPanel.setBorder(BorderFactory.createEmptyBorder(90, 180, 90, 180));
        trainPanel.setBackground(Color.LIGHT_GRAY);
        
        JButton btnAddTrain = createButton("Add Train");
        JButton btnViewTrains = createButton("View Trains");
        JButton btnDeleteTrain = createButton("Delete Train");
        
        btnAddTrain.addActionListener(e -> showAddTrain());
        btnViewTrains.addActionListener(e -> viewTrains());
        btnDeleteTrain.addActionListener(e -> deleteTrain());
        
        trainPanel.add(btnAddTrain);
        trainPanel.add(btnViewTrains);
        trainPanel.add(btnDeleteTrain);
        
        // Booking Panel
        // In initializeUI(), modify the booking panel:
JPanel bookingPanel = new JPanel(new GridLayout(5, 1, 10, 30));  
bookingPanel.setBorder(BorderFactory.createEmptyBorder(60, 180, 60, 180));
bookingPanel.setBackground(Color.LIGHT_GRAY);

JButton btnBookTrain = createButton("Book Train");
JButton btnViewBookings = createButton("View Bookings");
JButton btnViewPayments = createButton("View Payments");  // New button
JButton btnCancelBooking = createButton("Cancel Booking");
JButton btnViewTicket = createButton("View Ticket"); // New button


btnBookTrain.addActionListener(e -> showBookTrain());
btnViewBookings.addActionListener(e -> viewBookings());
btnViewPayments.addActionListener(e -> viewPayments());  // New action
btnCancelBooking.addActionListener(e -> cancelBooking());
btnViewTicket.addActionListener(e -> viewTicket());

bookingPanel.add(btnBookTrain);
bookingPanel.add(btnViewBookings);
bookingPanel.add(btnViewPayments);  // Added to panel
bookingPanel.add(btnCancelBooking);
bookingPanel.add(btnViewTicket);
        
        tabbedPane.addTab("Passengers", passengerPanel);
        tabbedPane.addTab("Trains", trainPanel);
        tabbedPane.addTab("Bookings", bookingPanel);
        
        add(tabbedPane);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.PLAIN, 16));
        
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(250, 50));
        return button;
    }

    // Passenger Methods
    private void showAddPassenger() {
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();
        
        Object[] fields = {"Name:", nameField, "Age:", ageField};
        
        int option = JOptionPane.showConfirmDialog(this, fields, "Add Passenger", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                String sql = "INSERT INTO passengers (name, age) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nameField.getText());
                stmt.setInt(2, Integer.parseInt(ageField.getText()));
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(this, "Passenger added successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void viewPassengers() {
        try {
            String sql = "SELECT * FROM passengers";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            Vector<String> columnNames = new Vector<>();
            columnNames.add("ID");
            columnNames.add("Name");
            columnNames.add("Age");
            
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("passenger_id"));
                row.add(rs.getString("name"));
                row.add(rs.getInt("age"));
                data.add(row);
            }
            
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            
            JOptionPane.showMessageDialog(this, scrollPane, "Passenger List", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deletePassenger() {
        JTextField idField = new JTextField();
        
        int option = JOptionPane.showConfirmDialog(
            this, new Object[]{"Enter Passenger ID:", idField}, 
            "Delete Passenger", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                String sql = "DELETE FROM passengers WHERE passenger_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(idField.getText()));
                int rows = stmt.executeUpdate();
                
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Passenger deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "No passenger found with that ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Train Methods
    private void showAddTrain() {
    JTextField nameField = new JTextField();
    JTextField sourceField = new JTextField();
    JTextField destField = new JTextField();
    JTextField seatsField = new JTextField();
    JTextField priceField = new JTextField();  // New field
    
    Object[] fields = {
        "Train Name:", nameField,
        "Source:", sourceField,
        "Destination:", destField,
        "Seats Available:", seatsField,
        "Ticket Price:", priceField  // New field
    };
    
    int option = JOptionPane.showConfirmDialog(this, fields, "Add Train", JOptionPane.OK_CANCEL_OPTION);
    
    if (option == JOptionPane.OK_OPTION) {
        try {
            String sql = "INSERT INTO train (train_name, source, destination, seats_available, price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText());
            stmt.setString(2, sourceField.getText());
            stmt.setString(3, destField.getText());
            stmt.setInt(4, Integer.parseInt(seatsField.getText()));
            stmt.setDouble(5, Double.parseDouble(priceField.getText()));  // New
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Train added successfully!");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

    private void viewTrains() {
    try {
        String sql = "SELECT * FROM train";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID");
        columnNames.add("Name");
        columnNames.add("Source");
        columnNames.add("Destination");
        columnNames.add("Seats");
        columnNames.add("Price");

        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> row = new Vector<>();
            row.add(rs.getInt("train_id"));
            row.add(rs.getString("train_name"));
            row.add(rs.getString("source"));
            row.add(rs.getString("destination"));
            row.add(rs.getInt("seats_available"));
            row.add(rs.getDouble("price"));  // Keep numeric for easier updates
            data.add(row);
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        JButton updateBtn = new JButton("Update Price");
        updateBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a train to update.");
                return;
            }

            int trainId = (int) table.getValueAt(selectedRow, 0);
            double currentPrice = (double) table.getValueAt(selectedRow, 5);

            String newPriceStr = JOptionPane.showInputDialog(this, "Enter new price:", currentPrice);
            if (newPriceStr == null || newPriceStr.trim().isEmpty()) return;

            try {
                double newPrice = Double.parseDouble(newPriceStr);

                PreparedStatement ps = conn.prepareStatement("UPDATE train SET price = ? WHERE train_id = ?");
                ps.setDouble(1, newPrice);
                ps.setInt(2, trainId);
                int updated = ps.executeUpdate();

                if (updated > 0) {
                    JOptionPane.showMessageDialog(this, "Ticket price updated.");
                    viewTrains(); // Refresh view
                } else {
                    JOptionPane.showMessageDialog(this, "Update failed.");
                }

                ps.close();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid price format.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(updateBtn, BorderLayout.SOUTH);

        JOptionPane.showMessageDialog(this, panel, "Train List", JOptionPane.PLAIN_MESSAGE);

        rs.close();
        stmt.close();
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}


    private void deleteTrain() {
        JTextField idField = new JTextField();
        
        int option = JOptionPane.showConfirmDialog(
            this, new Object[]{"Enter Train ID:", idField}, 
            "Delete Train", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            try {
                String sql = "DELETE FROM train WHERE train_id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(idField.getText()));
                int rows = stmt.executeUpdate();
                
                if (rows > 0) {
                    JOptionPane.showMessageDialog(this, "Train deleted successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "No train found with that ID", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Booking Methods
    private void showBookTrain() {
    try {
        // Load passengers
        Vector<String> passengers = new Vector<>();
        Vector<Integer> passengerIds = new Vector<>();
        String passengerSql = "SELECT passenger_id, name FROM passengers";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(passengerSql)) {
            while (rs.next()) {
                passengerIds.add(rs.getInt("passenger_id"));
                passengers.add(rs.getString("name"));
            }
        }
        
        // Load available trains with prices
        Vector<String> trains = new Vector<>();
        Vector<Integer> trainIds = new Vector<>();
        Vector<Double> prices = new Vector<>();
        String trainSql = "SELECT train_id, train_name, source, destination, price FROM train WHERE seats_available > 0";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(trainSql)) {
            while (rs.next()) {
                trainIds.add(rs.getInt("train_id"));
                prices.add(rs.getDouble("price"));
                trains.add(String.format("%s (%s to %s) - ₹%.2f", 
                    rs.getString("train_name"),
                    rs.getString("source"), 
                    rs.getString("destination"),
                    rs.getDouble("price")));
            }
        }
        
        // Create booking form
        JComboBox<String> passengerCombo = new JComboBox<>(passengers);
        JComboBox<String> trainCombo = new JComboBox<>(trains);
        JLabel priceLabel = new JLabel("Price: ₹0.00");
        
        trainCombo.addActionListener(e -> {
            int idx = trainCombo.getSelectedIndex();
            if (idx >= 0) {
                priceLabel.setText(String.format("Price: ₹%.2f", prices.get(idx)));
            }
        });
        
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.add(new JLabel("Select Passenger:"));
        panel.add(passengerCombo);
        panel.add(new JLabel("Select Train:"));
        panel.add(trainCombo);
        panel.add(priceLabel);
        
        int option = JOptionPane.showConfirmDialog(
            this, panel, "Book Train", JOptionPane.OK_CANCEL_OPTION);
        
        if (option == JOptionPane.OK_OPTION) {
            int passengerIdx = passengerCombo.getSelectedIndex();
            int trainIdx = trainCombo.getSelectedIndex();
            
            if (passengerIdx == -1 || trainIdx == -1) {
                JOptionPane.showMessageDialog(this, "Please select both passenger and train", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Start transaction
            conn.setAutoCommit(false);
            
            try {
                // Book the ticket
                String bookSql = "INSERT INTO travelsin (passenger_id, train_id) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(bookSql, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setInt(1, passengerIds.get(passengerIdx));
                    stmt.setInt(2, trainIds.get(trainIdx));
                    stmt.executeUpdate();
                    
                    // Get the generated travel ID
                    ResultSet rs = stmt.getGeneratedKeys();
                    int travelId = -1;
                    if (rs.next()) {
                        travelId = rs.getInt(1);
                    }
                    
                    // Record payment
                    String paymentSql = "INSERT INTO payments (travel_id, amount, payment_date) VALUES (?, ?, NOW())";
                    try (PreparedStatement paymentStmt = conn.prepareStatement(paymentSql)) {
                        paymentStmt.setInt(1, travelId);
                        paymentStmt.setDouble(2, prices.get(trainIdx));
                        paymentStmt.executeUpdate();
                    }
                    
                    // Update seat availability
                    String updateSql = "UPDATE train SET seats_available = seats_available - 1 WHERE train_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setInt(1, trainIds.get(trainIdx));
                        updateStmt.executeUpdate();
                    }
                }
                
                conn.commit();
                JOptionPane.showMessageDialog(this, "Booking successful! Payment recorded.");
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void viewBookings() {
        try {
            String sql = "SELECT t.travel_id, p.name AS passenger_name, tr.train_name, tr.source, tr.destination " +
                         "FROM travelsin t " +
                         "JOIN passengers p ON t.passenger_id = p.passenger_id " +
                         "JOIN train tr ON t.train_id = tr.train_id";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            Vector<String> columnNames = new Vector<>();
            columnNames.add("Booking ID");
            columnNames.add("Passenger Name");
            columnNames.add("Train Name");
            columnNames.add("From");
            columnNames.add("To");
            
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("travel_id"));
                row.add(rs.getString("passenger_name"));
                row.add(rs.getString("train_name"));
                row.add(rs.getString("source"));
                row.add(rs.getString("destination"));
                data.add(row);
            }
            
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(800, 400));
            
            JOptionPane.showMessageDialog(this, scrollPane, "Booking List", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
        private void viewPayments() {
        try {
            String sql = "SELECT p.payment_id, ps.name as passenger, tr.train_name, " +
                         "p.amount, p.payment_date, tr.source, tr.destination " +
                         "FROM payments p " +
                         "JOIN travelsin t ON p.travel_id = t.travel_id " +
                         "JOIN passengers ps ON t.passenger_id = ps.passenger_id " +
                         "JOIN train tr ON t.train_id = tr.train_id " +
                         "ORDER BY p.payment_date DESC";
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            Vector<String> columnNames = new Vector<>();
            columnNames.add("Payment ID");
            columnNames.add("Passenger");
            columnNames.add("Train");
            columnNames.add("Amount");
            columnNames.add("Date");
            columnNames.add("Route");
            
            Vector<Vector<Object>> data = new Vector<>();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("payment_id"));
                row.add(rs.getString("passenger"));
                row.add(rs.getString("train_name"));
                row.add(String.format("₹%.2f", rs.getDouble("amount")));
                row.add(rs.getString("payment_date"));
                row.add(rs.getString("source") + " → " + rs.getString("destination"));
                data.add(row);
            }
            
            JTable table = new JTable(data, columnNames);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(800, 400));
            
            JOptionPane.showMessageDialog(this, scrollPane, "Payment History", JOptionPane.PLAIN_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

     private void viewTicket() {
    JTextField ticketIdField = new JTextField();
    
    int option = JOptionPane.showConfirmDialog(
        this, new Object[]{"Enter Booking ID:", ticketIdField}, 
        "View Ticket", JOptionPane.OK_CANCEL_OPTION);
    
    if (option == JOptionPane.OK_OPTION) {
        try {
            String ticketId = ticketIdField.getText();
            
            // Query to fetch ticket details using the booking (travel) ID
            String sql = "SELECT t.travel_id, p.name AS passenger_name, tr.train_name, tr.source, " +
                         "tr.destination, tr.price, p.age, p.name AS passenger_name " +
                         "FROM travelsin t " +
                         "JOIN passengers p ON t.passenger_id = p.passenger_id " +
                         "JOIN train tr ON t.train_id = tr.train_id " +
                         "WHERE t.travel_id = ?";
                         
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(ticketId)); // Use the input ticket ID
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String travelId = rs.getString("travel_id");
                String passengerName = rs.getString("passenger_name");
                String trainName = rs.getString("train_name");
                String source = rs.getString("source");
                String destination = rs.getString("destination");
                double price = rs.getDouble("price");
                int age = rs.getInt("age");
                
                // Format the ticket
                String ticketDetails = String.format(
                        
                    "WELCOME TO INDIAN RAILWAYS\n\n"
                     + "Ticket ID: %s\n" +
                    "Passenger: %s, Age: %d\n" +
                    "Train: %s\n" +
                    "Route: %s to %s\n" +
                    "Price: ₹%.2f", 
                    travelId, passengerName, age, trainName, source, destination, price);
                
                // Display the ticket details
                JOptionPane.showMessageDialog(this, ticketDetails, "Ticket Details", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "No ticket found for this ID", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
   

private void cancelBooking() {
    JTextField travelIdField = new JTextField();
    
    int option = JOptionPane.showConfirmDialog(this, new Object[] {
        "Enter Booking (Travel) ID to Cancel:", travelIdField
    }, "Cancel Booking", JOptionPane.OK_CANCEL_OPTION);
    
    if (option == JOptionPane.OK_OPTION) {
        try {
            int travelId = Integer.parseInt(travelIdField.getText());
            
            // Begin transaction
            conn.setAutoCommit(false);
            
            // Get train_id for seat rollback
            String getTrainIdSql = "SELECT train_id FROM travelsin WHERE travel_id = ?";
            PreparedStatement getTrainStmt = conn.prepareStatement(getTrainIdSql);
            getTrainStmt.setInt(1, travelId);
            ResultSet rs = getTrainStmt.executeQuery();
            
            int trainId = -1;
            if (rs.next()) {
                trainId = rs.getInt("train_id");
            } else {
                JOptionPane.showMessageDialog(this, "No booking found with that ID", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Delete payment
            PreparedStatement delPayment = conn.prepareStatement("DELETE FROM payments WHERE travel_id = ?");
            delPayment.setInt(1, travelId);
            delPayment.executeUpdate();

            // Delete booking
            PreparedStatement delBooking = conn.prepareStatement("DELETE FROM travelsin WHERE travel_id = ?");
            delBooking.setInt(1, travelId);
            delBooking.executeUpdate();

            // Update seat availability
            PreparedStatement updateSeats = conn.prepareStatement("UPDATE train SET seats_available = seats_available + 1 WHERE train_id = ?");
            updateSeats.setInt(1, trainId);
            updateSeats.executeUpdate();
            
            conn.commit();
            JOptionPane.showMessageDialog(this, "Booking cancelled successfully.");
        } catch (Exception ex) {
            try { conn.rollback(); } catch (SQLException se) {}
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException se) {}
        }
    }
}

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RailwayManagementSystem().setVisible(true);
        });
    }
}