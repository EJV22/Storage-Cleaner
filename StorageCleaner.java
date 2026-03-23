import java.util.*;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;


public class StorageCleaner {

    //Main Method, this is where the Main Menu is going to be displayed!
    public static void main(String[] args){
        
        String filePath = "app_scores.txt";

        //Using Scanner to implement a UI
        Scanner scan = new Scanner(System.in);

        //Makes an array list for our files, this list is going to be called under the Files class/superclass
        Manager manager = new Manager();

        boolean running = true;

        // System keeps on looping the prompts until we are finished
        while (running){

            String menu =     """
            \n\n__________________________________Storage Cleaner!__________________________________


                                  Welcome to your personal storage cleaner!
                    This app will help you gauge file deletion using a scoring system!
                Take the system with a grain of salt! You may not want to delete some files!
            
                                         __________________________
                                        |                          |
                                        |   Select a Menu Item:    |
                                        |                          |
                                        |   1. Add File            |
                                        |   2. Show File Scores    |
                                        |   3. Clear Scoreboard    |
                                        |   4. Save Files          |
                                        |   5. Load Files          |
                                        |   6. Exit                |
                                        |__________________________|
            """;

            System.out.println(menu);

            int choice;

            try {choice = Integer.parseInt(scan.nextLine());}
            catch (Exception e) {System.out.println("Give a valid value!"); continue;}

            switch (choice) {
                case 1 -> addFiles(scan, manager);
                case 2 -> {displayScores(manager);}
                case 3 -> {manager.clearFiles(); System.out.println("\n__________Scoreboard cleared!___________");}
                case 4 -> manager.saveFiles(filePath);
                case 5 -> manager.loadFiles(filePath);
                case 6 -> running = exit(scan, manager, filePath);
                default -> System.out.println("Please choose a valid option (1-6).");
        }
    }
        // Deactivates the scanner, no more inputs needed
        scan.close();
    }    


    public static void displayScores(Manager fileManager) {

        if (fileManager.isEmpty()) {
            System.out.println("\nNo files available!");
            return;}

        ArrayList<storageFile> sorted = fileManager.getSortedFiles();

        topDeletion(fileManager);

        System.out.println("\n============================ FILE SCORES ============================\n");
        System.out.printf("%-3s | %-30s | %-10s | %-5s | %-7s\n", "No.", "Name", "Type", "Score", "Backup");
        System.out.println("---------------------------------------------------------------------");

        int num = 1;
        for (storageFile f : sorted) {

            int score = f.deletionScore();
            String type = (f.getFileType().toString());
            String backup = f.getIsBackup() ? "Yes" : "No";

            String recommendation = "";
            if (score >= 80) {
                recommendation = " <-- Recommended to delete!";
            }

            System.out.printf("%-3d | %-30s | %-10s | %-5d | %-7s%s\n",
                num, f.getName(), type, score, backup, recommendation);
            num++;
        }
        System.out.println("\n======================================================================\n");
    }

    public static void topDeletion(Manager fileManager) {

        if (fileManager.isEmpty()) return;

        storageFile top = fileManager.getTopDeletion();

        if (top == null) return;

        int score = top.deletionScore();

        System.out.println("\n************************ TOP DELETION SCORE ************************\n");
        System.out.printf("File: %s   | Type: %s | Score: %d | Backup: %s\n",
                      top.getName(),
                      top.getFileType(),
                      score,
                      top.getIsBackup() ? "Yes" : "No");
        System.out.println("\n********************************************************************\n");
}

