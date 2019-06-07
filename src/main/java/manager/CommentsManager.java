package manager;

import db.DBConnectionProvider;
import model.Comments;
import model.Post;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CommentsManager {

    private PostManager postManager;
    private UserManager userManager;
    private Connection connection;

    public CommentsManager() {

        connection = DBConnectionProvider.getInstance().getConnection();
        postManager  = new PostManager();
        userManager = new UserManager();
    }

    public void addComments(Comments comments) {
        try {
            String query = "INSERT INTO comments(`content`,`parent_id`,`author_id`,`post_id`)" +
                    "VALUES(?,?,?,?);";

            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, comments.getContent());
            statement.setLong(2, comments.getParentId());
            statement.setLong(3, comments.getUser().getId());
            statement.setLong(4, comments.getPost().getId());

            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                comments.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Comments getCommentById(int id) {
        String query = "SELECT * FROM comments WHERE id = " + id;
        return getCommentsFromDB(query);
    }


    public List<Comments> getAllComments() {
        String query = "SELECT * FROM comments";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Comments> allComments = new LinkedList<Comments>();
            while (resultSet.next()) {
                allComments.add(createCommentsFromResultSet(resultSet));
            }
            return allComments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Comments> getAllCommentsByPost(int postId) {
        String query = "SELECT * FROM comments where post_id = " + postId;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Comments> allComments = new LinkedList<Comments>();
            while (resultSet.next()) {
                allComments.add(createCommentsFromResultSet(resultSet));
            }
            return allComments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Comments> getAllCommentsByUser(int userId) {
        String query = "SELECT * FROM comments where user_id = " + userId;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            List<Comments> allComments = new LinkedList<Comments>();
            while (resultSet.next()) {
                allComments.add(createCommentsFromResultSet(resultSet));
            }
            return allComments;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeCommentsById(int id) {
        String query = "DELETE  FROM comments WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Comments getCommentsFromDB(String query) {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return createCommentsFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Comments createCommentsFromResultSet(ResultSet resultSet) throws SQLException {

        Comments comments = new Comments();
        comments.setId(resultSet.getInt(1));
        comments.setContent(resultSet.getString(2));
        comments.setParentId(resultSet.getInt(3));
        comments.setUser(userManager.getUserById(resultSet.getInt(4)));
        comments.setPost(postManager.getPostById(resultSet.getInt(5)));

        return comments;
    }
}
