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
 * Query that gets videos recommended to a user based on their subscribed apps
 */
public class RecommendedVideos extends YaTVQuery {

    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("email", Arrays.asList(1));
        return map;
    }
    private static final String sql = "SELECT v.title AS \"Recommended Video\", SUM(w.liked) AS \"Number of Likes\", v.description\n" +
            "FROM video v\n" +
            "INNER JOIN watched w ON v.id = w.videoID\n" +
            "INNER JOIN yauser yu ON yu.id = w.userID\n" +
            "INNER JOIN app a ON a.name = v.host\n" +
            "WHERE a.name IN \n" +
            "(\n" +
            "  SELECT a2.name \n" +
            "  FROM app a2\n" +
            "  INNER JOIN subscription s2 ON s2.appName = a2.name\n" +
            "  WHERE s2.userID = (SELECT yauser.id FROM yauser WHERE yauser.email = ?)\n" +
            ") OR\n" +
            "v.isFree\n" +
            "GROUP BY v.title\n" +
            "ORDER BY SUM(w.liked) DESC";


    public RecommendedVideos() {
        super(sql, createParamList());
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        System.out.println("Recommended Videos:");
        while(res.next()){
            System.out.printf("%s\ndescription:\n\t", res.getString("Recommended Video"));
            GeneralHelpers.cleanOutput(res.getString("description"), "\n\t", GeneralHelpers.MAXLINE);
            System.out.println("\n");
        }
    }

    @Override
    protected void calcParams(Connection connection) throws SQLException {

    }

    @Override
    protected void getInput() {
        String email = GeneralHelpers.getStringInput("What user would like recommended videos", "email", GeneralHelpers.FORMAT_EMAIL );

        params.put("email", email);
    }

    @Override
    protected ResultSet execute(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }
}

//-- -------------SCORING-------------
//        -- +1 Joined 4 tables
//        -- +1 Two subqueries (Inner subquery is kind of trivial)
//        -- +1 Aggregate Function
//        -- +1 Grouping
//        --     Needed to Group to get the correct aggregate function results
//        -- +1 Ordering
//        -- +1 WHERE/HAVING conditions not for joins
//        -- +1 This query is a recommendation system based on other users, which is incredibly useful for a tv application
//        -- ---------------------------------------
//        -- 7 Points = Complex

