import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;
    private int nights;
    private String roomId;
    private boolean cancelled;

    public Reservation(String guestName, String roomType, int nights, String roomId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
        this.roomId = roomId;
        this.cancelled = false;
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

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        this.cancelled = true;
    }

    public void displayReservation() {
        System.out.println("Reservation ID: " + roomId +
                " | Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Nights: " + nights +
                " | Status: " + (cancelled ? "Cancelled" : "Confirmed"));
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

    public void allocateRoom(String roomType) throws Exception {
        if (!inventory.containsKey(roomType)) {
            throw new Exception("Invalid room type: " + roomType);
        }
        int available = inventory.get(roomType);
        if (available <= 0) {
            throw new Exception("No availability for " + roomType);
        }
        inventory.put(roomType, available - 1);
    }

    public void restoreRoom(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " available: " + inventory.get(roomType));
        }
    }
}

class CancellationService {
    private RoomInventory inventory;
    private Stack<String> rollbackStack;

    public CancellationService(RoomInventory inventory) {
        this.inventory = inventory;
        this.rollbackStack = new Stack<>();
    }

    public void cancelReservation(Reservation reservation) {
        if (reservation == null || reservation.isCancelled()) {
            System.out.println("Cancellation Failed: Reservation does not exist or is already cancelled.");
            return;
        }

        reservation.cancel();
        rollbackStack.push(reservation.getReservationId());
        inventory.restoreRoom(reservation.getRoomType());

        System.out.println("\nCancellation Successful!");
        System.out.println("Reservation ID " + reservation.getReservationId() + " has been cancelled.");
    }

    public void displayRollbackHistory() {
        System.out.println("\n--- Rollback History (Released Room IDs) ---");
        if (rollbackStack.isEmpty()) {
            System.out.println("No cancellations performed.");
        } else {
            for (String roomId : rollbackStack) {
                System.out.println("Released Room ID: " + roomId);
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 10.1");
        System.out.println("-----------------------------------");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 1);
        inventory.addRoomType("Double Room", 1);

        Reservation reservation1 = new Reservation("Alice", "Single Room", 2, "SingleRoom-ABC123");
        Reservation reservation2 = new Reservation("Bob", "Double Room", 1, "DoubleRoom-XYZ789");

        try {
            inventory.allocateRoom(reservation1.getRoomType());
            inventory.allocateRoom(reservation2.getRoomType());
        } catch (Exception e) {
            System.out.println("Error during allocation: " + e.getMessage());
        }

        System.out.println("\n--- Confirmed Reservations ---");
        reservation1.displayReservation();
        reservation2.displayReservation();

        CancellationService cancellationService = new CancellationService(inventory);

        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter Reservation ID to cancel: ");
        String cancelId = scanner.nextLine();

        if (cancelId.equals(reservation1.getReservationId())) {
            cancellationService.cancelReservation(reservation1);
        } else if (cancelId.equals(reservation2.getReservationId())) {
            cancellationService.cancelReservation(reservation2);
        } else {
            System.out.println("Cancellation Failed: Reservation ID not found.");
        }

        cancellationService.displayRollbackHistory();
        inventory.displayInventory();

        System.out.println("\nApplication terminated successfully.");
        scanner.close();
    }
}