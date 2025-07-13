# Clinic Management Software

This is a Java-based Clinic Management Software developed using **IntelliJ IDEA**.  
The project includes a built-in database and provides functionality for managing patient data, appointments, consultations, prescriptions, and more.

## 🧰 Technologies Used

- Java 17+
- IntelliJ IDEA (recommended IDE)
- JDBC (Java Database Connectivity)
- SQLite (or your configured relational database)
- CSV audit logging
- MVC architectural pattern

## 📦 Features

- CRUD operations for:
  - Patients
  - Consultations
  - Prescriptions
  - Medical Records
- File-based audit logging
- Modular and extensible structure
- Simple CLI or menu-driven interface

## ⚙️ How to Run the Project

### 1. Clone the repository

```bash
git clone https://github.com/luminararu/clinic-management-software.git
cd clinic-management-software

2. Open in IntelliJ
Open IntelliJ IDEA

Click on Open and select the project folder

Wait for Gradle/Maven indexing if applicable

3. Set up the Database
The project uses a local database (e.g. SQLite).

Make sure the .db file (if used) exists in the resources or root folder.

No additional setup is required unless switching to a different DB engine.

4. Run the App
Navigate to src/Main.java

Right-click and select Run 'Main'

The main menu should appear in the console

5. Optional: Configure Audit
All actions are logged into audit.csv automatically.

This file is generated/updated in the root directory.




