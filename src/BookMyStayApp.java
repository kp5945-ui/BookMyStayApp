import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

class Reservation {
    private String guestName;
    private String roomType;
    private int nights;
    private String roomId;

    public Reservation(String guestName, String roomType, int nights, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
        this.roomId = roomId;
    }

    public String getReservationId() {
        return roomId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNights() {
        return nights;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + roomId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Nights: " + nights);
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
    }

    public void addRoomType(String roomType, int availability) {
        inventory.put(roomType, availability);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void allocateRoom(String roomType) throws InvalidBookingException {
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type: " + roomType);
        }
        int available = inventory.get(roomType);
        if (available <= 0) {
            throw new InvalidBookingException("No availability for " + roomType);
        }
        inventory.put(roomType, available - 1);
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
        System.out.println("Version: 9.1");
        System.out.println("-----------------------------------");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);
        inventory.addRoomType("Suite Room", 0);

        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single Room / Double Room / Suite Room): ");
            String roomType = scanner.nextLine();

            System.out.print("Enter number of nights: ");
            int nights = scanner.nextInt();
            scanner.nextLine();

            if (nights <= 0) {
                throw new InvalidBookingException("Number of nights must be greater than zero.");
            }

            inventory.allocateRoom(roomType);

            String roomId = roomType.replace(" ", "") + "-" + UUID.randomUUID().toString().substring(0, 6);
            Reservation reservation = new Reservation(guestName, roomType, nights, roomId);

            System.out.println("\n--- Reservation Confirmed ---");
            reservation.displayReservation();
        } catch (InvalidBookingException e) {
            System.out.println("\nBooking Failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\nUnexpected Error: " + e.getMessage());
        }

        inventory.displayInventory();
        System.out.println("\nApplication terminated safely.");
        scanner.close();
    }
}