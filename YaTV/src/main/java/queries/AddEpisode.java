package queries;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import utilities.GeneralHelpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


/**
 * Query that adds an episode to a show
 */
public class AddEpisode extends YaTVQuery{

    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("title", Arrays.asList(1));
        map.put("description", Arrays.asList(2));
        map.put("appHost", Arrays.asList(3));
        map.put("releaseDate", Arrays.asList(4));
        map.put("isFreeVideo", Arrays.asList(5));
        map.put("duration", Arrays.asList(6));
        map.put("showID", Arrays.asList(8,9));
        map.put("currentSeason", Arrays.asList(7,10));
        return map;
    }

    private static final String sql =
            "INSERT INTO video\n" +
                    "\t(title, description, host, releaseDate, isFree, durationMS, episodeNum, showID, seasonNum)\n" +
                    "VALUES\n" +
                    "\t(?, ?, ?, ?, ?, ?, \n" +
                    "     (SELECT MAX(v2.episodeNum) + 1\n" +
                    "       FROM video v2\n" +
                    "       WHERE v2.seasonNum = ? AND\n" +
                    "     \t\tv2.showID = ?),\n" +
                    "     ?,\n" +
                    "     ?);";

    private String showName;

    public AddEpisode() {
        super(sql, createParamList());
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        System.out.println("Video Successfully Added");
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
                        System.out.printf("%d. %s\n\tdescription:\n\t", idx, res.getString("title"));
                        GeneralHelpers.cleanOutput(res.getString("description"), "\n\t", GeneralHelpers.MAXLINE);
                        System.out.println();
                        idx++;
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
        final String curSeasonSql = "SELECT MAX(v.seasonNum)\n" +
                "FROM video v\n" +
                "WHERE v.showID = ?;";
        try (final PreparedStatement stmt = connection.prepareStatement(curSeasonSql)) {
            stmt.setString(1, params.get("showID"));
            try (final ResultSet res = stmt.executeQuery()) {
                res.next();
                params.put("currentSeason", res.getString(1));
            }
        }


    }

    @Override
    protected void getInput() {
        String title = GeneralHelpers.getStringInput("What is the title of the video");
        String description = GeneralHelpers.getStringInput("Please input the description of the video (No new lines)");
        String appHost = GeneralHelpers.getStringInput("Please input the app that will be hosting this video");
        String releaseDate = GeneralHelpers.getStringInput("Please input the release date of the video",
                "YYYY-MM-DD",
                GeneralHelpers.FORMAT_DATE);
        Boolean isFreeVideo = GeneralHelpers.getBoolInput("Please input whether this video will be free");
        int duration = GeneralHelpers.getIntInput("Please input the duration of the video in seconds");
        boolean knowsId = GeneralHelpers.getBoolInput("Do you know the ID of the show?");
        if (knowsId){
            int showId = GeneralHelpers.getIntInput("Input the show's ID");
            params.put("showID", Integer.toString(showId));
        } else {
            showName = GeneralHelpers.getStringInput("Input the Show's name");
        }

        params.put("title", title);

        params.put("description", description);
        params.put("appHost", appHost);
        params.put("releaseDate", releaseDate);
        params.put("isFreeVideo", isFreeVideo? "1" : "0");
        params.put("duration", Integer.toString(duration * 1000));
    }

    @Override
    protected ResultSet execute(PreparedStatement stmt) throws SQLException {
        stmt.executeUpdate();
        return null;
    }

}