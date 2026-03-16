import java.util.*;

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

    public synchronized boolean allocateRoom(String roomType) {
        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type: " + roomType);
            return false;
        }
        int available = inventory.get(roomType);
        if (available <= 0) {
            System.out.println("No availability for " + roomType);
            return false;
        }
        inventory.put(roomType, available - 1);
        return true;
    }

    public synchronized void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " available: " + inventory.get(roomType));
        }
    }
}

class BookingProcessor implements Runnable {
    private Reservation reservation;
    private RoomInventory inventory;

    public BookingProcessor(Reservation reservation, RoomInventory inventory) {
        this.reservation = reservation;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        synchronized (inventory) {
            boolean success = inventory.allocateRoom(reservation.getRoomType());
            if (success) {
                System.out.println("\nBooking Confirmed for " + reservation.getGuestName());
                reservation.displayReservation();
            } else {
                System.out.println("\nBooking Failed for " + reservation.getGuestName() +
                        " | Room Type: " + reservation.getRoomType());
            }
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 11.1");
        System.out.println("-----------------------------------");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 1);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("Alice", "Single Room", 2, "SingleRoom-ABC123"));
        reservations.add(new Reservation("Bob", "Single Room", 1, "SingleRoom-XYZ789"));
        reservations.add(new Reservation("Charlie", "Double Room", 1, "DoubleRoom-DEF456"));
        reservations.add(new Reservation("Diana", "Double Room", 2, "DoubleRoom-GHI789"));

        List<Thread> threads = new ArrayList<>();
        for (Reservation reservation : reservations) {
            Thread t = new Thread(new BookingProcessor(reservation, inventory));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted: " + e.getMessage());
            }
        }

        inventory.displayInventory();
        System.out.println("\nApplication terminated successfully.");
    }
}