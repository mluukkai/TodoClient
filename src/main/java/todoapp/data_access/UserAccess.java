
package todoapp.data_access;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import todoapp.model.User;

public class UserAccess implements IUserAccess {

    public UserAccess() {
        this("http://localhost:3000");
    }

    public UserAccess(String url) {
        this.URL = url;
    }    
    
    class UsernamePassword {
        String username;
        String password;

        public UsernamePassword(String username, String password) {
            this.username = username;
            this.password = password;
        }  
    }
    
    class NewUser {
        String username;
        String name;
        String password;        
        String password_confirmation;   

        public NewUser(String username, String name, String password) {
            this.username = username;
            this.name = name;
            this.password = password;
            this.password_confirmation = password;
        }      
    }
    
    private final String URL;
    
    @Override
    public User signUp(String username, String name, String password ) {
        HttpResponse<JsonNode> response = null;
              
        try{
            response = Unirest.post(URL+"/users/create")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(gson().toJson(new NewUser(username, name, password)))
                .asJson();
        } catch(Exception e){
            throw new IllegalStateException();
        }        
        
        if (response.getStatus()==401) {
            throw new IllegalStateException("non authorized");
        }          
        
        if (response.getStatus()==422) {
            throw new IllegalStateException(response.getBody().toString());
        }
        
        return gson().fromJson(response.getBody().toString(), User.class);
    }
    
    @Override
    public User login(String username, String password ) {
        HttpResponse<JsonNode> response = null;
              
        try{
            response = Unirest.post(URL+"/users/login")
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .body(gson().toJson(new UsernamePassword(username, password)))
                .asJson();
        } catch(Exception e){
            throw new IllegalStateException();
        }        
        
        if (response.getStatus()==401) {
            throw new IllegalStateException("non authorized");
        }          
        return gson().fromJson(response.getBody().toString(), User.class);
    }

    private static Gson gson() {
        return new Gson();
    }
}
