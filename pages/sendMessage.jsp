<!-- Foteinos Konstantinos (2023086) -->

<%@ page import="com.topicexchange.*" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <script type="text/javascript" src="../scripts/rendering.js"></script>
        <script>
            document.addEventListener("DOMContentLoaded",()=>{
                renderElements("..");
            });
        </script>
    </head>
    <body>
        
        <div id="main-content">

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
        
        </div>

    </body>
</html>