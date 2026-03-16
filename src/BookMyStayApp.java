import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Reservation {
    private String guestName;
    private String roomType;
    private int nights;

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

    public void displayReservation() {
        System.out.println("Guest: " + guestName +
                " | Room Type: " + roomType +
                " | Nights: " + nights);
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 5.1");
        System.out.println("-----------------------------------");

        Queue<Reservation> bookingQueue = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of booking requests to submit: ");
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
            bookingQueue.add(reservation);
            System.out.println("Request submitted successfully.");
        }

        System.out.println("\n--- Booking Requests in Queue (FIFO Order) ---");
        for (Reservation reservation : bookingQueue) {
            reservation.displayReservation();
        }

        System.out.println("\nApplication terminated successfully.");
        scanner.close();
    }
}