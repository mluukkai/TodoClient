
package todoapp.model;

public class User {
    private String username;
    private String name;
    private String token;

    public User(String username, String name, String token) {
        this.username = username;
        this.name = name;
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return token;
    }
    
}
