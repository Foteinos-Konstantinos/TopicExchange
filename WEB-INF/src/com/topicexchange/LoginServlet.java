/**
 * @author Foteinos Konstantinos (it2023086)
 */

package com.topicexchange;

import java.io.*;

import java.sql.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        resp.setContentType("text/html");

        PrintWriter pw = resp.getWriter();
        Connection conn = DatabaseConnector.establishConnection();
        if(conn==null){
            pw.write(Util.operationFailed("Cannot establish connection with the DB."));
            pw.close();
        }else{
            try {
                PreparedStatement stmnt = conn.prepareStatement("SELECT id FROM users WHERE uname = ? and upasshash = ?");
                stmnt.setString(1,username);
                stmnt.setString(2,Util.getHash256(password));
                ResultSet res = stmnt.executeQuery();
                if(!res.next()){
                    pw.write(Util.operationFailed("Please try again."));
                }else{
                    int id = res.getInt("ID");
                    resp.addCookie(new Cookie("user-id",Integer.toString(id)));
                    
                    //  Optional
                    resp.addCookie(new Cookie("user-name",username));

                    pw.write(Util.operationSucceeded("The given credentials are valid."));
                }
                res.close();
                stmnt.close();
            } catch (SQLException e) {
                pw.write(Util.operationFailed("Please try again."));
            }
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("\n=== CANNOT CLOSE DB CONNECTION ===\n");
                e.printStackTrace();
                //pw.write(Util.operationFailed("Cannot close the connection."));
            }
            pw.close();
        }
    }

}