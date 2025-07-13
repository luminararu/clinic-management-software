package cabinetmedical;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PrescriptieService implements CrudService<Prescriptie, Integer> {
    private static PrescriptieService instance;
    //private final Connection connection;
    private final AuditService auditService;

    private PrescriptieService() {
        //this.connection = DBConnection.getInstance().getConnection();
        this.auditService = AuditService.getInstance();
    }

    public static synchronized PrescriptieService getInstance() {
        if (instance == null) {
            instance = new PrescriptieService();
        }
        return instance;
    }

    @Override
    public Prescriptie create(Prescriptie prescriptie) {
        String sql = "INSERT INTO prescriptie (idMedic, idFisaMedicala, medicamente, instructiuni) VALUES (?, ?, ?, ?)";
        try (     Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, prescriptie.getIdMedic());
            stmt.setInt(2, prescriptie.getIdFisaMedicala());
            stmt.setString(3, String.join(",", prescriptie.getMedicamente()));
            stmt.setString(4, String.join(",", prescriptie.getInstructiuni()));

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                auditService.logAction("CREATE_PRESCRIPTIE");
                return prescriptie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Prescriptie findById(Integer id) {
        String sql = "SELECT * FROM prescriptie WHERE idPrescriptie = ?";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Prescriptie p = new Prescriptie();
                    p.setIdFisaMedicala(rs.getInt("idFisaMedicala"));
                    p.setIdMedic(rs.getInt("idMedic"));
                    p.setMedicamente(new ArrayList<>(List.of(rs.getString("medicamente").split(","))));
                    p.setInstructiuni(new ArrayList<>(List.of(rs.getString("instructiuni").split(","))));
                    auditService.logAction("READ_PRESCRIPTIE");
                    return p;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Prescriptie> findAll() {
        List<Prescriptie> lista = new ArrayList<>();
        String sql = "SELECT * FROM prescriptie";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Prescriptie p = new Prescriptie();
                p.setIdFisaMedicala(rs.getInt("idFisaMedicala"));
                p.setIdMedic(rs.getInt("idMedic"));
                p.setMedicamente(new ArrayList<>(List.of(rs.getString("medicamente").split(","))));
                p.setInstructiuni(new ArrayList<>(List.of(rs.getString("instructiuni").split(","))));
                lista.add(p);
            }
            auditService.logAction("READ_ALL_PRESCRIPTII");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public Prescriptie update(Prescriptie prescriptie) {
        String sql = "UPDATE prescriptie SET idMedic = ?, idFisaMedicala = ?, medicamente = ?, instructiuni = ? WHERE idPrescriptie = ?";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, prescriptie.getIdMedic());
            stmt.setInt(2, prescriptie.getIdFisaMedicala());
            stmt.setString(3, String.join(",", prescriptie.getMedicamente()));
            stmt.setString(4, String.join(",", prescriptie.getInstructiuni()));
            stmt.setInt(5, prescriptie.getIdPrescriptie());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                auditService.logAction("UPDATE_PRESCRIPTIE");
                return prescriptie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM prescriptie WHERE idPrescriptie = ?";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                auditService.logAction("DELETE_PRESCRIPTIE");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
