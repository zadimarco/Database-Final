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
 * Adds a show to a user's list
 */
public class AddShowToList extends YaTVQuery{

    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("email", Arrays.asList(1));
        map.put("showID", Arrays.asList(2));
        return map;
    }

    private static final String sql =
            "INSERT INTO userlistshows\n" +
                    "VALUES\n" +
                    "\t((SELECT u.id FROM yauser u WHERE u.email = ?), ?);";

    private String showName;


    public AddShowToList() {
        super(sql, createParamList());
    }


    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        System.out.println("Show successfully added to list");
    }

    @Override
    protected void calcParams(Connection connection) throws SQLException {
        final String getShowId = "SELECT s.id, s.title, s.description\n" +
                "FROM yashow s\n" +
                "WHERE s.title = ?;";

        if (params.getOrDefault("showID", null) == null) {
            System.out.println("Which of the following is the requested show?");

            Map<Integer, String[]> possibleShows = new HashMap<>();
            try (final PreparedStatement stmt = connection.prepareStatement(getShowId)) {
                stmt.setString(1, showName);
                int idx = 1;
                try (final ResultSet res = stmt.executeQuery()) {
                    while (res.next()) {
                        possibleShows.put(idx, new String[]{res.getString("id"), res.getString("title")});
                        System.out.printf("%d. %s\n\tdescription:", idx, res.getString("title"));
                        GeneralHelpers.cleanOutput(res.getString("description"), "\n\t", GeneralHelpers.MAXLINE);
                        idx++;
                        System.out.println();

                    }
                }

            }
            if (possibleShows.size() == 0){
                System.out.println("No Shows matched the requested title");
                showName = GeneralHelpers.getStringInput("Input the Show's name");
                calcParams(connection);
                return;
            }
            int correctOne = GeneralHelpers.getIntInput("Input one of the above choices");
            params.put("showID", possibleShows.get(correctOne)[0]);
        }
    }

    @Override
    protected void getInput() {
        String email = GeneralHelpers.getStringInput("What user would like to add a show to their list", "email", GeneralHelpers.FORMAT_EMAIL );

        boolean knowsId = GeneralHelpers.getBoolInput("Do you know the ID of the show?");
        if (knowsId){
            int showId = GeneralHelpers.getIntInput("Input the show's ID");
            params.put("showID", Integer.toString(showId));
        } else {
            showName = GeneralHelpers.getStringInput("Input the Show's name");
        }

    }

    @Override
    protected ResultSet execute(PreparedStatement stmt) throws SQLException {
        return null;
    }
}
