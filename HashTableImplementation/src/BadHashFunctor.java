import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class BadHashFunctor implements  HashFunctor{
    @Override
    public int hash(String item) { //the first character of each an item
        int hash=0;
            hash = item.charAt(0);

        return Math.abs(hash);
    }
























    public static ArrayList<String> buildStringList(int size){
        ArrayList<String> list = new ArrayList<>();

        for(int i = 0; i < size; i++){
            UUID randomUUID = UUID.randomUUID();
            list.add( randomUUID.toString().replaceAll("_", ""));
        }
        return list;
    }
    public static List<String> listOfWords(File documentFile) {

        List<String> words = readFromFile(documentFile);

        return words;
    }

    private static  List<String> readFromFile(File file) {

        ArrayList<String> words = new ArrayList<String>();

        try (Scanner fileInput = new Scanner(file)) {
            /*
             * Java's Scanner class is a simple lexer for Strings and primitive types (see
             * the Java API, if you are unfamiliar).
             */

            /*
             * The scanner can be directed how to delimit (or divide) the input. By default,
             * it uses whitespace as the delimiter. The following statement specifies
             * anything other than alphabetic characters as a delimiter (so that punctuation
             * and such will be ignored). The string argument is a regular expression that
             * specifies "anything but an alphabetic character". You need not understand any
             * of this for the assignment.
             */
            fileInput.useDelimiter("\\s*[^a-zA-Z]\\s*");

            while (fileInput.hasNext()) {
                String s = fileInput.next();
                if (!s.equals("")) {
                    words.add(s.toLowerCase());
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File " + file + " cannot be found.");
        }

       // System.out.println("Document is " + words);

        return words;
    }

}
