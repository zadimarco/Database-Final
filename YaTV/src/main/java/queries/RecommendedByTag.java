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
 * Recommends the best videos from a specific tag
 */
public class RecommendedByTag extends YaTVQuery {


    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("tag", Arrays.asList(1));
        return map;
    }
    private static final String sql = "SELECT tags.name AS \"Recommended\", IFNULL(10 * likes.rating, \"Unrated\") AS Rating, IF(likes.isMovie = 1, 'Movie', 'Show') AS 'Type of experience'\n" +
            "FROM\n" +
            "(\n" +
            "  SELECT IFNULL(s.id, v.id) AS id, IFNULL(s.title, v.title) AS name\n" +
            "  FROM video v\n" +
            "  LEFT OUTER JOIN yashow s ON v.showID = s.id\n" +
            "  LEFT OUTER JOIN tag t ON t.videoID = v.id\n" +
            "  GROUP BY name\n" +
            "  HAVING SUM( NOT t.tagName IS NULL AND t.tagName = ?)/COUNT(DISTINCT v.id) >= .5\n" +
            ") tags\n" +
            "INNER JOIN\n" +
            "(\n" +
            "  SELECT IFNULL(s.id, v.id) AS id, SUM(w.liked)/SUM(NOT w.userID is NULL) AS rating, SUM(NOT w.userID is NULL) AS totalRatings, s.id IS NULL AS isMovie\n" +
            "  FROM video v \n" +
            "  LEFT OUTER JOIN watched w ON v.id = w.videoID\n" +
            "  LEFT OUTER JOIN yashow s ON v.showID = s.id\n" +
            "  GROUP BY id, s.id\n" +
            ") likes\n" +
            "ON likes.id = tags.id\n" +
            "GROUP BY tags.name\n" +
            "ORDER BY likes.rating DESC, tags.name";


    public RecommendedByTag() {
        super(sql, createParamList());
    }
    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        System.out.println("\nI would recommend:");
        while(res.next()){
            if(res.getString("Rating").equals("Unrated")){
                System.out.printf("%s is an Unrated %s\n",
                        res.getString("Recommended"),
                        res.getString("Type of experience"));
            }else {
                System.out.printf("%s is a %s with a rating of %f\n",
                        res.getString("Recommended"),
                        res.getString("Type of experience"),
                        Double.parseDouble(res.getString("Rating")));
            }
        }
    }

    @Override
    protected void calcParams(Connection connection) throws SQLException {

    }

    @Override
    protected void getInput() {
        String tag = GeneralHelpers.getStringInput("What tag are you interested in");

        params.put("tag", tag);
    }

    @Override
    protected ResultSet execute(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }
}
