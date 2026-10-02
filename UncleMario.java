import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UncleMario {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatabaseManager.initializeDatabase();
        boolean programRunning = true;

        while (programRunning) {
            Customer customer = null;
            boolean rewardsMember = false;

            System.out.println("\nUNCLE MARIO'S PIZZERIA");
            System.out.println("\n1. Login");
            System.out.println("2. Create Account");
            System.out.println("3. Continue as Guest");
            System.out.println("4. Exit");

            int accountChoice = getInt(scanner, "Enter choice: ");

            if (accountChoice == 1) {
                System.out.println("\nLOGIN");
                System.out.print("\nUsername: ");
                String username = scanner.nextLine();

                if (!CustomerDAO.usernameExists(username)) {
                    System.out.println("\nAccount not found.");
                    System.out.println("Please create an account first.");
                    continue;
                }

                System.out.print("Password: ");
                String password = scanner.nextLine();
                customer = CustomerDAO.login(username, password);

                if (customer == null) {
                    System.out.println("\nIncorrect password.");
                    System.out.println("Please try again.");
                    continue;
                }

                rewardsMember = true;
                System.out.println("\nLogin successful!");
                System.out.println("Welcome back, " + customer.getUsername() + "!");
                System.out.println("Reward Points: " + customer.getPoints());

            } else if (accountChoice == 2) {
                System.out.println("\nCREATE ACCOUNT");
                System.out.print("\nCreate username: ");
                String username = scanner.nextLine();

                if (username.trim().isEmpty()) {
                    System.out.println("Username cannot be empty.");
                    continue;
                }

                if (CustomerDAO.usernameExists(username)) {
                    System.out.println("\nThat username already exists.");
                    System.out.println("Please choose another username.");
                    continue;
                }

                System.out.print("Create password: ");
                String password = scanner.nextLine();

                if (password.trim().isEmpty()) {
                    System.out.println("Password cannot be empty.");
                    continue;
                }

                customer = CustomerDAO.createAccount(username, password);

                if (customer == null) {
                    System.out.println("\nAccount could not be created.");
                    continue;
                }

                rewardsMember = true;
                System.out.println("\nAccount created successfully!");
                System.out.println("Welcome, " + customer.getUsername() + "!");
                System.out.println("Reward Points: " + customer.getPoints());

            } else if (accountChoice == 3) {
                System.out.println("\nContinuing as Guest...");
                System.out.println("Guests cannot earn or redeem reward points.");

            } else if (accountChoice == 4) {
                programRunning = false;
                break;

            } else {
                System.out.println("Invalid choice.");
                continue;
            }

            boolean customerSession = true;

            while (customerSession && programRunning) {
                placeOrder(scanner, customer, rewardsMember);
                boolean nextMenu = true;

                while (nextMenu && customerSession && programRunning) {
                    System.out.println("\nWHAT'S NEXT?");
                    System.out.println("\n1. Order Again");

                    if (rewardsMember) {
                        System.out.println("2. Go to My Account");
                    } else {
                        System.out.println("2. Login / Create Account");
                    }

                    System.out.println("3. Return to Main Screen");
                    System.out.println("4. Exit");

                    int nextChoice = getInt(scanner, "Enter choice: ");

                    if (nextChoice == 1) {
                        nextMenu = false;

                    } else if (nextChoice == 2) {
                        if (rewardsMember) {
                            boolean accountScreen = true;

                            while (accountScreen) {
                                System.out.println("\nMY ACCOUNT");
                                System.out.println("\nUsername: " + customer.getUsername());
                                System.out.println("Reward Points: " + customer.getPoints());

                                int rewardValue = customer.getPoints() / 100;
                                System.out.println("Reward Value: $" + rewardValue + ".00");

                                if (customer.isStudentVerified()) {
                                    System.out.println("Student Status: Verified");
                                    System.out.println("Student Email: " + customer.getStudentEmail());
                                } else {
                                    System.out.println("Student Status: Not Verified");
                                }

                                System.out.println("\n1. Place Another Order");
                                System.out.println("2. Student Verification");
                                System.out.println("3. Return");
                                System.out.println("4. Logout");

                                int myAccountChoice = getInt(scanner, "Enter choice: ");

                                if (myAccountChoice == 1) {
                                    accountScreen = false;
                                    nextMenu = false;

                                } else if (myAccountChoice == 2) {
                                    if (customer.isStudentVerified()) {
                                        System.out.println("\nYour student account is already verified.");
                                        System.out.println("Student Email: " + customer.getStudentEmail());
                                    } else {
                                        StudentVerification.verifyStudent(scanner, customer);
                                    }

                                } else if (myAccountChoice == 3) {
                                    accountScreen = false;

                                } else if (myAccountChoice == 4) {
                                    accountScreen = false;
                                    nextMenu = false;
                                    customerSession = false;
                                    System.out.println("\nLogged out successfully.");

                                } else {
                                    System.out.println("Invalid choice.");
                                }
                            }
                        } else {
                            nextMenu = false;
                            customerSession = false;
                        }

                    } else if (nextChoice == 3) {
                        nextMenu = false;
                        customerSession = false;

                    } else if (nextChoice == 4) {
                        nextMenu = false;
                        customerSession = false;
                        programRunning = false;

                    } else {
                        System.out.println("Invalid choice.");
                    }
                }
            }
        }

        System.out.println("\nTHANK YOU FOR VISITING UNCLE MARIO'S PIZZERIA!");
        scanner.close();
    }

    private static void placeOrder(Scanner scanner, Customer customer, boolean rewardsMember) {
        Cart cart = new Cart();
        int[] quantity = new int[1];
        boolean ordering = true;

        if (rewardsMember) {
            System.out.println("\nREWARDS PROGRAM");
            System.out.println("\n$1 spent = 10 points");
            System.out.println("100 points = $1.00 off");
            System.out.println("Current Points: " + customer.getPoints());
        }

        while (ordering) {
            System.out.println("\nUNCLE MARIO'S PIZZERIA");
            System.out.println("\nMENU");
            System.out.println("\n1. Specialty Pizzas");
            System.out.println("2. Build Your Own Pizza");
            System.out.println("3. Build Your Own Calzone");
            System.out.println("4. Sides");
            System.out.println("5. Desserts");
            System.out.println("6. Drinks");
            System.out.println("7. Special Deals");
            System.out.println("8. View Cart (" + cart.getItemCount() + ")");
            System.out.println("9. Checkout");
            System.out.println("0. Cancel Order");

            int menuChoice = getInt(scanner, "Enter choice: ");

            if (menuChoice == 1) {
                SpecialtyPizza pizza = specialtyPizzaMenu(scanner, quantity);

                if (pizza != null) {
                    cart.addItem(pizza, quantity[0]);
                }

            } else if (menuChoice == 2) {
                CustomPizza2 pizza = buildPizza(scanner, quantity);
                if (pizza != null) {
                    cart.addItem(pizza, quantity[0]);
                }

            } else if (menuChoice == 3) {
                CustomCalzone calzone = buildCalzone(scanner, quantity);
                if (calzone != null) {
                    cart.addItem(calzone, quantity[0]);
                }

            } else if (menuChoice == 4) {
                addMenuSelection(scanner, cart, () -> sidesMenu(scanner));

            } else if (menuChoice == 5) {
                addMenuSelection(scanner, cart, () -> dessertsMenu(scanner));

            } else if (menuChoice == 6) {
                addMenuSelection(scanner, cart, () -> drinksMenu(scanner));

            } else if (menuChoice == 7) {
                MenuItem deal = specialDealsMenu(scanner, customer, quantity);

                if (deal != null) {
                    cart.addItem(deal, quantity[0]);
                }

            } else if (menuChoice == 8) {
                boolean readyToCheckout = viewCart(scanner, cart);

                if (readyToCheckout) {
                    boolean completed = checkout(scanner, cart, customer, rewardsMember);

                    if (completed) {
                        ordering = false;
                    }
                }

            } else if (menuChoice == 9) {
                if (cart.isEmpty()) {
                    System.out.println("\nYour cart is empty.");
                } else {
                    System.out.println("\nPlease review your cart before checkout.");
                    boolean readyToCheckout = viewCart(scanner, cart);

                    if (readyToCheckout) {
                        boolean completed = checkout(scanner, cart, customer, rewardsMember);

                        if (completed) {
                            ordering = false;
                        }
                    }
                }

            } else if (menuChoice == 0) {
                if (!cart.isEmpty()) {
                    System.out.println("\nYour order has been cancelled.");
                }

                ordering = false;

            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static void addMenuSelection(
            Scanner scanner, Cart cart, java.util.function.Supplier<MenuItem> menu) {
        while (true) {
            MenuItem item = menu.get();
            if (item == null) {
                return;
            }
            int quantity = getQuantity(scanner);
            if (quantity > 0) {
                cart.addItem(item, quantity);
                return;
            }
        }
    }

    private static SpecialtyPizza specialtyPizzaMenu(Scanner scanner, int[] quantity) {
        selection:
        while (true) {
        System.out.println("\nSPECIALTY PIZZAS");
        System.out.println("\n1. Cheese");
        System.out.println("   Tomato Sauce, Mozzarella");
        System.out.println("\n2. Pepperoni");
        System.out.println("   Tomato Sauce, Mozzarella, Pepperoni");
        System.out.println("\n3. Meat Lovers");
        System.out.println("   Pepperoni, Sausage, Bacon, Ham");
        System.out.println("\n4. Supreme");
        System.out.println("   Pepperoni, Sausage, Mushrooms, Onions, Green Peppers, Black Olives");
        System.out.println("\n5. Veggie");
        System.out.println("   Mushrooms, Onions, Green Peppers, Black Olives, Tomatoes, Spinach");
        System.out.println("\n6. Hawaiian");
        System.out.println("   Ham, Pineapple");
        System.out.println("\n7. Buffalo Chicken");
        System.out.println("   Grilled Chicken, Buffalo Sauce, Mozzarella");
        System.out.println("\n8. BBQ Chicken");
        System.out.println("   Grilled Chicken, BBQ Sauce, Onions, Mozzarella");
        System.out.println("\n9. Hot Honey Pepperoni");
        System.out.println("   Pepperoni, Mozzarella, Hot Honey");
        System.out.println("\n10. Chicken Bacon Ranch");
        System.out.println("    Grilled Chicken, Bacon, Mozzarella, Ranch");
        System.out.println("\n11. White Garlic");
        System.out.println("    Garlic Parmesan Sauce, Mozzarella, Spinach, Tomatoes");
        System.out.println("\n12. Margherita");
        System.out.println("    Tomato Sauce, Mozzarella, Tomatoes, Basil");
        System.out.println("\n13. Philly Cheesesteak");
        System.out.println("    Beef, Onions, Green Peppers, Mozzarella");
        System.out.println("\n0. Go Back");

        int pizzaChoice;

        while (true) {
            pizzaChoice = getInt(scanner, "Enter choice: ");

            if (pizzaChoice >= 0 && pizzaChoice <= 13) {
                break;
            }

            System.out.println("Please choose a number from 0-13.");
        }

        if (pizzaChoice == 0) {
            return null;
        }

        String name = "";
        String description = "";

        switch (pizzaChoice) {
            case 1:
                name = "Cheese";
                description = "Tomato Sauce, Mozzarella";
                break;
            case 2:
                name = "Pepperoni";
                description = "Tomato Sauce, Mozzarella, Pepperoni";
                break;
            case 3:
                name = "Meat Lovers";
                description = "Pepperoni, Sausage, Bacon, Ham";
                break;
            case 4:
                name = "Supreme";
                description = "Pepperoni, Sausage, Mushrooms, Onions, Green Peppers, Black Olives";
                break;
            case 5:
                name = "Veggie";
                description = "Mushrooms, Onions, Green Peppers, Black Olives, Tomatoes, Spinach";
                break;
            case 6:
                name = "Hawaiian";
                description = "Ham, Pineapple";
                break;
            case 7:
                name = "Buffalo Chicken";
                description = "Grilled Chicken, Buffalo Sauce, Mozzarella";
                break;
            case 8:
                name = "BBQ Chicken";
                description = "Grilled Chicken, BBQ Sauce, Onions, Mozzarella";
                break;
            case 9:
                name = "Hot Honey Pepperoni";
                description = "Pepperoni, Mozzarella, Hot Honey";
                break;
            case 10:
                name = "Chicken Bacon Ranch";
                description = "Grilled Chicken, Bacon, Mozzarella, Ranch";
                break;
            case 11:
                name = "White Garlic";
                description = "Garlic Parmesan Sauce, Mozzarella, Spinach, Tomatoes";
                break;
            case 12:
                name = "Margherita";
                description = "Tomato Sauce, Mozzarella, Tomatoes, Basil";
                break;
            case 13:
                name = "Philly Cheesesteak";
                description = "Beef, Onions, Green Peppers, Mozzarella";
                break;
        }

        while (true) {
        System.out.println("\nSELECT SIZE");
        System.out.println("\n1. Small - $10.99");
        System.out.println("2. Medium - $13.99");
        System.out.println("3. Large - $16.99");
        System.out.println("4. XL - $19.99");
        System.out.println("0. Go Back");

        int sizeChoice;

        while (true) {
            sizeChoice = getInt(scanner, "Enter choice: ");

            if (sizeChoice >= 0 && sizeChoice <= 4) {
                break;
            }

            System.out.println("Please choose a number from 0-4.");
        }

        if (sizeChoice == 0) {
            continue selection;
        }

        String size;
        double price;

        if (sizeChoice == 1) {
            size = "Small";
            price = 10.99;
        } else if (sizeChoice == 2) {
            size = "Medium";
            price = 13.99;
        } else if (sizeChoice == 3) {
            size = "Large";
            price = 16.99;
        } else {
            size = "XL";
            price = 19.99;
        }

        SpecialtyPizza pizza = new SpecialtyPizza(name, size, description, price);

        System.out.println("\nYOUR PIZZA");
        System.out.println();
        System.out.println(pizza);

        if ((quantity[0] = getQuantity(scanner)) == 0) {
            continue;
        }

        return pizza;
        }
        }
    }

    private static CustomPizza2 buildPizza(Scanner scanner, int[] quantity) {
        int[] step = {0};
        int sizeChoice = 0;
        int crustChoice = 0;
        int sauceChoice = 0;
        int cheeseChoice = 0;
        List<String> selectedToppings = new ArrayList<>();

        while (step[0] >= 0) {
        CustomPizza2 pizza = new CustomPizza2("Build Your Own Pizza");

        System.out.println("\nBUILD YOUR OWN PIZZA");
        if (step[0] == 0) {
        System.out.println("\nSIZE");
        System.out.println("\n1. Small - $10.99");
        System.out.println("2. Medium - $13.99");
        System.out.println("3. Large - $16.99");
        System.out.println("4. XL - $19.99");

        System.out.println("0. Go Back");
        while (true) {
            sizeChoice = getInt(scanner, "Enter choice: ");

            if (sizeChoice >= 0 && sizeChoice <= 4) {
                break;
            }

            System.out.println("Please choose a number from 0-4.");
        }

        if (!advanceStep(step, sizeChoice != 0)) {
            continue;
        }
        }

        switch (sizeChoice) {
            case 1:
                pizza.setSize("Small");
                break;
            case 2:
                pizza.setSize("Medium");
                break;
            case 3:
                pizza.setSize("Large");
                break;
            case 4:
                pizza.setSize("XL");
                break;
        }

        if (step[0] == 1) {
        System.out.println("\nCRUST");
        System.out.println("\n1. Thin");
        System.out.println("2. Regular");
        System.out.println("3. Stuffed (+$2.00)");

        System.out.println("0. Go Back");
        while (true) {
            crustChoice = getInt(scanner, "Enter choice: ");

            if (crustChoice >= 0 && crustChoice <= 3) {
                break;
            }

            System.out.println("Please choose a number from 0-3.");
        }

        if (!advanceStep(step, crustChoice != 0)) {
            continue;
        }
        }

        if (crustChoice == 1) {
            pizza.setCrust("Thin");
        } else if (crustChoice == 3) {
            pizza.setCrust("Stuffed");
        } else {
            pizza.setCrust("Regular");
        }

        if (step[0] == 2) {
        System.out.println("\nSAUCE");
        System.out.println("\n1. Tomato");
        System.out.println("2. Alfredo");
        System.out.println("3. BBQ");
        System.out.println("4. Garlic Parmesan");
        System.out.println("5. No Sauce");

        System.out.println("0. Go Back");
        while (true) {
            sauceChoice = getInt(scanner, "Enter choice: ");

            if (sauceChoice >= 0 && sauceChoice <= 5) {
                break;
            }

            System.out.println("Please choose a number from 0-5.");
        }

        if (!advanceStep(step, sauceChoice != 0)) {
            continue;
        }
        }

        switch (sauceChoice) {
            case 2:
                pizza.setSauce("Alfredo");
                break;
            case 3:
                pizza.setSauce("BBQ");
                break;
            case 4:
                pizza.setSauce("Garlic Parmesan");
                break;
            case 5:
                pizza.setSauce("No Sauce");
                break;
            default:
                pizza.setSauce("Tomato");
        }

        if (step[0] == 3) {
        System.out.println("\nCHEESE");
        System.out.println("\n1. Mozzarella Cheese");
        System.out.println("2. Light Mozzarella");
        System.out.println("3. Extra Mozzarella (+$0.50)");
        System.out.println("4. Three-Cheese Blend (+$0.50)");
        System.out.println("5. No Cheese");

        System.out.println("0. Go Back");
        while (true) {
            cheeseChoice = getInt(scanner, "Enter choice: ");

            if (cheeseChoice >= 0 && cheeseChoice <= 5) {
                break;
            }

            System.out.println("Please choose a number from 0-5.");
        }

        if (!advanceStep(step, cheeseChoice != 0)) {
            continue;
        }
        }

        switch (cheeseChoice) {
            case 1:
                pizza.setCheese("Mozzarella Cheese");
                break;
            case 2:
                pizza.setCheese("Light Mozzarella");
                break;
            case 3:
                pizza.setCheese("Extra Mozzarella");
                break;
            case 4:
                pizza.setCheese("Three-Cheese Blend");
                break;
            case 5:
                pizza.setCheese("No Cheese");
                break;
        }

        System.out.println("\nEvery topping is $0.50.");
        boolean addingToppings = true;

        while (addingToppings) {
            printToppingMenu();
            int toppingChoice = getInt(scanner, "Enter choice: ");

            if (toppingChoice == 0) {
                step[0] = 3;
                break;
            } else if (toppingChoice == 18) {
                addingToppings = false;
            } else {
                String topping = getTopping(toppingChoice);

                if (topping != null) {
                    selectedToppings.add(topping);
                    System.out.println(topping + " added! (+$0.50)");
                } else {
                    System.out.println("Invalid topping choice.");
                }
            }
        }

        if (step[0] < 4) {
            continue;
        }

        for (String topping : selectedToppings) {
            pizza.addToppings(topping);
        }

        System.out.println("\nYOUR PIZZA");
        System.out.println();
        System.out.println(pizza);

        if ((quantity[0] = getQuantity(scanner)) == 0) {
            continue;
        }

        return pizza;
        }
        return null;
    }

    private static CustomCalzone buildCalzone(Scanner scanner, int[] quantity) {
        int[] step = {0};
        int cheeseChoice = 0;
        List<String> selectedToppings = new ArrayList<>();

        while (step[0] >= 0) {
        CustomCalzone calzone = new CustomCalzone("Build Your Own Calzone");

        System.out.println("\nBUILD YOUR OWN CALZONE");
        System.out.println("\nBase Price: $11.99");

        if (step[0] == 0) {
        System.out.println("\nCHEESE");
        System.out.println("\n1. Mozzarella Cheese");
        System.out.println("2. Light Mozzarella");
        System.out.println("3. Extra Mozzarella (+$0.50)");
        System.out.println("4. Three-Cheese Blend (+$0.50)");
        System.out.println("5. No Cheese");

        System.out.println("0. Go Back");
        while (true) {
            cheeseChoice = getInt(scanner, "Enter choice: ");

            if (cheeseChoice >= 0 && cheeseChoice <= 5) {
                break;
            }

            System.out.println("Please choose a number from 0-5.");
        }

        if (!advanceStep(step, cheeseChoice != 0)) {
            continue;
        }
        }

        switch (cheeseChoice) {
            case 1:
                calzone.setCheese("Mozzarella Cheese");
                break;
            case 2:
                calzone.setCheese("Light Mozzarella");
                break;
            case 3:
                calzone.setCheese("Extra Mozzarella");
                break;
            case 4:
                calzone.setCheese("Three-Cheese Blend");
                break;
            case 5:
                calzone.setCheese("No Cheese");
                break;
        }

        System.out.println("\nSTUFFINGS");
        System.out.println("\nEach stuffing is $0.50.");

        boolean addingStuffings = true;

        while (addingStuffings) {
            printToppingMenu();
            int stuffingChoice = getInt(scanner, "Enter choice: ");

            if (stuffingChoice == 0) {
                step[0] = 0;
                break;
            } else if (stuffingChoice == 18) {
                addingStuffings = false;
            } else {
                String stuffing = getTopping(stuffingChoice);

                if (stuffing != null) {
                    selectedToppings.add(stuffing);
                    System.out.println(stuffing + " added! (+$0.50)");
                } else {
                    System.out.println("Invalid stuffing choice.");
                }
            }
        }

        if (step[0] < 1) {
            continue;
        }

        for (String topping : selectedToppings) {
            calzone.addStuffings(topping);
        }

        System.out.println("\nYOUR CALZONE");
        System.out.println();
        System.out.println(calzone);

        if ((quantity[0] = getQuantity(scanner)) == 0) {
            continue;
        }

        return calzone;
        }
        return null;
    }

    private static MenuItem sidesMenu(Scanner scanner) {
        System.out.println("\nSIDES");
        System.out.println("\n1. Garlic Knots - $5.99");
        System.out.println("2. Cheese Bread - $6.99");
        System.out.println("3. Mozzarella Sticks - $7.99");
        System.out.println("4. French Fries - $4.99");
        System.out.println("5. Loaded Fries - $7.99");
        System.out.println("6. Breadsticks - $5.99");
        System.out.println("0. Go Back");

        int choice;

        while (true) {
            choice = getInt(scanner, "Enter choice: ");

            if (choice >= 0 && choice <= 6) {
                break;
            }

            System.out.println("Please choose a number from 0-6.");
        }

        switch (choice) {
            case 1:
                return new MenuItem("Garlic Knots", 5.99);
            case 2:
                return new MenuItem("Cheese Bread", 6.99);
            case 3:
                return new MenuItem("Mozzarella Sticks", 7.99);
            case 4:
                return new MenuItem("French Fries", 4.99);
            case 5:
                return new MenuItem("Loaded Fries", 7.99);
            case 6:
                return new MenuItem("Breadsticks", 5.99);
            default:
                return null;
        }
    }

    private static MenuItem dessertsMenu(Scanner scanner) {
        System.out.println("\nDESSERTS");
        System.out.println("\n1. Cinnamon Knots - $5.99");
        System.out.println("2. Chocolate Chip Cookie - $2.99");
        System.out.println("3. Brownie - $3.49");
        System.out.println("4. Cannoli - $4.49");
        System.out.println("0. Go Back");

        int choice;

        while (true) {
            choice = getInt(scanner, "Enter choice: ");

            if (choice >= 0 && choice <= 4) {
                break;
            }

            System.out.println("Please choose a number from 0-4.");
        }

        switch (choice) {
            case 1:
                return new MenuItem("Cinnamon Knots", 5.99);
            case 2:
                return new MenuItem("Chocolate Chip Cookie", 2.99);
            case 3:
                return new MenuItem("Brownie", 3.49);
            case 4:
                return new MenuItem("Cannoli", 4.49);
            default:
                return null;
        }
    }

    private static MenuItem drinksMenu(Scanner scanner) {
        System.out.println("\nDRINKS");
        printDrinkMenu();

        int choice;

        while (true) {
            choice = getInt(scanner, "Enter choice: ");

            if (choice >= 0 && choice <= 7) {
                break;
            }

            System.out.println("Please choose a number from 0-7.");
        }

        if (choice == 0) {
            return null;
        }

        return getDrinkItem(choice);
    }

    private static MenuItem specialDealsMenu(Scanner scanner, Customer customer, int[] quantity) {
        while (true) {
        System.out.println("\nSPECIAL DEALS");
        System.out.println("\n1. Mario's Combo - $19.99");
        System.out.println("   Large 1-Topping Pizza");
        System.out.println("   Breadsticks");
        System.out.println("   2 Drinks");

        System.out.println("\n2. Family Night - $34.99");
        System.out.println("   2 Large 1-Topping Pizzas");
        System.out.println("   Garlic Knots");
        System.out.println("   1 Drink");

        System.out.println("\n3. Student Special - $12.99");
        System.out.println("   Small 1-Topping Pizza");
        System.out.println("   1 Drink");
        System.out.println("   Verified students only");

        System.out.println("\n4. Two Large Pizzas - $27.99");
        System.out.println("   2 Large Cheese or Pepperoni Pizzas");

        System.out.println("\nFirst topping is included.");
        System.out.println("Additional toppings are $0.50 each.");
        System.out.println("\n0. Go Back");

        int choice;

        while (true) {
            choice = getInt(scanner, "Enter choice: ");

            if (choice >= 0 && choice <= 4) {
                break;
            }

            System.out.println("Please choose a number from 0-4.");
        }

        if (choice == 0) {
            return null;
        }

        if (choice == 1) {
            MenuItem deal = buildMariosCombo(scanner, quantity);
            if (deal != null) {
                return deal;
            }
            continue;
        }

        if (choice == 2) {
            MenuItem deal = buildFamilyNight(scanner, quantity);
            if (deal != null) {
                return deal;
            }
            continue;
        }

        if (choice == 3) {
            if (customer == null) {
                System.out.println("\nSTUDENT VERIFICATION REQUIRED");
                System.out.println("You must login or create an account to use the Student Special.");
                return null;
            }

            if (!customer.isStudentVerified()) {
                while (true) {
                    System.out.println("\nSTUDENT VERIFICATION REQUIRED");
                    System.out.println("\nA verified .edu email is required for the Student Special.");
                    System.out.println("\n1. Verify Student Email");
                    System.out.println("0. Go Back");

                    int verificationChoice = getInt(scanner, "Enter choice: ");

                    if (verificationChoice == 0) {
                        break;
                    }

                    if (verificationChoice == 1) {
                        if (StudentVerification.verifyStudent(scanner, customer)) {
                            break;
                        }

                        System.out.println("\nStudent Special was not added.");
                    } else {
                        System.out.println("Please choose 0 or 1.");
                    }
                }
            }

            if (!customer.isStudentVerified()) {
                continue;
            }

            MenuItem deal = buildStudentSpecial(scanner, quantity);
            if (deal != null) {
                return deal;
            }
            continue;
        }

        MenuItem deal = buildTwoLargeDeal(scanner, quantity);
            if (deal != null) {
                return deal;
            }
            continue;
        }
    }

    @SuppressWarnings("unchecked")
    private static MenuItem buildMariosCombo(Scanner scanner, int[] quantity) {
        System.out.println("\nMARIO'S COMBO");
        System.out.println("\nYour first pizza topping is included.");

        DealNavigation navigation = new DealNavigation(
                new String[] {"SELECT YOUR INCLUDED TOPPING", "", "SELECT DRINK 1", "SELECT DRINK 2"},
                new int[] {0, 1, 2, 2});

        while (navigation.choose(scanner)) {
        String includedTopping = (String) navigation.choices[0];
        List<String> extraToppings = (List<String>) navigation.choices[1];
        String drink1 = (String) navigation.choices[2];
        String drink2 = (String) navigation.choices[3];

        double extraCost = extraToppings.size() * 0.50;
        double total = 19.99 + extraCost;

        String pizzaDescription =
                includedTopping + formatExtraToppings(extraToppings);

        String description =
                "Mario's Combo | Large Pizza: "
                        + pizzaDescription
                        + " | Breadsticks | Drink 1: "
                        + drink1
                        + " | Drink 2: "
                        + drink2;

        System.out.println("\nMARIO'S COMBO SUMMARY");
        System.out.println("\nLarge Pizza: " + pizzaDescription);
        System.out.println("Breadsticks");
        System.out.println("Drink 1: " + drink1);
        System.out.println("Drink 2: " + drink2);

        if (!extraToppings.isEmpty()) {
            System.out.printf("Extra Toppings: +$%.2f%n", extraCost);
        }

        System.out.printf("Deal Total: $%.2f%n", total);
        if ((quantity[0] = getQuantity(scanner)) == 0) {
            navigation.step--;
            continue;
        }

        return new MenuItem(description, total);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static MenuItem buildFamilyNight(Scanner scanner, int[] quantity) {
        System.out.println("\nFAMILY NIGHT");
        DealNavigation navigation = new DealNavigation(
                new String[] {"SELECT PIZZA 1 INCLUDED TOPPING", "", "SELECT PIZZA 2 INCLUDED TOPPING", "", "SELECT YOUR DRINK"},
                new int[] {0, 1, 0, 1, 2});

        while (navigation.choose(scanner)) {
        String pizza1Included = (String) navigation.choices[0];
        List<String> pizza1Extras = (List<String>) navigation.choices[1];
        String pizza2Included = (String) navigation.choices[2];
        List<String> pizza2Extras = (List<String>) navigation.choices[3];
        String drink = (String) navigation.choices[4];

        int extraCount = pizza1Extras.size() + pizza2Extras.size();
        double extraCost = extraCount * 0.50;
        double total = 34.99 + extraCost;

        String pizza1 =
                pizza1Included + formatExtraToppings(pizza1Extras);

        String pizza2 =
                pizza2Included + formatExtraToppings(pizza2Extras);

        String description =
                "Family Night | Large Pizza 1: "
                        + pizza1
                        + " | Large Pizza 2: "
                        + pizza2
                        + " | Garlic Knots | Drink: "
                        + drink;

        System.out.println("\nFAMILY NIGHT SUMMARY");
        System.out.println("\nLarge Pizza 1: " + pizza1);
        System.out.println("Large Pizza 2: " + pizza2);
        System.out.println("Garlic Knots");
        System.out.println("Drink: " + drink);

        if (extraCount > 0) {
            System.out.printf("Extra Toppings: +$%.2f%n", extraCost);
        }

        System.out.printf("Deal Total: $%.2f%n", total);
        if ((quantity[0] = getQuantity(scanner)) == 0) {
            navigation.step--;
            continue;
        }

        return new MenuItem(description, total);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static MenuItem buildStudentSpecial(Scanner scanner, int[] quantity) {
        System.out.println("\nSTUDENT SPECIAL");
        System.out.println("\nIncludes:");
        System.out.println("Small Pizza");
        System.out.println("1 Topping");
        System.out.println("1 Drink");
        System.out.println("\nYour first topping is included.");

        DealNavigation navigation = new DealNavigation(
                new String[] {"SELECT YOUR INCLUDED TOPPING", "", "SELECT YOUR DRINK"},
                new int[] {0, 1, 2});

        while (navigation.choose(scanner)) {
        String includedTopping = (String) navigation.choices[0];
        List<String> extraToppings = (List<String>) navigation.choices[1];
        String drink = (String) navigation.choices[2];

        double extraCost = extraToppings.size() * 0.50;
        double total = 12.99 + extraCost;

        String pizzaDescription =
                includedTopping + formatExtraToppings(extraToppings);

        String description =
                "Student Special | Small Pizza: "
                        + pizzaDescription
                        + " | Drink: "
                        + drink;

        System.out.println("\nSTUDENT SPECIAL SUMMARY");
        System.out.println("\nSmall Pizza: " + pizzaDescription);
        System.out.println("Drink: " + drink);

        if (!extraToppings.isEmpty()) {
            System.out.printf("Extra Toppings: +$%.2f%n", extraCost);
        }

        System.out.printf("Deal Total: $%.2f%n", total);
        if ((quantity[0] = getQuantity(scanner)) == 0) {
            navigation.step--;
            continue;
        }

        return new MenuItem(description, total);
        }
        return null;
    }

    private static MenuItem buildTwoLargeDeal(Scanner scanner, int[] quantity) {
        System.out.println("\nTWO LARGE PIZZAS");

        DealNavigation navigation = new DealNavigation(
                new String[] {"Pizza 1", "Pizza 2"},
                new int[] {3, 3});

        while (navigation.choose(scanner)) {
        String pizza1 = (String) navigation.choices[0];
        String pizza2 = (String) navigation.choices[1];

        String description =
                "Two Large Pizzas | Pizza 1: "
                        + pizza1
                        + " | Pizza 2: "
                        + pizza2;

        System.out.println("\nDEAL SUMMARY");
        System.out.println("\nLarge Pizza 1: " + pizza1);
        System.out.println("Large Pizza 2: " + pizza2);
        System.out.println("Deal Total: $27.99");

        if ((quantity[0] = getQuantity(scanner)) == 0) {
            navigation.step--;
            continue;
        }

        return new MenuItem(description, 27.99);
        }
        return null;
    }

    private static class DealNavigation {
        private final String[] headings;
        private final int[] types;
        private final Object[] choices;
        private final int[][] extraSteps;
        private int step = 0;

        private DealNavigation(String[] headings, int[] types) {
            this.headings = headings;
            this.types = types;
            choices = new Object[types.length];
            extraSteps = new int[types.length][1];
            for (int i = 0; i < types.length; i++) {
                if (types[i] == 1) {
                    choices[i] = new ArrayList<String>();
                }
            }
        }

        @SuppressWarnings("unchecked")
        private boolean choose(Scanner scanner) {
            while (step >= 0 && step < types.length) {
                Object choice;
                if (types[step] == 0) {
                    choice = chooseIncludedTopping(scanner, headings[step]);
                } else if (types[step] == 1) {
                    choice = chooseExtraToppings(scanner, extraSteps[step],
                            (List<String>) choices[step]);
                } else {
                    System.out.println("\n" + headings[step]);
                    choice = types[step] == 2
                            ? chooseDealDrink(scanner) : chooseCheeseOrPepperoni(scanner);
                }
                if (choice == null) {
                    step--;
                } else {
                    choices[step++] = choice;
                }
            }
            return step >= 0;
        }
    }

    private static String chooseIncludedTopping(
            Scanner scanner,
            String heading) {

        while (true) {
            System.out.println("\n" + heading);
            printDealToppingMenu();
            System.out.println("0. Go Back");

            int choice = getInt(scanner, "Enter choice: ");
            if (choice == 0) {
                return null;
            }

            String topping = getTopping(choice);

            if (topping != null) {
                System.out.println(
                        topping + " selected as your included topping.");

                return topping;
            }

            System.out.println(
                    "Please choose a topping from 1-17, or 0 to go back.");
        }
    }

    private static List<String> chooseExtraToppings(
            Scanner scanner,
            int[] step,
            List<String> extraToppings) {

        while (true) {
        if (step[0] == 0) {
        System.out.println("\nWould you like additional toppings?");
        System.out.println("1. Yes - $0.50 each");
        System.out.println("2. No");
        System.out.println("0. Go Back");

        int choice;

        while (true) {
            choice = getInt(scanner, "Enter choice: ");

            if (choice >= 0 && choice <= 2) {
                break;
            }

            System.out.println("Please choose 0, 1 or 2.");
        }

        if (choice == 0) {
            return null;
        }

        if (choice == 2) {
            extraToppings.clear();
            return extraToppings;
        }
        step[0] = 1;
        }

        boolean adding = true;

        while (adding) {
            System.out.println("\nADDITIONAL TOPPINGS");
            System.out.println("Each additional topping is $0.50.");

            printToppingMenu();

            int toppingChoice =
                    getInt(scanner, "Enter choice: ");

            if (toppingChoice == 0) {
                break;
            } else if (toppingChoice == 18) {
                return extraToppings;
            } else {
                String topping = getTopping(toppingChoice);

                if (topping != null) {
                    extraToppings.add(topping);
                    System.out.println(topping + " added! (+$0.50)");
                } else {
                    System.out.println("Invalid topping choice.");
                }
            }
        }

        // Back from the topping list returns to the Yes/No choice.
        step[0] = 0;
        }
    }

    private static String formatExtraToppings(
            List<String> extras) {

        if (extras.isEmpty()) {
            return "";
        }

        return ", " + String.join(", ", extras);
    }

    private static String chooseDealDrink(
            Scanner scanner) {

        printDealDrinkMenu();

        while (true) {
            int choice = getInt(scanner, "Enter choice: ");
            if (choice == 0) {
                return null;
            }

            String drink = getDrinkName(choice);

            if (drink != null) {
                return drink;
            }

            System.out.println(
                    "Please choose a number from 0-7.");
        }
    }

    private static String chooseCheeseOrPepperoni(
            Scanner scanner) {

        System.out.println("1. Cheese");
        System.out.println("2. Pepperoni");
        System.out.println("0. Go Back");
        while (true) {
            int choice = getInt(scanner, "Enter choice: ");

            if (choice == 0) {
                return null;
            }

            if (choice == 1) {
                return "Cheese";
            }

            if (choice == 2) {
                return "Pepperoni";
            }

            System.out.println("Please choose 0, 1 or 2.");
        }
    }

    private static void printDrinkMenu() {
        System.out.println("\n1. Coke - 20 oz - $2.49");
        System.out.println("2. Diet Coke - 20 oz - $2.49");
        System.out.println("3. Sprite - 20 oz - $2.49");
        System.out.println("4. Dr Pepper - 20 oz - $2.49");
        System.out.println("5. Lemonade - $2.49");
        System.out.println("6. Iced Tea - $2.49");
        System.out.println("7. Bottled Water - $1.99");
        System.out.println("0. Go Back");
    }

    private static void printDealDrinkMenu() {
        System.out.println("\n1. Coke - 20 oz");
        System.out.println("2. Diet Coke - 20 oz");
        System.out.println("3. Sprite - 20 oz");
        System.out.println("4. Dr Pepper - 20 oz");
        System.out.println("5. Lemonade");
        System.out.println("6. Iced Tea");
        System.out.println("7. Bottled Water");
        System.out.println("0. Go Back");
    }

    private static MenuItem getDrinkItem(int choice) {
        switch (choice) {
            case 1:
                return new MenuItem("Coke - 20 oz", 2.49);
            case 2:
                return new MenuItem("Diet Coke - 20 oz", 2.49);
            case 3:
                return new MenuItem("Sprite - 20 oz", 2.49);
            case 4:
                return new MenuItem("Dr Pepper - 20 oz", 2.49);
            case 5:
                return new MenuItem("Lemonade", 2.49);
            case 6:
                return new MenuItem("Iced Tea", 2.49);
            case 7:
                return new MenuItem("Bottled Water", 1.99);
            default:
                return null;
        }
    }

    private static String getDrinkName(int choice) {
        switch (choice) {
            case 1:
                return "Coke - 20 oz";
            case 2:
                return "Diet Coke - 20 oz";
            case 3:
                return "Sprite - 20 oz";
            case 4:
                return "Dr Pepper - 20 oz";
            case 5:
                return "Lemonade";
            case 6:
                return "Iced Tea";
            case 7:
                return "Bottled Water";
            default:
                return null;
        }
    }

    private static void printDealToppingMenu() {
        System.out.println("\nMEATS");
        System.out.println("\n1. Pepperoni");
        System.out.println("2. Sausage");
        System.out.println("3. Bacon");
        System.out.println("4. Ham");
        System.out.println("5. Grilled Chicken");
        System.out.println("6. Beef");
        System.out.println("7. Meatballs");

        System.out.println("\nVEGETABLES");
        System.out.println("\n8. Mushrooms");
        System.out.println("9. Onions");
        System.out.println("10. Green Peppers");
        System.out.println("11. Black Olives");
        System.out.println("12. Banana Peppers");
        System.out.println("13. Jalapeno Peppers");
        System.out.println("14. Tomatoes");
        System.out.println("15. Spinach");
        System.out.println("16. Garlic");

        System.out.println("\nOTHER");
        System.out.println("\n17. Pineapple");
    }

    private static void printToppingMenu() {
        printDealToppingMenu();
        System.out.println("\n18. Finished");
        System.out.println("0. Go Back");
    }

    private static String getTopping(int choice) {
        switch (choice) {
            case 1:
                return "Pepperoni";
            case 2:
                return "Sausage";
            case 3:
                return "Bacon";
            case 4:
                return "Ham";
            case 5:
                return "Grilled Chicken";
            case 6:
                return "Beef";
            case 7:
                return "Meatballs";
            case 8:
                return "Mushrooms";
            case 9:
                return "Onions";
            case 10:
                return "Green Peppers";
            case 11:
                return "Black Olives";
            case 12:
                return "Banana Peppers";
            case 13:
                return "Jalapeno Peppers";
            case 14:
                return "Tomatoes";
            case 15:
                return "Spinach";
            case 16:
                return "Garlic";
            case 17:
                return "Pineapple";
            default:
                return null;
        }
    }

    private static boolean advanceStep(int[] step, boolean selected) {
        step[0] += selected ? 1 : -1;
        return selected;
    }

    private static int getQuantity(Scanner scanner) {
        System.out.println("0. Go Back");
        while (true) {
            int quantity = getInt(scanner, "Quantity: ");

            if (quantity >= 0) {
                return quantity;
            }

            System.out.println("Quantity must be at least 1, or 0 to go back.");
        }
    }

    private static boolean viewCart(
            Scanner scanner,
            Cart cart) {

        while (true) {
            cart.displayCart();

            if (cart.isEmpty()) {
                return false;
            }

            System.out.println();
            System.out.println("1. Remove Item");
            System.out.println("2. Continue Shopping");
            System.out.println("3. Checkout");

            int choice = getInt(scanner, "Enter choice: ");

            if (choice == 1) {
                int itemNumber =
                        getInt(
                                scanner,
                                "Enter item number to remove: ");

                cart.removeItem(itemNumber - 1);

            } else if (choice == 2) {
                return false;

            } else if (choice == 3) {
                return true;

            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private static boolean checkout(
            Scanner scanner,
            Cart cart,
            Customer customer,
            boolean rewardsMember) {

        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return false;
        }

        System.out.println("\nORDER SUMMARY");

        if (rewardsMember) {
            System.out.println("\nCustomer: " + customer.getUsername());
        } else {
            System.out.println("\nCustomer: Guest");
        }

        cart.displayCart();

        double originalPrice = cart.getSubtotal();
        double price = originalPrice;

        boolean discountSelected = false;

        while (!discountSelected) {
            System.out.println("\nDISCOUNTS");
            System.out.println("\n1. No Discount");
            System.out.println("2. Student Discount - 5%");

            int discountChoice;

            while (true) {
                discountChoice = getInt(scanner, "Enter choice: ");

                if (discountChoice == 1 || discountChoice == 2) {
                    break;
                }

                System.out.println("Please choose 1 or 2.");
            }

            if (discountChoice == 1) {
                discountSelected = true;
            } else if (customer == null) {
                System.out.println("\nSTUDENT VERIFICATION REQUIRED");
                System.out.println("You must login or create an account to use the student discount.");
                System.out.println("The student discount was not applied.");
                discountSelected = true;
            } else if (customer.isStudentVerified()) {
                double studentDiscount = price * 0.05;
                price -= studentDiscount;

                System.out.printf(
                        "%nStudent Discount: -$%.2f%n",
                        studentDiscount);

                discountSelected = true;
            } else {
                boolean verificationMenu = true;

                while (verificationMenu) {
                    System.out.println("\nSTUDENT VERIFICATION REQUIRED");
                    System.out.println("\nA verified .edu email is required for the 5% Student Discount.");
                    System.out.println("\n1. Verify Student Email");
                    System.out.println("0. Go Back");

                    int verificationChoice = getInt(scanner, "Enter choice: ");

                    if (verificationChoice == 0) {
                        verificationMenu = false;
                    } else if (verificationChoice == 1) {
                        if (StudentVerification.verifyStudent(scanner, customer)) {
                            double studentDiscount = price * 0.05;
                            price -= studentDiscount;

                            System.out.printf(
                                    "%nStudent Discount: -$%.2f%n",
                                    studentDiscount);

                            discountSelected = true;
                            verificationMenu = false;
                        } else {
                            System.out.println("\nThe student discount was not applied.");
                        }
                    } else {
                        System.out.println("Please choose 0 or 1.");
                    }
                }
            }
        }

        System.out.printf(
                "Subtotal: $%.2f%n",
                originalPrice);

        System.out.printf(
                "After Discount: $%.2f%n",
                price);

        if (rewardsMember) {
            System.out.println("\nREWARDS");
            System.out.println(
                    "\nCurrent Points: "
                            + customer.getPoints());

            System.out.println(
                    "100 points = $1.00 off");

            int maximumReward =
                    customer.getPoints() / 100;

            int maximumOrderReward =
                    (int) Math.floor(price);

            if (maximumReward > maximumOrderReward) {
                maximumReward = maximumOrderReward;
            }

            if (maximumReward > 0) {
                System.out.println(
                        "Maximum Reward Available: $"
                                + maximumReward);

                int rewardDollars =
                        getInt(
                                scanner,
                                "Reward dollars to use: $");

                if (rewardDollars < 0) {
                    rewardDollars = 0;
                }

                if (rewardDollars > maximumReward) {
                    System.out.println(
                            "You cannot redeem that amount.");

                    System.out.println(
                            "No reward points were used.");

                } else if (rewardDollars > 0) {
                    int pointsNeeded =
                            rewardDollars * 100;

                    if (customer.usePoints(pointsNeeded)) {
                        price -= rewardDollars;
                        CustomerDAO.updatePoints(customer);

                        System.out.println(
                                pointsNeeded
                                        + " points redeemed.");

                        System.out.printf(
                                "Rewards Discount: -$%.2f%n",
                                (double) rewardDollars);

                        System.out.println(
                                "Remaining Points: "
                                        + customer.getPoints());
                    }
                }

            } else {
                System.out.println(
                        "You do not currently have enough points to redeem.");
            }
        }

        if (price < 0) {
            price = 0;
        }

        System.out.println("\nCHECKOUT");
        System.out.printf("\nFinal Total: $%.2f%n", price);

        System.out.println("\nPAYMENT METHOD");
        System.out.println("\n1. Credit Card");
        System.out.println("2. Cash");
        System.out.println("3. PayPal");
        System.out.println("0. Return to Menu");

        int paymentChoice =
                getInt(scanner, "Enter choice: ");

        if (paymentChoice == 0) {
            System.out.println("\nReturning to menu...");
            return false;
        }

        IPayment payment;

        if (paymentChoice == 1) {
            payment = new CreditCardPayment();
        } else if (paymentChoice == 2) {
            payment = new CashPayment();
        } else if (paymentChoice == 3) {
            payment = new PayPalPayment();
        } else {
            System.out.println("Invalid payment method.");
            return false;
        }

        boolean successful =
                payment.processPayment(price);

        if (successful) {
            System.out.println("\nPAYMENT SUCCESSFUL!");

            if (rewardsMember) {
                int pointsEarned =
                        (int) Math.floor(price) * 10;

                customer.addPoints(pointsEarned);
                CustomerDAO.updatePoints(customer);

                System.out.println(
                        "\nPoints Earned: "
                                + pointsEarned);

                System.out.println(
                        "New Points Balance: "
                                + customer.getPoints());
            }

            cart.clear();
            System.out.println("\nThank you for your order!");
            return true;
        }

        System.out.println("\nPayment failed.");
        return false;
    }

    private static int getInt(
            Scanner scanner,
            String message) {

        while (true) {
            System.out.print(message);
            String input = scanner.nextLine();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Please enter a valid number.");
            }
        }
    }
}