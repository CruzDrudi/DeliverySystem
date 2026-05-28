# Delivery System 🍔🛵

An enterprise-level Java application simulating a restaurant delivery system. This project models the interactions between restaurants, clients, orders, chefs, and riders.

Recent updates have heavily refactored the core domain to adhere to **SOLID Principles** and implement **Classic Gang of Four (GoF) Design Patterns**, alongside the **MVC Architectural Pattern**.

---

## 🏗️ SOLID Principles Satisfied

### 1. Single Responsibility Principle (SRP)
* **The Problem:** The `Order` class was handling business logic (managing items, calculating totals) *and* display logic (printing receipts).
* **The Solution:** Extracted all printing logic into a dedicated **`ReceiptPrinter`** utility class. Now, `Order` only handles state, and `ReceiptPrinter` only formats and outputs to the console.

### 2. Open/Closed Principle (OCP)
* **The Problem:** Calculating estimated wait times relied on a hard-coded `switch` statement based on `OrderStatus`. Adding a new status required modifying the `Order` class.
* **The Solution:** Implemented the **Strategy Pattern**. The `Order` class is now *closed* for modification, but *open* for extension. We can add new wait-time rules by simply creating a new strategy class without touching existing code.

### 3. Liskov Substitution Principle (LSP)
* **The Implementation:** The application heavily utilizes clean polymorphism. Subclasses like `Food` and `Beverage` can perfectly substitute their base class `Product` inside an `OrderItem` without altering or breaking the correctness of the program. The same applies to the `PaymentOption` hierarchy (`Card` and `Cash`) and the `Vehicle` hierarchy (`Motorcycle` and `Car`).

### 4. Interface Segregation Principle (ISP)
* **The Implementation:** Instead of a massive `IOrder` interface forcing unused methods onto classes, responsibilities are cleanly segregated into small, focused functional interfaces like `Payable`, `Cancelable`, `Reviewable`, and `Trackable`.

### 5. Dependency Inversion Principle (DIP)
* **The Implementation:** High-level modules do not depend on low-level modules; both depend on abstractions. For example, the `Order` class does not depend on a concrete `Client` to send notifications; it depends on the `OrderStatusListener` interface. It does not depend on a concrete time calculator; it depends on the `EstimatedTimeStrategy` interface. This heavily decouples the domain logic from the implementation details.

---

## 🎨 Design Patterns Implemented

### 1. Builder Pattern
* **Location:** `OrderBuilder`
* **Purpose:** Solved the "Telescoping Constructor" anti-pattern. The `Order` class required a massive 10-parameter constructor. The Builder provides a fluent, readable API to construct orders step-by-step (e.g., `.setClient().addOrderItem().build()`).

### 2. Factory Pattern
* **Location:** `ProductFactory`
* **Purpose:** Decoupled object creation from the `Main` execution. Instead of calling `new Food()` or `new Beverage()` directly, the system passes a `ProductType` enum to the Factory, centralizing all product instantiation logic.

### 3. Strategy Pattern (with Registry)
* **Location:** `EstimatedTimeStrategy` (Interface), `DeliveredTimeStrategy`, `DefaultTimeStrategy`, `WaitTimeStrategyRegistry`
* **Purpose:** Replaced `switch` statements for calculating delivery times.
* **Architecture Note:** Built an advanced **Strategy Registry** using the "Supports" pattern. The registry auto-discovers the correct strategy for a given `OrderStatus`, completely decoupling the `Order` class from routing logic.

### 4. Observer Pattern (Listener)
* **Location:** `OrderStatusListener` (Interface), `Client` (Observer), `Order` (Subject)
* **Purpose:** Moved from a "polling" architecture (manually checking delivery status) to an event-driven "push" architecture. When an `Order` changes state (e.g., via `assignRider()`), it iterates through its registered listeners and automatically pushes notifications to the `Client`.

### 5. Decorator Pattern
* **Location:** `ProductDecorator`, `ExtraCheeseDecorator`, `BaconDecorator`
* **Purpose:** Solved the "Class Explosion" problem. Instead of creating concrete subclasses for every possible food combination (e.g., `BurgerWithCheeseAndBacon`), ingredients dynamically wrap around the base `Product` at runtime, aggregating prices and descriptions dynamically.

### 6. Proxy Pattern
* **Location:** `PaymentProcessor` (Interface), `RealPaymentProcessor`, `PaymentProxy`
* **Purpose:** Intercepts payments before they are executed. The `PaymentProxy` acts as a security bouncer, logging the transaction attempt and validating that the amount is greater than $0.00 before delegating to the `RealPaymentProcessor`.

### 7. Facade Pattern
* **Location:** `OrderFacade`
* **Purpose:** Hides the complex orchestration of calculating totals, applying discounts, validating, paying, preparing, and assigning riders. The client simply calls `processFullOrder()`, and the Facade safely handles the entire subsystem lifecycle.

### 8. Abstract Factory Pattern
* **Location:** `EmployeeFactory`, `MorningShiftFactory`, `NightShiftFactory`
* **Purpose:** Creates families of related objects (Chefs and Riders) without hardcoding their specific salary rates into the main execution. Changing from daytime standard pay to nighttime premium pay is as simple as switching the concrete factory instance.

### 9. MVC (Model-View-Controller) Pattern
* **Location:** `OrderController` (Controller), `Order` (Model), `ReceiptPrinter` (View)
* **Purpose:** Decoupled the data layer from the presentation layer. The `OrderController` intercepts the incoming request, mutates the `Order` state via the Facade, and immediately updates the console View via the Printer, completely removing orchestration logic from the `Main` thread.

---

## 🚀 How to Run
This project uses **Maven** for dependency management (Log4j2, Apache Commons).

1. Clone the repository.
2. Ensure you have JDK 21+ installed.
3. Build the project: `mvn clean install`
4. Run the main execution file: `com.solvd.delivery.Main`