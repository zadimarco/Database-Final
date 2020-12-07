package handlers;

public class Command {
    private final String name;
    private final String description;
    private Runnable query;

    public Command(String name, String description, Runnable query) {
        this.name = name;
        this.description = description;
        this.query = query;
    }

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public boolean setQuery(Runnable query){
        if (this.query == null) {
            this.query = query;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Runs this command. This will output whatever the command is supposed to.
     */
    public void runQuery() {
        this.query.run();
    }

    @Override
    public String toString(){
        return name + " : " + description;
    }
}
