package servlet;

import manager.CategoryManager;
import model.Category;
import model.User;
import model.UserType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/admin/addCategory")
public class AddCategoryServlet extends HttpServlet {

    private CategoryManager categoryManager = new CategoryManager();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        Category category = new Category();
        category.setName(name);
        categoryManager.addCategory(category);
        resp.sendRedirect("/admin/home");
    }
}
