/**
    @author Foteinos Konstantinos (2023086)
*/

package com.topicexchange;

import java.io.*;
import java.sql.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/createTopicServlet")
public class CreateTopicServlet extends HttpServlet {

    private class IncomeData{
        public String name;
        public String description;
        public int userId;
    }

    private class OutcomeData {
        public int rowsAffected;
        public OutcomeData(int num){
            this.rowsAffected=num;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        IncomeData topicData = ServletUtil.getRequestData(IncomeData.class, req);
        Connection conn = DatabaseConnector.establishConnection();
        if(conn==null){
            ServletUtil.sendResponseData(null, resp);
        }else{
            PreparedStatement stmnt;
            try {
                stmnt = conn.prepareStatement("INSERT INTO topics VALUES (null, ?, ?)");
                stmnt.setString(1, topicData.name.trim());
                stmnt.setString(2, topicData.description.trim());
                int numRows = stmnt.executeUpdate();
                stmnt.close();
                if(numRows>0){
                    ServletUtil.sendResponseData(new OutcomeData(numRows), resp);
                }else{
                    ServletUtil.sendResponseData(null, resp);
                }
            } catch (SQLException e) {
                ServletUtil.sendResponseData(null, resp);
            }
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("\n=== CANNOT CLOSE DB CONNECTION ===\n");
                e.printStackTrace();
            }
        }
    }

}