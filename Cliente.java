package grupo2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class Cliente implements Serializable{
        private static final long serialVersionUID = 100L;

    private String nome, endereco, email;
    private double nota;
    private static int cont = lerContador(), cpf, telefone;
    private int id;
    
    public Cliente(){
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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
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
