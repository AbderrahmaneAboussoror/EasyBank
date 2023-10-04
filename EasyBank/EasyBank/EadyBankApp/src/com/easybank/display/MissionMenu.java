package com.easybank.display;

import com.easybank.mission.IMissionService;
import com.easybank.mission.Mission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MissionMenu {
    private final IMissionService _iMissionService;
    public MissionMenu(IMissionService iMissionService) {
        this._iMissionService = iMissionService;
    }
    public void show(){
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("==== Menu ====");
            System.out.println("1. Ajouter une mission");
            System.out.println("2. Afficher la liste des missions");
            System.out.println("3. Supprimer une mission");
            System.out.println("4. Retour");

            try {
                System.out.print("Entrez votre choix: ");
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                choice = 0;
            }

            switch (choice){
                case 1 -> {
                    scanner.nextLine();
                    System.out.println("\nVous avez choisi l'ajout.\n\nTapez 1 si vous voulez continuer, ou 0 pour revenir au menu précédent: ");
                    String query = scanner.nextLine();
                    if (Objects.equals(query, "0")) { show(); }
                    else {
                        System.out.println("Tapez le nom de la mission : ");
                        String name = scanner.nextLine();
                        System.out.println("Rédigez une description pour la mission : ");
                        String description = scanner.nextLine();
                        Random random = new Random();
                        String code = "M" + random.nextInt(1000) + 1;
                        Mission mission = new Mission(code, name, description);
                        Optional<Mission> missionInserted = _iMissionService.save(mission);
                        missionInserted.ifPresentOrElse(mission1 -> System.out.println("Mission ajoutée!\n" + mission1), () -> System.out.println("Mission non ajoutée!!!"));
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
                    System.out.println("\nVous avez choisi l'affichage.\n\nTapez 1 si vous voulez continuer, ou 0 pour revenir au menu précédent: ");
                    String query = scanner.nextLine();
                    if (Objects.equals(query, "0")) { show(); }
                    else {
                        Optional<List<Mission>> missions = _iMissionService.findAll();
                        missions.ifPresentOrElse(System.out::println, () -> System.out.println("Aucune mission n'est enregistrée!!"));
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
                        System.out.println("Tapez le code de la mission : ");
                        String code = scanner.nextLine();
                        if (_iMissionService.delete(code)) { System.out.println("Mission supprimée!"); }
                        else { System.out.println("Mission non supprimée!!!!!"); }
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
