import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public interface AppMenu {
    default void startMenu() throws IOException {
        Scanner sc = new Scanner(System.in);
        Scanner val = new Scanner(System.in);
        Encrypt encrypt = new Encrypt();

        String textFile = "blabla-file.txt",
                encryptedText,
                decryptedText,
                password = "",
                encryptPassword,
                saltedPassword;

        boolean checkPassword = false,
                passwordCheck;

        long publicKeyG,publicKeyP ,userAPrivateKey, userBPrivateKey, userAResult, userBResult;

        ArrayList<String> content;
        ArrayList<String> encryptedContent = new ArrayList<>();

        System.out.println("\n" +
                "\n" +
                "d88888b d8b   db  .o88b. d8888b. db    db d8888b. d888888b d88888b d8888b.\n" +
                "88'     888o  88 d8P  Y8 88  `8D `8b  d8' 88  `8D `~~88~~' 88'     88  `8D\n" +
                "88ooooo 88V8o 88 8P      88oobY'  `8bd8'  88oodD'    88    88ooooo 88oobY'\n" +
                "88~~~~~ 88 V8o88 8b      88`8b      88    88~~~      88    88~~~~~ 88`8b  \n" +
                "88.     88  V888 Y8b  d8 88 `88.    88    88         88    88.     88 `88.\n" +
                "Y88888P VP   V8P  `Y88P' 88   YD    YP    88         YP    Y88888P 88   YD\n" +
                "\n");

        System.out.print("Select what kind of encryption you want: \n" +
                "1. Encrypt a text\n" +
                "2. Encrypt the content of a file\n" +
                "3. Decrypt the text\n" +
                "4. Decrypt the file content\n" +
                "5. Encrypt a password\n" +
                "6. Check hash and salt\n" +
                "7. Diffie-Hellman algorithm\n");

        switch (sc.nextInt()) {
            case 1:
                System.out.println("Please, insert a text to be encrypted: ");
                sc.nextLine();
                System.out.println("Encrypted text: " + encrypt.caesarCipher(sc.nextLine(), 3));
                break;
            case 2:
                sc.nextLine();
                content = encrypt.readFromFile(textFile);
                for (String line : content) {
                    System.out.println(encrypt.caesarCipher(line, 3));
                    encryptedContent.add(encrypt.caesarCipher(line, 3));
                }
                Encrypt.writeIntoFile("blabla-file-encrypted.txt", encryptedContent);
                break;
            case 3:
                sc.nextLine();
                decryptedText = encrypt.decipher(sc.nextLine(), 3);
                if (decryptedText.isEmpty()) {
                    System.out.println("Please, insert a text to be decrypted: ");
                    sc.nextLine();
                    encryptedText = encrypt.caesarCipher(sc.nextLine(), 3);
                    System.out.println("Decrypted text: " + encrypt.decipher(encryptedText, 3));
                } else {
                    System.out.println("Decrypted text: " + decryptedText);
                }
            case 4:
                // DECRYPT FROM FILE
                break;
            case 5:
                sc.nextLine();
                System.out.println("Please, enter a suitable password");
                while (!checkPassword) {
                    password = sc.nextLine();
                    passwordCheck = Encrypt.checkPassword(password);

                    if (passwordCheck) {
                        checkPassword = true;
                    } else {
                        System.out.println("That's not a suitable password :(, please try again!");
                    }
                }
                saltedPassword = Encrypt.saltPassword(password);
                encryptPassword = encrypt.caesarCipher(saltedPassword, 3);
                System.out.println("Encrypted password with salt: " + encryptPassword);
                break;
            case 6:
                // CHECK IF HASH + SALT MATCHES
                break;
            case 7:
                sc.nextLine();
                System.out.println("Please enter public key generator (g):");
                int g = val.nextInt();
                System.out.println("Please enter public key modulus (p):");
                int p = val.nextInt();
                System.out.println("Please enter private value (a):");
                int a = val.nextInt();
                System.out.println("Please enter private value (b):");
                int b = val.nextInt();
                BigInteger privateKey = Encrypt.diffieHellmanEncrypt(g, p, a, b);
                System.out.println("Diffie-Hellman private key: " + privateKey);
                break;
            default:
                System.out.println("Invalid Option, please try again.");
                startMenu();
                break;
        }
    }
}
