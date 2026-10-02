import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class EmailService {
    private static final String API_URL = "https://api.resend.com/emails";

    public static boolean sendVerificationCode(String email, String code) {
        String apiKey = System.getenv("RESEND_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            System.out.println("RESEND_API_KEY was not found.");
            return false;
        }

        String body = """
                {
                    "from": "Uncle Mario's Pizzeria <onboarding@resend.dev>",
                    "to": ["%s"],
                    "subject": "Your Uncle Mario's Student Verification Code",
                    "html": "<h2>Uncle Mario's Pizzeria</h2><p>Your student verification code is:</p><h1>%s</h1><p>This code expires in 10 minutes.</p>"
                }
                """.formatted(escapeJson(email), escapeJson(code));

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Authorization", "Bearer " + apiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                return true;
            }

            System.out.println("Email could not be sent.");
            System.out.println("Resend response: " + response.body());
            return false;

        } catch (Exception e) {
            System.out.println("Email error: " + e.getMessage());
            return false;
        }
    }

    private static String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}