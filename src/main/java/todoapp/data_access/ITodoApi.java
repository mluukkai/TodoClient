
package todoapp.data_access;

public interface ITodoApi {
    ITodoItemAccess todos(String token);
    IUserAccess users();
}
