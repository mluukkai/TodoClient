
package todoapp.data_access;

import todoapp.model.User;

public interface IUserAccess {

    User login(String username, String password);

    User signUp(String username, String name, String password);
    
}
