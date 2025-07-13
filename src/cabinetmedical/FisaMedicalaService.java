package cabinetmedical;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FisaMedicalaService {
    private static FisaMedicalaService instance;
    //private final Connection connection;
    private final AuditService auditService;

    private FisaMedicalaService() {
        //this.connection = DBConnection.getInstance().getConnection();
        this.auditService = AuditService.getInstance();
    }

    public static synchronized FisaMedicalaService getInstance() {
        if (instance == null) {
            instance = new FisaMedicalaService();
        }
        return instance;
    }

    public FisaMedicala create(FisaMedicala fisa) {
        String sql = "INSERT INTO fise_medicale (pacientId, grupaSanguina, alergii, boliCronice) VALUES (?, ?, ?, ?)";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, fisa.getPacientId());
            stmt.setString(2, fisa.getGrupaSanguina());
            stmt.setString(3, String.join(",", fisa.getAlergii()));
            stmt.setString(4, String.join(",", fisa.getBoliCronice()));

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                auditService.logAction("CREATE_FISA");
                return fisa;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public FisaMedicala findById(int id) {
        String sql = "SELECT * FROM fise_medicale WHERE idFisa = ?";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    FisaMedicala fisa = new FisaMedicala();
                    fisa.setIdPacient(rs.getInt("pacientId"));
                    fisa.setGrupaSanguina(rs.getString("grupaSanguina"));
                    fisa.getAlergii().addAll(List.of(rs.getString("alergii").split(",")));
                    fisa.getBoliCronice().addAll(List.of(rs.getString("boliCronice").split(",")));

                    auditService.logAction("READ_FISA");
                    return fisa;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<FisaMedicala> findAll() {
        List<FisaMedicala> fise = new ArrayList<>();
        String sql = "SELECT * FROM fise_medicale";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                FisaMedicala fisa = new FisaMedicala();
                fisa.setIdPacient(rs.getInt("pacientId"));
                fisa.setGrupaSanguina(rs.getString("grupaSanguina"));
                fisa.getAlergii().addAll(List.of(rs.getString("alergii").split(",")));
                fisa.getBoliCronice().addAll(List.of(rs.getString("boliCronice").split(",")));

                fise.add(fisa);
            }
            auditService.logAction("READ_ALL_FISE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fise;
    }

    public boolean update(FisaMedicala fisa) {
        String sql = "UPDATE fise_medicale SET grupaSanguina = ?, alergii = ?, boliCronice = ? WHERE pacientId = ?";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fisa.getGrupaSanguina());
            stmt.setString(2, String.join(",", fisa.getAlergii()));
            stmt.setString(3, String.join(",", fisa.getBoliCronice()));
            stmt.setInt(4, fisa.getPacientId());

            int rows = stmt.executeUpdate();
            if (rows > 0) {
                auditService.logAction("UPDATE_FISA");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteByPacientId(int pacientId) {
        String sql = "DELETE FROM fise_medicale WHERE pacientId = ?";
        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pacientId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                auditService.logAction("DELETE_FISA");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
