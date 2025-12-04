package com.example.demo;

import java.util.Scanner;

public class StudentManagementApp {
    private static Scanner sc = new Scanner(System.in);
    static StudentService service = new StudentService();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n==== STUDENT MANAGEMENT ====");
            System.out.println("1. View Students");
            System.out.println("2. Add Student");
            System.out.println("3. Search Students");
            System.out.println("4. Update Marks");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");

            System.out.print("Enter your choice :");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> service.viewAll();
                case 2 -> addStudent();
                case 3 -> search();
                case 4 -> update();
                case 5 -> delete();
                case 6 -> {
                    System.out.println("Good Bye");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    static void addStudent() {
        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        service.addStudent(name, marks);   // ✅ ID auto-generated
    }

    static void search() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        service.searchById(id);
    }

    static void update() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        System.out.print("Enter New Marks: ");
        double marks = sc.nextDouble();

        service.updateMarks(id, marks);
    }

    static void delete() {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        service.deleteById(id);
    }
}