import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Encrypt {
    public String caesarCipher(String input, int offset) {
        char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        char[] encrypted = new char[input.length()];
        offset = offset % ALPHABET.length;

        for (int i = 0; i < input.length(); i++) {
            char character = input.charAt(i);
            if (Character.isLetter(character)) {
                char base = Character.isLowerCase(character) ? 'a' : 'A';
                int index = ((character - base) + offset + ALPHABET.length) % ALPHABET.length;
                encrypted[i] = (char) (base + index);
            } else {
                encrypted[i] = character;
            }
        }
        return new String(encrypted);
    }

    public String decipher(String input, int offset) {
        return caesarCipher(input, 26 - (offset % 26));
    }

    public ArrayList<String> readFromFile(String fileName) throws FileNotFoundException {
        File inputFile = new File(fileName);
        Scanner sc = new Scanner(inputFile);
        ArrayList<String> content = new ArrayList<>();

        while (sc.hasNextLine()) {
            content.add(sc.nextLine());
        }

        return content;
    }

}
