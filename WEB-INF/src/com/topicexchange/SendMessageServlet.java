/**
    @author Foteinos Konstantinos (2023086)
*/

package com.topicexchange;

import java.sql.*;
import java.util.Map;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/sendMessageServlet")
public class SendMessageServlet extends BasicUIServlet {

    public SendMessageServlet(){
        super("topics","message");
    }

    @Override
    protected ExecutionResult executeCore(Map<String, String> parameters, Connection conn, HttpServletRequest req, HttpServletResponse resp)
            throws SQLException {

        int topicId = Integer.parseInt(parameters.get("topics"));

        int userId = -1;
        for(Cookie cookie:req.getCookies()){
            if(cookie.getName().equals("user-id")){
                userId = Integer.parseInt(cookie.getValue());
            }
        }

        ExecutionResult execRes;

        if(userId!=-1){
            PreparedStatement stmnt = conn.prepareStatement("INSERT INTO messages VALUES (null, ?, ?, ?, null)");
            stmnt.setInt(1,topicId);
            stmnt.setInt(2,userId);
            stmnt.setString(3,parameters.get("message").trim());
            int numRows = stmnt.executeUpdate();
            if(numRows>0){
                stmnt = conn.prepareStatement("SELECT count(*) AS num_msgs FROM messages WHERE topic_id = ?");
                stmnt.setInt(1,topicId);
                ResultSet res = stmnt.executeQuery();
                if(!res.next()){
                    execRes = new ExecutionResult(RESULT_TYPE.FAILURE, "Please try again.");
                }else{
                    execRes = new ExecutionResult(RESULT_TYPE.SUCCESS, "Number of messages for this topic: "+res.getInt("num_msgs"));
                }
            }else{
                execRes = new ExecutionResult(RESULT_TYPE.FAILURE, "Please try again.");
            }
            stmnt.close();
        }else{
            execRes = new ExecutionResult(RESULT_TYPE.FAILURE, "Please log in first.");
        }

        return execRes;
        
    }
}