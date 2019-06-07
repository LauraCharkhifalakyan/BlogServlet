package servlet;

import manager.CategoryManager;
import manager.PostManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/home")
public class HomeServlet extends HttpServlet {

    private PostManager postManager = new PostManager();
    CategoryManager categoryManager = new CategoryManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, ServletException, IOException {
        req.setAttribute("posts", postManager.getAllPost());
        req.setAttribute("categories",categoryManager.getAllCategories());
        req.getRequestDispatcher("/WEB-INF/home.jsp").forward(req, resp);
    }
}