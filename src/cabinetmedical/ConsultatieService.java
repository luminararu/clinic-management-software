package cabinetmedical;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultatieService implements CrudService<Consultatie, Integer> {
    private static ConsultatieService instance;
    //private final Connection connection;
    private AuditService auditService;

    private ConsultatieService() {
        //this.connection = DBConnection.getInstance().getConnection();
        this.auditService = AuditService.getInstance();
    }

    public static synchronized ConsultatieService getInstance() {
        if (instance == null) {
            instance = new ConsultatieService();
        }
        return instance;
    }

    @Override
    public Consultatie create(Consultatie consultatie) {
        String sql = "INSERT INTO consultatie (data, idMedic, idFisaMedicala) VALUES (?, ?, ?)";

        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, Timestamp.valueOf(consultatie.getData()));
            stmt.setInt(2, consultatie.getIdMedic());
            stmt.setInt(3, consultatie.getIdFisaMedicala());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idGenerat = generatedKeys.getInt(1);
                        Consultatie consultatieCuId = new Consultatie(
                                idGenerat,
                                consultatie.getData(),
                                consultatie.getIdMedic(),
                                consultatie.getIdFisaMedicala()
                        );
                        auditService.logAction("CREATE_CONSULTATIE");
                        return consultatieCuId;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Consultatie findById(Integer id) {
        String sql = "SELECT * FROM consultatie WHERE idConsultatie = ?";

        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Consultatie consultatie = new Consultatie(
                            rs.getInt("idConsultatie"),
                            rs.getTimestamp("data").toLocalDateTime(),
                            rs.getInt("idMedic"),
                            rs.getInt("idFisaMedicala")
                    );
                    auditService.logAction("READ_CONSULTATIE");
                    return consultatie;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<Consultatie> findByIdFisa(Integer idFisa) {
        List<Consultatie> consultatii = new ArrayList<>();

        String sql = "SELECT * FROM consultatie WHERE idFisaMedicala  = ?";

        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idFisa);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next())
                {
                    Consultatie consultatie = new Consultatie(
                            rs.getInt("idConsultatie"),
                            rs.getTimestamp("data").toLocalDateTime(),
                            rs.getInt("idMedic"),
                            rs.getInt("idFisaMedicala")
                    );
                    consultatii.add(consultatie);
                }

                auditService.logAction("READ_ALL_CONSULTATII");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultatii;
    }




    @Override
    public List<Consultatie> findAll() {
        List<Consultatie> consultatii = new ArrayList<>();
        String sql = "SELECT * FROM consultatie";

        try (   Connection connection = DBConnection.getInstance().getConnection();
                Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql))
        {

            while (rs.next())
            {
                Consultatie consultatie = new Consultatie(
                        rs.getInt("idConsultatie"),
                        rs.getTimestamp("data").toLocalDateTime(),
                        rs.getInt("idMedic"),
                        rs.getInt("idFisaMedicala")
                );
                consultatii.add(consultatie);
            }

            auditService.logAction("READ_ALL_CONSULTATII");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultatii;
    }

    @Override
    public Consultatie update(Consultatie consultatie) {
        String sql = "UPDATE consultatie SET data = ?, idMedic = ?, idFisaMedicala = ? WHERE idConsultatie = ?";

        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(consultatie.getData()));
            stmt.setInt(2, consultatie.getIdMedic());
            stmt.setInt(3, consultatie.getIdFisaMedicala());
            stmt.setInt(4, consultatie.getIdConsultatie());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                auditService.logAction("UPDATE_CONSULTATIE");
                return consultatie;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean delete(Integer id) {
        String sql = "DELETE FROM consultatie WHERE idConsultatie = ?";

        try (   Connection connection = DBConnection.getInstance().getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                auditService.logAction("DELETE_CONSULTATIE");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
