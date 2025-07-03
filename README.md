# 🚆 Railway Management System (Java + MySQL)

This is a desktop-based Railway Management System built with **Java Swing** and connected to a **MySQL** database using **JDBC**. It features a role-based login system with separate dashboards for Admin and Passengers, allowing full control over train management, ticket bookings, payments, and passenger data.

---

## 📌 Key Features

### 🔐 Authentication
- Role-based login system
- Separate panels for **Admin** and **Passenger**

### 👥 Passenger Panel
- Add Passenger
- View Passenger
- Delete Passenger
  
### 🚆 Train Panel
- Add Train
- View Train
- Delete Train
- Update price of Train ticket

### 🎫 Booking Train
- Book Train
- View Bookings
- View Payments
- Cancel Bookings
- View Ticket

---

## 🛠 Technologies Used

| Technology       | Description                |
|------------------|----------------------------|
| Java             | Core language              |
| Java Swing       | GUI components             |
| MySQL            | Backend database           |
| JDBC             | Java Database Connectivity |
| NetBeans IDE     | Recommended IDE            |

---

## 💾 Database Setup

1. Create a database in MySQL:

```sql
CREATE DATABASE railway;
USE railway;

CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  username VARCHAR(50) UNIQUE,
  password VARCHAR(50),
  role ENUM('admin', 'passenger') NOT NULL
);

CREATE TABLE trains (
  train_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  source VARCHAR(100),
  destination VARCHAR(100),
  departure_time TIME,
  arrival_time TIME,
  fare DOUBLE
);

CREATE TABLE passengers (
  passenger_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100),
  age INT,
  gender VARCHAR(10),
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE bookings (
  booking_id INT PRIMARY KEY AUTO_INCREMENT,
  train_id INT,
  passenger_id INT,
  travel_date DATE,
  FOREIGN KEY (train_id) REFERENCES trains(train_id),
  FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id)
);

CREATE TABLE payments (
  payment_id INT PRIMARY KEY AUTO_INCREMENT,
  booking_id INT,
  amount DOUBLE,
  method VARCHAR(20),
  payment_date DATE,
  FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);
```
Add default users manually or via code:
```sql
INSERT INTO users (username, password, role) VALUES 
('admin', 'admin123', 'admin'),
('passenger', 'pass123', 'passenger');
```
## ▶️ Running the Project
   ### 🔧 Configuration
   -  Open the project in NetBeans IDE
   - In the DB connection file (DBConnection.java or similar), configure:

```java
String url = "jdbc:mysql://localhost:3306/railway";
String user = "root";
String password = "your_mysql_password";
```

## 🏁 Steps to Run
  - Clean and Build the project in NetBeans
  - Run the main class (RailwayManagementUI.java )
  - Log in as admin or passenger

   ## 🧪 Sample Credentials
   | Role      | Username  | Password |
   | --------- | --------- | -------- |
   | Admin     | admin     | admin123 |
   | Passenger | passenger | pass123  |

---

  ## 📸 Screenshots
  ### Login Page:
![image](https://github.com/user-attachments/assets/8fd8f069-1b87-4243-bfbd-36fd13676f85)   ![image](https://github.com/user-attachments/assets/bad44e73-1dce-4e48-a6ec-13054c19a8bf)

 ## MAIN DASHBOARD: Passengers Panel
![image](https://github.com/user-attachments/assets/c0b38f2d-f33c-4a13-b208-5a5559d04d0c)

## PASSENGERS PANEL: Add Passengers
![image](https://github.com/user-attachments/assets/4eec4aca-544c-4b31-afd8-b0ca03545ab2)

## PASSENGERS PANEL: View Passengers
![image](https://github.com/user-attachments/assets/bb258fb8-54f6-4677-9f6c-2f77b035645e)

## PASSENGERS PANEL: Delete Passengers
![image](https://github.com/user-attachments/assets/092dda2c-bda2-4ed2-9fc2-1bb58f32e717)

---

## MAIN DASHBOARD: Trains Panel
![image](https://github.com/user-attachments/assets/aa4a5124-c63a-4559-a759-bc63fdbdd588)


## TRAINS PANEL: Add Trains
![image](https://github.com/user-attachments/assets/68514a2d-ead8-49d8-9b82-5112a94bbc96) ![image](https://github.com/user-attachments/assets/8e368e1f-2ecc-4656-90c2-fda6155f01b7)


## TRAINS PANEL: View Trains
![image](https://github.com/user-attachments/assets/809fc5c8-3337-4cad-b07a-1baab8de4400)

## Update Train
![image](https://github.com/user-attachments/assets/4f5953b3-e0ce-4258-b9b7-0c636c64e390)  ![image](https://github.com/user-attachments/assets/48b7c09b-74e5-4af5-9aa6-ae21fce5124a)

## TRAINS PANEL: Delete Train
![image](https://github.com/user-attachments/assets/5941e13d-0ca0-4195-bac0-24014e272bd6) ![image](https://github.com/user-attachments/assets/c468d24d-155b-407e-8adc-0c60f86bf9cd)

---

## MAIN DASHBOARD: Bookings Panel
![image](https://github.com/user-attachments/assets/ed978c19-3bc8-477f-b019-08459470b178)

## BOOKINGS PANEL: Book Train
![image](https://github.com/user-attachments/assets/118d9595-f178-4307-a4a0-eacbee998f84) ![image](https://github.com/user-attachments/assets/bdb294f7-fb26-411b-ac9a-04ad5150a2d3)

## BOOKINGS PANEL: View Bookings
![image](https://github.com/user-attachments/assets/691c6236-a848-41bd-8789-ebfc28cf16b9)

## BOOKINGS PANEL: View Payments
![image](https://github.com/user-attachments/assets/66ac52f2-27a5-40e7-bbc5-1de9306b8ebe)

## BOOKINGS PANEL: Cancel Booking
![image](https://github.com/user-attachments/assets/471e7b1f-db93-4cb6-892a-264bd3fff7c2) ![image](https://github.com/user-attachments/assets/3d90a81e-26d4-427a-b562-a9bd55ce5876)

## BOOKINGS PANEL: View Ticket
![image](https://github.com/user-attachments/assets/ed6c49c0-e315-4214-b914-a0021fff556d)  ![image](https://github.com/user-attachments/assets/5e29af9c-4c9f-4d1a-bfc8-845656d4cc57)

---
 This Project was made as a Mini Project in Java Programming And Database and Management Systems subject.






 

