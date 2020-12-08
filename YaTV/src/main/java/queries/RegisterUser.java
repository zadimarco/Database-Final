package queries;

import utilities.GeneralHelpers;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Query that registers a user in the database
 */
public class RegisterUser extends YaTVQuery{

    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("email", Arrays.asList(1));
        map.put("firstName", Arrays.asList(2));
        map.put("lastName", Arrays.asList(3));
        map.put("country", Arrays.asList(4));
        map.put("passw", Arrays.asList(5));
        map.put("salt", Arrays.asList(6));
        return map;
    }

    private static final String sql =
            "INSERT INTO yauser\n" +
            "(email, firstName, lastName, country, passw, salt)\n" +
            "VALUES\n" +
            "(?, ?, ?, ?, ?, ?);";

    public RegisterUser() {
        super(sql, createParamList());
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        System.out.println("User successfully registered");
    }

    @Override
    protected void calcParams(Connection connection) throws SQLException {}

    @Override
    protected void getInput() {
        String firstName = GeneralHelpers.getStringInput("What is the new user's first name");
        String lastName = GeneralHelpers.getStringInput("What is the new user's last name");
        String country = GeneralHelpers.getStringInput("What is the new user's country");

        String email = GeneralHelpers.getStringInput("What is the new user's email", GeneralHelpers.FORMAT_EMAIL);
        String passw = GeneralHelpers.getStringInput("What is the new user's password");


        params.put("email", email);
        params.put("country", country);
        params.put("firstName", firstName);
        params.put("lastName", lastName);
        params.put("passw", passw);

    }

    @Override
    protected void loadParams(PreparedStatement stmt) throws SQLException{
        byte[] salt = GeneralHelpers.getSalt();
        params.put("salt", Base64.getEncoder().encodeToString(salt));
        params.put("passw", GeneralHelpers.saltAndHash(params.get("passw"), salt));
        super.loadParams(stmt);
    }

    @Override
    protected ResultSet execute(PreparedStatement stmt) throws SQLException {
        stmt.executeUpdate();
        return null;
    }
}
