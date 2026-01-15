public abstract class User {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() { return username; }

    public boolean checkPassword(String input) {
        return password != null && password.equals(input);
    }
}