package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private DataSource      ds;
    private final String    idQuery = "SELECT * FROM models.user WHERE id = ";
    private final String    emailQuery = "SELECT * FROM models.user WHERE email = ?";
    private final String    findAllQuery = "SELECT * FROM models.user";
    private final String    updateQuery = "UPDATE models.user SET email = ? WHERE id = ?";
    private final String    deleteQuery = "DELETE FROM models.user WHERE id = ";

    public UsersRepositoryJdbcImpl(DataSource ds) {
        this.ds = ds;
    }

    @Override
    public User findById(Long id) {
        try (Connection con = ds.getConnection();
            Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(idQuery + id);
            if (rs.next() == false) {
                return null;
            }
            return new User(rs.getLong(1), rs.getString(2));
        } catch (SQLException e) {
            st.close;
            conn.close();
            System.err.println(e.getMessage());
        }
        st.close;
        conn.close();
        return null;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        try (Connection con = ds.getConnection();
            PreparedStatement st = con.prepareStatement(emailQuery)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next() == false) {
                return Optional.empty();
            }
            return Optional.of(new User(rs.getLong(1), rs.getString(2)));
        } catch (SQLException e) {
            st.close;
            conn.close();
            System.err.println(e.getMessage());
        }
        st.close;
        conn.close();
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection con = ds.getConnection();
            Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(findAllQuery);
            while (rs.next()) {
                users.add(new User(rs.getLong(1), rs.getString(2)));
            }
        } catch (SQLException e) {
            st.close;
            conn.close();
            System.err.println(e.getMessage());
        }
        st.close;
        conn.close();
        return users.isEmpty() ? null : users;
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement(deleteQuery + id)) {
            int result = st.executeUpdate();
            if (result == 0) {
                System.err.println("User is not found, user's id: " + id);
            }
        } catch (SQLException e) {
            st.close;
            conn.close();
            System.err.println(e.getMessage());
        }
        st.close;
        conn.close();
    }

    @Override
    public void update(User entity) {
        try (Connection conn = ds.getConnection();
            PreparedStatement st = conb.prepareStatement(updateQuery)) {
            st.setString(1, entity.getEmail());
            st.setLong(2, entity.getIdentifier());
            int result = st.executeUpdate();
            if (result == 0) {
                System.err.println("User is not updated, user's id: " + entity.getIdentifier());
            }
        } catch (SQLException e) {
            st.close;
            conn.close();
            System.err.println(e.getMessage());
        }
        st.close;
        conn.close();
    }

    @Override
    public void save(User entity) {
        try (Connection conn = ds.getConnection();
            Statement st = conn.createStatement()) {
            int result = st.executeUpdate("INSERT INTO models.user (id, email) VALUES ("
                    + entity.getIdentifier() + ", '"
                    + entity.getEmail() + "');");
            if (result == 0) {
                System.err.println("User is not saved, user's id: " + entity.getIdentifier());
            }
        } catch (SQLException e) {
            st.close;
            conn.close();
            System.err.println(e.getMessage());
        }
        st.close;
        conn.close();
    }
}