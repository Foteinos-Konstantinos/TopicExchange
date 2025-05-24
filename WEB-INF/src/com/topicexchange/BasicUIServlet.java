/**
    @author Foteinos Konstantinos (2023086)
*/

package com.topicexchange;

import java.io.*;

import java.sql.*;
import java.util.*;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public abstract class BasicUIServlet extends HttpServlet {

    private final String[] paramsNames;

    public enum RESULT_TYPE {
        FAILURE, SUCCESS
    }

    public static class ExecutionResult {
        public RESULT_TYPE type;
        public String msg;

        public ExecutionResult(RESULT_TYPE type, String msg) {
            this.type = type;
            this.msg = msg;
        }
    }

    protected BasicUIServlet(String... params) {
        this.paramsNames = params;
    }

    private static Map<String, String> getParameters(HttpServletRequest req, String... params) {
        Map<String, String> res = new HashMap<>();
        for (String param : params) {
            res.put(param, req.getParameter(param));
        }
        return res;
    }

    /**
     * DO NOT CLOSE EITHER CONNECTION OR PRINT WRITER!!
     */
    abstract protected ExecutionResult executeCore(Map<String, String> parameters, Connection conn,
            HttpServletRequest req, HttpServletResponse resp) throws SQLException;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, String> parameters = getParameters(req, paramsNames);

        resp.setContentType("text/html");

        PrintWriter pw = resp.getWriter();
        Connection conn = DatabaseConnector.establishConnection();
        if (conn == null) {
            pw.write(Util.operationFailed("Cannot establish connection with the DB."));
        } else {
            try {
                ExecutionResult res = this.executeCore(parameters, conn, req, resp);
                if (res.type == RESULT_TYPE.SUCCESS) {
                    pw.write(Util.operationSucceeded(res.msg));
                } else {
                    pw.write(Util.operationFailed(res.msg));
                }
            } catch (SQLException e) {
                pw.write(Util.operationFailed("Please try again."));
            }
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println("\n=== CANNOT CLOSE DB CONNECTION ===\n");
                e.printStackTrace();
            }
        }
        pw.close();
    }

}