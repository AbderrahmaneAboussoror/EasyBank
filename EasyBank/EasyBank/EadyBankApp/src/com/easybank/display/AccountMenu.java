package com.easybank.display;

import com.easybank.Person.Person;
import com.easybank.Person.client.Client;
import com.easybank.Person.client.IClientService;
import com.easybank.Person.employee.Employee;
import com.easybank.Person.employee.IEmployeeService;
import com.easybank.account.Account;
import com.easybank.account.AccountStatus;
import com.easybank.account.IAccountDAO;
import com.easybank.account.currentaccount.CurrentAccount;
import com.easybank.account.currentaccount.ICurrentAccountService;
import com.easybank.account.savingaccount.ISavingAccountService;
import com.easybank.account.savingaccount.SavingAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccountMenu {
    private final ICurrentAccountService _iCurrentAccountService;
    private final ISavingAccountService _iSavingAccountService;
    private final IEmployeeService _iEmployeeService;
    private final IClientService _iClientService;
    public AccountMenu(ICurrentAccountService iCurrentAccountService, ISavingAccountService iSavingAccountService, IClientService iClientService, IEmployeeService iEmployeeService) {
        this._iCurrentAccountService = iCurrentAccountService;
        this._iSavingAccountService = iSavingAccountService;
        this._iClientService = iClientService;
        this._iEmployeeService = iEmployeeService;
    }
    public void show() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("==== Menu ====");
            System.out.println("1. Ajouter un compte");
            System.out.println("2. Rechercher un compte");
            System.out.println("3. Supprimer un compte");
            System.out.println("4. Modifier un compte");
            System.out.println("5. Afficher les comptes");
            System.out.println("6. Retour");

            try {
                System.out.print("Entrez votre choix: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
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
                        System.out.println("Tapez c pour ajouter un compte courant ou s pour ajouter un compte epargne : ");
                        String accType = scanner.nextLine();
                        switch (accType) {
                            case "c" -> {
                                System.out.println("Entrer le nombre du compte : ");
                                String number = scanner.nextLine();
                                System.out.println("Entrer le solde : ");
                                Double balance = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println("Entrer la date de création (yyyy/MM/dd) : ");
                                String createdAtInput = scanner.nextLine();
                                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                                LocalDate createdAt = LocalDate.parse(createdAtInput, dateTimeFormatter);
                                System.out.println("Entrer le statut du compte : ");
                                String status = scanner.nextLine();
                                System.out.println("entrer le decouvert : ");
                                Double overdraft = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println("entrer le code du client : ");
                                String clientCode = scanner.nextLine();
                                System.out.println("Entrer la matricule d'employé : ");
                                String employeeCode = scanner.nextLine();

                                Optional<Person> client = _iClientService.find(clientCode);
                                Optional<Person> employee = _iEmployeeService.find(employeeCode);

                                AccountStatus accountStatus = _iCurrentAccountService.map(status);

                                CurrentAccount currentAccount = new CurrentAccount(number, balance, createdAt, accountStatus, (Employee) employee.orElse(null), (Client) client.orElse(null), overdraft);
                                Optional<Account> currentAccountAdded = _iCurrentAccountService.save(currentAccount);
                                currentAccountAdded.ifPresentOrElse(currentAccount1 -> System.out.println("Compte courant ajouté! \n" + currentAccount1.toString()), () -> System.out.println(""));
                            }
                            case "s" -> {
                                System.out.println("Entrer le nombre du compte : ");
                                String number = scanner.nextLine();
                                System.out.println("Entrer le solde : ");
                                Double balance = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println("Entrer la date de création (yyyy/MM/dd) : ");
                                String createdAtInput = scanner.nextLine();
                                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                                LocalDate createdAt = LocalDate.parse(createdAtInput, dateTimeFormatter);
                                System.out.println("Entrer le statut du compte : ");
                                String status = scanner.nextLine();
                                System.out.println("entrer le taux : ");
                                Double interestRate = scanner.nextDouble();
                                scanner.nextLine();
                                System.out.println("entrer le code du client : ");
                                String clientCode = scanner.nextLine();
                                System.out.println("Entrer la matricule d'employé : ");
                                String employeeCode = scanner.nextLine();

                                Optional<Person> client = _iClientService.find(clientCode);
                                Optional<Person> employee = _iEmployeeService.find(employeeCode);

                                AccountStatus accountStatus = _iSavingAccountService.map(status);

                                SavingAccount savingAccount = new SavingAccount(number, balance, createdAt, accountStatus, (Employee) employee.orElse(null), (Client) client.orElse(null), interestRate);
                                Optional<Account> savingAccountAdded = _iSavingAccountService.save(savingAccount);
                                savingAccountAdded.ifPresentOrElse(savingAccount1 -> System.out.println("Compte epargne ajouté! \n" + savingAccount1.toString()), () -> System.out.println(""));
                            }
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
                case 2 -> {
                    scanner.nextLine();
                    System.out.println("\nVous avez choisi la recherche.\n\nTapez 1 si vous voulez continuer, ou 0 pour revenir au menu précédent: ");
                    String query = scanner.nextLine();
                    if (Objects.equals(query, "0")) { choice = 6; }
                    else {
                        System.out.println("Tapez c pour la recherche des comptes par client ou o pour la recherche par opération : ");
                        String decision = scanner.nextLine();
                        switch (decision) {
                            case "c" -> {
                                System.out.println("Tapez le code de client : ");
                                String clientCode = scanner.nextLine();
                                Optional<List<Account>> currentAccounts = _iCurrentAccountService.findByClient(clientCode);
                                Optional<List<Account>> savingAccounts = _iSavingAccountService.findByClient(clientCode);
                                Optional<List<Account>> accounts = Optional.of(Stream.concat(
                                        currentAccounts.orElse(Collections.emptyList()).stream(),
                                        savingAccounts.orElse(Collections.emptyList()).stream()
                                ).toList());
                                accounts.ifPresentOrElse(System.out::println, () -> System.out.println("Aucun compte trouvé!!"));
                            }
                            case "o" -> {
                                System.out.println("Tapez le numéro de l'opération : ");
                                int operationCode = scanner.nextInt();
                                scanner.nextLine();
                                Optional<Account> currentAccount = _iCurrentAccountService.findByOperation(operationCode);
                                currentAccount.ifPresentOrElse(System.out::println, () -> {
                                    Optional<Account> savingAccount = _iSavingAccountService.findByOperation(operationCode);
                                    savingAccount.ifPresentOrElse(System.out::println, () -> System.out.println("Aucun compte trouvé!!"));
                                });
                            }
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
                        System.out.println("Tapez le numéro du compte a supprimer : ");
                        String number = scanner.nextLine();
                        if (!_iCurrentAccountService.delete(number)) {
                            if (!_iSavingAccountService.delete(number)) System.out.println("Compte inexistant");
                            else System.out.println("Compte supprimé!");
                        }
                        else System.out.println("Compte supprimé");
                    }
                }
                case 4 -> {
                    scanner.nextLine();
                    System.out.println("\nVous avez choisi la modification.\n\nTapez 1 si vous voulez continuer, ou 0 pour revenir au menu précédent: ");
                    String query = scanner.nextLine();
                    if (Objects.equals(query, "0")) { choice = 6; }
                    else {
                        System.out.println("Tapez s pour la modification du statut ou c pour la modification du compte entier : ");
                        String decision = scanner.nextLine();
                        switch (decision) {
                            case "s" -> {
                                System.out.println("Tapez le numéro du compte : ");
                                String number = scanner.nextLine();
                                Optional<Account> account = _iCurrentAccountService.findById(number);
                                account.ifPresentOrElse(account1 -> {
                                    System.out.println(account1 + "\n\nTapez le nouveau statut : ");
                                    String status = scanner.nextLine();
                                    AccountStatus accountStatus = _iCurrentAccountService.map(status);
                                    account1.set_status(accountStatus);
                                    Optional<Account> updatedAccount = _iCurrentAccountService.updateStatus(account1);
                                    updatedAccount.ifPresentOrElse(updatedAccount1 -> System.out.println("Statut modifié !\n" + updatedAccount1.toString()), () -> System.out.println("statut non modifié!!!"));
                                }, () -> {
                                    Optional<Account> account1 = _iSavingAccountService.findById(number);
                                    account1.ifPresentOrElse(account2 -> {
                                        System.out.println(account2 + "\n\nTapez le nouveau statut : ");
                                        String status = scanner.nextLine();
                                        AccountStatus accountStatus = _iSavingAccountService.map(status);
                                        account2.set_status(accountStatus);
                                        Optional<Account> updatedAccount = _iSavingAccountService.updateStatus(account2);
                                        updatedAccount.ifPresentOrElse(updatedAccount1 -> System.out.println("Statut modifié !\n" + updatedAccount1.toString()), () -> System.out.println("statut non modifié!!!"));
                                    }, () -> {
                                        System.out.println("Compte inexistant");
                                            });
                                });
                            }
                            case "c" -> {
                                System.out.println("Tapez le numéro du compte : ");
                                String number = scanner.nextLine();
                                Optional<Account> account = _iCurrentAccountService.findById(number);
                                account.ifPresentOrElse(account1 -> {
                                    System.out.println("Entrer le solde : ");
                                    account1.set_balance(scanner.nextDouble());
                                    scanner.nextLine();
                                    System.out.println("Entrer la date de création (yyyy/MM/dd) : ");
                                    String createdAtInput = scanner.nextLine();
                                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                                    account1.set_createdAt(LocalDate.parse(createdAtInput, dateTimeFormatter));
                                    System.out.println("Entrer le statut du compte : ");
                                    String status = scanner.nextLine();
                                    AccountStatus accountStatus = _iCurrentAccountService.map(status);
                                    account1.set_status(accountStatus);
                                    System.out.println("entrer le decouvert : ");
                                    ((CurrentAccount) account1).set_overdraft(scanner.nextDouble());
                                    scanner.nextLine();
                                    System.out.println("entrer le code du client : ");
                                    String clientCode = scanner.nextLine();
                                    System.out.println("Entrer la matricule d'employé : ");
                                    String employeeCode = scanner.nextLine();
                                    Optional<Person> client = _iClientService.find(clientCode);
                                    Optional<Person> employee = _iEmployeeService.find(employeeCode);
                                    account1.set_client((Client) client.orElse(null));
                                    account1.set_employee((Employee) employee.orElse(null));

                                    Optional<Account> updatedAccount = _iCurrentAccountService.update(account1);
                                    updatedAccount.ifPresentOrElse(updatedAccount1 -> System.out.println("Compte modifié !\n" + updatedAccount1.toString()), () -> System.out.println("Compte non modifié!!!"));
                                }, () -> {
                                    Optional<Account> account1 = _iSavingAccountService.findById(number);
                                    account1.ifPresentOrElse(account2 -> {
                                        System.out.println("Entrer le solde : ");
                                        account2.set_balance(scanner.nextDouble());
                                        scanner.nextLine();
                                        System.out.println("Entrer la date de création (yyyy/MM/dd) : ");
                                        String createdAtInput = scanner.nextLine();
                                        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                                        account2.set_createdAt(LocalDate.parse(createdAtInput, dateTimeFormatter));
                                        System.out.println("Entrer le statut du compte : ");
                                        String status = scanner.nextLine();
                                        AccountStatus accountStatus = _iCurrentAccountService.map(status);
                                        account2.set_status(accountStatus);
                                        System.out.println("entrer le decouvert : ");
                                        ((CurrentAccount) account2).set_overdraft(scanner.nextDouble());
                                        scanner.nextLine();
                                        System.out.println("entrer le code du client : ");
                                        String clientCode = scanner.nextLine();
                                        System.out.println("Entrer la matricule d'employé : ");
                                        String employeeCode = scanner.nextLine();
                                        Optional<Person> client = _iClientService.find(clientCode);
                                        Optional<Person> employee = _iEmployeeService.find(employeeCode);
                                        account2.set_client((Client) client.orElse(null));
                                        account2.set_employee((Employee) employee.orElse(null));

                                        Optional<Account> updatedAccount = _iSavingAccountService.update(account2);
                                        updatedAccount.ifPresentOrElse(updatedAccount1 -> System.out.println("Compte modifié !\n" + updatedAccount1.toString()), () -> System.out.println("Compte non modifié!!!"));
                                    }, () -> {
                                        System.out.println("Compte inexistant");
                                    });
                                });
                            }
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
                case 5 -> {
                    scanner.nextLine();
                    System.out.println("\nVous avez choisi l'affichage.\n\nTapez 1 si vous voulez continuer, ou 0 pour revenir au menu précédent: ");
                    String query = scanner.nextLine();
                    if (Objects.equals(query, "0")) { choice = 6; }
                    else {
                        System.out.println("1. Affichage normal\n2. Affichage par statut par date\n Tapez votre choix : ");
                        String decision = scanner.nextLine();
                        switch (decision) {
                            case "1" -> {
                                Optional<List<Account>> currentAccounts = _iCurrentAccountService.findAll();
                                Optional<List<Account>> savingAccounts = _iSavingAccountService.findAll();
                                Optional<List<Account>> accounts = Optional.of(
                                        Stream.concat(
                                            currentAccounts.orElse(Collections.emptyList()).stream(),
                                            savingAccounts.orElse(Collections.emptyList()).stream()
                                        ).toList()
                                );
                                accounts.ifPresentOrElse(System.out::println, () -> System.out.println("Aucun coumpte trouvé!!"));
                            }
                            case "2" -> {
                                System.out.println("Tapez la date de création ou le statut : ");
                                String input = scanner.nextLine();
                                Optional<List<Account>> currentAccounts = _iCurrentAccountService.filter(input);
                                Optional<List<Account>> savingAccounts = _iSavingAccountService.filter(input);
                                Optional<List<Account>> accounts = Optional.of(
                                        Stream.concat(
                                                currentAccounts.orElse(Collections.emptyList()).stream(),
                                                savingAccounts.orElse(Collections.emptyList()).stream()
                                        ).toList()
                                );
                                accounts.ifPresentOrElse(System.out::println, () -> System.out.println("Aucun compte trouvé"));
                            }
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
                case 6 -> {
                    return;
                }
            }
        }while (choice != 6);
    }
}
