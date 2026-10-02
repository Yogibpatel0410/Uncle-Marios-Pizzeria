public class Customer {
    private int id;
    private String username;
    private int points;
    private String studentEmail;
    private boolean studentVerified;

    public Customer(int id, String username, int points) {
        this(id, username, points, null, false);
    }

    public Customer(int id, String username, int points,
                    String studentEmail, boolean studentVerified) {
        this.id = id;
        this.username = username;
        this.points = points;
        this.studentEmail = studentEmail;
        this.studentVerified = studentVerified;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public boolean isStudentVerified() {
        return studentVerified;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public void setStudentVerified(boolean studentVerified) {
        this.studentVerified = studentVerified;
    }

    public void addPoints(int points) {
        if (points > 0) {
            this.points += points;
        }
    }

    public boolean usePoints(int points) {
        if (points <= 0 || points > this.points) {
            return false;
        }

        this.points -= points;
        return true;
    }
}