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
        System.out.println("Reservation ID: " + roomId);
        System.out.println("Guest: " + guestName);
        System.out.println("Room Type: " + roomType);
        System.out.println("Nights: " + nights);
    }
}

class AddOnService {
    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getCost() {
        return cost;
    }

    public void displayService() {
        System.out.println("Service: " + serviceName + " | Cost: $" + cost);
    }
}

class AddOnServiceManager {
    private Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    public void addServiceToReservation(String reservationId, AddOnService service) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);
    }

    public void displayServicesForReservation(String reservationId) {
        List<AddOnService> services = reservationServices.getOrDefault(reservationId, new ArrayList<>());
        if (services.isEmpty()) {
            System.out.println("No add-on services selected for Reservation ID: " + reservationId);
        } else {
            System.out.println("\n--- Add-On Services for Reservation ID: " + reservationId + " ---");
            double totalCost = 0;
            for (AddOnService service : services) {
                service.displayService();
                totalCost += service.getCost();
            }
            System.out.println("Total Additional Cost: $" + totalCost);
        }
    }
}

public class UseCase7AddOnServices {
    public static void main(String[] args) {
        System.out.println("Welcome to the Hotel Booking System!");
        System.out.println("Application: Book My Stay");
        System.out.println("Version: 7.1");
        System.out.println("-----------------------------------");

        Reservation reservation = new Reservation("Alice", "Suite Room", 3, "SuiteRoom-ABC123");
        reservation.displayReservation();

        AddOnServiceManager serviceManager = new AddOnServiceManager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nEnter number of add-on services to select: ");
        int serviceCount = scanner.nextInt();
        scanner.nextLine();

        for (int i = 1; i <= serviceCount; i++) {
            System.out.println("\n--- Add-On Service " + i + " ---");
            System.out.print("Enter service name (e.g., Breakfast, Spa, Airport Pickup): ");
            String serviceName = scanner.nextLine();

            System.out.print("Enter service cost: ");
            double cost = scanner.nextDouble();
            scanner.nextLine();

            AddOnService service = new AddOnService(serviceName, cost);
            serviceManager.addServiceToReservation(reservation.getReservationId(), service);
        }

        serviceManager.displayServicesForReservation(reservation.getReservationId());

        System.out.println("\nApplication terminated successfully.");
        scanner.close();
    }
}