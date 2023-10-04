package com.easybank.display;

import com.easybank.Person.Person;
import com.easybank.Person.client.Client;
import com.easybank.Person.client.ClientService;
import com.easybank.Person.client.IClientService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ClientMenu {
    private final IClientService _iClientService;
    public ClientMenu(IClientService iClientService) {
        this._iClientService = iClientService;
    }
    public void show() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("==== Menu ====");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Rechercher un client");
            System.out.println("3. Supprimer un client");
            System.out.println("4. Modifier un client");
            System.out.println("5. Afficher les clients");
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
                        System.out.println("Entrer le prénom de client : ");
                        String firstName = scanner.nextLine();
                        System.out.println("Entrer le nom de client : ");
                        String lastName = scanner.nextLine();
                        System.out.println("Entrer la date de naissance (yyyy/MM/dd) : ");
                        String birthDateInput = scanner.nextLine();
                        DateTimeFormatter birthDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                        LocalDate birthDate = LocalDate.parse(birthDateInput, birthDateFormatter);
                        System.out.println("Entrer le numéro de téléphone : ");
                        String phoneNumber = scanner.nextLine();
                        System.out.println("Entrer l'adresse : ");
                        String address = scanner.nextLine();
                        Random random = new Random();
                        String code = "TT" + random.nextInt(1000) + 1;

                        Person client = new Client(code, firstName, lastName, birthDate, phoneNumber, address);
                        Optional<Person> clientAdded = _iClientService.save(client);
                        clientAdded.ifPresentOrElse(person -> System.out.println("Client ajouté !!\n\n" + person.toString()), () -> System.out.println("Client non ajouté!!!!!!!"));

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
                        System.out.println("Tapez n'importe quel attribut pour rechercher le client voulu : ");
                        String attribute = scanner.nextLine();
                        Optional<Person> client = _iClientService.find(attribute);
                        if (client.isPresent()) {
                            System.out.println(client.get().toString());
                        } else {
                            System.out.println("Client non trouvé");
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
                        System.out.println("Tapez n'importe le code pour supprimer le client voulu : ");
                        String code = scanner.nextLine();
                        if (_iClientService.delete(code)) { System.out.println("Employé supprimé !"); }
                        else { System.out.println("Client non supprimé !!!!"); }
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
                        System.out.println("Tapez le code de client voulu : ");
                        String attribute = scanner.nextLine();
                        Optional<Person> client = _iClientService.find(attribute);
                        if (client.isPresent()) {
                            Client e = (Client) client.get();
                            System.out.println("Entrer le prénom de client : ");
                            e.set_firstName(scanner.nextLine());
                            System.out.println("Entrer le nom de client : ");
                            e.set_lastName(scanner.nextLine());
                            System.out.println("Entrer la date de naissance (yyyy/MM/dd) : ");
                            String birthDateInput = scanner.nextLine();
                            DateTimeFormatter birthDateFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                            LocalDate birthDate = LocalDate.parse(birthDateInput, birthDateFormatter);
                            e.set_birthDate(birthDate);
                            System.out.println("Entrer le numéro de téléphone : ");
                            e.set_phoneNumber(scanner.nextLine());
                            System.out.println("Entrer l'adresse : ");
                            e.set_address(scanner.nextLine());

                            Optional<Person> newClient = _iClientService.update(e);
                            newClient.ifPresentOrElse(person -> System.out.println("Client modifié !!\n\n" + person.toString()), () -> System.out.println("Client non modifié!!!!!!!"));
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
                        List<Person> clients = _iClientService.findAll();
                        clients.forEach(System.out::println);
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
        }while (choice != 6);
    }
}
