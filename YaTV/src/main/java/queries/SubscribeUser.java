package queries;

import utilities.GeneralHelpers;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Query that subscribes a user to an app
 */
public class SubscribeUser extends YaTVQuery{

    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("email", Arrays.asList(1));
        map.put("app", Arrays.asList(2));
        map.put("cost", Arrays.asList(3));
        map.put("expirationDate", Arrays.asList(4));
        return map;
    }

    private static final String sql =
            "INSERT INTO subscription\n" +
                    "VALUES\n" +
                    "((SELECT y.id FROM yauser y WHERE y.email = ?), \n" +
                    "?, ?, ?);";

    public SubscribeUser() {
        super(sql, createParamList());
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        System.out.println("User successfully subscribed");
    }

    @Override
    protected void calcParams(Connection connection) throws SQLException {}

    @Override
    protected void getInput() {
        String email = GeneralHelpers.getStringInput("Please enter a user's email");
        String app = GeneralHelpers.getStringInput("Please enter the app name");
        String cost = GeneralHelpers.getStringInput("How much does the subscription cost", "No $ and include cents",
                GeneralHelpers.FORMAT_COST);
        String expirationDate = GeneralHelpers.getStringInput("When does the subscription expire", "YYYY-MM-DD", "....-..-..");


        params.put("email", email);
        params.put("app", app);
        params.put("cost", cost);
        params.put("expirationDate", expirationDate);

    }

    @Override
    protected ResultSet execute(PreparedStatement stmt) throws SQLException {
        stmt.executeUpdate();
        return null;
    }

}
