package util.db;

import model.Polynomial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PolynomialDAO {
    private static Connection connection = DBConnector.getConnection();

    private static Polynomial getPolynomialFromResultSet(ResultSet resultSet) throws SQLException {
        int n = resultSet.getInt("n");
        int j = resultSet.getInt("j");
        int oct = resultSet.getInt("oct");
        char letter = resultSet.getString("letter").charAt(0);
        return new Polynomial(n, j, oct, letter);
    }

    public Polynomial getPolynomialByOct(int searchedOct) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM polynomial WHERE oct = ?");
        preparedStatement.setInt(1, searchedOct);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return getPolynomialFromResultSet(resultSet);
        }
        return null;
    }

    public List<Polynomial> getPolynomialsByN(int searchedN) throws SQLException {
        List<Polynomial> list = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM polynomial WHERE n = ?");
        preparedStatement.setInt(1, searchedN);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
            list.add(getPolynomialFromResultSet(resultSet));

        return list;
    }

    public List<Integer> getAllN() throws SQLException {
        List<Integer> list = new ArrayList<>();
        ResultSet resultSet = connection.createStatement().executeQuery("SELECT DISTINCT n FROM polynomial ORDER BY n");
        while (resultSet.next())
            list.add(resultSet.getInt(1));
        return list;
    }
}
