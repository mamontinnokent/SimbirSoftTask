package ru.task.dao;

import ru.task.model.Word;
import ru.task.settings.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class WordDAO {
    //  language=SQL
    private static String SELECT_ALL_WORDS_BY_SITE_ID = "SELECT * FROM words WHERE site_id = ? ORDER BY count_of_word DESC, word DESC";
    //  language=SQL
    private static String DELETE_ALL = "DELETE FROM words";
    //  language=SQL
    private static String DELETE_BY_SITE_ID = "DELETE FROM words WHERE site_id = ?";
    //  language=SQL
    private static String INSERT = "INSERT words(site_id, word, count_of_word) VALUES (?, ?, ?)";


    public static List<Word> getWordsBySiteId(Long id) {
        List<Word> words = new ArrayList<>();
        try {
            PreparedStatement statement = DBConnector.getConnection().prepareStatement(SELECT_ALL_WORDS_BY_SITE_ID);
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())
                words.add(new Word(id, resultSet.getString("word"), resultSet.getLong("count_of_word")));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return words;
    }

    public static boolean deleteWordsByID(Long id) {
        try {
            PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement(DELETE_BY_SITE_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public static boolean deleteAll() {
        try {
            PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement(DELETE_ALL);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean delete(Long id) {
        try {
            PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement(DELETE_BY_SITE_ID);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public static void insert(Word word) {
        try {
            PreparedStatement preparedStatement = DBConnector.getConnection().prepareStatement(INSERT);
            preparedStatement.setLong(1, word.getSiteId());
            preparedStatement.setString(2, word.getWord());
            preparedStatement.setLong(3, word.getCountOfWord());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
