
package todoapp;

import todoapp.model.*;
import todoapp.data_access.*;
import java.util.List;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        TodoService todoService = new TodoService(new TodoApi("https://otm-todo-api.herokuapp.com"));
        Ui ui = new Ui();
        ui.setTodo(todoService);
        SwingUtilities.invokeLater(ui);        
        //todoa();
    }

    private static void todoa() {
        String url = "https://otm-todo-api.herokuapp.com/";
        IUserAccess userAccessObject = new UserAccess(url);
        //System.out.println(userAccessObject.signUp("mluukkai", "Matti Luukkainen", "mluukkai"));
        
        User user = userAccessObject.login("mluukkai", "mluukkai");
        
        System.out.println(user.getToken());
        
        ITodoItemAccess itemAccessObject = new TodoItemAccess(user.getToken(), url);
        
        List<TodoItem> items = itemAccessObject.getAll();
        for (TodoItem item : items) {
            System.out.println(item);
        }
     
        System.out.println("--");
        
        //userAccessObject.signUp("pekka", "Pekka Mikkola", "pekka");
        
        
        //System.out.println(user);
        //System.out.println(itemAccessObject.markDone(undoneItems.get(1)));
        
        //itemAccessObject.delete(undoneItems.get(undoneItems.size()-1));
        
        //TodoItem undoneItems = new TodoItem("wash dishes", "with machine but easily breakable undoneItems by hand", false, 2);
    
        //itemAccessObject.create(undoneItems);
    }
}
