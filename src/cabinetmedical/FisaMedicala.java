package cabinetmedical;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Scanner;

public class FisaMedicala{
    static int countFisa = 0;
    private final int idFisa;
    private int pacientId;
    private String grupaSanguina;
    private ArrayList<String> alergii;
    private ArrayList<String> boliCronice;
    private ArrayList<Consultatie> istoricConsultatii;
    private ArrayList<Prescriptie>prescriptii;

    public int getIdFisa() {
        return idFisa;
    }
    public int getIdPacient() {
        return pacientId;
    }

    public void setIdPacient(int idPacient) {
        this.pacientId = idPacient;
    }

    public FisaMedicala(){
        this.idFisa = ++countFisa;
        this.grupaSanguina = "";
        this.alergii = new ArrayList<String>();
        this.boliCronice = new ArrayList<String>();
        this.istoricConsultatii = new ArrayList<Consultatie>();
        this.prescriptii = new ArrayList<Prescriptie>();
    }

    public FisaMedicala(int pacientId, String grupaSanguina, ArrayList<String>alergii, ArrayList<String>boli, ArrayList<Consultatie>consultatii, ArrayList<Prescriptie>prescriptii)
    {
        this.idFisa = ++countFisa;
        this.grupaSanguina = grupaSanguina;
        this.alergii = alergii;
        this.boliCronice = boli;
        this.istoricConsultatii = consultatii;
        this.prescriptii = prescriptii;
    }

    public String getGrupaSanguina() {
        return grupaSanguina;
    }
    public void setGrupaSanguina(String grupaSanguina) {
        this.grupaSanguina = grupaSanguina;
    }

    public ArrayList<String> getAlergii() {
        return alergii;
    }

    public int getPacientId() {
        return pacientId;
    }

    public void setPacientId(int pacientId) {
        this.pacientId = pacientId;
    }

    public void setAlergii(ArrayList<String> alergii) {
        this.alergii = alergii;
    }

    public ArrayList<String> getBoliCronice() {
        return boliCronice;
    }

    public void addConsultatii(Consultatie consultatie) {
        istoricConsultatii.add(consultatie);
    }

    public void setBoliCronice(ArrayList<String> boliCronice) {
        this.boliCronice = boliCronice;
    }

    public ArrayList<Consultatie> getIstoricConsultatii() {
        return istoricConsultatii;
    }

    public void setConsultatii(ArrayList<Consultatie> consultatii) {
        this.istoricConsultatii = consultatii;
    }

    public void citesteFisaMedicala() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduceti grupa sanguina: ");
        grupaSanguina = sc.nextLine();

        System.out.println("\n--- Introducere alergii ---");
        System.out.println("Introduceți alergii (scrieți 'stop' pentru a termina):");

        while(true) {
            System.out.print("Alergii: ");
            String alergie = sc.nextLine();
            if (alergie.equalsIgnoreCase("stop")) {
                break;
            }
            this.alergii.add(alergie);
        }

        System.out.println("\n--- Introducere boli cronice ---");
        System.out.println("Introduceti boli cronice ('stop' pentru a termina):");

        while(true) {
            System.out.print("Boli cronice: ");
            String boli = sc.nextLine();
            if (boli.equalsIgnoreCase("stop")) {
                break;
            }
            this.boliCronice.add(boli);
        }

        System.out.println("\n--- Introducere o consultatie  ---");
        Consultatie consultatie = new Consultatie();
        consultatie.citesteConsultatie();
        consultatie.setIdFisaMedicala(this.idFisa);
        // adaugare in baza de date
        ConsultatieService consultatieService = ConsultatieService.getInstance();
        consultatieService.create(consultatie);

        this.istoricConsultatii.add(consultatie);

        System.out.println("\n--- Introducere o prescriptie---");

        Prescriptie prescriptie = new Prescriptie();
        prescriptie.citestePrescriptie();
        prescriptie.setIdFisaMedicala(this.idFisa);
        //adaugare in baza de date
        PrescriptieService prescriptieService = PrescriptieService.getInstance();
        prescriptieService.create(prescriptie);

