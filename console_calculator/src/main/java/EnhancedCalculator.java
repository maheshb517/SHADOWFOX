import java.util.Scanner;

public class EnhancedCalculator {

    private static final double USD_TO_INR = 82.5; // Example conversion rate
    private static final double INR_TO_USD = 1 / USD_TO_INR;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Enhanced Console-Based Calculator ---");
            System.out.println("1. Basic Arithmetic");
            System.out.println("2. Scientific Calculations");
            System.out.println("3. Unit Conversions");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    basicArithmetic(scanner);
                    break;
                case 2:
                    scientificCalculations(scanner);
                    break;
                case 3:
                    unitConversions(scanner);
                    break;
                case 4:
                    System.out.println(" Exiting calculator. Thank you!");
                    break;
                default:
                    System.out.println(" Invalid choice! Please select a valid option.");
            }
        } while (choice != 4);

        scanner.close();
    }

    // 1. Basic Arithmetic Operations
    private static void basicArithmetic(Scanner scanner) {
        System.out.print("\nEnter first number: ");
        double num1 = scanner.nextDouble();
        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();

        System.out.println("Choose operation: +, -, *, /");
        char operation = scanner.next().charAt(0);

        double result;

        switch (operation) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("Error: Division by zero is not allowed!");
                    return;
                }
                break;
            default:
                System.out.println("Invalid operation!");
                return;
        }

        System.out.println("Result: " + result);
    }

    // 2. Scientific Calculations (Square Root & Exponentiation)
    private static void scientificCalculations(Scanner scanner) {
        System.out.println("\nScientific Calculations:");
        System.out.println("1. Square Root");
        System.out.println("2. Exponentiation (Power)");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        double result;

        switch (choice) {
            case 1:
                System.out.print("Enter a number: ");
                double num = scanner.nextDouble();
                if (num < 0) {
                    System.out.println("Error: Square root of negative numbers is not supported!");
                    return;
                }
                result = Math.sqrt(num);
                System.out.println("Square Root: " + result);
                break;
            case 2:
                System.out.print("Enter base: ");
                double base = scanner.nextDouble();
                System.out.print("Enter exponent: ");
                double exponent = scanner.nextDouble();
                result = Math.pow(base, exponent);
                System.out.println("Result: " + result);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // 3. Unit Conversions (Temperature & Currency)
    private static void unitConversions(Scanner scanner) {
        System.out.println("\nUnit Conversions:");
        System.out.println("1. Temperature (Celsius <-> Fahrenheit)");
        System.out.println("2. Currency (USD <-> INR)");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                temperatureConversion(scanner);
                break;
            case 2:
                currencyConversion(scanner);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Temperature Conversion
    private static void temperatureConversion(Scanner scanner) {
        System.out.println("\nTemperature Conversion:");
        System.out.println("1. Celsius to Fahrenheit");
        System.out.println("2. Fahrenheit to Celsius");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        double result;

        switch (choice) {
            case 1:
                System.out.print("Enter temperature in Celsius: ");
                double celsius = scanner.nextDouble();
                result = (celsius * 9/5) + 32;
                System.out.println("Fahrenheit: " + result + "°F");
                break;
            case 2:
                System.out.print("Enter temperature in Fahrenheit: ");
                double fahrenheit = scanner.nextDouble();
                result = (fahrenheit - 32) * 5/9;
                System.out.println("Celsius: " + result + "°C");
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }

    // Currency Conversion
    private static void currencyConversion(Scanner scanner) {
        System.out.println("\nCurrency Conversion:");
        System.out.println("1. USD to INR");
        System.out.println("2. INR to USD");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        double result;

        switch (choice) {
            case 1:
                System.out.print("Enter amount in USD: ");
                double usd = scanner.nextDouble();
                result = usd * USD_TO_INR;
                System.out.println("INR: ₹" + result);
                break;
            case 2:
                System.out.print("Enter amount in INR: ");
                double inr = scanner.nextDouble();
                result = inr * INR_TO_USD;
                System.out.println("USD: $" + result);
                break;
            default:
                System.out.println("Invalid choice!");
        }
    }
}
