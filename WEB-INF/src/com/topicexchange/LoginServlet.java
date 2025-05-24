/**
 * @author Foteinos Konstantinos (it2023086)
 */

package com.topicexchange;

import java.sql.*;
import java.util.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/loginServlet")
public class LoginServlet extends BasicUIServlet {

    public LoginServlet() {
        super("username", "password");
    }

    @Override
    protected ExecutionResult executeCore(Map<String, String> parameters, Connection conn, HttpServletRequest req,
            HttpServletResponse resp) throws SQLException {
        PreparedStatement stmnt = conn.prepareStatement("SELECT id FROM users WHERE uname = ? and upasshash = ?");
        stmnt.setString(1, parameters.get("username"));
        stmnt.setString(2, Util.getHash256(parameters.get("password")));
        ResultSet res = stmnt.executeQuery();
        ExecutionResult execRes;
        if (!res.next()) {
            execRes = new ExecutionResult(RESULT_TYPE.FAILURE, "Please try again.");
        } else {
            int id = res.getInt("ID");
            resp.addCookie(new Cookie("user-id", Integer.toString(id)));
            // Optional
            resp.addCookie(new Cookie("user-name", parameters.get("username")));
            execRes = new ExecutionResult(RESULT_TYPE.SUCCESS, "Your credentials are valid.");
        }
        res.close();
        stmnt.close();
        return execRes;
    }

}