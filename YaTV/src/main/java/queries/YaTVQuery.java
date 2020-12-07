package queries;

import utilities.GeneralHelpers;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class YaTVQuery implements Runnable {

    private final String query;
    protected Map<String, String> params;
    private final Map<String, List<Integer>> paramPositions;


    protected YaTVQuery(String query, Map<String, List<Integer>> paramPositions) {
        this.query = query;
        params = new HashMap<>();
        this.paramPositions = paramPositions;
    }

    /**
     * Set the param in the query at the given position
     *
     * @param name The name of the parameter to replace
     * @param param    The value to place into the parameter (simply provide string representations of numbers or booleans)
     */
    protected void setParam(String name, String param) {
        if (paramPositions.getOrDefault(name, null) != null) {
            params.put(name, param);
        } else {
            System.out.println("Trying to set non existent parameter");
        }
    }

    protected void loadParams(PreparedStatement stmt) throws SQLException {
        for (String name : this.params.keySet()) {
            List<Integer> positions = paramPositions.get(name);
            for (Integer i : positions) {
                stmt.setString(i, this.params.get(name));
            }
        }
    }

    protected abstract void handleOutput(ResultSet res) throws SQLException;
    protected abstract void calcParams(Connection connection) throws SQLException;
    protected abstract void getInput();
    protected abstract ResultSet execute(PreparedStatement stmt) throws SQLException;

    public void run() {
        getInput();

        try (final Connection connection = DriverManager.getConnection(GeneralHelpers.createConnectionUrl())) {
            calcParams(connection);
            try (final PreparedStatement stmt = connection.prepareStatement(query)) {
                loadParams(stmt);
                try (final ResultSet res = execute(stmt)) {
                    handleOutput(res);
                }
            }

        } catch (java.sql.SQLIntegrityConstraintViolationException e){
            System.out.println("I cannot perform that action, as there is a duplicate.\n");
        } catch (SQLException throwables) {
            System.out.println("I cannot seem to perform this task. Please make sure the inputs are correct\n");
            throwables.printStackTrace();
        }
        resetParams();
    }

    private void resetParams(){
        this.params = new HashMap<>();
    }
}
