package ma.dnaengineering.backend.controller;

import ma.dnaengineering.backend.model.Employee;
import ma.dnaengineering.backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @PostMapping("/upload")
    public ResponseEntity<String> uploadEmployeeData(@RequestParam("file") MultipartFile file) {
        try {
            // Appeler le service pour traiter le fichier CSV
            employeeService.processCSV(file);
            return new ResponseEntity<>("Fichier traité avec succès", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Erreur lors du traitement du fichier", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        try {
            List<Employee> employees = employeeService.parseCSV();
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/average-salary")
    public ResponseEntity<Map<String, BigDecimal>> getAverageSalaryByJobTitle() {
        try {
            Map<String, BigDecimal> jobTitleAverageSalaryMap = employeeService.calculateAverageSalaryByJobTitle();
            return new ResponseEntity<>(jobTitleAverageSalaryMap, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
