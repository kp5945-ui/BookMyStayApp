import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;
    private int nights;
    private String roomId;

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
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

    public void assignRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void displayConfirmation() {
        System.out.println("Reservation Confirmed!");
        System.out.println("Guest: " + guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Nights: " + nights);
        System.out.println("Assigned Room ID: " + roomId);
        System.out.println("-----------------------------------");
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;
    private HashMap<String, Set<String>> allocatedRooms;

    public RoomInventory() {
        inventory = new HashMap<>();
        allocatedRooms = new HashMap<>();
    }

    public void addRoomType(String roomType, int availability) {
        inventory.put(roomType, availability);
        allocatedRooms.put(roomType, new HashSet<>());
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public boolean allocateRoom(String roomType, Reservation reservation) {
        int available = getAvailability(roomType);
        if (available > 0) {
            String roomId = generateUniqueRoomId(roomType);
            allocatedRooms.get(roomType).add(roomId);
            reservation.assignRoomId(roomId);
            inventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    private String generateUniqueRoomId(String roomType) {
        return roomType.replace(" ", "") + "-" + UUID.randomUUID().toString().substring(0, 6);
    }

    public void displayInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (String roomType : inventory.keySet()) {
            System.out.println(roomType + " available: " + inventory.get(roomType));
        }
    }
}

class BookingService {
    private Queue<Reservation> bookingQueue;
    private RoomInventory inventory;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        bookingQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        bookingQueue.add(reservation);
    }

    public void processRequests() {
        System.out.println("\n--- Processing Booking Requests ---");
        while (!bookingQueue.isEmpty()) {
            Reservation reservation = bookingQueue.poll();
            boolean success = inventory.allocateRoom(reservation.getRoomType(), reservation);
            if (success) {
                reservation.displayConfirmation();
            } else {
                System.out.println("Reservation Failed for " + reservation.getGuestName() +
                        " | Room Type: " + reservation.getRoomType() +
                        " | Nights: " + reservation.getNights());
                System.out.println("Reason: No availability.");
                System.out.println("-----------------------------------");
            }
        }
        inventory.displayInventory();
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 6.1");
        System.out.println("-----------------------------------");

        RoomInventory inventory = new RoomInventory();
        inventory.addRoomType("Single Room", 2);
        inventory.addRoomType("Double Room", 2);
        inventory.addRoomType("Suite Room", 1);

        BookingService bookingService = new BookingService(inventory);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of booking requests: ");
        int requestCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= requestCount; i++) {
            System.out.println("\n--- Booking Request " + i + " ---");
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single Room / Double Room / Suite Room): ");
            String roomType = scanner.nextLine();

            System.out.print("Enter number of nights: ");
            int nights = scanner.nextInt();
            scanner.nextLine();

            Reservation reservation = new Reservation(guestName, roomType, nights);
            bookingService.addRequest(reservation);
        }

        bookingService.processRequests();

        System.out.println("\nApplication terminated successfully.");
        scanner.close();
    }
}