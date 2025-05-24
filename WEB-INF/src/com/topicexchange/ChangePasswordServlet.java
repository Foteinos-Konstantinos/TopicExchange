/**
    @author Foteinos Konstantinos (2023086)
*/

package com.topicexchange;

import java.sql.*;
import java.util.Map;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/changePasswordServlet")
public class ChangePasswordServlet extends BasicUIServlet {

    public ChangePasswordServlet() {
        super("username", "old-password", "new-password");
    }

    @Override
    protected ExecutionResult executeCore(Map<String, String> parameters, Connection conn, HttpServletRequest req,
            HttpServletResponse resp) throws SQLException {
        PreparedStatement stmnt = conn.prepareStatement(
                "UPDATE users SET upasshash = ? WHERE id IN (SELECT id FROM users WHERE uname = ? and upasshash = ?)");
        stmnt.setString(1, Util.getHash256(parameters.get("new-password")));
        stmnt.setString(2, parameters.get("username"));
        stmnt.setString(3, Util.getHash256(parameters.get("old-password")));
        int numRows = stmnt.executeUpdate();
        stmnt.close();
        ExecutionResult res;
        if (numRows == 0) {
            res = new ExecutionResult(RESULT_TYPE.FAILURE, "Please try again.");
        } else {
            res = new ExecutionResult(RESULT_TYPE.SUCCESS, "Your credentials have been updated.");
        }
        return res;
    }

}