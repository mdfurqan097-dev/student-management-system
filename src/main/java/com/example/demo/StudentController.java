package com.example.demo;
import java.util.Comparator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    // ✅ SORTING HANDLER
    @GetMapping("/students")
    public String home(Model model,
                       @RequestParam(required = false) String sort,
                       @RequestParam(required = false) String dir) {

        var list = service.getAllStudents();

        if (sort != null && dir != null) {
            switch (sort) {
                case "id" -> list.sort(dir.equals("asc") ?
                        Comparator.comparingInt(Student::getId) :
                        Comparator.comparingInt(Student::getId).reversed());

                case "name" -> list.sort(dir.equals("asc") ?
                        Comparator.comparing(Student::getName) :
                        Comparator.comparing(Student::getName).reversed());

                case "marks" -> list.sort(dir.equals("asc") ?
                        Comparator.comparingDouble(Student::getMarks) :
                        Comparator.comparingDouble(Student::getMarks).reversed());
            }
        }

        model.addAttribute("students", list);
        model.addAttribute("debugCount", list.size());
        model.addAttribute("sort", sort);
        model.addAttribute("dir", dir);

        return "students";
    }

    @PostMapping("/add")
    public String addStudent(@RequestParam String name,
                             @RequestParam double marks,
                             RedirectAttributes ra) {

        if (name.isBlank() || marks < 0 || marks > 100) {
            ra.addFlashAttribute("error", "Invalid Name or Marks (0–100 only)");
            return "redirect:/students";
        }

        service.addStudent(name, marks);
        return "redirect:/students";
    }

    @PostMapping("/update")
    public String update(@RequestParam int id,
                         @RequestParam double marks) {

        service.updateMarks(id, marks);
        return "redirect:/students";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        service.deleteById(id);
        return "redirect:/students";
    }
}
