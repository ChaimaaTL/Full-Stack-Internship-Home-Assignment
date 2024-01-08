package ma.dnaengineering.backend.service;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.opencsv.CSVReader;


import ma.dnaengineering.backend.model.Employee;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EmployeeService {


    public Map<String, BigDecimal> calculateAverageSalaryByJobTitle() throws IOException {
        List<Employee> employees = parseCSV();
        Map<String, BigDecimal> jobTitleAverageSalaryMap = new HashMap<>();
        Map<String, BigDecimal> jobTitleSalarySum = new HashMap<>();
        Map<String, Integer> jobTitleCount = new HashMap<>();

        for (Employee employee : employees) {
            String jobTitle = employee.getJobTitle();
            BigDecimal salary = employee.getSalary();

            // Met à jour la somme des salaires pour chaque titre de poste
            jobTitleSalarySum.put(jobTitle, jobTitleSalarySum.getOrDefault(jobTitle, BigDecimal.ZERO).add(salary));

            // Incrémente le nombre d'employés pour chaque titre de poste
            jobTitleCount.put(jobTitle, jobTitleCount.getOrDefault(jobTitle, 0) + 1);
        }

        // Calcule la moyenne pour chaque titre de poste
        for (Map.Entry<String, BigDecimal> entry : jobTitleSalarySum.entrySet()) {
            String jobTitle = entry.getKey();
            BigDecimal totalSalary = entry.getValue();
            int count = jobTitleCount.get(jobTitle);

            BigDecimal averageSalary = totalSalary.divide(BigDecimal.valueOf(count), 2, BigDecimal.ROUND_HALF_UP);
            jobTitleAverageSalaryMap.put(jobTitle, averageSalary);
        }

        return jobTitleAverageSalaryMap;
    }
    public List<Employee> parseCSV() throws IOException {
        List<Employee> employees = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("src/main/java/ma/dnaengineering/backend/service/data/employees.csv"))) {
            String[] headers = reader.readNext(); // Lire la première ligne pour obtenir les en-têtes

            // Assurez-vous que les en-têtes sont présents et ont les noms attendus
            if (headers != null && headers.length == 4 && "id".equals(headers[0]) && "employee_name".equals(headers[1])
                    && "job_title".equals(headers[2]) && "salary".equals(headers[3])) {

                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    Employee employee = new Employee();
                    try {
                        // Utilisez des constantes pour représenter les indices des colonnes
                        employee.setId(Long.parseLong(nextLine[0]));
                        employee.setEmployeeName(nextLine[1]);
                        employee.setJobTitle(nextLine[2]);
                        employee.setSalary(new BigDecimal(nextLine[3]));

                        employees.add(employee);
                    } catch (NumberFormatException e) {
                        System.err.println("Erreur de conversion numérique dans le fichier CSV : " + e.getMessage());
                        // Vous pouvez choisir de gérer cette exception de la manière qui vous convient
                    }
                }
            } else {
                System.err.println("En-têtes du fichier CSV invalides.");
                // Gérer le cas où les en-têtes ne sont pas conformes aux attentes
            }
        }

        return employees;
    }


    // ... autres méthodes existantes ...

    public void processCSV(MultipartFile file) throws IOException {

        System.out.println("Début du traitement du fichier CSV dans le service...");
        List<Employee> employees = new ArrayList<>();

        try (InputStream is = file.getInputStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {

            // Ignorer la première ligne (en-têtes)
            String line = br.readLine();

            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");

                if (columns.length == 4) { // Vérifier si le nombre de colonnes est correct
                    Employee employee = new Employee();
                    employee.setId(Long.parseLong(columns[0]));
                    employee.setEmployeeName(columns[1]);
                    employee.setJobTitle(columns[2]);
                    employee.setSalary(new BigDecimal(columns[3]));

                    employees.add(employee);
                }
            }
        }
        System.out.println("Fin du traitement du fichier CSV dans le service.");
    }
}
