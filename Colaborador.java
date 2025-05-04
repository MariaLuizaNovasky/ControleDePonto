package ControleDePonto;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;

public class Colaborador {
	public static void main(String[] args) {
		final String DRIVER = "com.mysql.cj.jdbc.Driver";
		final String HOST = "localhost";
		final int PORT = 3306;
		final String DATABASE = "Banco";
		final String URL = "jdbc:mysql://" + HOST +
							":" + PORT +
							"/" + DATABASE;
		final String USER = "admin";
		final String PASSWORD = "Uni@2025";

        Scanner scanner = new Scanner(System.in);

        System.out.print ("Insira o nome: ");
        String nome = scanner.nextLine();

        System.out.print ("Insira o CPF: ");
        String cpf = scanner.nextLine();

        System.out.print ("Insira o cargo: ");
        String cargo = scanner.nextLine();

        System.out.print("Insira a Data de Admissão (formato yyyy-mm-dd): ");
        String admissaoStr = scanner.nextLine();

        Date admissao = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date utilDate = sdf.parse(admissaoStr);
            admissao = new java.sql.Date(utilDate.getTime());
        } catch (Exception e) {
            System.out.println("Erro ao converter a data: " + e.getMessage());
            scanner.close();
            return;
        }

        // Gera ID de login aleatório de 4 dígitos
        Random random = new Random();
        int idLogin = 1000 + random.nextInt(9000);  

        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO colaborador (id_login, nome, cpf, cargo, admissao) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, idLogin);
            statement.setString(2, nome);
            statement.setString(3, cpf);
            statement.setString(4, cargo);
            statement.setDate(5, admissao);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("  ");
                System.out.println("Colaborador inserido com sucesso!");
                System.out.println("ID de login gerado para o colaborador: " + idLogin);
            }

            statement.close();
            connection.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("ID de login duplicado. Tente novamente.");
        } catch (ClassNotFoundException erro) {
            System.out.println("Driver não encontrado!\n" + erro.toString());
        } catch (SQLException erro) {
            System.out.println("Problemas na conexão com a fonte de dados\n" + erro.toString());
        } finally {
            scanner.close();
        }
    }
}
