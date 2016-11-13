
package todoapp.model;

public class TodoItem {
    private String name;
    private String description;
    private boolean done;
    private int estimate;
    private int id;
   
    public TodoItem(String name, String description, boolean done, int estimate) {
        this.name = name;
        this.description = description;
        this.done = done;
        this.estimate = estimate;
    }
        
    public TodoItem(String name, String description, boolean done, int estimate, int id) {
        this.name = name;
        this.description = description;
        this.done = done;
        this.estimate = estimate;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public int getEstimate() {
        return estimate;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDone() {
        return done;
    }

    @Override
    public String toString() {
        return name+ " id: "+id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
}
