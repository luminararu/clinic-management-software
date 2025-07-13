import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Scanner;

import cabinetmedical.*;

public class Main {
    static Service service = new Service();
    public static void main(String[] args) {
        /*Pacient pacient = new Pacient();
        Doctor docotor = new Doctor();
        System.out.println(pacient.getId());
        System.out.println(docotor.getId());
         */

        TableCreator.createTables();

        boolean var = true;

        while (var == true)
        {
            Scanner sc = new Scanner(System.in);
            int alegere  = 0;
            System.out.println("Apasa tasta 1 pentru adaugarea unui doctor ");
            System.out.println("Apasa tasta 2 pentru afisarea  doctorilor ");
            //System.out.println("Apasa tasta 3 pentru afisarea facturarilor ");
            System.out.println("Apasa tasta 3 pentru adaugarea unui cabinet ");
            System.out.println("Apasa tasta 4 pentru a afisa toate cabinetele");
            System.out.println("Apasa tasta 5 pentru a adauga un pacient");
            System.out.println("Apasa tasta 6 pentru afisarea tuturor consultatiilor in functie de data");
            System.out.println("Apasa tasta 7 pentru adaugarea unei consultatii a unui pacient existent(daca este posibil)");
            System.out.println("Apasa tasta 8 pentru a afisa fisa medicala a unui pacient existent");
            //System.out.println("Apasa tasta 9 pentru a a inregistra facturare a unui pacient existent");
            System.out.println("Apasa tasta 10 pentru modificarea unui Docotor");
            System.out.println("Apasa tasta 11 pentru stergerea unui doctor");
            System.out.println("Apasa tasta 12 pentru modificarea unui cabinet");
            System.out.println("Apasa tasta 13 pentru stergerea unui cabinet");
            System.out.println("Apasa tasta 14 pentru afisarea tuturor pacientilor");
            System.out.println("Apasa tasta 15 pentru afisarea tuturor consultatiilor in functie de data");
            alegere = sc.nextInt();
            switch (alegere)
            {
                case 1:
                    service.AdaugDoctor();
                    break;
                case 2:
                    service.AfisareDoctori();
                    break;
                case 3:
                    service.AdaugCabinet();
                    break;
                case 4:
                    service.afisareCabinete();
                    break;
                case 5:
                    service.AdaugPacient();
                    break;
                case 6:
                    service.AfisezConsultatii();
                    break;
                case 7:
                    service.AdaugConsultatii();
                    break;
                case 8:
                    System.out.println("Tasteaza id ul pacientului caruia vrei sa îi afisezi fisa medicala");
                    int idPacient = sc.nextInt();
                    for(Pacient pacient: service.getPacients())
                        if(pacient.getId() == idPacient) {
                            service.AfisareFisaMedicala(pacient);
                        }
                    break;
                case 9:
                    System.out.println("Tasteaza id ul pacientului caruia vrei sa îi inregistrezi facturare");
                    String nume= sc.nextLine();
                    service.FacturaCalc(nume);
                    break;
                case 10:
                    service.ModificDocotor();
                    break;

                case 11:
                    System.out.println("Scrie Numele doctorului pe care vrei sa il stergi");
                    Scanner scope = new Scanner(System.in);
                    String name = scope.nextLine();

                    service.StergeDoctor(name);
                    break;
                case 12:
                    service.ModificCabinet();
                    break;
                case 13:
                    service.StergeCabinet();
                    break;
                case 14:
                    service.AfisarePacienti();
                    break;
                case 15:
                    System.out.println("Adauaga numele pacientului caruia vrei sa ii afisezi consultatiile");
                    Scanner scanner = new Scanner(System.in);
                    String text = scanner.nextLine();
                    service.AfisezConsultatii(text);
                    break;
                default:
                    System.out.println("Alegere invalida tasteaza alta tasta");
                    break;

            }
        }
    }
}