package cabinetmedical;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AuditService {
    private static AuditService instance;
    private static final String AUDIT_FILE = "audit.csv";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private AuditService() {}

    public static synchronized AuditService getInstance() {
        if (instance == null) {
            instance = new AuditService();
        }
        return instance;
    }

    public void logAction(String actionName) {
        try (FileWriter writer = new FileWriter(AUDIT_FILE, true)) {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.append(actionName).append(",").append(timestamp).append("\n");
            writer.flush();
        } catch (IOException e) {
            System.err.println("Eroare la scrierea în fișierul de audit: " + e.getMessage());
        }
    }
}