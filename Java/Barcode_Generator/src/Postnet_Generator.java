import java.util.AbstractMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Postnet_Generator class can generate postnet barcodes, and binary representations of 5 digit US zipcodes
 *
 * @author elipaulsen
 */
public class Postnet_Generator {
    /**
     * A map that has chars 0-9 as keys and their barcode representations as values
     */
    private static final Map<Character,String> codeMap = Map.ofEntries(
            new AbstractMap.SimpleEntry<>('0', "||..."),
            new AbstractMap.SimpleEntry<>('1', "...||"),
            new AbstractMap.SimpleEntry<>('2', "..|.|"),
            new AbstractMap.SimpleEntry<>('3', "..||."),
            new AbstractMap.SimpleEntry<>('4', ".|..|"),
            new AbstractMap.SimpleEntry<>('5', ".|.|."),
            new AbstractMap.SimpleEntry<>('6', ".||.."),
            new AbstractMap.SimpleEntry<>('7', "|...|"),
            new AbstractMap.SimpleEntry<>('8', "|..|."),
            new AbstractMap.SimpleEntry<>('9', "|.|..")
    );

    /**
     * Computes the check digit for a given zipcode
     * @param zipCode a valid 5 digit US zipcode
     * @return the string value of the sum digit
     */
    private String getCheckDigit(int zipCode){
        int sum = 0;
        while(String.valueOf(zipCode).length() != 1){
            sum += zipCode % 10;
            zipCode /= 10;
        }
        sum += zipCode % 10;

        int key = 10 - (sum % 10);
        if(sum%10 == 0){
            key = 0;
        }
        return String.valueOf(key);
    }

    /**
     * generates the barcode of a given zipcode with '|' = high bars and '.' = low bars
     * @param zipCode a valid 5 digit US zipcode
     * @return barcode representation
     */
    private String generateBarcode(String zipCode){
        zipCode += getCheckDigit(Integer.parseInt(zipCode));
        StringBuilder barcode = new StringBuilder("|");
        for(char c: zipCode.toCharArray()){
            barcode.append(codeMap.get(c));
        }
        barcode.append("|");

        return barcode.toString();
    }

    /**
     * generates the binary string of a given postnet barcode
     * @param barcode postnet barcode
     * @return binary representation
     */
    private String generateBinary(String barcode) {
        StringBuilder bin = new StringBuilder();
        for(char c: barcode.toCharArray()){
            if(c == '|'){
                bin.append(1);
            }
            else{
                bin.append(0);
            }
        }
        return bin.toString();
    }

    /**
     * Executable for Postnet_Generator that allows a user to input a zipcode
     * and receive both a barcode, and a binary string for the zipcode
     * @param args
     */
    public static void main(String[] args) {
        Postnet_Generator gen = new Postnet_Generator();
        Scanner input = new Scanner(System.in);

        System.out.println("Enter Zip Code");
        String zip = input.next();
        while(zip.length() != 5){
            System.out.println("Please enter valid 5 digit zip code");
            zip = input.next();
        }
        String bcode = gen.generateBarcode(zip);

        System.out.println("Barcode: " + bcode);
        System.out.println("Binary:  " + gen.generateBinary(bcode));
    }
}