        this.prescriptii.add(prescriptie);

    }



    public void citesteFisaMedicalaPacient(int pacientId)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduceti grupa sanguina: ");
        grupaSanguina = sc.nextLine();

        System.out.println("\n--- Introducere alergii ---");
        System.out.println("Introduceți alergii (scrieți 'stop' pentru a termina):");

        while(true) {
            System.out.print("Alergii: ");
            String alergie = sc.nextLine();
            if (alergie.equalsIgnoreCase("stop")) {
                break;
            }
            this.alergii.add(alergie);
        }

        System.out.println("\n--- Introducere boli cronice ---");
        System.out.println("Introduceti boli cronice ('stop' pentru a termina):");

        while(true) {
            System.out.print("Boli cronice: ");
            String boli = sc.nextLine();
            if (boli.equalsIgnoreCase("stop")) {
                break;
            }
            this.boliCronice.add(boli);
        }

        this.pacientId = pacientId;

        FisaMedicalaService fisaMedicalaService = FisaMedicalaService.getInstance();

        fisaMedicalaService.create(this);

        System.out.println("\n--- Introducere o consultatie  ---");
        Consultatie consultatie = new Consultatie();
        consultatie.citesteConsultatie();
        consultatie.setIdFisaMedicala(this.idFisa);
        // adaugare in baza de date
        ConsultatieService consultatieService = ConsultatieService.getInstance();
        consultatieService.create(consultatie);

        this.istoricConsultatii.add(consultatie);

        System.out.println("\n--- Introducere o prescriptie---");

        Prescriptie prescriptie = new Prescriptie();
        prescriptie.citestePrescriptie();
        prescriptie.setIdFisaMedicala(this.idFisa);
        //adaugare in baza de date
        PrescriptieService prescriptieService = PrescriptieService.getInstance();
        prescriptieService.create(prescriptie);

        this.prescriptii.add(prescriptie);



    }



    public void afisareFisaMedicala() {
        System.out.println("\n=============================================");
        System.out.println("               FIȘĂ MEDICALĂ               ");
        System.out.println("=============================================");
        System.out.println("ID Fișă: " + this.idFisa);
        System.out.println("ID Pacient: " + this.pacientId);
        System.out.println("Grupa sanguină: " + this.grupaSanguina);

        // Afișare alergii
        System.out.println("\n--- Alergii ---");
        if (this.alergii.isEmpty()) {
            System.out.println("Nu există alergii înregistrate.");
        } else {
            for (int i = 0; i < this.alergii.size(); i++) {
                System.out.println((i + 1) + ". " + this.alergii.get(i));
            }
        }

        // Afișare boli cronice
        System.out.println("\n--- Boli cronice ---");
        if (this.boliCronice.isEmpty()) {
            System.out.println("Nu există boli cronice înregistrate.");
        } else {
            for (int i = 0; i < this.boliCronice.size(); i++) {
                System.out.println((i + 1) + ". " + this.boliCronice.get(i));
            }
        }

        // Afișare istoric consultații
        System.out.println("\n--- Istoric consultații (" + this.istoricConsultatii.size() + ") ---");
        if (this.istoricConsultatii.isEmpty()) {
            System.out.println("Nu există consultații înregistrate.");
        } else {
            for (Consultatie consultatie : this.istoricConsultatii) {
                consultatie.afisareConsultatie(); // Folosește metoda de afișare din clasa Consultatie
                System.out.println("-----------------------------");
            }
        }

        // Afișare prescripții
        System.out.println("\n--- Prescripții (" + this.prescriptii.size() + ") ---");
        if (this.prescriptii.isEmpty()) {
            System.out.println("Nu există prescripții înregistrate.");
        } else {
            for (Prescriptie prescriptie : this.prescriptii) {
                prescriptie.afisarePrescriptie(); // Folosește metoda de afișare din clasa Prescriptie
                System.out.println("-----------------------------");
            }
        }

        System.out.println("\n=============================================");
    }
}