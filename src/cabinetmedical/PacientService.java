package cabinetmedical;

import java.util.ArrayList;
import java.sql.*;
import java.util.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PacientService implements CrudService<Pacient, Integer> {
    private static PacientService instance = new PacientService();
  //  private Connection connection;
    private AuditService auditService;

    private PacientService() {
       // this.connection = DBConnection.getInstance().getConnection();
        this.auditService = AuditService.getInstance();
    }

    public static synchronized PacientService getInstance() {
        if (instance == null) {
            instance = new PacientService();
        }
        return instance;
    }

    public Pacient create(Pacient pacient) {
        String sql = "INSERT INTO pacient (nume, cnp) VALUES (?, ?)";

        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            if (connection == null || connection.isClosed()) {
                System.err.println("Eroare: Conexiunea la baza de date este nulă sau închisă înainte de operație.");
                return null; // Returnează null dacă nu avem o conexiune validă
            }
            stmt.setString(1, pacient.getName());
            stmt.setString(2, pacient.getCnp());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGenerat = generatedKeys.getInt(1);
                        Pacient pacientCuId = new Pacient(idGenerat, pacient.getName(), pacient.getCnp());
                        pacient.setId(idGenerat);

                        auditService.logAction("CREATE_PACIENT");
                        return pacientCuId;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }



    @Override
    public Pacient findById(Integer id) {
        String sql = "SELECT * FROM pacienti WHERE id = ?";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Pacient pacient = new Pacient(
                            rs.getInt("id"),
                            rs.getString("nume"),
                            rs.getString("cnp")
                    );
                    auditService.logAction("READ_PACIENT");
                    return pacient;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Pacient> findAll() {
        List<Pacient> pacienti = new ArrayList<>();
        String sql = "SELECT * FROM pacient";
        try (Connection connection = DBConnection.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Pacient pacient = new Pacient(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("cnp")
                );
                pacienti.add(pacient);
            }
            auditService.logAction("READ_ALL_PACIENTI");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pacienti;
    }

    @Override
    public Pacient update(Pacient pacient) {
        String sql = "UPDATE pacienti SET nume = ?, prenume = ?, cnp = ?, telefon = ?, email = ? WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pacient.getName());
            stmt.setString(3, pacient.getCnp());
            stmt.setInt(6, pacient.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                auditService.logAction("UPDATE_PACIENT");
                return pacient;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM pacienti WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                auditService.logAction("DELETE_PACIENT");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
