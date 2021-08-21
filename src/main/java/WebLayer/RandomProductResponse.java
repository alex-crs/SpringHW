package WebLayer;

import BLL.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@WebServlet(name = "RandomProducts", urlPatterns = "/randomProductList")
public class RandomProductResponse extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(RandomProductResponse.class);
    private transient ServletConfig config;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        this.config = servletConfig;
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Get product list");
        Random random = new Random();
        resp.getWriter().println("<head><h3>Product list:</h3><hr></head>");
        for (int i = 1; i <= 10; i++) {
            resp.getWriter().println(new Product(i, "product" + i, random.nextInt(100)));
            resp.getWriter().println("<head><br></head>");
        }
        resp.getWriter().println("<head><hr></head>");
    }

    @Override
    public String getServletInfo() {
        return "BasicServlet";
    }

    @Override
    public void destroy() {
        logger.info("Servlet {} destroyed", getServletInfo());
    }
}
