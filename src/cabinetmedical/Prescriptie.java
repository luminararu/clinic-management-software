package cabinetmedical;
import java.util.Scanner;

import java.util.ArrayList;

public class Prescriptie {
    static int countPrescriptie = 0;
    private final int idPrescriptie;
    private int idMedic;
    private int idFisaMedicala;
    private ArrayList<String> medicamente;
    private ArrayList<String> instructiuni;

    public int getIdFisaMedicala()
    {
        return idFisaMedicala;
    }

    public void setIdFisaMedicala(int idFisaMedicala)
    {
        this.idFisaMedicala = idFisaMedicala;
    }
    public Prescriptie(){
        this.idPrescriptie = ++countPrescriptie;
        this.idMedic = 0;
        this.medicamente = new ArrayList<>();
        this.instructiuni = new ArrayList<>();
    }

    public int getIdPrescriptie() {
        return idPrescriptie;
    }

    public int getIdMedic() {
        return idMedic;
    }

    public void setIdMedic(int idMedic) {
        this.idMedic = idMedic;
    }

    public ArrayList<String> getMedicamente() {
        return medicamente;
    }

    public void setMedicamente(ArrayList<String> medicamente) {
        this.medicamente = medicamente;
    }

    public ArrayList<String> getInstructiuni() {
        return instructiuni;
    }

    public void setInstructiuni(ArrayList<String> instructiuni) {
        this.instructiuni = instructiuni;
    }

    public Prescriptie(ArrayList<String> medicament, ArrayList<String> instructiuni) {
        this.idPrescriptie = ++countPrescriptie;
        this.idMedic = 0;
        this.medicamente = medicament;
        this.instructiuni = instructiuni;
    }

    public void citestePrescriptie() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Introduceți ID-ul medicului: ");
        setIdMedic(sc.nextInt());
        sc.nextLine();

        // Citire Medicamente
        System.out.println("\n--- Introducere medicamente ---");
        System.out.println("Introduceți medicamente (scrieți 'stop' pentru a termina):");
        while (true) {
            System.out.print("Medicament: ");
            String medicament = sc.nextLine();
            if (medicament.equalsIgnoreCase("stop")) {
                break;
            }
            this.medicamente.add(medicament);
        }

        System.out.println("\n--- Introducere instrucțiuni ---");
        System.out.println("Introduceți instrucțiuni (scrieți 'stop' pentru a termina):");

        while (true) {
            System.out.print("Instrucțiune: ");
            String instructiune = sc.nextLine();
            if (instructiune.equalsIgnoreCase("stop")) {
                break;
            }
            this.instructiuni.add(instructiune);
        }

        System.out.println("\nPrescripția a fost înregistrată cu ID: " + this.idPrescriptie);
    }

    public void afisarePrescriptie() {
        System.out.println("\n===== Detalii Prescripție =====");
        System.out.println("ID Prescripție: " + this.idPrescriptie);
        System.out.println("ID Medic: " + this.idMedic);

        System.out.println("\nMedicamente prescrise:");
        if (this.medicamente.isEmpty()) {
            System.out.println("Nu există medicamente adăugate.");
        } else {
            for (int i = 0; i < this.medicamente.size(); i++) {
                System.out.println((i + 1) + ". " + this.medicamente.get(i));
            }
        }

        System.out.println("\nInstrucțiuni de administrare:");
        if (this.instructiuni.isEmpty()) {
            System.out.println("Nu există instrucțiuni adăugate.");
        } else {
            for (int i = 0; i < this.instructiuni.size(); i++) {
                System.out.println((i + 1) + ". " + this.instructiuni.get(i));
            }
        }
        System.out.println("==============================");
    }

}