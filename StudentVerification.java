import java.security.SecureRandom;
import java.util.Scanner;

public class StudentVerification {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final long EXPIRATION_TIME = 10 * 60 * 1000;

    public static boolean verifyStudent(
            Scanner scanner,
            Customer customer) {

        if (customer == null) {
            System.out.println("\nYou must login or create an account");
            System.out.println("to use student benefits.");
            return false;
        }

        if (customer.isStudentVerified()) {
            return true;
        }

        System.out.println("\nSTUDENT VERIFICATION");
        System.out.print("\nEnter your .edu email: ");
        String email = scanner.nextLine().trim().toLowerCase();

        if (!isEduEmail(email)) {
            System.out.println("\nA valid .edu email is required.");
            return false;
        }

        String code = generateCode();
        long expiration = System.currentTimeMillis() + EXPIRATION_TIME;

        System.out.println("\nSending verification code...");

        if (!EmailService.sendVerificationCode(email, code)) {
            System.out.println("Verification email could not be sent.");
            return false;
        }

        System.out.println("Verification code sent!");
        System.out.println("The code expires in 10 minutes.");

        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("\nEnter 6-digit verification code: ");
            String enteredCode = scanner.nextLine().trim();

            if (System.currentTimeMillis() > expiration) {
                System.out.println("\nVerification code expired.");
                return false;
            }

            if (code.equals(enteredCode)) {
                if (CustomerDAO.verifyStudent(customer, email)) {
                    System.out.println("\nStudent verification successful!");
                    return true;
                }

                System.out.println("\nCould not save student verification.");
                return false;
            }

            int attemptsLeft = 3 - attempt;

            if (attemptsLeft > 0) {
                System.out.println(
                        "Incorrect code. "
                                + attemptsLeft
                                + " attempt(s) remaining.");
            }
        }

        System.out.println("\nStudent verification failed.");
        return false;
    }

    private static boolean isEduEmail(String email) {
        return email.matches(
                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.edu$");
    }

    private static String generateCode() {
        return String.format("%06d", RANDOM.nextInt(1000000));
    }
}