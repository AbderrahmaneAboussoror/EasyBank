package com.easybank.display;

import com.easybank.Main;
import com.easybank.Person.Person;
import com.easybank.Person.employee.Employee;
import com.easybank.Person.employee.IEmployeeService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EmployeeMenu {
    private final IEmployeeService _iEmployeeService;

    public EmployeeMenu(IEmployeeService iEmployeeService) {
        _iEmployeeService = iEmployeeService;
    }
    public void show() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("==== Menu ====");
            System.out.println("1. Ajouter un employé");
            System.out.println("2. Rechercher un employé");
            System.out.println("3. Supprimer un employé");
            System.out.println("4. Modifier un employé");
            System.out.println("5. Afficher les employés");
            System.out.println("6. Retour");

            try {
                System.out.print("Entrez votre choix: ");
                choice = scanner.nextInt();
            }catch (InputMismatchException e) {
                scanner.nextLine();
                choice = 0;
            }

            switch (choice) {
                case 1 -> {
                    scanner.nextLine();
                    System.out.println("\nVous avez choisi l'ajout.\n\nTapez 1 si vous voulez continuer, ou 0 pour revenir au menu précédent: ");
                    String query = scanner.nextLine();
                    if (Objects.equals(query, "0")) { choice = 6; }
                    else {
                        System.out.println("Entrer le prénom de l'employé : ");
                        String firstName = scanner.nextLine();
                        System.out.println("Entrer le nom de l'employé : ");
                        String lastName = scanner.nextLine();
                        System.out.println("Entrer la date de naissance (yyyy/MM/dd) : ");
                        String birthDateInput = scanner.nextLine();
                        DateTimeFormatter birthDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDate birthDate = LocalDate.parse(birthDateInput, birthDateFormatter);
                        System.out.println("Entrer le numéro de téléphone : ");
                        String phoneNumber = scanner.nextLine();
                        System.out.println("Entrer l'email : ");
                        String email = scanner.nextLine();
                        System.out.println("Entrer la date de recrutement (yyyy/MM/dd) : ");
                        String recruitedAtInput = scanner.nextLine();
                        DateTimeFormatter recruitedAtFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDate recruitedAt = LocalDate.parse(recruitedAtInput, recruitedAtFormatter);
                        Random random = new Random();
                        String code = "HH" + random.nextInt(1000) + 1;
                        Person employee = new Employee(code, firstName, lastName, birthDate, phoneNumber, email, recruitedAt);
                        Optional<Person> employeeSaved = _iEmployeeService.save(employee);
                        if (employeeSaved.isPresent()) {
                            System.out.println("Employé ajouté !\n\n");
                            System.out.println(employeeSaved.get().toString());
                        } else {
                            System.out.println("Employé non trouvé");
                        }

                        System.out.println("\nAppuyez sur n'importe quelle clé pour revenir");
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                        try {
                            bufferedReader.readLine();
                            show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case 2 -> {
                    scanner.nextLine();
                    System.out.println("\nVous avez choisi la recherche.\n\nTapez 1 si vous voulez continuer, ou 0 pour revenir au menu précédent: ");
                    String query = scanner.nextLine();
                    if (Objects.equals(query, "0")) { choice = 6; }
                    else {
                        System.out.println("Tapez n'importe quel attribut pour rechercher l'employé voulu : ");
                        String attribute = scanner.nextLine();
                        Optional<Person> employee = _iEmployeeService.find(attribute);
                        if (employee.isPresent()) {
                            System.out.println(employee.get().toString());
                        } else {
                            System.out.println("Employé non trouvé");
                        }
                    }
                    System.out.println("\nAppuyez sur n'importe quelle clé pour revenir");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        bufferedReader.readLine();
                        show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 3 -> {
                    scanner.nextLine();
                    System.out.println("\nVous avez choisi la suppression.\n\nTapez 1 si vous voulez continuer, ou 0 pour revenir au menu précédent: ");
                    String query = scanner.nextLine();
                    if (Objects.equals(query, "0")) { choice = 6; }
                    else {
                        System.out.println("Tapez n'importe la matricule pour supprimer l'employé voulu : ");
                        String code = scanner.nextLine();
                        if (_iEmployeeService.delete(code)) { System.out.println("Employé supprimé !"); }
                        else { System.out.println("Employé non supprimé !!!!"); }
                        System.out.println("\nAppuyez sur n'importe quelle clé pour revenir");
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                        try {
                            bufferedReader.readLine();
                            show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case 4 -> {
                    scanner.nextLine();
                    System.out.println("\nVous avez choisi la modification.\n\nTapez 1 si vous voulez continuer, ou 0 pour revenir au menu précédent: ");
                    String query = scanner.nextLine();
                    if (Objects.equals(query, "0")) { choice = 6; }
                    else {
                        System.out.println("Tapez la matricule de l'employé voulu : ");
                        String attribute = scanner.nextLine();
                        Optional<Person> employee = _iEmployeeService.find(attribute);
                        if (employee.isPresent()) {
                            Employee e = (Employee) employee.get();
                            System.out.println("Entrer le prénom de l'employé : ");
                            e.set_firstName(scanner.nextLine());
                            System.out.println("Entrer le nom de l'employé : ");
                            e.set_lastName(scanner.nextLine());
                            System.out.println("Entrer la date de naissance (yyyy/MM/dd) : ");
                            String birthDateInput = scanner.nextLine();
                            DateTimeFormatter birthDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                            LocalDate birthDate = LocalDate.parse(birthDateInput, birthDateFormatter);
                            e.set_birthDate(birthDate);
                            System.out.println("Entrer le numéro de téléphone : ");
                            e.set_phoneNumber(scanner.nextLine());
                            System.out.println("Entrer l'email : ");
                            e.set_email(scanner.nextLine());
                            System.out.println("Entrer la date de recrutement (yyyy/MM/dd) : ");
                            String recruitedAtInput = scanner.nextLine();
                            DateTimeFormatter recruitedAtFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                            LocalDate recruitedAt = LocalDate.parse(recruitedAtInput, recruitedAtFormatter);
                            e.set_recruitedAt(recruitedAt);

                            Optional<Person> newEmployee = _iEmployeeService.update(e);
                            newEmployee.ifPresentOrElse(person -> System.out.println("Employé modifié !!\n\n" + person.toString()), () -> System.out.println("Employé non modifié!!!!!!!"));
                        } else {
                            System.out.println("Employé non trouvé");
                        }
                        System.out.println("\nAppuyez sur n'importe quelle clé pour revenir");
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                        try {
                            bufferedReader.readLine();
                            show();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                case 5 -> {
                    scanner.nextLine();
                    System.out.println("\nVous avez choisi l'affichage.\n\nTapez 1 si vous voulez continuer, ou 0 pour revenir au menu précédent: ");
                    String query = scanner.nextLine();
                    if (Objects.equals(query, "0")) { choice = 6; }
                    else {
                        List<Person> employees = _iEmployeeService.findAll();
                        employees.forEach(System.out::println);
                    }
                    System.out.println("\nAppuyez sur n'importe quelle clé pour revenir");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        bufferedReader.readLine();
                        show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 6 -> {
                    return;
                }
            }
        } while (choice != 6);

    }
}
