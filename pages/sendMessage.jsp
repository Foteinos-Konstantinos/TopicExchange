<!-- Foteinos Konstantinos (2023086) -->

<!---------------------------------------------------------------------------------------------------------------->

<%@ page import="com.topicexchange.*" %>
<%@ page import="java.sql.*" %>

<!---------------------------------------------------------------------------------------------------------------->

<!DOCTYPE html>
<html lang="en">
    <head>
        <title>TopicExchange</title>
        <link rel="stylesheet" href="../styles/main.css" />
        <link rel="stylesheet" href="../styles/forms.css" />
        <link rel="stylesheet" href="../styles/auth.css" />
        <link rel="stylesheet" href="../styles/index.css" />
        <meta charset="utf-8">
    </head>
    <body>

        <div id="top-bar">
            <a href="../index.html" class="back-to-index">TopicExchange</a>
            <a href="../pages/login.html" class="animated-button">Log In</a>
            <a href="../pages/changePassword.html" class="animated-button">Change Password</a>
            <a href="../pages/sendMessage.jsp" class="animated-button">Send message</a>
            <a href="../pages/createTopic.html" class="animated-button">Create topic</a>
            <a href="https://github.com/Foteinos-Konstantinos/TopicExchange" class="animated-button">GitHub Repository</a>
            <span id="connected-user">(No connected user)</span>
        </div>
        
        <div id="main-content">

        <!---------------------------------------------------------------------------------------------------------------->

        <%
        Cookie[] cookies = request.getCookies();
        String userId = null;
        if(cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if(cookies[i].getName().equals("user-id")){
                    userId=cookies[i].getValue();
                }
            }
        }
        if(userId!=null){
            ResultSet res = null;
            Connection conn = DatabaseConnector.establishConnection();
            if(conn!=null){
            %>
            <form action="../sendMessageServlet" method="post" id="send-message">
            <%
                try {
                    Statement stmnt = conn.createStatement();
                    res = stmnt.executeQuery("SELECT * FROM topics ORDER BY id");
                    if(!res.isBeforeFirst()){
                    %>
                <span>Cannot find any topic.</span>
                    <%
                    }else{
                    %>
                <label for="topics">Choose a topic:</label>
                <select id="topics" name="topics">
                    <%  
                        while(res.next()){
                        %>
                    <option value="<%= res.getString("ID") %>"><%= res.getString("NAME") %></option>
                        <%
                        }
                    %>
                </select>
                <label for="topics">Insert the message:</label>
                <textarea name="message" required></textarea>
                <button id="submit-button" type="submit">Send message</button>
            </form>
                    <%
                    }
                    res.close();
                    stmnt.close();
                } catch (SQLException e) {
                    out.println(e);
                }
            }
        }else{
            %>
            <div class="failure">
                <h1>Operation failed</h1>
                <span>Please log in first.</span>
            </div>
            <%
        }
        %>

        <!---------------------------------------------------------------------------------------------------------------->
        
        </div>


        <div id="bottom-bar">
            <span>Foteinos Konstantinos (it2023086) @ DIT HUA</span>
            <span>Web Dev</span>
        </div>

        <!---------------------------------------------------------------------------------------------------------------->

        <!---------------------------------------------------------------------------------------------------------------->

    </body>
</html>