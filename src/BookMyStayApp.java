
import java.util.Scanner;

public class BookMyStayApp {

    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 1.0");
        System.out.println("-----------------------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your name: ");
        String userName = scanner.nextLine();
        System.out.print("Enter number of nights you want to stay: ");
        int nights = scanner.nextInt();
        System.out.println("\nThank you, " + userName + "!");
        System.out.println("You have requested to book " + nights + " night(s).");
        System.out.println("Our team will process your booking shortly.");
        scanner.close();
        System.out.println("\nApplication terminated successfully.");
    }
}