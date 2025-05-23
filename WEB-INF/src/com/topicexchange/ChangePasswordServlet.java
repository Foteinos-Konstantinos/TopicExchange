/**
    @author Foteinos Konstantinos (2023086)
*/

package com.topicexchange;

import java.io.*;

import java.sql.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/changePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String username = req.getParameter("username");
        String oldPassword = req.getParameter("old-password");
        String newPassword = req.getParameter("new-password");

        resp.setContentType("text/html");

        PrintWriter pw = resp.getWriter();
        Connection conn = DatabaseConnector.establishConnection();
        if(conn==null){
            pw.write(Util.operationFailed("Cannot establish connection with the DB."));
            pw.close();
        }else{
            try {
                PreparedStatement stmnt = conn.prepareStatement("UPDATE users SET upasshash = ? WHERE id IN (SELECT id FROM users WHERE uname = ? and upasshash = ?)");
                stmnt.setString(1,Util.getHash256(newPassword));
                stmnt.setString(2,username);
                stmnt.setString(3,Util.getHash256(oldPassword));
                int numRows = stmnt.executeUpdate();
                stmnt.close();
                if(numRows==0){
                    pw.write(Util.operationFailed("Please try again."));
                }else{
                    pw.write(Util.operationSucceeded("Your credentials have been updated."));
                }
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