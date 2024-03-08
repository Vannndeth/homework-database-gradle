package repository;

import com.github.javafaker.Faker;
import model.Person;
import utils.PropertyUtils;
import utils.SQLUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PersonRepository {
    private final Properties properties;

    public PersonRepository() {
        properties = PropertyUtils.loadProperty();
    }

    private Connection startDatabaseConnection() throws SQLException {
        return DriverManager.getConnection(
                properties.getProperty("DB_URL"),
                properties.getProperty("USERNAME"),
                properties.getProperty("PASSWORD")
        );
    }

    public List<Person> getAllPerson() {
        try (
                Connection connection = startDatabaseConnection();
                Statement statement = connection.createStatement();
        ) {
            var personList = new ArrayList<Person>();
            var rs = statement.executeQuery(SQLUtils.PersonSQL.GET_ALL_PERSON);
            while (rs.next()) {
                personList.add(
                        new Person()
                                .setId(rs.getInt("id"))
                                .setFullName(rs.getString("fullname"))
                                .setGender(rs.getString
                                        ("gender"))
                                .setEmail(rs.getString("email"))
                                .setAddress(rs.getString("address"))
                                .setUser_id(rs.getInt("user_id"))
                );
            }
            return personList;

        } catch (SQLException ex) {
            System.out.println("Failed to retreive all the person data ! ");
            ex.printStackTrace();
        }

        return null;
    }

    public int addNewPerson(Person person) {
        try (
                Connection connection = startDatabaseConnection();
                PreparedStatement ps = connection.prepareStatement(SQLUtils.PersonSQL.INSERT_PERSON);
        ) {

            ps.setString(1, person.getFullName());
            ps.setString(2, person.getGender());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getAddress());
            ps.setInt(5, person.getUser_id());

            return ps.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error when adding a new person");
            ex.printStackTrace();
        }
        return 0;
    }

    public List<Person> generatePersonData(int n) {
        Faker faker = new Faker();
        List<Person> persons = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            persons.add(new Person()
                    .setId(1000 + i)
                    .setEmail(faker.internet().emailAddress())
                    .setFullName(faker.name().fullName())
                    .setGender(faker.options().option("male", "female"))
                    .setAddress(faker.address().country())
                    .setUser_id(1));
        }
        return persons;
    }

    public int updatePerson(Person updatedPerson) {
        try
                (
                        Connection connection = startDatabaseConnection();
                        PreparedStatement ps = connection.prepareStatement(SQLUtils.PersonSQL.UPDATE_PERSON)
                ) {

            ps.setString(1, updatedPerson.getFullName());
            ps.setString(2, updatedPerson.getGender());
            ps.setString(3, updatedPerson.getEmail());
            ps.setString(4, updatedPerson.getAddress());
            ps.setInt(5, updatedPerson.getId());

            return ps.executeUpdate();


        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }

    }

    public int deletePersonByID(int personID) {
        try (
                Connection connection = startDatabaseConnection();
                PreparedStatement ps = connection.prepareStatement(SQLUtils.PersonSQL.DELETE_BY_ID)

        ) {
            ps.setInt(1, personID);
            return ps.executeUpdate(); // return int -> number of records that we deleted !

        } catch (SQLException ex) {
            System.out.println("Failed to delete the person record with ID = " + personID);
            ex.printStackTrace();
            return 0;
        }

    }


}
