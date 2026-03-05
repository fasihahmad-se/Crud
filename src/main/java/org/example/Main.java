package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Main obj = new Main();

        System.out.println("1. Insert User");
        System.out.println("2. View Users");
        System.out.println("3. Update User");
        System.out.println("4. Delete User");
        System.out.print("Enter your choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Enter name: ");
                String name = sc.nextLine();

                System.out.print("Enter age: ");
                int age = sc.nextInt();
                sc.nextLine();

                System.out.print("Enter your Email Address: ");
                String email = sc.nextLine();


                while (!isValid(email)) {
                    System.out.println("Please enter a valid email");
                    if (sc.hasNext()) {
                        email = sc.nextLine();
                    } else {
                        System.out.println("Invalid input. Please enter an email.");
                        sc.next();
                    }
                }

                System.out.print("Enter Phone Number: ");
                String phone = sc.nextLine();


                while (!isValidMobile(phone)) {
                    System.out.println("Please enter a valid phone number");
                    if (sc.hasNext()) {
                        phone = sc.nextLine();
                    } else {
                        System.out.println("Invalid phone number.");
                        sc.next();
                    }
                }

                obj.insertUser(name, age, email, phone);
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
                sc.nextLine();

                System.out.print("Enter new email to update :");
                String newEmail=sc.nextLine();

                while (!isValid(newEmail)) {
                    System.out.println("Please enter a valid email");
                    if (sc.hasNext()) {
                        newEmail = sc.nextLine();
                    } else {
                        System.out.println("Invalid input. Please enter an email.");
                        sc.next();
                    }
                }

                System.out.print("Enter new phone number to update :");
                String newPhone=sc.nextLine();

                while (!isValidMobile(newPhone)) {
                    System.out.println("Please enter a valid phone number");
                    if (sc.hasNext()) {
                        newPhone = sc.nextLine();
                    } else {
                        System.out.println("Invalid phone number.");
                        sc.next();
                    }
                }


                obj.updateUser(updateId, newName, newAge , newEmail , newPhone);
                break;

            case 4:
                System.out.print("Enter user ID to delete: ");
                int deleteId = sc.nextInt();

                obj.deleteUser(deleteId);
                break;


            default:
                System.out.println("Invalid choice!");
        }

    }

    public static boolean isValid(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern p = Pattern.compile(emailRegex);
        return email != null && p.matcher(email).matches();

    }

        public static boolean isValidMobile(String phone) {
             String PK_MOBILE_REGEX = "^(?:\\+92|92|0)?3\\d{9}$";
            if (phone == null) return false;
            return Pattern.matches(PK_MOBILE_REGEX, phone);
        }


    public void insertUser(String name, int age,String email, String phone) {
        String sql = "INSERT INTO users (name, age,email,phone) VALUES (?, ? ,? ,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3,email);
            pst.setString( 4,phone);

            int rowsAffected = pst.executeUpdate();
            System.out.println(rowsAffected + " User(s) Inserted Successfully!");

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
                String email=resultSet.getString("email");
                String phone=resultSet.getString("phone");

                System.out.println( id + " | " + name + " | " + age + " | "+email + " | "+ phone);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(int id, String name, int age , String email,String phone) {
        String sql = "UPDATE users SET name = ?, age = ?, email = ?, phone = ? WHERE id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, name);
            pst.setInt(2, age);
            pst.setString(3,email);
            pst.setString(4,phone);
            pst.setInt(5, id);

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