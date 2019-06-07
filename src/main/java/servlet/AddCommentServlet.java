package servlet;

import manager.CommentsManager;
import model.Comments;
import model.Post;
import model.User;
import model.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns="/addComment")
public class AddCommentServlet extends HttpServlet {

    CommentsManager commentsManager = new CommentsManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Post post = (Post) req.getSession().getAttribute("post");

        if (user == null || user.getUserType() != UserType.ADMIN) {
            resp.sendRedirect("/login.jsp");
        }
        String content = req.getParameter("content");

        Comments comments = new Comments();
        comments.setContent(content);
        //comments.setParentId(comments);
        comments.setUser(user);
        comments.setPost(post);

        commentsManager.addComments(comments);
        resp.sendRedirect("/currentPost");
    }
}
