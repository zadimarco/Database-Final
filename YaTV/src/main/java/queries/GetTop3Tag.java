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
 * Query that gets the top 3 tags on YaTV
 */
public class GetTop3Tag extends YaTVQuery{

    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        return map;
    }

    private static final String sql =
            "SELECT v.title AS `Video Title`, t.tagName AS `Tag Name`, SUM(w.videoID IS NOT NULL) AS `Watch Count`\n" +
                    "FROM video v \n" +
                    "LEFT OUTER JOIN watched w ON v.id = w.videoID\n" +
                    "INNER JOIN tag t ON t.videoID = v.id\n" +
                    "INNER JOIN\n" +
                    "(\n" +
                    "  SELECT t.tagName AS Tag, COUNT(*) AS Watches\n" +
                    "  FROM tag t\n" +
                    "  INNER JOIN video v ON t.videoID = v.id\n" +
                    "  INNER JOIN watched w ON w.videoID = v.id\n" +
                    "  GROUP BY t.tagName\n" +
                    "  ORDER BY Watches DESC\n" +
                    "  LIMIT 3\n" +
                    ") top3 ON top3.Tag = t.tagName\n" +
                    "GROUP BY v.id, t.tagName\n" +
                    "ORDER BY `Tag Name`, `Watch Count` DESC, `Video Title`;";

    public GetTop3Tag() {
        super(sql, createParamList());
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        String curTag = null;
        int curTagPlace = 1;
        int curVidPlace = 1;
        while(res.next()){
            if(curTag == null || !curTag.equals(res.getString("Tag Name"))){
                if (curTag != null){
                    System.out.println();
                }
                System.out.printf("#%d Tag is %s\n",
                        curTagPlace,
                        res.getString("Tag Name"));
                curTagPlace++;
                curTag = res.getString("Tag Name");
                curVidPlace = 1;
            }
            String out = String.format("\t#%d is \"%s\" with %d views",
                    curVidPlace,
                    res.getString("Video Title"),
                    res.getInt("Watch Count"));
            GeneralHelpers.cleanOutput(out, "\n\t\t", GeneralHelpers.MAXLINE);
            curVidPlace++;
            System.out.println();

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
