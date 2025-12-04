package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;

@Service
public class StudentService {

    private ArrayList<Student> students = new ArrayList<>();
    private final String FILE_NAME = "students.dat";
    private final String ID_FILE = "last_id.txt";
    private int nextId = 1;

    // ✅ Used by BOTH console & web
    public StudentService() {
        loadLastId();
        loadFromFile();
    }

    // =========================
    // ===== WEB METHODS =======
    // =========================

    public void addStudent(String name, double marks) {
        Student s = new Student(nextId, name, marks);
        students.add(s);
        nextId++;
        saveLastId();
        saveToFile();

        System.out.println("WEB ADD -> total students = " + students.size());
    }

    public ArrayList<Student> getAllStudents() {
        return students;
    }

    public void deleteById(int id) {
        students.removeIf(s -> s.getId() == id);
        saveToFile();
    }

    // =========================
    // ===== CONSOLE METHODS ===
    // =========================

    public void viewAll() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
            return;
        }
        for (Student s : students) {
            System.out.println(s);
        }
    }

    public void searchById(int id) {
        for (Student s : students) {
            if (s.getId() == id) {
                System.out.println("Found: " + s);
                return;
            }
        }
        System.out.println("Student not found.");
    }

    public void updateMarks(int id, double newMarks) {
        for (Student s : students) {
            if (s.getId() == id) {
                s.setMarks(newMarks);
                saveToFile();
                return;
            }
        }
    }


    // =========================
    // ===== FILE HANDLING =====
    // =========================

    private void saveToFile() {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveLastId() {
        try (FileWriter fw = new FileWriter(ID_FILE)) {
            fw.write(String.valueOf(nextId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void loadLastId() {
        File file = new File(ID_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            nextId = Integer.parseInt(br.readLine());
        } catch (Exception e) {
            nextId = 1;
        }
    }


}
