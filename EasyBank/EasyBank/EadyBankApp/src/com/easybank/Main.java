package com.easybank;


import com.easybank.Person.IPersonDAO;
import com.easybank.Person.Person;
import com.easybank.Person.client.Client;
import com.easybank.Person.client.ClientDAO;
import com.easybank.Person.client.ClientService;
import com.easybank.Person.client.IClientService;
import com.easybank.Person.employee.EmployeeDAO;
import com.easybank.Person.employee.EmployeeService;
import com.easybank.Person.employee.IEmployeeService;
import com.easybank.account.IAccountDAO;
import com.easybank.account.currentaccount.CurrentAccountDAO;
import com.easybank.account.currentaccount.CurrentAccountService;
import com.easybank.account.currentaccount.ICurrentAccountService;
import com.easybank.account.savingaccount.ISavingAccountService;
import com.easybank.account.savingaccount.SavingAccountDAO;
import com.easybank.account.savingaccount.SavingAccountService;
import com.easybank.display.*;
import com.easybank.mission.IMissionDAO;
import com.easybank.mission.IMissionService;
import com.easybank.mission.MissionDAO;
import com.easybank.mission.MissionService;
import com.easybank.operation.IOperationDAO;
import com.easybank.operation.IOperationService;
import com.easybank.operation.OperationDAO;
import com.easybank.operation.OperationService;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("==== Menu ====");
            System.out.println("1. Gestion des employés");
            System.out.println("2. Gestion des clients");
            System.out.println("3. Gestion des comptes");
            System.out.println("4. Gestion des opérations");
            System.out.println("5. Gestion des missions");
            System.out.println("6. Exit");

            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
            }catch (InputMismatchException e) {
                scanner.nextLine();
                choice = 0;
            }

            // Process the user's choice
            switch (choice) {
                case 1 ->
                {
                    System.out.println("Vous avez choisi la gestion des employés.");
                    IPersonDAO employeeDAO = new EmployeeDAO();
                    IEmployeeService employeeService = new EmployeeService(employeeDAO);
                    EmployeeMenu employeeMenu = new EmployeeMenu(employeeService);
                    employeeMenu.show();
                }
                case 2 -> {
                    System.out.println("Vous avez choisi la gestion des clients.");
                    IPersonDAO clientDAO = new ClientDAO();
                    IClientService clientService = new ClientService(clientDAO);
                    ClientMenu clientMenu = new ClientMenu(clientService);
                    clientMenu.show();
                }
                case 3 ->{
                    System.out.println("Vous avez choisi la gestion des comptes.");
                    AccountMenu accountMenu = getAccountMenu();
                    accountMenu.show();
                }
                case 4 ->
                {
                    System.out.println("Vous avez choisi la gestion des opérations.");
                    OperationMenu operationMenu = getOperationMenu();
                    operationMenu.show();

                }
                case 5 -> {
                    System.out.println("Vous avez choisi la gestion des missions.");
                    IMissionDAO iMissionDAO = new MissionDAO();
                    IMissionService iMissionService = new MissionService(iMissionDAO);
                    MissionMenu missionMenu = new MissionMenu(iMissionService);
                    missionMenu.show();
                }
                case 6 ->
                        System.out.println("Exit du menu.");
                default ->
                        System.out.println("Choix invalid, veuillez saisir une option valide!");
            }

            System.out.println();
        } while (choice != 6);
    }

    private static AccountMenu getAccountMenu() {
        IPersonDAO clientDAO = new ClientDAO();
        IClientService clientService = new ClientService(clientDAO);
        IPersonDAO employeeDAO = new EmployeeDAO();
        IEmployeeService employeeService = new EmployeeService(employeeDAO);
        IAccountDAO iCurrentAccountDAO = new CurrentAccountDAO(clientDAO, employeeDAO);
        IAccountDAO iSavingAccountDAO = new SavingAccountDAO(clientDAO, employeeDAO);
        IOperationDAO iOperationDAO = new OperationDAO(employeeDAO, iCurrentAccountDAO, iSavingAccountDAO);
        ICurrentAccountService iCurrentAccountService = new CurrentAccountService(iCurrentAccountDAO, iOperationDAO);
        ISavingAccountService iSavingAccountService = new SavingAccountService(iSavingAccountDAO, iOperationDAO);
        return new AccountMenu(iCurrentAccountService, iSavingAccountService, clientService, employeeService);
    }
    private static OperationMenu getOperationMenu() {
        IPersonDAO clientDAO = new ClientDAO();
        IPersonDAO employeeDAO = new EmployeeDAO();
        IEmployeeService employeeService = new EmployeeService(employeeDAO);
        IAccountDAO iCurrentAccountDAO = new CurrentAccountDAO(clientDAO, employeeDAO);
        IAccountDAO iSavingAccountDAO = new SavingAccountDAO(clientDAO, employeeDAO);
        IOperationDAO iOperationDAO = new OperationDAO(employeeDAO, iCurrentAccountDAO, iSavingAccountDAO);
        IOperationService iOperationService = new OperationService(iOperationDAO);
        ICurrentAccountService iCurrentAccountService = new CurrentAccountService(iCurrentAccountDAO, iOperationDAO);
        ISavingAccountService iSavingAccountService = new SavingAccountService(iSavingAccountDAO, iOperationDAO);
        return new OperationMenu(iOperationService, iSavingAccountService, iCurrentAccountService, employeeService);
    }
}