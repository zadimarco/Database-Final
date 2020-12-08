package handlers;

import queries.Top10Shows;
import queries.YaTVQuery;

/**
 * Runner for the app
 */
public class Runner {

    public static void main(String[] args) {
        QueryHandler queryHandler = new QueryHandler();
        boolean exited = false;
        do {
            exited = queryHandler.nextCommand();

            System.out.println();
        } while (!exited);
    }
}
