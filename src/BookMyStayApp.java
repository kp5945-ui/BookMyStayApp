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

class BookingHistory {
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
}

class BookingReportService {
    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    public void displayAllReservations() {
        System.out.println("\n--- Booking History ---");
        List<Reservation> reservations = history.getAllReservations();
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
        } else {
            for (Reservation reservation : reservations) {
                reservation.displayReservation();
            }
        }
    }

    public void generateSummaryReport() {
        System.out.println("\n--- Booking Summary Report ---");
        Map<String, Integer> roomTypeCount = new HashMap<>();
        int totalNights = 0;

        for (Reservation reservation : history.getAllReservations()) {
            roomTypeCount.put(reservation.getRoomType(),
                    roomTypeCount.getOrDefault(reservation.getRoomType(), 0) + 1);
            totalNights += reservation.getNights();
        }

        for (String roomType : roomTypeCount.keySet()) {
            System.out.println(roomType + " bookings: " + roomTypeCount.get(roomType));
        }
        System.out.println("Total nights booked: " + totalNights);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 8.1");
        System.out.println("-----------------------------------");

        BookingHistory history = new BookingHistory();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of confirmed reservations to add: ");
        int count = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= count; i++) {
            System.out.println("\n--- Reservation " + i + " ---");
            System.out.print("Enter guest name: ");
            String guestName = scanner.nextLine();

            System.out.print("Enter room type (Single Room / Double Room / Suite Room): ");
            String roomType = scanner.nextLine();

            System.out.print("Enter number of nights: ");
            int nights = scanner.nextInt();
            scanner.nextLine();

            String roomId = roomType.replace(" ", "") + "-" + UUID.randomUUID().toString().substring(0, 6);
            Reservation reservation = new Reservation(guestName, roomType, nights, roomId);
            history.addReservation(reservation);
        }

        BookingReportService reportService = new BookingReportService(history);
        reportService.displayAllReservations();
        reportService.generateSummaryReport();

        System.out.println("\nApplication terminated successfully.");
        scanner.close();
    }
}