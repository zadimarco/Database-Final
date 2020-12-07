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


public class Top10Shows extends YaTVQuery{

    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        return map;
    }

    private static final String sql =
            "SELECT s.title, s.description, COUNT(w.liked) AS count\n" +
                    "FROM yashow s\n" +
                    "LEFT OUTER JOIN video v ON v.showID = s.id\n" +
                    "LEFT OUTER JOIN watched w ON w.videoID = v.id\n" +
                    "GROUP BY s.id\n" +
                    "ORDER BY COUNT(s.id) DESC\n" +
                    "LIMIT 10;\n";

    public Top10Shows() {
        super(sql, createParamList());
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        int placement = 1;
        while(res.next()){
            String first = String.format("#%d is %s with %d views",
                    placement,
                    res.getString("title"),
                    res.getInt("count"));
            GeneralHelpers.cleanOutput(first, "\n", GeneralHelpers.MAXLINE);
            System.out.print("\n\tdescription:\n\t");
            GeneralHelpers.cleanOutput(res.getString("description"),"\n\t", GeneralHelpers.MAXLINE);
            System.out.println("\n");
            placement++;
        }
        if (placement < 10) {
            System.out.println("There are all the shows with at least 1 view that have");
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
