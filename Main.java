import java.util.*;

// ---------- Patrón Singleton ----------
class DatabaseConnection {
    private static DatabaseConnection instance;

    private DatabaseConnection() {
        System.out.println("[Singleton] Conexión creada.");
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        } else {
            System.out.println("[Singleton] Conexión reutilizada.");
        }
        return instance;
    }

    public void connect() {
        System.out.println("[BD] Conectado a la base de datos.");
    }
}

// ---------- Patrón Factory ----------
interface User {
    void access();
}

class Client implements User {
    public void access() {
        System.out.println("Cliente: puede comprar productos.");
    }
}

class Admin implements User {
    public void access() {
        System.out.println("Admin: puede gestionar el sistema.");
    }
}

class Operator implements User {
    public void access() {
        System.out.println("Operador: puede supervisar operaciones.");
    }
}

class UserFactory {
    public static User createUser(String type) {
        switch (type.toLowerCase()) {
            case "cliente":
                return new Client();
            case "admin":
                return new Admin();
            case "operador":
                return new Operator();
            default:
                throw new IllegalArgumentException("Tipo de usuario inválido");
        }
    }
}

// ---------- Patrón Abstract Factory ----------
interface Button {
    void render();
}

interface Menu {
    void render();
}

interface GUIFactory {
    Button createButton();
    Menu createMenu();
}

class WindowsButton implements Button {
    public void render() {
        System.out.println("[Windows] Botón renderizado.");
    }
}

class WindowsMenu implements Menu {
    public void render() {
        System.out.println("[Windows] Menú renderizado.");
    }
}

class WindowsFactory implements GUIFactory {
    public Button createButton() {
        return new WindowsButton();
    }

    public Menu createMenu() {
        return new WindowsMenu();
    }
}

// ---------- Patrón Builder ----------
class Order {
    String customer;
    List<String> items = new ArrayList<>();
    String shipping;
    String notes;

    public void show() {
        System.out.println("\n[Orden]");
        System.out.println("Cliente: " + customer);
        System.out.println("Ítems: " + items);
        System.out.println("Envío: " + shipping);
        System.out.println("Notas: " + notes);
    }
}

class OrderBuilder {
    private Order order = new Order();

    public OrderBuilder setCustomer(String name) {
        order.customer = name;
        return this;
    }

    public OrderBuilder addItem(String item) {
        order.items.add(item);
        return this;
    }

    public OrderBuilder setShipping(String method) {
        order.shipping = method;
        return this;
    }

    public OrderBuilder setNotes(String note) {
        order.notes = note;
        return this;
    }

    public Order build() {
        return order;
    }
}

// ---------- Patrón Prototype ----------
class LogMessage implements Cloneable {
    private String template;
    private String details;

    public LogMessage(String template) {
        this.template = template;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LogMessage clone() {
        try {
            return (LogMessage) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public void show() {
        System.out.println("[LOG] " + template + " - " + details);
    }
}

// ---------- Clase Principal ----------
public class Main {
    public static void main(String[] args) {
        // Singleton
        System.out.println("\n--- Singleton ---");
        DatabaseConnection db1 = DatabaseConnection.getInstance();
        db1.connect();
        DatabaseConnection db2 = DatabaseConnection.getInstance();
        db2.connect();

        // Factory
        System.out.println("\n--- Factory ---");
        User u1 = UserFactory.createUser("cliente");
        User u2 = UserFactory.createUser("admin");
        u1.access();
        u2.access();

        // Abstract Factory
        System.out.println("\n--- Abstract Factory (Windows GUI) ---");
        GUIFactory gui = new WindowsFactory();
        Button btn = gui.createButton();
        Menu menu = gui.createMenu();
        btn.render();
        menu.render();

        // Builder
        System.out.println("\n--- Builder ---");
        Order order = new OrderBuilder()
                .setCustomer("María")
                .addItem("Teclado")
                .addItem("Mouse")
                .setShipping("Envío estándar")
                .setNotes("Enviar en paquete ecológico")
                .build();
        order.show();

        // Prototype
        System.out.println("\n--- Prototype ---");
        LogMessage templateError = new LogMessage("ERROR");
        LogMessage log1 = templateError.clone();
        log1.setDetails("Archivo no encontrado");

        LogMessage log2 = templateError.clone();
        log2.setDetails("Permisos insuficientes");

        log1.show();
        log2.show();
    }
}
