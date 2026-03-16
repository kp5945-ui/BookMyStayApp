import java.io.*;
import java.util.*;

class Reservation implements Serializable {
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

class RoomInventory implements Serializable {
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

    public boolean allocateRoom(String roomType) {
        if (!inventory.containsKey(roomType)) return false;
        int available = inventory.get(roomType);
        if (available <= 0) return false;
        inventory.put(roomType, available - 1);
        return true;
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " available: " + inventory.get(roomType));
        }
    }
}

class BookingHistory implements Serializable {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        history.add(reservation);
    }

    public List<Reservation> getAllReservations() {
        return new ArrayList<>(history);
    }

    public void displayHistory() {
        System.out.println("\n--- Booking History ---");
        if (history.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : history) {
                reservation.displayReservation();
            }
        }
    }
}

class PersistenceService {
    public static void saveState(RoomInventory inventory, BookingHistory history, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(inventory);
            out.writeObject(history);
            System.out.println("\nSystem state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving state: " + e.getMessage());
        }
    }

    public static Object[] loadState(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            RoomInventory inventory = (RoomInventory) in.readObject();
            BookingHistory history = (BookingHistory) in.readObject();
            System.out.println("\nSystem state loaded successfully.");
            return new Object[]{inventory, history};
        } catch (Exception e) {
            System.out.println("No previous state found or error loading state. Starting fresh.");
            return new Object[]{new RoomInventory(), new BookingHistory()};
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 12.1");
        System.out.println("-----------------------------------");

        String filename = "system_state.dat";
        Object[] state = PersistenceService.loadState(filename);
        RoomInventory inventory = (RoomInventory) state[0];
        BookingHistory history = (BookingHistory) state[1];

        if (inventory.getAvailability("Single Room") == 0 &&
                inventory.getAvailability("Double Room") == 0 &&
                inventory.getAvailability("Suite Room") == 0) {
            inventory.addRoomType("Single Room", 2);
            inventory.addRoomType("Double Room", 1);
            inventory.addRoomType("Suite Room", 1);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();

        System.out.print("Enter room type (Single Room / Double Room / Suite Room): ");
        String roomType = scanner.nextLine();

        System.out.print("Enter number of nights: ");
        int nights = scanner.nextInt();
        scanner.nextLine();

        boolean success = inventory.allocateRoom(roomType);
        if (success) {
            String roomId = roomType.replace(" ", "") + "-" + UUID.randomUUID().toString().substring(0, 6);
            Reservation reservation = new Reservation(guestName, roomType, nights, roomId);
            history.addReservation(reservation);
            System.out.println("\n--- Reservation Confirmed ---");
            reservation.displayReservation();
        } else {
            System.out.println("\nBooking Failed: No availability for " + roomType);
        }

        inventory.displayInventory();
        history.displayHistory();

        PersistenceService.saveState(inventory, history, filename);

        System.out.println("\nApplication terminated safely with persistence.");
        scanner.close();
    }
}