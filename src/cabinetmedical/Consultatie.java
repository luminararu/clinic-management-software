package cabinetmedical;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;



public class Consultatie {
    static int countConsultati = 0;
    private final int idConsultatie;
    private LocalDateTime data;
    private int idMedic;
    private int idFisaMedicala;

    public int getIdFisaMedicala() {
        return idFisaMedicala;
    }

    public void setIdFisaMedicala(int idFisaMedicala) {
        this.idFisaMedicala = idFisaMedicala;
    }

    public Consultatie(){
        this.idConsultatie = ++countConsultati;
        this.data = LocalDateTime.of(2025, 1, 1, 0, 0);
        this.idMedic = 0;
    }

    public Consultatie(int idConsultatie,  LocalDateTime data, int PacientId, int idMedic) {
        //this.idConsultatie = ++countConsultati;

        this.idConsultatie = idConsultatie;
        this.data = data;
        this.idMedic = idMedic;
    }

    public int getIdConsultatie() {
        return idConsultatie;
    }
    public int getIdMedic() {
        return idMedic;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }


    public void citesteConsultatie() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n--- Introducere dată consultație ---");
        System.out.print("Anul (ex: 2025): ");
        int an = sc.nextInt();
        System.out.print("Luna (1-12): ");
        int luna = sc.nextInt();
        System.out.print("Ziua (1-31): ");
        int zi = sc.nextInt();
        System.out.print("Ora (0-23): ");
        int ora = sc.nextInt();
        System.out.print("Minutul (0-59): ");
        int minut = sc.nextInt();

        this.data = LocalDateTime.of(an, luna, zi, ora, minut);
        sc.nextLine();
       // System.out.print("\nID Pacient: ");

        System.out.print("ID Medic: ");
        this.idMedic = sc.nextInt();

        System.out.println("\nConsultația a fost înregistrată cu ID: " + this.idConsultatie);

    }


    public void afisareConsultatie() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println("\n===== Detalii Consultație =====");
        System.out.println("ID Consultație: " + this.idConsultatie);
        System.out.println("Data și ora: " + this.data.format(formatter));
        System.out.println("ID Medic: " + this.idMedic);
        System.out.println("===============================");
    }


}