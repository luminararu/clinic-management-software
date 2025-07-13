package cabinetmedical;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Cabinet{

    static int countDepartament = 0;
    private final int idCabinet;
    private String denumire;
    private ArrayList<Doctor> doctori ;
    private ArrayList<String> echipamente ;

    public Cabinet(){
        this.idCabinet = ++countDepartament;
        this.denumire = "";
        this.doctori = new ArrayList<Doctor>();
        this.echipamente = new ArrayList<String>();
    }

    public Cabinet(String denumire, ArrayList<Doctor> doctori, ArrayList<String> echipamente) {
        this.idCabinet = ++countDepartament;
        this.denumire = denumire;
        this.doctori = doctori;
        this.echipamente = echipamente;

    }

    public Cabinet(int idCabinet, String denumire, ArrayList<String> echipamente) {
        this.idCabinet = idCabinet;
        this.denumire = denumire;
        this.echipamente = echipamente;
    }


    public String getEchipamenteAsString() {
        if (echipamente == null || echipamente.isEmpty()) {
            return "";
        }
        return String.join(",", echipamente);
    }

    public Cabinet(int idCabinet, String denumire, String echipamenteString) {
        this.idCabinet = idCabinet;
        this.denumire = denumire;
        this.doctori = new ArrayList<Doctor>();
        // Convertire String în ArrayList
        this.echipamente = new ArrayList<>();
        if (echipamenteString != null && !echipamenteString.isEmpty()) {
            String[] items = echipamenteString.split(",");
            for (String item : items) {
                this.echipamente.add(item.trim());
            }
        }
    }


    public Cabinet(int idCabinet, String denumire, ArrayList<Doctor> doctori, ArrayList<String> echipamente) {
        this.idCabinet = idCabinet;
        this.denumire = denumire;
        this.doctori = doctori;
        this.echipamente = echipamente;
    }

    public int getIdCabinet() {
        return idCabinet;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }
    public void setEchipamente(ArrayList<String> echipamente) {
        this.echipamente = echipamente;
    }

    public ArrayList<String> getEchipamente() {
        return echipamente;
    }

    public void setDoctori(ArrayList<Doctor> doctori) {
        this.doctori = doctori;
    }

    public ArrayList<Doctor> getDoctori() {
        return doctori;
    }

    public void citesteCabinet(){
        Scanner sc = new Scanner(System.in);
            System.out.println("Denumire cabinet: ");
            this.denumire = sc.nextLine();
            sc.nextLine();
            System.out.println("Primul Doctor: ");

            Doctor obj = new Doctor();
            obj.citesteDateDoctorNoCabinet();

            obj.setIdCabinet(this.idCabinet);
            obj.setDepartament(this.denumire);

            CabinetService cabinetService =CabinetService.getInstance();
            cabinetService.create(this);
            DoctorService doctorService = DoctorService.getInstance();
            doctorService.create(obj);
            this.doctori.add(obj);

            System.out.println("Introduceti echipamente: ");
            System.out.println("Introduceti instructiuni pana la stop");

            while(true){
                String echipament = sc.nextLine();
                if(echipament.equals("stop"))
                    break;
                this.echipamente.add(echipament);
            }
    }

    public void afiseazaCabinet(){
        System.out.println("============ Afiare Cabinet =========== ");
        System.out.println("Cabinetul " + this.denumire);

        for(Doctor doctor : this.doctori){
            doctor.afiseazaDoctor();
        }
        for(String echipament : this.echipamente){
            System.out.println(echipament);
        }
    }

    public void copyFrom( Cabinet cabinet)
    {
        this.denumire = cabinet.denumire;
        this.doctori = cabinet.doctori;
        this.echipamente = cabinet.echipamente;
    }
}
