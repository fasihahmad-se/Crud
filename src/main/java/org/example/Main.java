package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
          Main obj = new Main();

        System.out.println("1. Insert User");
        System.out.println("2. View Users");
        System.out.println("3. Update User");
        System.out.println("4. Delete User");
        System.out.println("5. Enter Email");
        System.out.println("6. Enter Phone Number");
        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter name: ");
                String name = sc.nextLine();

                System.out.print("Enter age: ");
                int age = sc.nextInt();

                System.out.print("Enter your Email Address:  ");
                String email= sc.nextLine();

                System.out.print("Enter Phone Number:   ");
                int phone=sc.nextInt();
                sc.nextLine();

                obj.insertUser(name, age,email,phone);
                break;
            case 2:
                obj.viewUsers();
                break;
            case 3:
                System.out.print("Enter user ID to update: ");
                int updateId = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter new name: ");
                String newName = sc.nextLine();

                System.out.print("Enter new age: ");
                int newAge = sc.nextInt();

                obj.updateUser(updateId, newName, newAge);
                break;

            case 4:
                System.out.print("Enter user ID to delete: ");
                int deleteId = sc.nextInt();

                obj.deleteUser(deleteId);
                break;


            default:
                System.out.println("Invalid choice!");
        }

        sc.close();
    }

    public void insertUser(String name, int age,String email, int phone) {
        String sql = "INSERT INTO users (name, age,email,phone) VALUES (?, ? ,? ,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3,email);
            pst.setInt(4,phone);

            int rowsAffected = pst.executeUpdate();
            System.out.println(rowsAffected + " user(s) inserted successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewUsers() {
        String sql = "SELECT * FROM users";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             java.sql.ResultSet resultSet = pst.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");

                System.out.println( id + " | " + name + " | " + age);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(int id, String name, int age) {
        String sql = "UPDATE users SET name = ?, age = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setInt(3, id);
            int rowAffected = pst.executeUpdate();

            if (rowAffected > 0) {
                System.out.println("Row updated successfully.");
            } else {
                System.out.println("User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteUser(int id) {
        String sql = "DELETE FROM users where id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, id);
            int rowAffected = pst.executeUpdate();

            if (rowAffected > 0) {
                System.out.println("Row updated successfully.");
            } else {
                System.out.println("User not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}