    public static void addFiles(Scanner scan, Manager fileManager){

        //What's the name!
        System.out.print("___________________________________\n");
        System.out.print("\nEnter file name: ");
        String name = scan.nextLine();

        //Input the storage
        int storage;

        while (true){
        System.out.print("\nEnter storage (MB): ");

            try {
                storage = Integer.parseInt(scan.nextLine());
                break;
                }
            catch (Exception e){
                System.out.println("\nIntegers only!\n");
                }
        }

        //Is there a backup?
        boolean isBackup;

        while (true){
        System.out.print("\nDoes this file have a backup? (y/n): ");
        String input = scan.nextLine();

            if(input.equals("y")){
                isBackup = true;
                break;}
            else if (input.equals("n")){
                isBackup = false;
                break;}
            else{
            System.out.println("Please enter true or false!");
        }
        }  

        // Is this file an App or a Media?
        storageFile file = null;
        int type;

        System.out.println("\n_________File Type_________");

        storageFile.Type fileType;
        appFile.AppType appType;
        mediaFile.MediaType mediaType;

        // More exception catching for errors!
        while(true) {
            System.out.println ("\n1. App");
            System.out.println ("2. Media\n");

            try{
                type = Integer.parseInt(scan.nextLine());
                if (type == 1 || type == 2) break;
                else {System.out.println("1 or 2 please!:   ");}
                }
            catch (Exception e) {System.out.println("Integer please!");}
        }

            //You chose App!
            if (type == 1){

                fileType = storageFile.Type.app;

            System.out.println("\n_________App Type:________");

            // Is this an entertainment app or a productivity app?
            int app;
            
            while (true){
                System.out.println("\n1. Gaming/Social");
                System.out.println("2. Productivity\n");

                try {
                    app = Integer.parseInt(scan.nextLine());
                    if (app == 1 || app == 2) break;
                    else {System.out.println("1 or 2 please!:   ");}
                }
                catch (Exception e) {System.out.println("Integer please!");}
            }
            
            if (app == 1){
                appType = appFile.AppType.GAMING_OR_SOCIAL;
            }
            else{
                appType = appFile.AppType.PRODUCTIVITY;
            }

            int usage;

            while (true){
                System.out.println ("\nUsage/Opens per day (rough estimate): ");
                try {
                    usage = Integer.parseInt(scan.nextLine());
                    break;
                }
                catch (Exception e) {System.out.println("Integer please!");}
                }

            file = new appFile(name, fileType, storage, isBackup, usage, appType);
        }
        else if (type == 2){

            fileType = storageFile.Type.media;

            System.out.println ("\n________Media Type:________");

            // The file type is Media!
            int media;

            // More catching
            while (true){

                System.out.println ("\n1. mp4 (video)");
                System.out.println ("2. png (picture)\n");

                try {
                    media = Integer.parseInt(scan.nextLine());
                    if (media == 1 || media == 2) break;
                    else {System.out.println("1 or 2 please!:   ");}
                }
                catch (Exception e) {System.out.println("Integer please!");}
            }

            int mediaLength = 0;
            
            // the mediaType in the Media Class is a video/mp4
            if (media == 1){
                mediaType = mediaFile.MediaType.mp4;

                while (true){
                    System.out.println("How long is this vid (secs)?  ");
                try{
                    mediaLength = Integer.parseInt(scan.nextLine());
                    break;}
                catch (Exception e) {System.out.println("Integer please!");}
                }

            }
            else{
                mediaType = mediaFile.MediaType.png;}
        
            int opens;

            while (true){
                    
                try{
                    System.out.println("\nHow many times have you opened this media file this week?");
                    opens = Integer.parseInt(scan.nextLine());
                    break;
                }
                catch(Exception e) {System.out.println("Integer please!\n");}
            }
            
            file = new mediaFile(name, fileType, storage, isBackup, mediaType, opens, mediaLength);
            }

            if (!(fileManager.addFile(file))) {
                System.out.println("Duplicate name + file type found, indicating backup...!"); 
                }   
            else{
                String out = """
                         __________________________
                        |                          |
                        | File added successfully! |
                        |__________________________|
                """;
                System.out.println(out);
                }
        }

    public static boolean exit(Scanner scan, Manager fileManager, String filePath){
            //Exit!

            System.out.println ("Save files before leaving? (y/n)");
            String inp = scan.nextLine();

            if (inp.equalsIgnoreCase("y")){
                fileManager.saveFiles(filePath);
            }

            String bye = """
                                __________
                               |          |
                               |   Bye!   |
                               |__________|
                        """;
            System.out.println(bye);

            //Gets you out of the while loop!
            return false;
    }

}


