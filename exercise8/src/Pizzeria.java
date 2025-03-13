import java.util.Scanner;

public class Pizzeria {
    enum PizzaSelection {
        PEPPERONI("Pepperoni", "Lots of pepperoni and extra cheese", 18),
        HAWAIIAN("Hawaiian", "Pineapple, ham, and extra cheese", 22),
        VEGGIE("Veggie", "Green pepper, onion, tomatoes, mushroom, and black olives", 25),
        BBQ_CHICKEN("BBQ Chicken", "Chicken in BBQ sauce, bacon, onion, green pepper, and cheddar cheese", 35),
        EXTRAVAGANZA("Extravaganza", "Pepperoni, ham, Italian sausage, beef, onions, green pepper, mushrooms, black olives, and extra cheese", 45);

        private final String pizzaName;
        private final String pizzaToppings;
        private final int price;

        PizzaSelection(String pizzaName, String pizzaToppings, int price) {
            this.pizzaName = pizzaName;
            this.pizzaToppings = pizzaToppings;
            this.price = price;
        }

        public String getPizzaName() { return pizzaName; }
        public String getPizzaToppings() { return pizzaToppings; }
        public int getPrice() { return price; }

        @Override
        public String toString() {
            return pizzaName + " Pizza with " + pizzaToppings + ", for €" + price;
        }
    }

    enum PizzaToppings {
        HAM("Ham", 2), PEPPERONI("Pepperoni", 2), BEEF("Beef", 2),
        CHICKEN("Chicken", 2), SAUSAGE("Sausage", 2), PINEAPPLE("Pineapple", 1),
        ONION("Onion", 0.5), TOMATOES("Tomatoes", 0.4), GREEN_PEPPER("Green Pepper", 0.5),
        BLACK_OLIVES("Black Olives", 0.5), SPINACH("Spinach", 0.5),
        CHEDDAR_CHEESE("Cheddar Cheese", 0.8), MOZZARELLA_CHEESE("Mozzarella Cheese", 0.8),
        FETA_CHEESE("Feta Cheese", 1), PARMESAN_CHEESE("Parmesan Cheese", 1);

        private final String topping;
        private final double toppingPrice;

        PizzaToppings(String topping, double toppingPrice) {
            this.topping = topping;
            this.toppingPrice = toppingPrice;
        }

        public String getTopping() { return topping; }
        public double getToppingPrice() { return toppingPrice; }

        @Override
        public String toString() {
            return topping + ": €" + toppingPrice;
        }
    }

    enum PizzaSize {
        LARGE("Large", 10), MEDIUM("Medium", 5), SMALL("Small", 0);

        private final String pizzaSize;
        private final int addToPizzaPrice;

        PizzaSize(String pizzaSize, int addToPizzaPrice) {
            this.pizzaSize = pizzaSize;
            this.addToPizzaPrice = addToPizzaPrice;
        }

        public String getPizzaSize() { return pizzaSize; }
        public int getAddToPizzaPrice() { return addToPizzaPrice; }

        @Override
        public String toString() {
            return pizzaSize + ": €" + addToPizzaPrice;
        }
    }

    enum SideDish {
        CALZONE("Calzone", 15), CHICKEN_PUFF("Chicken Puff", 20),
        MUFFIN("Muffin", 12), NOTHING("No side dish", 0);

        private final String sideDishName;
        private final int addToPizzaPrice;

        SideDish(String sideDishName, int addToPizzaPrice) {
            this.sideDishName = sideDishName;
            this.addToPizzaPrice = addToPizzaPrice;
        }

        public String getSideDishName() { return sideDishName; }
        public int getAddToPizzaPrice() { return addToPizzaPrice; }

        @Override
        public String toString() {
            return sideDishName + ": €" + addToPizzaPrice;
        }
    }

    enum Drinks {
        COCA_COLA("Coca Cola", 8), COCOA_DRINK("Cocoa Drink", 10),
        NOTHING("No drinks", 0);

        private final String drinkName;
        private final int addToPizzaPrice;

        Drinks(String drinkName, int addToPizzaPrice) {
            this.drinkName = drinkName;
            this.addToPizzaPrice = addToPizzaPrice;
        }

        public String getDrinkName() { return drinkName; }
        public int getAddToPizzaPrice() { return addToPizzaPrice; }

        @Override
        public String toString() {
            return drinkName + ": €" + addToPizzaPrice;
        }
    }

    private static final double PIZZA_BASE_PRICE = 10.0;
    private String[] pizzasOrdered = new String[10];
    private String[] pizzaSizesOrdered = new String[10];
    private String[] sideDishesOrdered = new String[20];
    private String[] drinksOrdered = new String[20];
    private double totalOrderPrice = 0.0;
    private int orderCount = 0;

