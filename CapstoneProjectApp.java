import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
class CapstoneFlashcard {
    private String term;
    private String definition;
    public CapstoneFlashcard(String term, String definition) {
        this.term = term;
        this.definition = definition;
    }
    public String getTerm() {
        return term;
    }
    public String getDefinition() {
        return definition;
    }
    public String toString() {
        return "\nTerm: " + term + "\n\nDefinition: " + definition;
    }
}
public class CapstoneProjectApp {
    private List<CapstoneFlashcard> flashcards;
    private Scanner scanner;
    public CapstoneProjectApp() {
        flashcards = new ArrayList<>();
        scanner = new Scanner(System.in);
        loadFlashcardsFromFile();
    }
    public void addFlashcard() {
        System.out.println("\nEnter the term:\n");
        scanner.nextLine();
        String term = scanner.nextLine();
        System.out.println("\nTerm accepted.");
        System.out.println("\nEnter the definition:\n");
        String definition = scanner.nextLine();
        System.out.println("\nDefinition accepted.");
        flashcards.add(new CapstoneFlashcard(term, definition));
        System.out.println("\nFlashcard added!");
        saveFlashcardsToFile();
    }
    public void viewFlashcards() {
        System.out.println("\nHere are all the flashcards in your set:");
        if (flashcards.isEmpty()) {
            System.out.println("\nNo flashcards available.");
            return;
        }
        for (CapstoneFlashcard flashcard : flashcards) {
            System.out.println();
            System.out.println(flashcard);
        }
    }
    public void deleteFlashcards() {
        if (flashcards.isEmpty()) {
            System.out.println("\nNo flashcards available to delete.");
            return;
        }
        System.out.println("\nEnter the term of the flashcard you want to delete:\n");
        scanner.nextLine();
        String delTerm = scanner.nextLine();
        boolean removed = flashcards.removeIf(flashcard -> flashcard.getTerm().equalsIgnoreCase(delTerm));
        if (removed) {
            System.out.println("\nFlashcard deleted.");
            saveFlashcardsToFile();
        } else {
            System.out.println("\nFlashcard not found.");
        }
    }

    public void quiz() {
        if (flashcards.isEmpty()) {
            System.out.println("\nNo flashcards are currently available for quizzing.");
            return;
        }
        Collections.shuffle(flashcards);
        for (CapstoneFlashcard flashcard : flashcards) {
            System.out.println("\nWhat is the definition of: \n\n" + flashcard.getTerm());
            System.out.println("\nThe definition is:\n");
            String answer = scanner.nextLine();
            if (answer.equalsIgnoreCase(flashcard.getDefinition())) {
                System.out.println("\nCorrect!");
            } else {
                System.out.println("\nIncorrect. The correct definition is: \n\n" + flashcard.getDefinition());
            }
        }
    }
    
    public void saveFlashcardsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("flashcards.txt"))) {
            for (CapstoneFlashcard flashcard : flashcards) {
                writer.write(flashcard.getTerm() + ":" + flashcard.getDefinition());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("\nAn error occurred while saving flashcards.");
        }
    }
    public void loadFlashcardsFromFile() {
        try (Scanner fileScanner = new Scanner(new File("flashcards.txt"))) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(":");
                if (data.length == 2) {
                    flashcards.add(new CapstoneFlashcard(data[0], data[1]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("\nNo saved flashcards found. Starting fresh.");
        }
    }
    public void start() {
        while (true) {
            System.out.println("\n--- Capstone Project App Menu ---");
            System.out.println("\n1. Add Flashcard");
            System.out.println("\n2. View Flashcards");
            System.out.println("\n3. Delete Flashcard");
            System.out.println("\n4. Quiz Yourself");
            System.out.println("\n5. Exit");
            System.out.print("\nChoose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addFlashcard();
                    break;
                case 2:
                    viewFlashcards();
                    break;
                case 3:
                    deleteFlashcards();
                    break;
                case 4:
                    quiz();
                    break;
                case 5:
                    System.out.println("\nExiting Now");
                    return;
                default:
                    System.out.println("\nInvalid choice. Try again.");
            }
        }
    }
    public static void main(String[] args) {
        CapstoneProjectApp app = new CapstoneProjectApp();
        app.start();
    }
}
