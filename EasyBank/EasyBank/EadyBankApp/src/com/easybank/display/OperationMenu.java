package com.easybank.display;

import com.easybank.Person.Person;
import com.easybank.Person.employee.Employee;
import com.easybank.Person.employee.IEmployeeService;
import com.easybank.account.Account;
import com.easybank.account.currentaccount.CurrentAccount;
import com.easybank.account.currentaccount.ICurrentAccountService;
import com.easybank.account.savingaccount.ISavingAccountService;
import com.easybank.account.savingaccount.SavingAccount;
import com.easybank.operation.IOperationService;
import com.easybank.operation.Operation;
import com.easybank.operation.OperationDAO;
import com.easybank.operation.PaymentType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

public class OperationMenu {
    private final IOperationService _iOperationService;
    private final ICurrentAccountService _iCurrentAccountService;
    private final ISavingAccountService _iSavingAccountService;
    private final IEmployeeService _iEmployeeService;

    public OperationMenu(IOperationService iOperationService, ISavingAccountService iSavingAccountService,ICurrentAccountService iCurrentAccountService, IEmployeeService iEmployeeService) {
        this._iOperationService = iOperationService;
        this._iSavingAccountService = iSavingAccountService;
        this._iCurrentAccountService = iCurrentAccountService;
        this._iEmployeeService = iEmployeeService;
    }
    public void show() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("==== Menu ====");
            System.out.println("1. Ajouter une opération");
            System.out.println("2. Rechercher une opération");
            System.out.println("3. Supprimer une opération");
            System.out.println("4. Retour");

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
                    System.out.println("\nVous avez choisi l'ajout'.\n\nTapez 1 si vous voulez continuer, ou 0 pour revenir au menu précédent: ");
                    String query = scanner.nextLine();
                    if (Objects.equals(query, "0")) { show(); }
                    else {
                        System.out.println("Tapez le montant : ");
                        Double price = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.println("Tapez le type de paiement\n1.Withdrawal\n2.Deposit\n");
                        int decision = scanner.nextInt();
                        scanner.nextLine();
                        PaymentType paymentType = PaymentType.Withdrawal;
                        if (decision == 2) {
                            paymentType = PaymentType.Deposit;
                        }
                        System.out.println("Tapez la matricule de l'employé : ");
                        String code = scanner.nextLine();
                        Optional<Person> employee = _iEmployeeService.find(code);
                        System.out.println("Tapez le numéro du compte : ");
                        String number = scanner.nextLine();
                        Optional<Account> account = _iSavingAccountService.findById(number);
                        if (employee.isPresent()) {
                            if (account.isPresent()) {
                                if (account.get() instanceof SavingAccount) {
                                    Operation operation = new Operation(
                                            LocalDate.now(),
                                            price,
                                            paymentType,
                                            (Employee) employee.get(),
                                            null,
                                            (SavingAccount) account.get()
                                    );
                                    Optional<Operation> newOperation = _iOperationService.save(operation);
                                    newOperation.ifPresentOrElse(
                                            newOperation1 -> System.out.println("Opération ajoutée !\n" + newOperation1),
                                            () -> System.out.println("Operation non ajoutée!!!!!")
                                    );
                                }
                            }
                            else {
                                account = _iCurrentAccountService.findById(number);
                                if (account.isPresent()) {
                                    Operation operation = new Operation(
                                            LocalDate.now(),
                                            price,
                                            paymentType,
                                            (Employee) employee.get(),
                                            (CurrentAccount) account.get(),
                                            null
                                    );
                                    Optional<Operation> newOperation = _iOperationService.save(operation);
                                    newOperation.ifPresentOrElse(
                                            newOperation1 -> System.out.println("Opération ajoutée !\n" + newOperation1),
                                            () -> System.out.println("Operation non ajoutée!!!!!")
                                    );
                                } else {
                                    System.out.println("Aucun compte trouvé !!!");
                                }
                            }

                        } else {
                            System.out.println("Employé non trouvé");
                        }
                    }
                    scanner.nextLine();
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
                    if (Objects.equals(query, "0")) { show(); }
                    else {
                        System.out.println("Tapez le numéro d'opération : ");
                        int number = scanner.nextInt();
                        Optional<Operation> operation = _iOperationService.findById(number);
                        operation.ifPresentOrElse(System.out::println, () -> System.out.println("Opération non trouvé!!"));
                    }
                    scanner.nextLine();
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
                    if (Objects.equals(query, "0")) { show(); }
                    else {
                        System.out.println("Tapez le numéro d'opération : ");
                        int number = scanner.nextInt();
                        if (_iOperationService.delete(number)) { System.out.println("Operation supprimé!!"); }
                        else { System.out.println("Opération non supprimé!!!"); }
                    }
                    scanner.nextLine();
                    System.out.println("\nAppuyez sur n'importe quelle clé pour revenir");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        bufferedReader.readLine();
                        show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                case 4 -> {
                    return;
                }
            }

        }while (choice != 4);
    }
}
