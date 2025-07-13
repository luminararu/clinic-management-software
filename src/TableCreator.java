import cabinetmedical.DBConnection;

import java.sql.Connection;
import java.sql.*;
import java.sql.Statement;

public class TableCreator {

    public static void createTables() {
        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement stmt = connection.createStatement()) {

            String createDoctorTable = "CREATE TABLE IF NOT EXISTS doctor (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "nume VARCHAR(100), " +
                    "departament VARCHAR(100), " +
                    "specializare VARCHAR(100)," +
                    "experience INT," +
                    "idCabinet INT," +
                    "FOREIGN KEY (idCabinet) REFERENCES cabinet(id) ON DELETE SET NULL" +
                    ");";

            String createPacientTable = "CREATE TABLE IF NOT EXISTS pacient (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "nume VARCHAR(100), " +
                    "cnp VARCHAR(100)" +
                    ");";

            String createFisaMedicalaTable = "CREATE TABLE IF NOT EXISTS fise_medicale (" +
                    "idFisa INT PRIMARY KEY AUTO_INCREMENT, " +
                    "pacientId INT UNIQUE, " +
                    "grupaSanguina VARCHAR(10), " +
                    "alergii TEXT, " +
                    "boliCronice TEXT, " +
                    "FOREIGN KEY (pacientId) REFERENCES pacient(id) ON DELETE SET NULL" +
                    ");";

            String createConsultatieTable = "CREATE TABLE IF NOT EXISTS consultatie (" +
                    "idConsultatie INT PRIMARY KEY AUTO_INCREMENT, " +
                    "data DATETIME, " +
                    "idMedic INT, " +
                    "idFisaMedicala INT, " +
                    "FOREIGN KEY (idMedic) REFERENCES doctor(id), " +
                    "FOREIGN KEY (idFisaMedicala) REFERENCES fise_medicale(idFisa) ON DELETE SET NULL" +
                    ");";

            String createPrescriptieTable = "CREATE TABLE IF NOT EXISTS prescriptie (" +
                    "idPrescriptie INT PRIMARY KEY AUTO_INCREMENT, " +
                    "idMedic INT, " +
                    "idFisaMedicala INT, " +
                    "medicamente TEXT, " +
                    "instructiuni TEXT, " +
                    "FOREIGN KEY (idMedic) REFERENCES doctor(id)  ON DELETE SET NULL, " +
                    "FOREIGN KEY (idFisaMedicala) REFERENCES fise_medicale(idFisa) ON DELETE SET NULL" +
                    ");";

            String createFacturareTable = "CREATE TABLE IF NOT EXISTS facturare (" +
                    "idFacturare INT PRIMARY KEY AUTO_INCREMENT, " +
                    "suma DOUBLE, " +
                    "denumire VARCHAR(255), " +
                    "idConsultatie INT, " +
                    "FOREIGN KEY (idConsultatie) REFERENCES consultatie(idConsultatie) ON DELETE SET NULL" +
                    ");";

            String createCabinetTable = "CREATE TABLE IF NOT EXISTS cabinet (" +
                    "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    "denumire VARCHAR(200), " +
                    "echipamente TEXT" + // Adăugat câmpul 'echipamente' de tip TEXT
                    ");";


            // Ordine corectă: fise_pacienti -> doctor -> pacient -> cabinet -> consultatie
           /*
           stmt.execute(createDoctorTable);
            stmt.execute(createPacientTable);
            stmt.execute(createFisaMedicalaTable);
            stmt.execute(createConsultatieTable);
            stmt.execute(createPrescriptieTable);
            stmt.execute(createFacturareTable);
            stmt.execute(createCabinetTable);

            */

            /*
            // 1. Drop tables in reverse dependency order (for clean runs during development)
            try { stmt.execute("DROP TABLE IF EXISTS facturare;"); } catch (SQLException e) { System.err.println("Eroare la ștergerea tabelei facturare: " + e.getMessage()); }

            try { stmt.execute("DROP TABLE IF EXISTS prescriptie;"); } catch (SQLException e) { System.err.println("Eroare la ștergerea tabelei prescriptie: " + e.getMessage()); }

            try { stmt.execute("DROP TABLE IF EXISTS consultatie;"); } catch (SQLException e) { System.err.println("Eroare la ștergerea tabelei consultatie: " + e.getMessage()); }
            try { stmt.execute("DROP TABLE IF EXISTS fise_medicale;"); } catch (SQLException e) { System.err.println("Eroare la ștergerea tabelei fise_medicale: " + e.getMessage());}

            try { stmt.execute("DROP TABLE IF EXISTS pacient;"); } catch (SQLException e) { System.err.println("Eroare la ștergerea tabelei pacient: " + e.getMessage()); }
            try { stmt.execute("DROP TABLE IF EXISTS doctor;"); } catch (SQLException e) { System.err.println("Eroare la ștergerea tabelei doctor: " + e.getMessage()); }
            try { stmt.execute("DROP TABLE IF EXISTS Cabinet;"); } catch (SQLException e) { System.err.println("Eroare la ștergerea tabelei cabinet: " + e.getMessage()); }


            System.out.println("Tabelele au fost sterse");

            */

            // 2. Create parent tables first
            // 3. Create cabinet (if it doesn't depend on others here)

            stmt.execute(createCabinetTable); // Create this earlier if other tables reference it

            stmt.execute(createDoctorTable);
            stmt.execute(createPacientTable); // If pacient table has foreign keys to fisa_medicala, create it AFTER fisa_medicala

            stmt.execute(createFisaMedicalaTable); // Make sure this is fise_medicale or fise_pacienti's SQL


            // 4. Create tables that depend on the above (e.g., consultatie depends on doctor and fise_medicale)
            stmt.execute(createConsultatieTable);

            // 5. Create tables that depend on consultatie
            stmt.execute(createPrescriptieTable);
            stmt.execute(createFacturareTable);



            System.out.println("Tabelele au fost create cu succes.");




        } catch (Exception e) {
            System.out.println("Eroare la crearea tabelelor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
