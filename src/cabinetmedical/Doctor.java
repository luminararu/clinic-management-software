package cabinetmedical;
import java.util.Scanner;

public class Doctor extends Persoana {
    private String name;
    private String departament;
    private String specialty;
    private int experience;
    private int idCabinet;

    public Doctor(){
        super();
        this.name = "Docotor";
        this.departament = "";
        this.specialty = "";
        this.experience = 0;
        this.idCabinet = 0;
    }
    public Doctor(int id, String name, String departament, String specialty, int experience, int idCabinet) {
        super(id); // dacă `Persoana` are un câmp id și un constructor cu id
        this.name = name;
        this.departament = departament;
        this.specialty = specialty;
        this.experience = experience;
        this.idCabinet = idCabinet;
    }


    public int getIdCabinet()
    {
        return idCabinet;
    }

    public void setIdCabinet(int idCabinet) {
        this.idCabinet = idCabinet;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDepartament() {
        return departament;
    }
    public void setDepartament(String departament) {
        this.departament = departament;
    }
    public String getSpecialty() {
        return specialty;
    }
    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }
    public int getExperience() {
        return experience;
    }
    public void setExperience(int experience) {
        this.experience = experience;
    }

    public void copyFrom(Doctor doctor) {
        if(doctor != null) {
            this.name = doctor.getName();
            this.departament = doctor.getDepartament();
            this.specialty = doctor.getSpecialty();
            this.experience = doctor.getExperience();
            this.idCabinet = doctor.getIdCabinet();
        }
    }

    public void citesteDateDoctor(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduceti numele doctorului: ");
        name = sc.nextLine();

        System.out.println("Introduceti numele specialty: ");
        specialty = sc.nextLine();
        System.out.println("Introduceti anii de experienta: ");
        experience = sc.nextInt();

        System.out.println("Introduceti numele departamentului: ");
        departament = sc.nextLine();

        System.out.println("Introduceti idCabinet: ");
        idCabinet = sc.nextInt();
    }


    public void citesteDateDoctorNoCabinet(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduceti numele doctorului: ");
        name = sc.nextLine();
        //System.out.println("Introduceti numele departamentului: ");
        //departament = sc.nextLine();
        System.out.println("Introduceti numele specialty: ");
        specialty = sc.nextLine();
        System.out.println("Introduceti anii de experienta: ");
        experience = sc.nextInt();
    }

    public void afiseazaDoctor(){
        System.out.println("Id ul doctorului: " + this.getId());
        System.out.println("Doctorul : " + name);
        System.out.println("Departamentul: " + departament);
        System.out.println("Specialty: " + specialty);
        System.out.println("Experience: " + experience);
        //System.out.println("IdCabinet: " + idCabinet);
    }


}