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


public class FindMovies extends YaTVQuery{

    private static Map<String, List<Integer>> createParamList(){
        Map<String, List<Integer>> map = new HashMap<>();
        return map;
    }

    private static final String sql =
            "SELECT v.title, v.description\n" +
                    "FROM video v\n" +
                    "WHERE v.showID is NULL AND \n" +
                    "v.durationMS > 3600000 AND\n" +
                    "v.releaseDate > CURDATE() - INTERVAL 1 YEAR;";

    public FindMovies() {
        super(sql, createParamList());
    }

    @Override
    protected void handleOutput(ResultSet res) throws SQLException {
        System.out.println("Here are all the videos over an hour long released within a year:");
        while(res.next()){
            System.out.printf("%s\n\tdescription:\n\t", res.getString("title"));
            GeneralHelpers.cleanOutput(res.getString("description"),"\n\t", GeneralHelpers.MAXLINE);
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
