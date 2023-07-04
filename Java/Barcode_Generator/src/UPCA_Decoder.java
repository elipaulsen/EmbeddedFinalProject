import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * The UPCA_Decoder class decodes 95 digit UPC-A barcodes
 * into 11 digit product codes
 */
public class UPCA_Decoder {
    /**
     * a map that has chars 0-9 as values, and UPC-A leftside barcode as keys
     */
    private static final Map<String, Character> upcLeft = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("0001101", '0'),
            new AbstractMap.SimpleEntry<>("0011001", '1'),
            new AbstractMap.SimpleEntry<>("0010011", '2'),
            new AbstractMap.SimpleEntry<>("0111101", '3'),
            new AbstractMap.SimpleEntry<>("0100011", '4'),
            new AbstractMap.SimpleEntry<>("0110001", '5'),
            new AbstractMap.SimpleEntry<>("0101111", '6'),
            new AbstractMap.SimpleEntry<>("0111011", '7'),
            new AbstractMap.SimpleEntry<>("0110111", '8'),
            new AbstractMap.SimpleEntry<>("0001011", '9')
    );

    /**
     * a map that has chars 0-9 as values, and UPC-A rightside barcode as keys
     */
    private static final Map<String, Character> upcRight = Map.ofEntries(
            new AbstractMap.SimpleEntry<>("1110010", '0'),
            new AbstractMap.SimpleEntry<>("1100110", '1'),
            new AbstractMap.SimpleEntry<>("1101100", '2'),
            new AbstractMap.SimpleEntry<>("1000010", '3'),
            new AbstractMap.SimpleEntry<>("1011100", '4'),
            new AbstractMap.SimpleEntry<>("1001110", '5'),
            new AbstractMap.SimpleEntry<>("1010000", '6'),
            new AbstractMap.SimpleEntry<>("1000100", '7'),
            new AbstractMap.SimpleEntry<>("1001000", '8'),
            new AbstractMap.SimpleEntry<>("1110100", '9')
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
     * decodes the UPC-A barcode into original product code
     * @param upc 95 digit UPC-A barcode
     * @return 11 digit product code
     */
    private String decode(String upc) {
        String left = upc.substring(3,45);
        String right = upc.substring(50,85);

        int ptr = 0;
        StringBuilder productCode = new StringBuilder();
        while(ptr+7 <= left.length()){
            productCode.append(upcLeft.get(left.substring(ptr, ptr + 7)));
            ptr += 7;
        }
        ptr = 0;
        while(ptr+7 <= right.length()){
            productCode.append(upcRight.get(right.substring(ptr, ptr + 7)));
            ptr += 7;
        }
        return productCode.toString();
    }

    /**
     * Executable for UPCA_Decoder that allows a user to input
     * a barcode and receive a product code
     * @param args
     */
    public static void main(String[] args) {
        UPCA_Decoder decoder = new UPCA_Decoder();
        Scanner input = new Scanner(System.in);

        System.out.println("Enter 95 bit UPC-A Barcode");
        String upc = input.next();
        while(upc.length() != 95){
            System.out.println("UPC-A must be 95 bits");
            upc = input.next();
        }
        String prodCode = decoder.decode(upc);
        System.out.println("Product Code: " + prodCode);

        if(Objects.equals(String.valueOf(upcRight.get(upc.substring(85, 92))), decoder.getCheckDigit(prodCode))){
            System.out.println("Check Digit: " + decoder.getCheckDigit(prodCode) + " -- Valid Check Digit");
        }
        else{
            System.out.println("Check Digit: " + decoder.getCheckDigit(prodCode) + " -- Invalid Check Digit");
        }
    }

}
