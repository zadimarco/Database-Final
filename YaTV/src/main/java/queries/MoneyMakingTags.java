package queries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoneyMakingTags extends YaTVQuery {

    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        return map;
    }

    private static final String sql =
            "SELECT tb1.name AS `App Name`, tb1.tagName AS `Tag`, CONCAT(\"$\", FORMAT(SUM(tb1.cost * tb1.numTagWatched/userTotal.totalShows), 2)) AS `Profit From Tag`\n" +
                    "FROM \n" +
                    "( \n" +
                    "  SELECT s.cost, a2.name, t.tagName, u.id, COUNT(DISTINCT w.videoID) AS numTagWatched\n" +
                    "  FROM app a2\n" +
                    "  INNER JOIN video v ON v.host = a2.name\n" +
                    "  INNER JOIN watched w ON w.videoID = v.id\n" +
                    "  INNER JOIN yauser u ON u.id = w.userID\n" +
                    "  INNER JOIN subscription s ON s.userID = u.id AND s.appName = a2.name\n" +
                    "  INNER JOIN tag t ON v.id = t.videoID\n" +
                    "  WHERE NOT v.isFree\n" +
                    "  GROUP BY u.id, a2.name, t.tagName\n" +
                    ") tb1\n" +
                    "INNER JOIN\n" +
                    "(\n" +
                    "  SELECT u2.id, s2.appName, COUNT(*) AS totalShows, u2.firstName, v2.title\n" +
                    "  FROM yauser u2\n" +
                    "  INNER JOIN subscription s2 ON u2.id = s2.userID\n" +
                    "  INNER JOIN watched w2 ON u2.id = w2.userID\n" +
                    "  INNER JOIN video v2 ON v2.id = w2.videoID AND v2.host = s2.appName\n" +
                    "  WHERE NOT v2.isFree\n" +
                    "  GROUP BY s2.appName, u2.id\n" +
                    ") userTotal ON userTotal.appName = tb1.name AND userTotal.id = tb1.id\n" +
                    "GROUP BY tb1.name, tb1.tagName\n" +
                    "ORDER BY `App Name`, `Profit From Tag` ASC, `Tag`";

    public MoneyMakingTags() {
        super(sql, createParamList());
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        int placement = 1;
        String app = null;
        while(res.next()){
            if (app == null || !res.getString("App Name").equals(app)){
                if (app != null)
                    System.out.println();
                System.out.printf("Top tags for %s\n", res.getString("App Name"));
                app = res.getString("App Name");
                placement = 1;
            }
            System.out.printf("\t#%d \"%s\" tag made %s\n", placement,
                    res.getString("Tag"),
                    res.getString("Profit From Tag"));
            placement++;
        }
    }

    @Override
    protected void calcParams(Connection connection) throws SQLException {}

    @Override
    protected void getInput() {}

    @Override
    protected ResultSet execute(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }
}
