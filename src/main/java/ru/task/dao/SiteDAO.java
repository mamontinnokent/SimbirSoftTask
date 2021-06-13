package ru.task.dao;

import ru.task.model.Site;
import ru.task.settings.DBConnector;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SiteDAO {
    //  language=SQL
    private static String SELECT_ALL = "SELECT * FROM sites";
    //  language=SQL
    private static String SELECT_SITE_FROM_ID = "SELECT * FROM sites WHERE id = ?";
    //  language=SQL
    private static String INSERT_SITE = "INSERT sites(url) VALUES (?)";
    //  language=SQL
    private static String DELETE_ALL = "DELETE FROM sites";
    //  language=SQL
    private static String DELETE_BY_ID = "DELETE FROM sites WHERE id = ?";


    public static List<Site> findAll() {
        ArrayList<Site> links = new ArrayList<>();
        try (ResultSet resultSet = DBConnector.getConnection().createStatement().executeQuery(SELECT_ALL)) {
            while (resultSet.next()) {
                Site site = new Site();
                site.setId(resultSet.getLong(1));
                site.setUrl(resultSet.getString(2));
                site.setWords(WordDAO.getWordsBySiteId(resultSet.getLong(1)));

                links.add(site);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return links;
    }

    public Site findSiteById(Long id) {
        Site site = null;
        try (PreparedStatement statement = DBConnector.getConnection().prepareStatement(SELECT_SITE_FROM_ID)) {
            statement.setLong(1, id);
            statement.executeUpdate();
            ResultSet resultSet = statement.getResultSet();

            site = new Site();
            site.setUrl(resultSet.getString("url"));
            site.setWords(WordDAO.getWordsBySiteId(id));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return site;
    }

    public static boolean deleteById(Long id) {
        try {
            PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement(DELETE_BY_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            WordDAO.deleteWordsByID(id);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }


    public static Long insert(String url) {
        try {
            PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement(INSERT_SITE, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, url);

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next())
                return rs.getLong(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    public static boolean deleteAll() {
        try {
            PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement(DELETE_ALL);
            preparedStatement.executeUpdate();
            WordDAO.deleteAll();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
