package cabinetmedical;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Comparator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Objects;



public class Service {

    private List<Pacient>pacients;
    private List<Doctor>doctors;
    private List<FisaMedicala> fiseMedicale;
    private List<Facturare> facturi;
    private List<Cabinet> cabinete;
    PacientService pacientService = PacientService.getInstance();
    FisaMedicalaService fisaMedicalaService = FisaMedicalaService.getInstance();
    DoctorService doctorService = DoctorService.getInstance();
    CabinetService cabinetService = CabinetService.getInstance();
    ConsultatieService consultatieService = ConsultatieService.getInstance();


    public Service() {
        pacients = new ArrayList<Pacient>();
        doctors = new ArrayList<Doctor>();
        fiseMedicale = new ArrayList<FisaMedicala>();
        facturi = new ArrayList<Facturare>();
        cabinete = new ArrayList<Cabinet>();
    }

    public List<Pacient> getPacients() {

        List<Pacient>pacienti = pacientService.findAll();

        return pacienti;
    }

    public void stergConsultatii(int idConsultati)
    {
        for(Pacient p : pacients)
        {
            for(int j = 0; j < fiseMedicale.size(); j++)
            {
                if(fiseMedicale.get(j).getIdPacient() == p.getId())
                {
                    ArrayList<Consultatie> cons = fiseMedicale.get(j).getIstoricConsultatii();
                    for(int i = 0;  i <cons.size(); i++)
                    {
                        if(cons.get(i).getIdConsultatie() == idConsultati)
                            cons.remove(i);
                        fiseMedicale.get(j).setConsultatii(cons);
                    }
                }
            }
        }
    }

    public void AdaugPacient() {


        Pacient pacient = new Pacient();
        pacient.citesteDatePacient();

        pacientService.create(pacient);


        FisaMedicala fisaMedicala = new FisaMedicala();
        fisaMedicala.citesteFisaMedicalaPacient(pacient.getId());

        fisaMedicala.setIdPacient(pacient.getId());
        fiseMedicale.add(fisaMedicala);


    }

    public void AfisezPacienti() {
        List<Pacient> pacients = pacientService.findAll();
        for(Pacient p : pacients)
        {
            p.afisarePacient();
        }
    }

    public void AdaugDoctor() {
        Doctor doctor = new Doctor();
        doctor.citesteDateDoctor();

        doctorService.create(doctor);

        doctors.add(doctor);

    }

    public void AdaugCabinet() {
        Cabinet cabinet = new Cabinet();
        cabinet.citesteCabinet();

        CabinetService cabinetService = CabinetService.getInstance();
        cabinetService.create(cabinet);

        cabinete.add(cabinet);
    }

    public void AfisarePacienti() {
        List<Pacient> pacients = pacientService.findAll();
        for (Pacient pacient : pacients) {
            pacient.afisarePacient();
        }
    }

    public void AfisareDoctori()
    {
       /* for (Doctor doctor : doctors) {
            doctor.afiseazaDoctor();
        }

        */

        DoctorService doctorService = DoctorService.getInstance();
        List<Doctor> doc = doctorService.findAll();
        for (Doctor doctor : doc) {
            doctor.afiseazaDoctor();
        }
    }

    public void afisareCabinete() {
        List<Cabinet> cabinete = cabinetService.findAll();
        List<Doctor> doctors = doctorService.findAll();

        if (cabinete.size() == 0)
            System.out.println("No Cabinete found");
        else {
            for (Cabinet cabinet : cabinete) {
                cabinet.afiseazaCabinet();
                System.out.println("======= Doctorii din cabinet =======");

                boolean hasDoctor = false;
                for (Doctor doctor : doctors) {
                    // Această versiune funcționează dacă getIdCabinet() returnează Integer (nu int)
                    if (Objects.equals(doctor.getIdCabinet(), cabinet.getIdCabinet())) {
                        doctor.afiseazaDoctor();
                        hasDoctor = true;
                    }
                }

                if (!hasDoctor) {
                    System.out.println("Nu există doctori în acest cabinet.");
                }

                System.out.println("===================================");
                System.out.println();
            }
        }
    }


    public void AfisareFisaMedicala(Pacient pacient) {
        fiseMedicale = fisaMedicalaService.findAll();
        for (FisaMedicala fisaMedicala : fiseMedicale) {
            if(fisaMedicala.getIdPacient() == pacient.getId())
                fisaMedicala.afisareFisaMedicala();
        }
    }

