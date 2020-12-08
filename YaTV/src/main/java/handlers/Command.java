package handlers;


/**
 * Represents the Individual commands that a user can input
 */
public class Command {
    private final String name;
    private final String description;
    private Runnable query;

    /**
     * Create a user command
     * @param name The name of the command
     * @param description A description of what the command does
     * @param query The query that this command represents
     */
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
