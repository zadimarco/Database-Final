package queries;

import utilities.GeneralHelpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Query that updates the app version on a specific platform
 */
public class UpdateAppVersion extends YaTVQuery {

    private static final String sql =
            "UPDATE appplatform\n" +
                    "SET \n" +
                    "\tversion = ?\n" +
                    "WHERE\n" +
                    "\tappName LIKE ? AND\n" +
                    "    platformName LIKE ?;";

    public UpdateAppVersion() {
        super(sql, createParamList());
    }

    private static Map<String, List<Integer>> createParamList() {
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("newVersion", Arrays.asList(1));
        map.put("app", Arrays.asList(2));
        map.put("platform", Arrays.asList(3));
        return map;
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        System.out.println("Version successfully updated");
    }

    @Override
    protected void calcParams(Connection connection) throws SQLException {

    }

    @Override
    protected void getInput() {
        String newVersion = GeneralHelpers.getStringInput("Please input the new App version", "#.#.#", "[0-9]*?\\.[0-9]*?\\.[0-9]*?");
        String app = GeneralHelpers.getStringInput("What is the app are you updating");
        String platform = GeneralHelpers.getStringInput("What platform are you updating the app on");

        params.put("newVersion", newVersion);
        params.put("app", app);
        params.put("platform", platform);


    }

    @Override
    protected ResultSet execute(PreparedStatement stmt) throws SQLException {
        stmt.executeUpdate();
        return null;
    }

}
