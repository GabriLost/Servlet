import com.myrmi.Result;
import java.sql.*;
import java.util.ArrayList;


public class DBServer {

    private Connection con = null; // соединение с БД 
    private Statement stmt = null; // оператор 
    public ArrayList<Result> lst = null;
    public DBServer(String DBName, String ip, int port) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        try {
            System.out.println("подключаю драйвер");
            Class.forName("com.mysql.jdbc.Driver").newInstance();        
            String url = "jdbc:mysql://"+ip+":"+port+"/"+DBName;// устанавливаю соединение с БД  
            System.out.println("устанавливаю соединение с БД");
            con = DriverManager.getConnection(url, "root", "aker6230"); 
            stmt = con.createStatement();
        } catch (SQLException e)
        {
            System.out.println("Невозможно подключиться к базе данных!");
            System.out.println(" >> "+e.getMessage());
            System.exit(0);
        } 
    };
    public synchronized ArrayList<Result> ShowNews(){
        ArrayList<Result> list = new ArrayList();
        String sql = "SELECT n.title AS news_title, n.text, c.title FROM  `news` n,  `category` c WHERE c.category_id = n.category_id_fk"; 
        ResultSet rs; 
        Result o;
        try {
            rs = stmt.executeQuery(sql);
            while (rs.next()) 
            { 
                o = new Result();
                String news_title = rs.getString("news_title");
                String text = rs.getString("text");
                String title = rs.getString("title");
                o.news_title = news_title;
                o.text = text;
                o.title = title;
                list.add(o);
            } 
            rs.close(); 
        } catch (SQLException ex) {
            o = new Result();
            o.news_title = ex.getMessage();
            o.text = null;
            o.title = null;
            list.add(o);
            
            return list;
        }
        return list;
    };
    boolean Disconnect() throws SQLException{
        con.close();  // завершаю соединение
        return true;
    };
}
