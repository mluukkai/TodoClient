
package todoapp.data_access;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import java.util.ArrayList;
import java.util.List;
import todoapp.model.TodoItem;

public class TodoItemAccess implements ITodoItemAccess {
    private String token;
    private final String URL;

    public TodoItemAccess(String token, String url) {
        this.token = token;
        this.URL = url;
    }    
    
    public TodoItemAccess(String token) {
        this(token, "http://localhost:3000");
    }
    
    @Override
    public TodoItem markDone(TodoItem item) {
        HttpResponse<JsonNode> response = null;
        
        try{
            response = Unirest.patch(URL+"/todo_items/"+item.getId())
                .header("accept", "application/json")
                .header("Authorization", token)
                .asJson();
        } catch(Exception e){
            throw new IllegalStateException();
        }   
        
        if (response.getStatus()==401) {
            throw new IllegalStateException("non authorized");
        }  

        return gson().fromJson(response.getBody().toString(), TodoItem.class);
    }
   
    @Override
    public void delete(TodoItem item) {
        HttpResponse<JsonNode> response = null;
        
        try{
            response = Unirest.delete(URL+"/todo_items/"+item.getId())
                .header("accept", "application/json")
                .header("Authorization", token)
                .asJson();
        } catch(Exception e){
            throw new IllegalStateException();
        }   
        
        if (response.getStatus()==401) {
            throw new IllegalStateException("non authorized");
        }           
    }
    
    @Override
    public void create(TodoItem item) {
        HttpResponse<JsonNode> response = null;
        
        String json = gson().toJson(item, TodoItem.class);
        System.out.println(json);
        
        try{
            response = Unirest.post(URL+"/todo_items")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body(json)
                .asJson();
        } catch(Exception e){
            throw new IllegalStateException();
        }        
        
        if (response.getStatus()==401) {
            throw new IllegalStateException("non authorized");
        }         
    }
    
    @Override
    public List<TodoItem> getAll() {
        HttpResponse<JsonNode> response = null;
        
        try{
            response = Unirest.get(URL+"/todo_items")
                .header("accept", "application/json")
                .header("Authorization", token)
                .asJson();
        } catch(Exception e){
            throw new IllegalStateException();
        }
        
        if (response.getStatus()==401) {
            throw new IllegalStateException("non authorized");
        }        
        
        TodoItem[] items = gson().fromJson(response.getBody().toString(), TodoItem[].class);

        ArrayList<TodoItem> list = new ArrayList<>();
        for (TodoItem item : items) {
            list.add(item);
        }
        return list;
    }

    private static Gson gson() {
        return new Gson();
    }
    
}
