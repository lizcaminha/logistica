import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Grupo2 {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        SistemaLogistica sistema = new SistemaLogistica();
        int opcao;

        do {
            System.out.println("\n=== SISTEMA DE LOGÍSTICA ===");
            System.out.println("1 - Cadastrar motorista");
            System.out.println("2 - Cadastrar veículo");
            System.out.println("3 - Cadastrar entrega");
            System.out.println("4 - Listar motoristas");
            System.out.println("5 - Listar veículos");
            System.out.println("6 - Listar entregas");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = entrada.nextInt();
            entrada.nextLine(); // limpar buffer

            switch (opcao) {
                case 1:
                    sistema.cadastrarMotorista(entrada);
                    break;
                case 2:
                    sistema.cadastrarVeiculo(entrada);
                    break;
                case 3:
                    sistema.cadastrarEntrega(entrada);
                    break;
                case 4:
                    sistema.listarMotoristas();
                    break;
                case 5:
                    sistema.listarVeiculos();
                    break;
                case 6:
                    sistema.listarEntregas();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 0);

        entrada.close();
    }
}

// ==================== SISTEMA PRINCIPAL ====================
class SistemaLogistica {
    private ArrayList<Motorista> motoristas = new ArrayList<>();
    private ArrayList<Veiculo> veiculos = new ArrayList<>();
    private ArrayList<Entrega> entregas = new ArrayList<>();

