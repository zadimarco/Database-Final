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
 * Query that gets and ranks the revenue of apps from a country
 */
public class GetRankedRevenue extends YaTVQuery{

    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("country", Arrays.asList(1));
        return map;
    }

    private static final String sql =
            "SELECT a.name AS App, IFNULL(SUM(tb.cost), 0) AS revenue\n" +
                    "FROM app a\n" +
                    "LEFT OUTER JOIN \n" +
                    "(\n" +
                    "  SELECT *\n" +
                    "  FROM subscription s\n" +
                    "  INNER JOIN yauser u ON u.id = s.userID\n" +
                    "  WHERE u.country = ?\n" +
                    "\n" +
                    ") tb ON a.name = tb.appName\n" +
                    "GROUP BY a.name\n" +
                    "ORDER BY revenue DESC";

    public GetRankedRevenue() {
        super(sql, createParamList());
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        System.out.printf("Here are all the app revenues from the given country (%s)\n",
                params.get("country"));
        while(res.next()){
            System.out.printf("%s made $%d\n",
                    res.getString("App"),
                    res.getInt("revenue"));
        }
    }

    @Override
    protected void calcParams(Connection connection) throws SQLException {

    }

    @Override
    protected void getInput() {
        String country = GeneralHelpers.getStringInput("What country would you like revenues from");
        setParam("country", country);
    }

    @Override
    protected ResultSet execute(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }

}


