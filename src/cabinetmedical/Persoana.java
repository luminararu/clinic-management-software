package cabinetmedical;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Persoana {
    private int id; // nu final

    public Persoana() {
        // nu seta id aici
    }

    public Persoana(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {  // adaugă setter
        this.id = id;
    }
}
