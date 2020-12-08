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
 * Query that retrieves all similar users
 */
public class SimilarUsers extends YaTVQuery {

    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("email", Arrays.asList(1,2));
        return map;
    }

    private static final String sql =
            "SELECT CONCAT(tb.firstName , \" \", tb.lastName, \" has similar tastes (\", tb.percentageAgrees,\"% rate)\") AS `Other Users`\n" +
                    "FROM \n" +
                    "(SELECT yu1.firstName, yu1.lastName, yu1.email, SUM(w1.liked = w2.liked)/ COUNT(*) * 100 AS percentageAgrees\n" +
                    " FROM yauser yu1\n" +
                    " INNER JOIN watched w1 ON yu1.id = w1.userID\n" +
                    " INNER JOIN watched w2 ON w1.videoID = w2.videoID\n" +
                    " WHERE w2.userID = (SELECT yu2.id FROM yauser yu2 WHERE yu2.email = ?)\n" +
                    " GROUP BY yu1.id\n" +
                    " HAVING SUM(w1.liked = w2.liked)/COUNT(*) > .75) tb\n" +
                    "WHERE NOT tb.email = ?\n" +
                    "ORDER BY percentageAgrees DESC";

    public SimilarUsers() {
        super(sql, createParamList());
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        while(res.next()) {
            GeneralHelpers.cleanOutput(res.getString("Other Users"), "\n", GeneralHelpers.MAXLINE);
            System.out.println();
        }
    }

    @Override
    protected void calcParams(Connection connection) throws SQLException {

    }

    @Override
    protected void getInput() {
        String email = GeneralHelpers.getStringInput("What user would like similar users", "email", GeneralHelpers.FORMAT_EMAIL );

        params.put("email", email);
    }

    @Override
    protected ResultSet execute(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }
}
//
//-- -------------SCORING-------------
//        -- +1 Joined 3 tables (2 are the same table, but there are 3 tables total still)
//        -- +1 One subquery (didn't add 2, as the inner subquery could probably be taken out)
//        -- +1 Aggregate Function
//        -- +1 Grouping
//        --     Needed to Group to get the correct aggregate function results
//        -- +1 WHERE/HAVING conditions not for joins
//        -- +1 Non-aggregate function Concat being used
//        -- +1 This query allows people to find other users with similar tastes, which I feel is a useful feature
//        -- ---------------------------------------
//        -- 7 Points = Complex
