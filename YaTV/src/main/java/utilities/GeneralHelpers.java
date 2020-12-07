package utilities;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.BreakIterator;
import java.util.Base64;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GeneralHelpers {
    public static final int MAXLINE = 250;

    public static final String FORMAT_COST = "[0-9]*?\\.[0-9]{2}";
    public static final String FORMAT_EMAIL = ".*?@.*?\\....";
    public static final String FORMAT_DATE = "[0-9]{4}-[0-1][0-9]-[0-3][0-9]";


    public static String createConnectionUrl() {
        Properties props = YaTVProperties.getConfigs();

        return "jdbc:mysql:" + props.getProperty("sqlServer") + "?" +
                "user=" + props.getProperty("sqlUser") + "&" +
                "password=" + props.getProperty("sqlPassword");
    }


    /**
     * Salts and hashes the given string
     * Using method from https://www.baeldung.com/java-password-hashing
     *
     * @param str The string to hash
     * @return The hashed string
     */
    public static String saltAndHash(String str, byte[] salt) {


        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(salt);

        byte[] hashedString = md.digest(str.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(hashedString);

    }

    public static byte[] getSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }


    /**
     * Breaks String into portions with the given delimiter based on length and prints
     *
     * @param str       The string to partition
     * @param delimiter The delimiter to append between the sections
     * @param maxLength The max length of the portions of the string
     */
    public static void cleanOutput(String str, String delimiter, int maxLength) {
        BreakIterator breakIterator = BreakIterator.getLineInstance();
        breakIterator.setText(str);

        int subStart = breakIterator.first();
        int subEnd = breakIterator.next();

        int lineLength = 0;

        while (subEnd != BreakIterator.DONE) {
            String section = str.substring(subStart, subEnd);
            lineLength = lineLength + (subEnd - subStart);
            if (lineLength >= maxLength) {
                System.out.print(delimiter);
                lineLength = subEnd - subStart;
            }
            System.out.print(section);
            subStart = subEnd;
            subEnd = breakIterator.next();
        }
    }

    public static String getStringInput(String output, String formatHuman, String format) {
        String inp;
        do {
            Scanner s = new Scanner(System.in);
            System.out.println(output + " (" + formatHuman + "):");
            inp = s.nextLine();
            if (!Pattern.matches(format, inp)) {
                System.out.println("The given input is not of the correct format");
            }

        } while (!Pattern.matches(format, inp));
        return inp;
    }

    public static String getStringInput(String output, String format) {
        String inp;
        do {
            Scanner s = new Scanner(System.in);
            System.out.println(output + ":");
            inp = s.nextLine();
            if (!Pattern.matches(format, inp)) {
                System.out.println("The given input is not of the correct format");
            }

        } while (!Pattern.matches(format, inp));
        return inp;
    }

    public static String getStringInput(String output) {
        String inp;
        Scanner s = new Scanner(System.in);
        System.out.println(output + ":");
        inp = s.nextLine();


        return inp;
    }

    public static int getIntInput(String output) {

        do {

            Scanner s = new Scanner(System.in);
            System.out.println(output + " (Whole Number):");
            try {
                return Integer.parseInt(s.nextLine());
            } catch (Exception e){
                System.out.println("Input was not a valid number");
            }

        } while (true);
    }

    public static Boolean getBoolInput(String output) {
        String inp;
        do {
            Scanner s = new Scanner(System.in);
            System.out.println(output + " (y/n):");
            inp = s.nextLine().toLowerCase();

        } while(!(inp.equals("y") || inp.equals("n")));
        return inp.equals("y");
    }
}
