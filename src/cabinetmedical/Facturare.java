package cabinetmedical;
import java.util.Scanner;

public class Facturare
{
    static int countFacturare = 0;
    private final int idFacturare;
    private double suma;
    private String denumire;
    private int idConsultatie;

    public Facturare()
    {
        this.idFacturare = ++countFacturare;
        this.suma = 0;
        this.denumire = "";
        this.idConsultatie = 0;
    }

    public Facturare(double suma, String denumire, int idConsultatie)
    {
        this.idFacturare = ++countFacturare;
        this.suma = suma;
        this.denumire = denumire;
        this.idConsultatie = idConsultatie;
    }

    public int getIdFacturare() {
        return idFacturare;
    }

    public int getIdConsultatie() {
        return idConsultatie;
    }

    public void setIdConsultatie(int idConsultatie) {
        this.idConsultatie = idConsultatie;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
    public int getIdFcaturare() {
        return idFacturare;
    }


    public void citireFacturare()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Introducere date facturare ---");

        System.out.print("Denumire serviciu/procedură: ");
        this.denumire = scanner.nextLine();

        System.out.print("Suma de plată: ");
        this.suma = scanner.nextDouble();
        scanner.nextLine(); // Consumăm newline rămas

        /*System.out.print("ID Consultație asociată: ");
        this.idConsultatie = scanner.nextInt();
        scanner.nextLine(); // Consumăm newline rămas
         */
        System.out.println("Factura a fost înregistrată cu ID: " + this.idFacturare);
    }

    public void afisareFacturare()
    {
        System.out.println("\n=================================");
        System.out.println("          DETALII FACTURĂ         ");
        System.out.println("=================================");
        System.out.println("ID Factură: " + this.idFacturare);
        System.out.println("Denumire: " + this.denumire);
        System.out.println("Sumă: " + this.suma + " RON");
        System.out.println("ID Consultație asociată: " + this.idConsultatie);
        System.out.println("=================================");
    }

}