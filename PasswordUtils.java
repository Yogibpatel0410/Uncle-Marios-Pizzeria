import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtils {

    private static final int ITERATIONS = 65536;
    private static final int KEY_LENGTH = 256;

    // Creates a random salt
    public static String generateSalt() {

        SecureRandom random =
                new SecureRandom();

        byte[] salt =
                new byte[16];

        random.nextBytes(salt);

        return Base64.getEncoder()
                .encodeToString(salt);
    }

    // Hashes the password
    public static String hashPassword(
            String password,
            String salt) {

        try {

            byte[] saltBytes =
                    Base64.getDecoder()
                            .decode(salt);

            PBEKeySpec spec =
                    new PBEKeySpec(
                            password.toCharArray(),
                            saltBytes,
                            ITERATIONS,
                            KEY_LENGTH
                    );

            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance(
                            "PBKDF2WithHmacSHA256");

            byte[] hash =
                    factory.generateSecret(spec)
                            .getEncoded();

            return Base64.getEncoder()
                    .encodeToString(hash);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Could not hash password.",
                    e);
        }
    }

    // Checks if entered password matches saved password
    public static boolean verifyPassword(
            String password,
            String storedHash,
            String storedSalt) {

        String newHash =
                hashPassword(
                        password,
                        storedSalt);

        return newHash.equals(storedHash);
    }
}