package ControleDePonto;

import java.sql.*;
import java.util.Scanner;

public class RegistroPonto {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Connection connection = Conexao.conectar();  // <<< Agora a conexão vem da sua classe externa

            System.out.print("Digite o ID do colaborador: ");
            int id_login = scanner.nextInt();
            scanner.nextLine();

            System.out.println("Qual ponto deseja registrar?");
            System.out.println("1 - Entrada");
            System.out.println("2 - Saída para almoço");
            System.out.println("3 - Volta do almoço");
            System.out.println("4 - Saída final");
            int opcao = scanner.nextInt();

            String coluna = null;
            switch (opcao) {
                case 1: coluna = "hora_entrada"; break;
                case 2: coluna = "hora_saida_almoco"; break;
                case 3: coluna = "hora_volta_almoco"; break;
                case 4: coluna = "hora_saida_final"; break;
                default:
                    System.out.println("Opção inválida.");
                    connection.close();
                    return;
            }

            String updateSql = "UPDATE ponto SET " + coluna + " = CURRENT_TIME() WHERE id_login = ? AND data_registro = CURDATE()";
            PreparedStatement updateStmt = connection.prepareStatement(updateSql);
            updateStmt.setInt(1, id_login);
            int rowsUpdated = updateStmt.executeUpdate();

            if (rowsUpdated == 0) {
                String insertSql = "INSERT INTO ponto (id_login, data_registro, " + coluna + ") VALUES (?, CURDATE(), CURRENT_TIME())";
                PreparedStatement insertStmt = connection.prepareStatement(insertSql);
                insertStmt.setInt(1, id_login);
                insertStmt.executeUpdate();
                insertStmt.close();
                System.out.println("Registro de ponto criado e salvo com sucesso!");
            } else {
                System.out.println("Ponto atualizado com sucesso!");
            }

            updateStmt.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Erro ao acessar o banco de dados!\n" + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
