package queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostAnticipatedShows extends YaTVQuery {
    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        return map;
    }
    private static final String sql = "SELECT s.title, COUNT(*) + IF(showCount.totalEps = 0, 0, showToWatch.numToWatch/showCount.totalEps) AS `Number of times this show appears on a list`\n" +
            "FROM userlistshows uls\n" +
            "INNER JOIN yashow s ON s.id = uls.showID\n" +
            "INNER JOIN\n" +
            "(\n" +
            "  SELECT SUM(v3.id IS NOT NULL) AS totalEps, s2.id\n" +
            "  FROM yashow s2 \n" +
            "  LEFT OUTER JOIN video v3 ON v3.showID = s2.id\n" +
            "  GROUP BY s2.id\n" +
            ") showCount ON showCount.id = s.id \n" +
            "INNER JOIN\n" +
            "(\n" +
            "  SELECT COUNT(v4.id) AS numToWatch, s3.id\n" +
            "  FROM yashow s3\n" +
            "  LEFT OUTER JOIN \n" +
            "  (video v4 \n" +
            "  INNER JOIN userlistvideos ulv2 ON ulv2.videoID = v4.id\n" +
            "  ) ON v4.showID = s3.id\n" +
            "  GROUP BY s3.id\n" +
            ") showToWatch ON showToWatch.id = s.id\n" +
            "GROUP BY s.id\n" +
            "ORDER BY `Number of times this show appears on a list` DESC\n";

    public MostAnticipatedShows() {
        super(sql, createParamList());
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        System.out.println("Here are the most anticipated shows (ranked by appearances on user lists)");
        int placement = 1;
        while(res.next()){
            System.out.printf("#%d\t%s\n", placement, res.getString("title"));
            placement++;
        }
    }

    @Override
    protected void calcParams(Connection connection) throws SQLException {

    }

    @Override
    protected void getInput() {

    }

    @Override
    protected ResultSet execute(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }
}
