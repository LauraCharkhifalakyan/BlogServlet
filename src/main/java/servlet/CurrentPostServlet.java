package servlet;

import manager.CommentsManager;
import manager.PostManager;
import manager.UserManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/currentPost")
public class CurrentPostServlet extends HttpServlet {

    UserManager userManager = new UserManager();
    PostManager postManager = new PostManager();
    CommentsManager commentsManager = new CommentsManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("user", userManager.getAllUsers());
        req.setAttribute("posts", postManager.getAllPost());
        req.setAttribute("allComments", commentsManager.getAllComments());
        req.getRequestDispatcher("/WEB-INF/currentPost.jsp").forward(req, resp);
    }
}
