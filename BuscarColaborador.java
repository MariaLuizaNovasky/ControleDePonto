package ControleDePonto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
 
public class BuscarColaborador {
	public static <Date> void main(String[] args) {
		final String DRIVER = "com.mysql.cj.jdbc.Driver";
		final String HOST = "localhost";
		final int PORT = 3306;
		final String DATABASE = "banco";
		final String URL = "jdbc:mysql://" + HOST +
							":" + PORT +
							"/" + DATABASE;
		final String USER = "admin";
		final String PASSWORD = "Uni@2025";
 
        Scanner scanner = new Scanner(System.in);
 
        System.out.print("Digite o ID do colaborador que deseja buscar: ");
        int id_login = scanner.nextInt();
 
        try {
            Class.forName(DRIVER);
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
 
            String sql = "SELECT * FROM colaborador WHERE id_login = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id_login);
 
            ResultSet resultSet = statement.executeQuery();
 
            if (resultSet.next()) {
                int idLogin = resultSet.getInt("id_login");
                String nome = resultSet.getString("nome");
                String cpf = resultSet.getString("cpf");
                String cargo = resultSet.getString("cargo");
                java.sql.Date admissao = resultSet.getDate("admissao");
 
                System.out.println("\n=== Colaborador Encontrado ===");
                System.out.println("ID de Login: " + idLogin);
                System.out.println("Nome: " + nome);
                System.out.println("CPF: " + cpf);
                System.out.println("Cargo: " + cargo);
                System.out.println("Admissão: " + admissao);
            } else {
                System.out.println("Nenhum colaborador encontrado com o ID: " + id_login);
            }
 
            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC não encontrado!\n" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco de dados!\n" + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}