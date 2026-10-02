# Uncle Mario's Pizzeria 🍕

Uncle Mario's Pizzeria is a Java-based pizza ordering system that simulates the ordering experience of a pizzeria. Customers can create accounts, customize pizzas and calzones, order specialty items and deals, manage a shopping cart, earn rewards, verify student status, and complete checkout using multiple payment methods.

## Features

### Customer Accounts
- Create a customer account
- Secure login system
- Password hashing and salting
- Customer information stored using SQLite
- Continue ordering as a guest

### Pizza Ordering
- Choose from 13 specialty pizzas
- Select pizza size
- Build a custom pizza
- Choose crust, sauce, cheese, and toppings
- Multiple quantities supported

### Build Your Own Calzone
- Choose cheese options
- Add custom stuffings
- Automatic price calculation

### Menu
Customers can order:
- Specialty pizzas
- Custom pizzas
- Custom calzones
- Sides
- Desserts
- Drinks
- Special deals

### Special Deals
Includes:
- Mario's Combo
- Family Night
- Student Special
- Two Large Pizzas Deal

Deal customization supports included toppings, additional toppings, and drink selections.

### Shopping Cart
- Add multiple menu items
- Select quantities
- View cart
- Remove items
- Review subtotal
- Continue shopping
- Review cart before checkout

### Rewards Program
Registered customers can:
- Earn 10 points for every $1 spent
- Redeem 100 points for $1 off
- View their current points balance
- Apply rewards during checkout

### Student Verification
- Supports `.edu` student email verification
- Generates a random 6-digit verification code
- Verification codes expire after 10 minutes
- Limits verification attempts
- Verified status is saved to the customer account
- Verified students can receive a 5% student discount
- Verified students can access the Student Special

Email verification is implemented using the Resend API. A verified sender domain is required to send verification emails to arbitrary `.edu` addresses.

### Checkout
Customers can:
- Review their order before checkout
- Apply eligible discounts
- Redeem reward points
- Return to the menu before payment
- Pay using Credit Card, Cash, or PayPal

## Technologies Used

- Java
- SQLite
- JDBC
- Object-Oriented Programming
- Java HTTP Client
- Resend Email API
- Git
- GitHub

## Project Structure

The project separates responsibilities across multiple Java classes and interfaces, including:

- `UncleMario.java` - Main application and ordering flow
- `Cart.java` / `CartItem.java` - Shopping cart management
- `Customer.java` / `CustomerDAO.java` - Customer accounts and database operations
- `DatabaseManager.java` - SQLite database connection and setup
- `CustomPizza2.java` - Custom pizza creation and pricing
- `CustomCalzone.java` - Custom calzone creation and pricing
- `SpecialtyPizza.java` - Specialty pizza ordering
- `StudentVerification.java` - Student verification process
- `EmailService.java` - Verification email integration
- `PasswordUtils.java` - Password security
- Payment classes - Credit Card, Cash, and PayPal payment handling

## Running the Project

### Requirements

- Java JDK
- SQLite JDBC driver
- Resend API key for email verification

### Compile

From the project directory:

```bash
javac -cp ".:lib/*" *.java
```

### Run

```bash
java -cp ".:lib/*" UncleMario
```

For student verification, set the Resend API key as an environment variable before running the application:

```bash
export RESEND_API_KEY="your_api_key"
```

Never store API keys directly in the source code or commit them to GitHub.

## Future Development

Planned improvements include:

- Responsive web interface
- Java backend API
- Online ordering interface
- Visual pizza customization
- Improved account dashboard
- Order history
- Enhanced rewards dashboard
- Mobile-friendly design

## Author

**Yogesh Patel**

Computer Science  
Georgia State University