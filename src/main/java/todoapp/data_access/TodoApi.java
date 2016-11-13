
package todoapp.data_access;

public class TodoApi implements ITodoApi {
    private final String URL;

    public TodoApi(String URL) {
        this.URL = URL;
    }

    @Override
    public ITodoItemAccess todos(String token) {
        return new TodoItemAccess(token, URL);
    }

    @Override
    public IUserAccess users() {
        return new UserAccess(URL);
    }
     
}
