package cabinetmedical;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorService implements CrudService<Doctor, Integer> {
    private static DoctorService instance;
    //private final Connection connection;
    private final AuditService auditService;

    private DoctorService() {
        //this.connection = DBConnection.getInstance().getConnection();
        this.auditService = AuditService.getInstance();
    }

    public static synchronized DoctorService getInstance() {
        if (instance == null) {
            instance = new DoctorService();
        }
        return instance;
    }

    @Override
    public Doctor create(Doctor doctor) {
        String sql = "INSERT INTO doctor (nume, departament, specializare, experience, idCabinet) VALUES (?, ?, ?, ?, ?)";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getDepartament());
            stmt.setString(3, doctor.getSpecialty());
            stmt.setInt(4, doctor.getExperience());
            stmt.setInt(5, doctor.getIdCabinet());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        doctor.setId(id);
                        auditService.logAction("CREATE_DOCTOR");
                        return new Doctor(id, doctor.getName(), doctor.getDepartament(), doctor.getSpecialty(), doctor.getExperience(), doctor.getIdCabinet());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Doctor findById(Integer id) {
        String sql = "SELECT * FROM doctor WHERE id = ?";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Doctor doctor = new Doctor(
                            rs.getInt("id"),
                            rs.getString("nume"),
                            rs.getString("departament"),
                            rs.getString("specialitate"),
                            rs.getInt("experienta"),
                            rs.getInt("idCabinet")
                    );
                    auditService.logAction("READ_DOCTOR");
                    return doctor;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctor";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Doctor doctor = new Doctor(
                        rs.getInt("id"),
                        rs.getString("nume"),
                        rs.getString("departament"),
                        rs.getString("specializare"),
                        rs.getInt("experience"),
                        rs.getInt("idCabinet")
                );
                doctors.add(doctor);
            }
            auditService.logAction("READ_ALL_DOCTORI");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctors;
    }

    @Override
    public Doctor update(Doctor doctor) {
        String sql = "UPDATE doctor SET nume = ?, departament = ?, specializare = ?, experience = ?, idCabinet = ? WHERE id = ?";

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getDepartament());
            stmt.setString(3, doctor.getSpecialty());
            stmt.setInt(4, doctor.getExperience());
            stmt.setInt(5, doctor.getIdCabinet());
            stmt.setInt(6, doctor.getId()); // ← id-ul vine din obiectul doctor

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                auditService.logAction("UPDATE_DOCTOR");
                return doctor; // returnezi obiectul actualizat
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM doctor WHERE id = ?";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                auditService.logAction("DELETE_DOCTOR");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}