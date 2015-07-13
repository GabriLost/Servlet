import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.myrmi.Result;
import java.sql.SQLException;


public class MyServlet extends HttpServlet implements Servlet
{
    private DBServer dataBase = null;
    private ArrayList<Result> NewsList = null;
    
    @Override
    public void init(ServletConfig config)throws ServletException
    {
                         
    }    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try 
        {
            dataBase = new DBServer("news_agency","localhost",3306);
        } 
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            System.out.println("Error" + ex.getMessage()); } 
        NewsList = dataBase.ShowNews();
        response.setContentType("text/html;charset=UTF-8");
        try
        (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Новости </title>");
            out.println("<link href =\"newcss.css\"" + "rel=\"stylesheet\" type=\"text/css\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<h3> Список новостей </h1>");

            //Таблица
            out.println("<table>");
            //Заголовок
            out.println("<tr>");
            out.println("<th>№</th>");
            out.println("<th>Название</th>");
            out.println("<th>Содержание</th>");
            out.println("<th>Тема</th>");
            out.println("</tr>");

            for(int i = 0;i < NewsList.size() ; i++)
            {
                out.println("<tr>");
                out.println("<th>" + i + "</th>");
                out.println("<th>" + NewsList.get(i).news_title + "</th>");
                out.println("<th>" + NewsList.get(i).text+ "</th>");
                out.println("<th>" + NewsList.get(i).title + "</th>");
                out.println("</tr>");                  
            }
            out.println("</body>");
            out.println("</html>");
        }
      
    }
    
    @Override
    public String getServletInfo() 
    {
        return "Список новостей";
    }
}
