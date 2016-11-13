
package todoapp.data_access;

import java.util.List;
import todoapp.model.TodoItem;

public interface ITodoItemAccess {

    void create(TodoItem item);

    void delete(TodoItem item);

    List<TodoItem> getAll();

    TodoItem markDone(TodoItem item);
    
}
