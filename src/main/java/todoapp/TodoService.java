
package todoapp;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import todoapp.data_access.*;
import todoapp.model.TodoItem;
import todoapp.model.User;

public class TodoService {
    private ArrayList<TodoItem> items;
    private String token;
    private ITodoApi api;

    public TodoService(TodoApi api) {
        items = new ArrayList<>();
        this.api = api;
    }
    
    public void addItem(TodoItem item){
        items.add(item);
        api.todos(token).create(item);
    }

    public List<TodoItem> items() {
        if ( token==null ) {
            throw new IllegalStateException("no logged in user");
        }
        
        if (items.isEmpty()) {
            System.out.println("itemit!");
            for (TodoItem item : api.todos(token).getAll()) {
                items.add(item);
            }
        }
        
        return items;
    }

    public List<TodoItem> undoneItems() {
        return items().stream().filter(i->!i.isDone()).collect(Collectors.toList());
    }

    public void markDone(TodoItem item) {
        item.setDone(true);
        api.todos(token).markDone(item);
    }

    public void removeItem(TodoItem item) {
        items.remove(item);
    }
    
    public boolean login(String username, String password) {
        try{
            User user = api.users().login(username, password);
            token = user.getToken();
            return true;
        } catch(IllegalStateException e) {
            return false;
        }
        
    }
}
