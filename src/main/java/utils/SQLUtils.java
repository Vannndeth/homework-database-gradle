package utils;

public class SQLUtils {
    public  static class PersonSQL{

        // all constant must in uppercase
        public static final String GET_ALL_PERSON = """
            select * from person;
            """;
        public static final String INSERT_PERSON = """
                INSERT INTO person ("fullname","gender","email","address","user_id") VALUES(?,?,?,?,?);
                """;

        public  static final String DELETE_BY_ID = """
                delete from person where id = ?
                """;

        public  static final String UPDATE_PERSON = """
                update person set  fullname=?,gender=?,email=?,address=?
                where id = ?
                """;
    }
    public  static class SystemUser{
        // sql string in here !
    }

}
