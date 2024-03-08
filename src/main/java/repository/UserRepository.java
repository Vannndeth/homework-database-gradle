package repository;

import model.User;
import utils.PropertyUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepository {
    private final Properties properties;

    public UserRepository() {
        properties = PropertyUtils.loadProperty();
    }

    public static final String getAllUserSql = """
            select * from user;
            """;
    public static final String loginUserSql = """
            select * from "user" where username = ? and password = ?;
            """;

    private Connection startDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("DB_URL"),
                properties.getProperty("USERNAME"),
                properties.getProperty("PASSWORD")
        );
    }

    public  boolean loginUser(String username,String password) {
    try (Connection connection = startDatabaseConnection();
         PreparedStatement preparedStatement = connection.prepareStatement(loginUserSql)) {

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        try (ResultSet rs = preparedStatement.executeQuery()) {
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        }
    } catch (SQLException ex) {
        System.out.println("Error checking user credentials.");
        ex.printStackTrace();
    }

    return false; // Default to invalid
    }

        public List<User> getAllUser () {
            try (
                    Connection connection = startDatabaseConnection();
                    Statement statement = connection.createStatement();
            ) {
                var userList = new ArrayList<User>();
                var rs = statement.executeQuery(getAllUserSql);
                while (rs.next()) {
                    userList.add(
                            new User()
                                    .setId(rs.getInt("id"))
                                    .setUsername(rs.getString("username"))
                                    .setPassword(rs.getString("password"))
                    );
                }
                return userList;

            } catch (SQLException ex) {
                System.out.println("Failed to retreive all the user data ! ");
                ex.printStackTrace();
            }
            return null;
        }
    }
