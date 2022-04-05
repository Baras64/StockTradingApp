package Databases;

import GUI.AlertMessages;
import GUI.LoginWindow;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

public class DataBase implements JdbcData {
    public static boolean authenticateUser(LoginData loginData) {
        String username = loginData.getUsername();
        String password = loginData.getPassword();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String usernameDB = "";
        String passwordDB = "";

        try {
            connection = establishConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select username,password from login_credentials");
            while (resultSet.next()) {
                usernameDB = resultSet.getString("username");
                passwordDB = resultSet.getString("password");

                if (PasswordValidation.confirmPassword(password, passwordDB) && username.equals(usernameDB))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            AlertMessages.getAlert("User Not Found");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            AlertMessages.getAlert("Couldn't connect to JDBC");
        } catch (NoSuchAlgorithmException e) {

        } catch (InvalidKeySpecException e) {

        }
        return false;
    }

    public static boolean newUserCreation(LoginData loginData) {
        String username = loginData.getUsername();
        String password = loginData.getPassword();
        String emailID = loginData.getEmailID();

        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        String sqlTableCreate = "create table " + username + "(date varchar(20),ticker varchar(20),type varchar(20), quantity integer(20))";
        try {
            connection = establishConnection();
            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement("insert into login_credentials values (?,?,?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, PasswordValidation.generateStrongPasswordHash(password));
            preparedStatement.setString(3, emailID);
            int n = preparedStatement.executeUpdate();
            System.out.println(n + " rows affected");
            statement.executeUpdate("drop table if exists " + username);
            statement.executeUpdate(sqlTableCreate);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            connection = null;
            statement = null;
        }
        return true;
    }

    public static String getEmailId(String username) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String query = "select * from login_credentials where username=" + "\"" + username + "\"";
        String emailID = null;
        try {
            connection = establishConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                emailID = resultSet.getString("emails");
        } catch (SQLException e) {
            AlertMessages.getAlert("User Not Found");
        } catch (ClassNotFoundException e) {
            AlertMessages.getAlert("Couldn't connect to JDBC");
        }
        return emailID;
    }

    public static void updateUser(String password) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String hashedPassword = null;
        try {
            hashedPassword = PasswordValidation.generateStrongPasswordHash(password);
            connection = establishConnection();
            statement = connection.createStatement();
            statement.executeUpdate("update login_credentials set password=\"" + hashedPassword + "\" where username=\"" + LoginWindow.username + "\"");
        } catch (SQLException e) {
            AlertMessages.getAlert("User Not Found");
        } catch (ClassNotFoundException e) {
            AlertMessages.getAlert("Couldn't connect to JDBC");
        } catch (NoSuchAlgorithmException e) {

        } catch (InvalidKeySpecException e) {

        }
    }

    public static void savePortfolio(String username) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = establishConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from " + username);
            FileWriter fileWriter = new FileWriter("D:\\" + username + " Portfolio.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            while (resultSet.next()) {
                bufferedWriter.write(resultSet.getString("date") + "\t" + resultSet.getString("ticker") + "\t" + resultSet.getString("type") + "\t" + resultSet.getInt("quantity"));
                bufferedWriter.newLine();

            }
            bufferedWriter.close();
            fileWriter.close();
        } catch (SQLException e) {
            AlertMessages.getAlert("User Not Found");
        } catch (ClassNotFoundException e) {
            AlertMessages.getAlert("Couldn't connect to JDBC");
        } catch (IOException e) {
            AlertMessages.getAlert("Error in saving file");
        }
    }

    private static Connection establishConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        Class.forName(jdbc_driver);
        connection = DriverManager.getConnection(jdbc_url, sql_username, sql_password);
        return connection;
    }
}
