
import java.util.Scanner;

abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double pricePerNight;

    public Room(String roomType, int numberOfBeds, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Price per Night: $" + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 50.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 90.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 150.0);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 2.1");
        System.out.println("-----------------------------------");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        int singleRoomAvailability = 5;
        int doubleRoomAvailability = 3;
        int suiteRoomAvailability = 2;

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n--- Room Information ---");
        single.displayRoomDetails();
        System.out.println("Available: " + singleRoomAvailability);
        System.out.print("How many Single Rooms would you like to book? ");
        int singleBooking = scanner.nextInt();
        singleRoomAvailability -= singleBooking;

        System.out.println();
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleRoomAvailability);
        System.out.print("How many Double Rooms would you like to book? ");
        int doubleBooking = scanner.nextInt();
        doubleRoomAvailability -= doubleBooking;

        System.out.println();
        suite.displayRoomDetails();
        System.out.println("Available: " + suiteRoomAvailability);
        System.out.print("How many Suite Rooms would you like to book? ");
        int suiteBooking = scanner.nextInt();
        suiteRoomAvailability -= suiteBooking;

        System.out.println("\n--- Updated Availability ---");
        System.out.println("Single Rooms left: " + singleRoomAvailability);
        System.out.println("Double Rooms left: " + doubleRoomAvailability);
        System.out.println("Suite Rooms left: " + suiteRoomAvailability);

        System.out.println("\nApplication terminated successfully.");
        scanner.close();
    }
}