package database; /**
 * Created by mtush on 2/1/2017.
 */

import java.sql.*;


public class SQLiteConnection {


    public static Connection Connector()
    {
        Connection conn=null;
        try{
            //https://bitbucket.org/xerial/sqlite-jdbc/downloads
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:db/resultSystem.db");

            return conn;

        }catch(ClassNotFoundException | SQLException e)
        {
            System.out.println(e);

        }
        return null;
    }

}