    public void takeOrder() {
        Scanner scanner = new Scanner(System.in);
        boolean moreOrders = true;

        while (moreOrders && orderCount < 10) {
            System.out.println("Welcome to Slice-o-Heaven Pizzeria. Here’s what we serve:");
            int i = 1;
            for (PizzaSelection pizza : PizzaSelection.values()) {
                System.out.println(i++ + ". " + pizza.toString());
            }
            System.out.println(i + ". Custom Pizza with a maximum of 10 toppings that you choose");
            System.out.print("Please enter your choice (1 - 6): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            if (choice >= 1 && choice <= 5) {
                PizzaSelection selectedPizza = PizzaSelection.values()[choice - 1];
                pizzasOrdered[orderCount] = selectedPizza.toString();
                totalOrderPrice += selectedPizza.getPrice();
            } else if (choice == 6) {
                System.out.println("\nAvailable toppings:");
                for (PizzaToppings topping : PizzaToppings.values()) {
                    System.out.println(topping.toString());
                }
                
                System.out.println("\nEnter up to 10 topping numbers (1-15) separated by spaces, then press Enter:");
                String[] toppingChoices = scanner.nextLine().split("\\s+");
                double customPrice = PIZZA_BASE_PRICE;
                StringBuilder customToppings = new StringBuilder("Custom Pizza with ");
                
                for (int j = 0; j < Math.min(toppingChoices.length, 10); j++) {
                    int toppingNum = Integer.parseInt(toppingChoices[j]) - 1;
                    if (toppingNum >= 0 && toppingNum < PizzaToppings.values().length) {
                        PizzaToppings topping = PizzaToppings.values()[toppingNum];
                        customPrice += topping.getToppingPrice();
                        customToppings.append(topping.getTopping());
                        if (j < Math.min(toppingChoices.length, 10) - 1) {
                            customToppings.append(", ");
                        }
                    }
                }
                customToppings.append(", for €").append(String.format("%.1f", customPrice));
                pizzasOrdered[orderCount] = customToppings.toString();
                totalOrderPrice += customPrice;
            }

            // Pizza Size
            System.out.println("\nPizza Sizes:");
            for (PizzaSize size : PizzaSize.values()) {
                System.out.println(size.toString());
            }
            System.out.print("Choose size (1-3): ");
            int sizeChoice = scanner.nextInt();
            PizzaSize selectedSize = PizzaSize.values()[sizeChoice - 1];
            pizzaSizesOrdered[orderCount] = selectedSize.getPizzaSize();
            totalOrderPrice += selectedSize.getAddToPizzaPrice();

            // Side Dish
            System.out.println("\nSide Dishes:");
            for (SideDish side : SideDish.values()) {
                System.out.println(side.toString());
            }
            System.out.print("Choose side dish (1-4): ");
            int sideChoice = scanner.nextInt();
            SideDish selectedSide = SideDish.values()[sideChoice - 1];
            sideDishesOrdered[orderCount] = selectedSide.getSideDishName();
            totalOrderPrice += selectedSide.getAddToPizzaPrice();

            // Drinks
            System.out.println("\nDrinks:");
            for (Drinks drink : Drinks.values()) {
                System.out.println(drink.toString());
            }
            System.out.print("Choose drink (1-3): ");
            int drinkChoice = scanner.nextInt();
            Drinks selectedDrink = Drinks.values()[drinkChoice - 1];
            drinksOrdered[orderCount] = selectedDrink.getDrinkName();
            totalOrderPrice += selectedDrink.getAddToPizzaPrice();

            orderCount++;
            
            if (orderCount < 10) {
                System.out.print("\nWould you like to order more? (yes/no): ");
                moreOrders = scanner.next().equalsIgnoreCase("yes");
                scanner.nextLine(); // Clear buffer
            } else {
                System.out.println("Maximum order limit reached!");
                moreOrders = false;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder orderDetails = new StringBuilder("Thank you for dining with Slice-o-Heaven Pizzeria. Your order details are as follows:\n");
        for (int i = 0; i < orderCount; i++) {
            orderDetails.append(i + 1).append(". ")
                .append(pizzasOrdered[i]).append("\n")
                .append(pizzaSizesOrdered[i]).append(": €")
                .append(PizzaSize.valueOf(pizzaSizesOrdered[i].toUpperCase()).getAddToPizzaPrice()).append("\n")
                .append(sideDishesOrdered[i]).append(": €")
                .append(SideDish.valueOf(sideDishesOrdered[i].toUpperCase().replace(" ", "_")).getAddToPizzaPrice()).append("\n")
                .append(drinksOrdered[i]).append(": €")
                .append(Drinks.valueOf(drinksOrdered[i].toUpperCase().replace(" ", "_")).getAddToPizzaPrice()).append("\n\n");
        }
        orderDetails.append("ORDER TOTAL: €").append(String.format("%.1f", totalOrderPrice));
        return orderDetails.toString();
    }

    public static void main(String[] args) {
        Pizzeria pizzeria = new Pizzeria();
        pizzeria.takeOrder();
        System.out.println("\n" + pizzeria);
    }
}