    public void AdaugConsultatii()
    {
        Consultatie consultatie = new Consultatie();

        System.out.println("Introduceti numele pacientului");
        Scanner scanner=new Scanner(System.in);

        String nume = scanner.nextLine();

        consultatie.citesteConsultatie();

        ArrayList<Consultatie> consultatii = new ArrayList<Consultatie>();

        for(FisaMedicala fisaMedicala : fiseMedicale){
            consultatii.addAll(fisaMedicala.getIstoricConsultatii());
        }

        for(Consultatie obj : consultatii){
            if(obj.getData() == consultatie.getData() && obj.getIdMedic() == consultatie.getIdMedic())
            {
                System.out.println("Exista deja o consultatie pentru aceasta data");
                return;
            }
        }

        for(Pacient pacient : pacients)
        {
            if(nume == pacient.getName())
            {
                for (FisaMedicala fisaMedicala : fiseMedicale)
                {
                    if (fisaMedicala.getIdPacient() == pacient.getId())
                    {
                        consultatie.setIdFisaMedicala(fisaMedicala.getIdFisa());
                        fisaMedicala.addConsultatii(consultatie);
                    }
                }
            }
        }
    }

    public void AfisezConsultatii(){

        List<Consultatie> consultatii = consultatieService.findAll();
        System.out.println(consultatii.size());

        consultatii.sort(Comparator.comparing(Consultatie::getData));
        for(Consultatie consultatie : consultatii){
            consultatie.afisareConsultatie();
        }
    }

    public void AfisezConsultatii(String nume){
        List<Consultatie> consultatii = new ArrayList<Consultatie>();

        pacients = pacientService.findAll();
        fiseMedicale = fisaMedicalaService.findAll();


        for(Pacient pacient : pacients) {
            if(nume.equals(pacient.getName())) {
                for (FisaMedicala fisaMedicala : fiseMedicale) {
                    List<Consultatie> consultatie = consultatieService.findByIdFisa(fisaMedicala.getIdFisa());
                    consultatii.addAll(consultatie);
                }
            }
        }

        consultatii.sort(Comparator.comparing(Consultatie::getData));
        for(Consultatie consultatie : consultatii){
            if(consultatie.getData().isBefore(LocalDateTime.now()))
            {
                System.out.println("======Consultatii trecute======");
            }
            else
            {
                System.out.println("======Consultatii viitoare======");
            }
            consultatie.afisareConsultatie();
        }
    }

    public void FacturaCalc(String nume) {

        ArrayList<Consultatie> consultatii = new ArrayList<Consultatie>();
        Facturare facturare = new Facturare();

        for(Pacient pacient : pacients) {
            if(nume.equals(pacient.getName())) {
                for (FisaMedicala fisaMedicala : fiseMedicale) {
                    if(pacient.getId() == fisaMedicala.getIdPacient())
                        consultatii.addAll(fisaMedicala.getIstoricConsultatii());
                }
            }
        }

        for(Consultatie consultatie : consultatii)
        {
            if(consultatie.getData().isBefore(LocalDateTime.now()))
                facturare.citireFacturare();
        }
    }

    public void StergeDoctor(String nume){
        DoctorService doctorService = DoctorService.getInstance();
        List <Doctor> doctors = doctorService.findAll();

        boolean ok = false;
        for(Doctor doctor : doctors)
        {
            if (doctor.getName().equals(nume))
            {
                doctorService.delete(doctor.getId());
                ok = true;
            }
        }
        if(ok == false)
            System.out.println("Doctorului pe care vrei sa-l stergi nu exista");
    }

    public void FacturaAfisez()
    {
        for(Facturare obj: facturi)
        {
            obj.afisareFacturare();
        }
    }

    public void ModificDocotor()
    {

        System.out.println("======Modificare docotor======");
        System.out.println("Tastati id-ul doctorului pe care vreti sa il modificati");
        Scanner sc = new Scanner(System.in);
        Integer id = sc.nextInt();
        sc.nextLine();

        DoctorService doctorService = DoctorService.getInstance();
        List<Doctor> doctors = doctorService.findAll();

        for(Doctor doctor : doctors)
        {
            if(doctor.getId() == id)
            {
                Doctor doc = new Doctor();
                doc.citesteDateDoctorNoCabinet();
                doc.setIdCabinet(doctor.getIdCabinet());
                doctor.copyFrom(doc);

                doctorService.update(doctor);
            }
        }

    }

    public void ModificCabinet()
    {

        System.out.println("======Modificare cabinet======");
        System.out.println("Tastati id ul Cabinet ului pe care vreti sa il modificati");
        Scanner sc = new Scanner(System.in);
        Integer id = sc.nextInt();
        sc.nextLine();

        CabinetService cabinetService = CabinetService.getInstance();
        List<Cabinet> cabinets = cabinetService.findAll();

        for( Cabinet cabinet : cabinets)
        {
            if(cabinet.getIdCabinet() == id) {
                Cabinet c = new Cabinet();
                c.citesteCabinet();
                cabinet.copyFrom(c);
                cabinetService.update(cabinet);
            }
        }
    }

    public void StergeCabinet()
    {
        System.out.println("======StergeCabinet======");
        System.out.println("Introduceti id ul Cabinetului pe care vreti sa il stergeti");

        Scanner sc1 = new Scanner(System.in);
        int id = sc1.nextInt();
        CabinetService cabinetService = CabinetService.getInstance();
        cabinetService.delete(id);


    }
}