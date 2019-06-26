import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // 12345
        BigDecimal weight;
        BigDecimal distance = new BigDecimal("10000");
        BigDecimal pricePerKg = new BigDecimal("30");
        BigDecimal pricePerKm = new BigDecimal("50");

        // Added with inputMethod:
        weight=inputMethod(1);
        if(weight.equals(BigDecimal.ZERO))
            return;
        distance=inputMethod(2);
        if(distance.equals(BigDecimal.ZERO))
            return;

        // Legacy code next:
        System.out.println("Weight: " + weight);
        System.out.println("Distance: " + distance);

        BigDecimal price = weight.multiply(pricePerKg).add(distance.multiply(pricePerKm));
        System.out.println("result: " + price);
    }

    public static BigDecimal inputMethod(int type) {
        // 1 - distance; 2 - weight; 0 - exit (return)
        BigDecimal result;
        Scanner scanner = new Scanner(System.in);
        // Message:
        System.out.print("Enter the ");
        if(type==1)
            System.out.print("distance in km ");
        else if(type==2)
            System.out.print("weight in kg ");
        System.out.print("or press \"0\" to exit: ");

        String inputString = scanner.nextLine();

        if(inputString.matches("[a-zA-Z]+")||inputString.length() < 1) {
            System.out.println("Input error, only digits are allowed...");
            return BigDecimal.ZERO;
        }

        else if (!inputString.toLowerCase().equals("0")) {
            result = new BigDecimal(inputString);
        }
        else {
            System.out.println("\nBye!");
            result = new BigDecimal(BigInteger.ZERO);
        }
        return result;
    }
}
