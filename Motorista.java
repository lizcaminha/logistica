package grupo2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class Motorista implements Serializable{
        private static final long serialVersionUID = 100L;

    private String nome, 
    private static int cpf, cnh, exp;
    private static int cont = lerContador();
    private int id;
    
    public Motorista(){
        cont++;
        id = cont;
        salvarContador(cont);
    }
    
    public static int getCont() {
        return cont;
    }

    public static void setCont(int aCont) {
        cont = aCont;
    }

    public String getNome() {
        return nome;
    }

    public void setCPF(int cpf) {
        this.cpf = cpf;
    }
    public String getCPF() {
        return cpf;
    }

    public void setCNH(int cnh) {
        this.cnh = cpf;
    }
    public int getId() {
        return id;
    }
    
    public static void salvarContador(int valor) {
        try (FileWriter fw = new FileWriter("src/data/contador.txt")) {
            fw.write(String.valueOf(valor));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int lerContador() {
        File arquivo = new File("src/data/contador.txt");
        if (!arquivo.exists()) {
            return 0;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            return Integer.parseInt(br.readLine());
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
