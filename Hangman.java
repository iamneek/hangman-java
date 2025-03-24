import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;

public class Hangman {
    static String[] hangmanFigures = {
        """


        """,
        """
             O

        """,
        """
             O
            -

        """,
        """
             O
            --

        """,
        """
             O
            ---

        """,
        """
             O
            ---
             |

        """,
        """
             O
            ---
             |
            /

        """,
        """
             O
            ---
             |
            /\\

        """
    };

    static boolean gameOver = false;
    static int life = 7;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String word = getWord();
        System.out.println("Word: " + word);
        int wordLength = word.length();
        System.out.println("Welcome to Hangman, your first word is "+ wordLength + " character long.\n");
        printHangman(wordLength);
        while (life > 0 && !gameOver) {
            System.out.println("\nEnter a character ( ♥ x" + life + " ): ");
            String userInputRaw = scanner.nextLine();
            if(userInputRaw.length() > 1) {
                System.out.println("\nInvalid input! Please enter a single character.\n");
                continue;
            }
            char userInput = userInputRaw.charAt(0);
            CheckWord(userInput, word);
        }
        scanner.close();
    }

    public static void printHangman(int wordLength) {
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
        for (int i = 0; i < wordLength; i++) {
            System.out.print("_ ");
        }
    }

    public static String getWord() {
        String[] words = {"Formula", "Java", "World", "Alphabet", "Instructor", "Foreigner", "Worker",
    "Sophisticated", "Honor", "Information", "Technology", "Python", "Adequate", "Appropriate",
    "Human", "Sapeins", "Bachelor", "Contradiction", "Symphony", "Crazy", "Mother", "Brother", "Son", "Mamina"};
        String word = words[ThreadLocalRandom.current().nextInt(0, words.length)];
        return word;
    }

    public static void CheckWord(char userInput, String word) {
        String[] wordSplit = word.split("");
        ArrayList<Integer> matchIndexList = new ArrayList<>();

        for (int i = 0; i < wordSplit.length; i++) {
            if (wordSplit[i].charAt(0) == userInput && !(matchIndexList.contains(i))) {
                System.out.println("Character " + wordSplit[i] +" matches.");
            }
        }

    }
}
