import java.util.AbstractMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The Postnet_Reader can transform a binary string back into a 5 digit us zipcode
 *
 * @author elipaulsen
 */
public class Postnet_Reader {
    /**
     * A map that has chars 0-9 as values and their binary representations as keys
     */
    private static final Map<String,Character> binMap = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("11000",'0'),
            new AbstractMap.SimpleEntry<>("00011",'1'),
            new AbstractMap.SimpleEntry<>("00101",'2'),
            new AbstractMap.SimpleEntry<>("00110",'3'),
            new AbstractMap.SimpleEntry<>("01001",'4'),
            new AbstractMap.SimpleEntry<>("01010",'5'),
            new AbstractMap.SimpleEntry<>("01100",'6'),
            new AbstractMap.SimpleEntry<>("10001",'7'),
            new AbstractMap.SimpleEntry<>("10010",'8'),
            new AbstractMap.SimpleEntry<>("10100",'9')
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
     * finds the zipcode that is represented by the given binary string
     * @param bin binary string
     * @return zipcode
     */
    private String findZip(String bin) {
        int ptr = 1;
        StringBuilder zip = new StringBuilder();
        while(ptr+5 < bin.length()-5){
            zip.append(binMap.get(bin.substring(ptr,ptr+5)));
            ptr += 5;
        }
        return zip.toString();

    }

    /**
     * Executable for Postnet_Reader that allows a user
     * to input a binary string and receive the original zipcode
     * @param args
     */
    public static void main(String[] args) {
        Postnet_Reader rdr = new Postnet_Reader();
        Scanner input = new Scanner(System.in);

        System.out.println("Enter Binary Representation: ");
        String bin = input.next();

        String zip = rdr.findZip(bin);

        System.out.println("ZipCode: " + zip);
        System.out.println("Check Digit: " + rdr.getCheckDigit(Integer.parseInt(zip))+ " -- Valid Check Digit");

    }
}
