package Databases;

import GUI.AlertMessages;
import GUI.LoginWindow;
import GUI.Portfolio;
import javafx.collections.ObservableList;

import java.sql.*;

public class PortfolioDataBase implements JdbcData {
    public static void addTransactionDate(String date, String ticker, String type, int qty) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(jdbc_driver);
            connection = DriverManager.getConnection(jdbc_url, sql_username, sql_password);
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("insert into " + LoginWindow.username + " values (?,?,?,?)");
            preparedStatement.setString(1, date);
            preparedStatement.setString(2, ticker);
            preparedStatement.setString(3, type);
            preparedStatement.setInt(4, qty);
            int n = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            AlertMessages.getAlert("User Not Found");
        } catch (ClassNotFoundException e) {
            AlertMessages.getAlert("Couldn't connect to JDBC");
        }
    }

    public static void getUserTransaction(ObservableList<Portfolio.Stonks> vals) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName(jdbc_driver);
            connection = DriverManager.getConnection(jdbc_url, sql_username, sql_password);
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from " + LoginWindow.username);
            while (resultSet.next()) {
                vals.add(new Portfolio.Stonks(resultSet.getString("date"), resultSet.getString("ticker"), resultSet.getString("type"), resultSet.getInt("quantity")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AlertMessages.getAlert("User Not Found");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            AlertMessages.getAlert("Couldn't connect to JDBC");
        }
    }
}
