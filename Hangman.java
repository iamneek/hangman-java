import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Hangman {
    static String[] hangmanFigures = {
            """


                    """,
            """
                    +---+
                    |   |
                        |
                        |
                        |
                        |
                    =========
                    """,
            """
                    +---+
                    |   |
                    O   |
                        |
                        |
                        |
                    =========
                    """,
            """
                    +---+
                    |   |
                    O   |
                    |   |
                        |
                        |
                    =========
                        """,
            """
                     +---+
                     |   |
                     O   |
                    /|   |
                         |
                         |
                     =========
                         """,
            """
                     +---+
                     |   |
                     O   |
                    /|\\  |
                         |
                         |
                     =========
                             """,
            """
                     +---+
                     |   |
                     O   |
                    /|\\  |
                    /    |
                         |
                     =========
                                             """,
            """
                     +---+
                     |   |
                     O   |
                    /|\\  |
                    / \\  |
                         |
                     =========
                                         """
    };

    static boolean gameOver = false;
    static int life = 7;
    static String[] returned = getWord();
    static String word = returned[0];
    static ArrayList<Integer> matchIndexList = new ArrayList<>();
    static String[] wordFill = new String[word.length()];
    static String hint = returned[1];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int wordLength = word.length();

        System.out.println("Welcome to Hangman, your first word is " + wordLength + " character long.\n");
        System.out.println("Hint: " + hint);
        for (int i = 0; i < word.length(); i++) {
            wordFill[i] = "_";
        }

        printHangman(new ArrayList<Integer>());

        while (life > 0 && !gameOver) {
            System.out.print("\nEnter a character ( ♥ x" + life + " ): ");
            String userInputRaw = scanner.nextLine();

            if (userInputRaw.length() > 1) {
                System.out.println("\nInvalid input! Please enter a single character.\n");
                continue;
            }

            char userInput = userInputRaw.charAt(0);
            CheckWord(userInput, word);
            String userWord = String.join("", wordFill);
            if (word.equals(userWord)) {
                System.out.println("\nYou Guessed it on the " + (7 - life + 1) + "th try!");
                gameOver = true;
            }
        }

        if (life == 0) {
            System.out.println("\nYou did not guess the correct word :(\nThe word was " + word);
        }

        scanner.close();
    }

    public static void printHangman(ArrayList<Integer> matchedIndex) {
        switch (life) {
            case 7:
                System.out.println(hangmanFigures[0]);
                break;
            case 6:
                System.out.println(hangmanFigures[1]);
                break;
            case 5:
                System.out.println(hangmanFigures[2]);
                break;
            case 4:
                System.out.println(hangmanFigures[3]);
                break;
            case 3:
                System.out.println(hangmanFigures[4]);
                break;
            case 2:
                System.out.println(hangmanFigures[5]);
                break;
            case 1:
                System.out.println(hangmanFigures[6]);
                break;
            case 0:
                System.out.println(hangmanFigures[7]);
                break;
            default:
                break;
        }

        for (Integer index : matchedIndex) {
            wordFill[index] = String.valueOf(word.charAt(index));
        }

        for (int i = 0; i < word.length(); i++) {
            System.out.print(wordFill[i] + " ");
        }
    }

    public static String[] getWord() {

        try {
            File wordFile = new File("WordList.csv");
            Scanner WordScanner = new Scanner(wordFile);
            ArrayList<String> wordList = new ArrayList<>();
            ArrayList<String> hintList = new ArrayList<>();
            while (WordScanner.hasNextLine()) {
                String[] splittedWordList = WordScanner.nextLine().split(",");
                wordList.add(splittedWordList[0]);
                hintList.add(splittedWordList[1]);
            }
            WordScanner.close();
            int randomIndex = ThreadLocalRandom.current().nextInt(0, wordList.size());
            String wordGen = wordList.get(randomIndex);
            String hintGen = hintList.get(randomIndex);
            String[] finalRes = new String[2];
            finalRes[0] = wordGen;
            finalRes[1] = hintGen;
            return finalRes;

        } catch (FileNotFoundException e) {
            System.out.println(e);
        }

        return new String[2];
    }

    public static void CheckWord(char userInput, String word) {
        String[] wordSplit = word.split("");
        boolean atLeastOne = false;

        for (int i = 0; i < wordSplit.length; i++) {
            if (wordSplit[i].charAt(0) == userInput
                    || wordSplit[i].charAt(0) == String.valueOf(userInput).toUpperCase().charAt(0)) {
                atLeastOne = true;
                System.out.println("Character " + wordSplit[i] + " matches.");
                if (!matchIndexList.contains(i)) {
                    matchIndexList.add(i);
                }
            }
        }

        printHangman(matchIndexList);

        if (!atLeastOne) {
            life--;
            System.out.println("");
            printHangman(matchIndexList);
        }
    }
}
