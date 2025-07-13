package cabinetmedical;
import java.util.Scanner;

public class Pacient extends Persoana
{
    private String name;
    private String cnp;

    /*public void setIdFisaPacient(int idFisaPacient)
    {
        this.idFisaPacient = idFisaPacient;
    }
    public int getIdFisaPacient()
    {
        return idFisaPacient;
    }*/

    public void setCnp(String cnp)
    {
        this.cnp = cnp;
    }
    public String getCnp()
    {
        return cnp;
    }

    public String getName()
    {
        return name;
    }

    public Pacient()
    {
        super();
        this.name = "Pacient";
        this.cnp = "";
      //  this.idFisaPacient = 0;
    }


    public String toString() {
        return "Pacient{id=" + getId() + ", nume='" + name + "', cnp='" + cnp + "'}";
    }

    public Pacient(int id, String name, String cnp) {
        super(id);  // apelăm constructorul din Persoana
        this.name = name;
        this.cnp = cnp;
    }


    // Funcție de citire a datelor pacientului de la tastatură
    public void citesteDatePacient()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceți numele pacientului: ");
        this.name = scanner.nextLine();

        System.out.print("Introduceți CNP-ul pacientului: ");
        this.cnp = scanner.nextLine();

        //System.out.print("Introduceți ID-ul fișei pacientului: ");
        //this.idFisaPacient = scanner.nextInt();

        // Nu este necesar să închidem scanner-ul aici dacă vrem să-l folosim în continuare
    }

    public void afisarePacient()
    {
        System.out.println("Id-ul pacientului: " + getId());
        System.out.println("Numele pacientului: " + this.name);
        System.out.println("CNP-ul pacientului: " + this.cnp);
      //  System.out.println("Afiseaza fisa pacientului: " + this.idFisaPacient);
    }

}