    // Cadastrar Motorista
    public void cadastrarMotorista(Scanner entrada) {
        System.out.print("Nome: ");
        String nome = entrada.nextLine();
        System.out.print("CPF: ");
        String cpf = entrada.nextLine();
        System.out.print("Categoria CNH: ");
        String cnh = entrada.nextLine();
        System.out.print("Anos de experiência: ");
        int anos = entrada.nextInt();
        entrada.nextLine();

        Motorista m = new Motorista(nome, cpf, cnh, anos);
        motoristas.add(m);
        System.out.println("Motorista cadastrado com sucesso!");

        try (FileOutputStream fileOut = new FileOutputStream("src/data/Motorista"+m.getId()+".ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(m); //Salva o aluno
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found Excpetion");
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }

    // Cadastrar Veículo
    public void cadastrarVeiculo(Scanner entrada) {
        System.out.print("Placa: ");
        String placa = entrada.nextLine();
        System.out.print("Modelo: ");
        String modelo = entrada.nextLine();
        System.out.print("Ano: ");
        int ano = entrada.nextInt();
        System.out.print("Capacidade de carga (kg): ");
        double carga = entrada.nextDouble();
        entrada.nextLine();

        Veiculo v = new Veiculo(placa, modelo, ano, carga);
        veiculos.add(v);
        System.out.println("Veículo cadastrado com sucesso!");

        try (FileOutputStream fileOut = new FileOutputStream("src/data/Veiculo"+v.getId()+".ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(v); //Salva o aluno
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found Excpetion");
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }

    // Cadastrar Entrega
    public void cadastrarEntrega(Scanner entrada) {
        if (motoristas.isEmpty() || veiculos.isEmpty()) {
            System.out.println("Cadastre pelo menos 1 motorista e 1 veículo antes.");
            return;
        }

        System.out.print("Destino da entrega: ");
        String destino = entrada.nextLine();
        System.out.print("Distância (km): ");
        double distancia = entrada.nextDouble();
        entrada.nextLine();

        Motorista m = motoristas.get(0);
        Veiculo v = veiculos.get(0);
        Rota r = new Rota("Centro", destino, distancia);

        Entrega e = new Entrega(m, v, r);
        entregas.add(e);

        System.out.println("Entrega cadastrada com sucesso!");

        try (FileOutputStream fileOut = new FileOutputStream("src/data/Entrega"+e.getId()+".ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(e); //Salva o aluno
        } catch (FileNotFoundException ex) {
            System.out.println("File Not Found Excpetion");
        } catch (IOException ex) {
            System.out.println("IO Exception");
        }
    }

    // Listagens
    public void listarMotoristas() {
        if (motoristas.isEmpty()) {
            System.out.println("Nenhum motorista cadastrado.");
            return;
        }
        System.out.println("\n--- MOTORISTAS ---");
        for (Motorista m : motoristas) {
            System.out.println(m);
        }
    }

    public void listarVeiculos() {
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
            return;
        }
        System.out.println("\n--- VEÍCULOS ---");
        for (Veiculo v : veiculos) {
            System.out.println(v);
        }
    }

    public void listarEntregas() {
        if (entregas.isEmpty()) {
            System.out.println("Nenhuma entrega cadastrada.");
            return;
        }
        System.out.println("\n--- ENTREGAS ---");
        for (Entrega e : entregas) {
            System.out.println(e);
        }
    }
}

// ==================== CLASSES DE DADOS ====================

class Motorista {
    private String nome;
    private String cpf;
    private String categoriaCNH;
    private int anosExperiencia;
    private double salario;

    public Motorista(String nome, String cpf, String categoriaCNH, int anosExperiencia) {
        this.nome = nome;
        this.cpf = cpf;
        this.categoriaCNH = categoriaCNH;
        this.anosExperiencia = anosExperiencia;
        this.salario = 2000 + (anosExperiencia * 150);
    }

    public double calcularBonus() {
        return anosExperiencia * 100;
    }

    @Override
    public String toString() {
        return nome + " | CPF: " + cpf + " | CNH: " + categoriaCNH + " | Bônus: R$" + calcularBonus();
    }
    
}

class Veiculo {
    private String placa;
    private String modelo;
    private int ano;
    private double capacidadeCarga;
    private double quilometragem;

    public Veiculo(String placa, String modelo, int ano, double capacidadeCarga) {
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.capacidadeCarga = capacidadeCarga;
        this.quilometragem = 0;
    }

    public double calcularConsumo(double distancia) {
        double consumoMedio = 5.0; // km/l
        return distancia / consumoMedio;
    }

    @Override
    public String toString() {
        return placa + " | " + modelo + " | Ano: " + ano + " | Capacidade: " + capacidadeCarga + "kg";
    }
}

class Entrega {
    private Motorista motorista;
    private Veiculo veiculo;
    private Rota rota;
    private boolean entregue;

    public Entrega(Motorista motorista, Veiculo veiculo, Rota rota) {
        this.motorista = motorista;
        this.veiculo = veiculo;
        this.rota = rota;
        this.entregue = false;
    }

    public double calcularCusto() {
        return rota.getDistancia() * 2.5; // custo base por km
    }

    @Override
    public String toString() {
        return "Entrega para " + rota.getDestino() +
               " | Distância: " + rota.getDistancia() + "km" +
               " | Custo estimado: R$" + calcularCusto();
    }
}

class Rota {
    private String origem;
    private String destino;
    private double distancia;

    public Rota(String origem, String destino, double distancia) {
        this.origem = origem;
        this.destino = destino;
        this.distancia = distancia;
    }

    public String getDestino() {
        return destino;
    }

    public double getDistancia() {
        return distancia;
    }

    public double calcularCustoPedagio() {
        return distancia * 0.1;
    }
}
//INTERFACE DE SALVAMENTO
 private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

        File pasta = new File(diretorio);
        Pattern pattern = Pattern.compile(padraoArquivo);
        File[] arquivos = pasta.listFiles((dir, name) -> pattern.matcher(name).matches());
        
        int i=1;
        StringBuilder sb = new StringBuilder("<html>");
        for (File arq : arquivos) {//for-each
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq))) {
                Motorista m = (Motorista) ois.readObject();
                sb.append("Motorista ")
                .append(i)
                .append(": Nome: ")
                .append(m.getNome())
                .append(" | CPF: ")
                .append(m.getCPF())
                .append(": Categoria CNH: ")
                .append(m.getCNH())
                .append(" | Anos de Experiência: ")
                .append(m.getExp())
                .append("<br>");    
                i++;
            } catch (Exception e) {
                System.out.println("Erro ao ler arquivo: "+arq.getName()+"-"+e.getMessage());
            }
            //VER SE ISSO ESTA CERTO
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq))) {
                Veiculo v = (Veiculo) ois.readObject();
                sb.append("Veículo ")
                .append(i)
                .append(": Placa: ")
                .append(v.getPlaca())
                .append(" | Modelo: ")
                .append(v.getModelo())
                .append(": Ano: ")
                .append(v.getAno())
                .append(" | Capacidade (kgs): ")
                .append(v.getCarga())
                .append("<br>");    
                i++;
            } catch (Exception e) {
                System.out.println("Erro ao ler arquivo: "+arq.getName()+"-"+e.getMessage());
            }
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arq))) {
                Entrega e = (Entrega) ois.readObject();
                sb.append("Entrega ")
                .append(i)
                .append(": Motorista: ")
                .append(e.getMotorista())
                .append(" | Veículo: ")
                .append(e.getVeiculo())
                .append(": Rota: ")
                .append(e.getRota())
                .append("<br>");    
                i++;
            } catch (Exception e) {
                System.out.println("Erro ao ler arquivo: "+arq.getName()+"-"+e.getMessage());
            }
        }
        sb.append("</html>");
        jLabel3.setText(sb.toString());
                
        CardLayout cla = (CardLayout) (jPanel2.getLayout());
        cla.show(jPanel2, "card3");
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Cadastro().setVisible(true));
    }

//Verificador de CPF

public class ValidadorCPF {
    public static boolean eCPF(string cpf){
        if (cpf.equals("00000000000")||("11111111111")||("22222222222")||("33333333333")||("44444444444")||("55555555555")||("66666666666")||("77777777777")||("88888888888")||("999999999999")|| (cpf.length!=11)){
            return(false);
        }
        try{
            int soma = 0;
            int peso = 10;
            for(int i=0; i<9; i++){
                int numero = (int) (cpf.charAt(i)-48);
                soma = soma + (numero*peso);
            }
        }
    }
}