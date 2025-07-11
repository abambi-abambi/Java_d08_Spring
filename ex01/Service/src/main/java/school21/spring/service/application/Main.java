package school21.spring.service.application;

import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

import com.zaxxer.hikari.HikariDataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        createTable(context);
        UsersRepository usersRepositoryJdbc = context.getBean("usersRepositoryJdbc", UsersRepository.class);
        UsersRepository usersRepositoryJdbcTemplate = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);

        System.out.println("\n---INSERT NEW USERS JDBC---");

        User user1 = new User(1L, "1@school21.ru");
        User user2 = new User(2L,"2@school21.ru");
        User user3 = new User(3L,"3@school21.ru");
        User user4 = new User(4L,"4@school21.ru");
        List<User> users = Arrays.asList(user1, user2, user3, user4);
        users.forEach(System.out::println);
        users.forEach(user -> usersRepositoryJdbc.save(user));

        System.out.println("\n---FIND ALL USERS---");

        System.out.println(usersRepositoryJdbc.findAll());
        System.out.println(usersRepositoryJdbcTemplate.findAll());

        System.out.println("\n---INSERT NEW USERS  JDBC_TEMPLATE---");

        User user5 = new User(5L, "5@yandex.ru");
        User user6 = new User(6L, "6@yandex.ru");
        User user7 = new User(7L, "7@yandex.ru");
        User user8 = new User(8L, "8@yandex.ru");
        User user9 = new User(9L, "9@yandex.ru");
        User user10 = new User(10L, "10@yandex.ru");
        users = Arrays.asList(user5, user6, user7, user8, user9, user10);
        users.forEach(System.out::println);
        users.forEach(user -> usersRepositoryJdbcTemplate.save(user));

        System.out.println("\n---FIND ALL USERS---");

        System.out.println(usersRepositoryJdbc.findAll());
        System.out.println(usersRepositoryJdbcTemplate.findAll());

        System.out.println("\n---DELETE USERS WITH ID: 9 10---");

        usersRepositoryJdbc.delete(10L);
        usersRepositoryJdbcTemplate.delete(9L);

        System.out.println("\n---UPDATE USERS WITH ID: 5 6 7 8 ---");

        user5.setEmail("UPDATED_" + user5.getEmail());
        user6.setEmail("UPDATED_" + user6.getEmail());
        user7.setEmail("UPDATED_" + user7.getEmail());
        user8.setEmail("UPDATED_" + user8.getEmail());
        usersRepositoryJdbc.update(user5);
        usersRepositoryJdbc.update(user6);
        usersRepositoryJdbcTemplate.update(user7);
        usersRepositoryJdbcTemplate.update(user8);

        System.out.println("\n---FIND ALL USERS---");

        System.out.println(usersRepositoryJdbc.findAll());
        System.out.println(usersRepositoryJdbcTemplate.findAll());

        System.out.println("\n---FIND BY ID---");

        System.out.println(usersRepositoryJdbc.findById(1l));
        System.out.println(usersRepositoryJdbcTemplate.findById(1l));
        System.out.println(usersRepositoryJdbc.findById(2l));
        System.out.println(usersRepositoryJdbcTemplate.findById(2l));

        System.out.println("\n---FIND BY MAIL---");

        System.out.println(usersRepositoryJdbc.findByEmail("1@school21.ru"));
        System.out.println(usersRepositoryJdbc.findByEmail("2@school21.ru"));
        System.out.println(usersRepositoryJdbcTemplate.findByEmail("1@school21.ru"));
        System.out.println(usersRepositoryJdbcTemplate.findByEmail("2@school21.ru"));
        System.out.println(usersRepositoryJdbc.findByEmail("EMPTY"));
        System.out.println(usersRepositoryJdbcTemplate.findByEmail("EMPTY"));
    }

    private static void createTable(ApplicationContext context) {
        DataSource dataSource = context.getBean("hikariDataSource", HikariDataSource.class);
        try (Connection conn = dataSource.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate("drop schema if exists models cascade;");
            st.executeUpdate("create schema if not exists models;");
            st.executeUpdate("create table if not exists models.user "
                    + "(id integer not null, email varchar(50) not null);");
        } catch (SQLException e) {
            st.close();
            conn.close();
            System.err.println(e.getMessage());
        }
        st.close();
        conn.close();
    }
}