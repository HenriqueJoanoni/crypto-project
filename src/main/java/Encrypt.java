import java.io.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
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

    public static String saltPassword(String password) {
        byte[] saltBytes = new byte[32];
        SecureRandom rd = new SecureRandom();
        rd.nextBytes(saltBytes);
        return Base64.getEncoder().encodeToString(saltBytes);
    }

    public static boolean checkPassword(String password) {
        String passwordPattern = "^[^-\\s]\\w{10}+$";
        return password.matches(passwordPattern);
    }

    public static void writeIntoFile(String fileName, ArrayList<String> content) throws IOException {
        try {
            BufferedWriter wr = new BufferedWriter(new FileWriter(fileName));
            for (String line : content) {
                wr.write(line);
                wr.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BigInteger diffieHellmanEncrypt(int g, int p, int a, int b)
    {
        BigInteger A = BigInteger.valueOf((long) (Math.pow(g, a) % p));     //A = g^a mod p
        BigInteger B = BigInteger.valueOf((long) (Math.pow(g, b) % p));     //B = g^b mod p

        BigInteger s = B.modPow(BigInteger.valueOf(a), BigInteger.valueOf(p));  //s = B^a mod p
        //Calculate the private keys for both parties to make sure they are the same - otherwise something has gone wrong
        BigInteger sTest = A.modPow(BigInteger.valueOf(b), BigInteger.valueOf(p));  //s = A^b mod p

        if (s.equals(sTest)) {  //Note: == doesn't work on BigInt - you need to use .equals
            return s;
        }
        else {
            System.out.println("Something went wrong while calculating Diffie-Hellman private key");
            return BigInteger.valueOf(0);
        }
    }

}
