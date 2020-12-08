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
 * Finds all free videos on a platform
 */
public class FindFree extends YaTVQuery{

    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        map.put("platform", Arrays.asList(1));
        return map;
    }

    private static final String sql =
            "SELECT DISTINCT v.title\n" +
                    "FROM video v\n" +
                    "INNER JOIN app a ON v.host = a.name\n" +
                    "INNER JOIN appplatform ap ON a.name = ap.appName\n" +
                    "WHERE ap.platformName = ? AND\n" +
                    "\tv.isFree = True;";

    public FindFree() {
        super(sql, createParamList());
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        System.out.printf("Here are all the free videos on %s:\n", params.get("platform"));
        while(res.next()){
            System.out.printf("%s\n", res.getString("title"));
        }
    }

    @Override
    protected void calcParams(Connection connection) throws SQLException {}

    @Override
    protected void getInput() {
        String platform = GeneralHelpers.getStringInput("Input a platform name");
        params.put("platform", platform);
    }

    @Override
    protected ResultSet execute(PreparedStatement stmt) throws SQLException {
        return stmt.executeQuery();
    }

}
