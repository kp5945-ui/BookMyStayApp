import java.util.HashMap;
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

    public String getRoomType() {
        return roomType;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
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

    public HashMap<String, Integer> getAllAvailability() {
        return new HashMap<>(inventory);
    }
}

class SearchService {
    private RoomInventory inventory;
    private Room[] rooms;

    public SearchService(RoomInventory inventory, Room[] rooms) {
        this.inventory = inventory;
        this.rooms = rooms;
    }

    public void searchAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room room : rooms) {
            int availability = inventory.getAvailability(room.getRoomType());
            if (availability > 0) {
                room.displayRoomDetails();
                System.out.println("Available: " + availability + "\n");
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 4.1");
        System.out.println("-----------------------------------");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType(single.getRoomType(), 5);
        inventory.addRoomType(doubleRoom.getRoomType(), 3);
        inventory.addRoomType(suite.getRoomType(), 0);

        Room[] rooms = { single, doubleRoom, suite };
        SearchService searchService = new SearchService(inventory, rooms);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter to search available rooms...");
        scanner.nextLine();

        searchService.searchAvailableRooms();

        System.out.println("Application terminated successfully.");
        scanner.close();
    }
}