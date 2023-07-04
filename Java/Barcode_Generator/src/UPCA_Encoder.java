import java.util.AbstractMap;
import java.util.Map;
import java.util.Scanner;

/**
 * The UPCA_Encoder class encodes 11 digit product codes
 * into 95 digit UPCA barcodes
 */
public class UPCA_Encoder {
    /**
     * a map that has chars 0-9 as keys, and UPC-A leftside barcode as values
     */
    private static final Map<Character, String> upcLeft = Map.ofEntries(
            new AbstractMap.SimpleEntry<>('0', "0001101"),
            new AbstractMap.SimpleEntry<>('1', "0011001"),
            new AbstractMap.SimpleEntry<>('2', "0010011"),
            new AbstractMap.SimpleEntry<>('3', "0111101"),
            new AbstractMap.SimpleEntry<>('4', "0100011"),
            new AbstractMap.SimpleEntry<>('5', "0110001"),
            new AbstractMap.SimpleEntry<>('6', "0101111"),
            new AbstractMap.SimpleEntry<>('7', "0111011"),
            new AbstractMap.SimpleEntry<>('8', "0110111"),
            new AbstractMap.SimpleEntry<>('9', "0001011")
            );

    /**
     * a map that has chars 0-9 as keys, and UPC-A rightside barcode as values
     */
    private static final Map<Character, String> upcRight = Map.ofEntries(
            new AbstractMap.SimpleEntry<>('0', "1110010"),
            new AbstractMap.SimpleEntry<>('1', "1100110"),
            new AbstractMap.SimpleEntry<>('2', "1101100"),
            new AbstractMap.SimpleEntry<>('3', "1000010"),
            new AbstractMap.SimpleEntry<>('4', "1011100"),
            new AbstractMap.SimpleEntry<>('5', "1001110"),
            new AbstractMap.SimpleEntry<>('6', "1010000"),
            new AbstractMap.SimpleEntry<>('7', "1000100"),
            new AbstractMap.SimpleEntry<>('8', "1001000"),
            new AbstractMap.SimpleEntry<>('9', "1110100")
    );

    /**
     * Computes the check digit of an 11 digit product code
     * @param productCode 11 digit universal product code
     * @return string value of check digit
     */
    private String getCheckDigit(String productCode) {
        int total = 0;
        for(Character c: productCode.toCharArray()){
            if(Integer.valueOf(c) % 2 == 0){
                total += Integer.valueOf(c);
            }
            else{
                total += 3*Integer.valueOf(c);
            }
        }
        int key = 10 - (total%10);
        if(key == 10){
            return "0";
        }
        return String.valueOf(10 - (total % 10));
    }

    /**
     * encodes the given product code into a barcode
     * @param productCode 11 digit universal product code
     * @return 95 digit UPC-A barcode
     */
    private String encode(String productCode) {
        productCode += getCheckDigit(productCode);
        StringBuilder code = new StringBuilder("101");
        for(char c: productCode.substring(0,6).toCharArray()) {
            code.append(upcLeft.get(c));
        }
        code.append("01010");
        for(char c: productCode.substring(6,12).toCharArray()) {
            code.append(upcRight.get(c));
        }
        code.append("101");
        return code.toString();
    }

    /**
     * Executable for UPCA_Encoder that allows a user to input
     * a product code and receive a barcode
     * @param args
     */
    public static void main(String[] args) {
        UPCA_Encoder encoder = new UPCA_Encoder();
        Scanner input = new Scanner(System.in);

        System.out.println("Enter 11 Digit Product Code");
        String code = input.next();
        while(code.length() != 11){
            System.out.println("Code must be 11 digits");
            code = input.next();
        }
        System.out.println("UPC-A Barcode: " + encoder.encode(code));
    }

}
