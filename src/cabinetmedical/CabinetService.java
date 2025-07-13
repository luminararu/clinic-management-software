package cabinetmedical;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CabinetService implements CrudService<Cabinet, Integer> {
    private static CabinetService instance;
    private final AuditService auditService;

    private CabinetService() {
        this.auditService = AuditService.getInstance();
    }

    public static synchronized CabinetService getInstance() {
        if (instance == null) {
            instance = new CabinetService();
        }
        return instance;
    }

    @Override
    public Cabinet create(Cabinet cabinet) {
        String sql = "INSERT INTO cabinet (denumire, echipamente) VALUES (?, ?)";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cabinet.getDenumire());
            stmt.setString(2, cabinet.getEchipamenteAsString());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        auditService.logAction("CREATE_CABINET");
                        return new Cabinet(id, cabinet.getDenumire(), cabinet.getEchipamente());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cabinet findById(Integer id) {
        String sql = "SELECT * FROM cabinet WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cabinet cabinet = new Cabinet(rs.getInt("id"), rs.getString("denumire"), rs.getString("echipamente"));
                    auditService.logAction("READ_CABINET");
                    return cabinet;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cabinet> findAll() {
        List<Cabinet> cabinete = new ArrayList<>();
        String sql = "SELECT * FROM cabinet";
        try (Connection connection = DBConnection.getInstance().getConnection();

             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cabinet cabinet = new Cabinet(rs.getInt("id"), rs.getString("denumire"), rs.getString("echipamente"));
                cabinete.add(cabinet);
            }
            auditService.logAction("READ_ALL_CABINETE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cabinete;
    }

    @Override
    public Cabinet update(Cabinet cabinet) {
        String sql = "UPDATE cabinet SET denumire = ?, echipamente = ? WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, cabinet.getDenumire());
            stmt.setInt(2, cabinet.getIdCabinet());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                auditService.logAction("UPDATE_CABINET");
                return cabinet;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM cabinet WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                auditService.logAction("DELETE_CABINET");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
