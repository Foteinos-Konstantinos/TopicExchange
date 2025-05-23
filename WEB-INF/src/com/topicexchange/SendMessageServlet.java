/**
    @author Foteinos Konstantinos (2023086)
*/

package com.topicexchange;

import java.io.*;

import java.sql.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/sendMessageServlet")
public class SendMessageServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        int topic_id = Integer.parseInt(req.getParameter("topics"));
        String message = req.getParameter("message");
        int user_id = -1;
        for(Cookie cookie:req.getCookies()){
            if(cookie.getName().equals("user-id")){
                user_id = Integer.parseInt(cookie.getValue());
            }
        }

        PrintWriter pw = resp.getWriter();

        if(user_id!=-1){

            resp.setContentType("text/html");

            Connection conn = DatabaseConnector.establishConnection();
            if(conn==null){
                pw.write(Util.operationFailed("Cannot establish connection with the DB."));
            }else{
                try {
                    PreparedStatement stmnt = conn.prepareStatement("INSERT INTO messages VALUES (null, ?, ?, ?, null)");
                    stmnt.setInt(1,topic_id);
                    stmnt.setInt(2,user_id);
                    stmnt.setString(3,message.trim());
                    int numRows = stmnt.executeUpdate();
                    if(numRows>0){
                        stmnt = conn.prepareStatement("SELECT count(*) AS num_msgs FROM messages WHERE topic_id = ?");
                        stmnt.setInt(1,topic_id);
                        ResultSet res = stmnt.executeQuery();
                        if(!res.next()){
                            pw.write(Util.operationFailed("Please try again."));
                        }else{
                            pw.write(Util.operationSucceeded("Number of messages for this topic: "+res.getInt("num_msgs")));
                        }
                    }else{
                        pw.write(Util.operationFailed("Please try again."));
                    }
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
            }
        }else{
            pw.write(Util.operationFailed("Please log in first."));
        }

        pw.close();

    }
}