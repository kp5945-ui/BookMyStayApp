import java.util.HashMap;
import java.util.Scanner;

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int availability) {
        inventory.put(roomType, availability);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void updateAvailability(String roomType, int booked) {
        if (inventory.containsKey(roomType)) {
            int current = inventory.get(roomType);
            if (booked <= current) {
                inventory.put(roomType, current - booked);
                System.out.println(booked + " " + roomType + "(s) booked successfully.");
            } else {
                System.out.println("Not enough " + roomType + " available.");
            }
        } else {
            System.out.println("Room type not found.");
        }
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " available: " + inventory.get(roomType));
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 3.1");
        System.out.println("-----------------------------------");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 5);
        inventory.addRoomType("Double Room", 3);
        inventory.addRoomType("Suite Room", 2);

        inventory.displayInventory();

        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter room type to book (Single Room / Double Room / Suite Room): ");
        String roomType = scanner.nextLine();

        System.out.print("Enter number of rooms to book: ");
        int booked = scanner.nextInt();

        inventory.updateAvailability(roomType, booked);
        inventory.displayInventory();

        System.out.println("\nApplication terminated successfully.");
        scanner.close();
    }
}