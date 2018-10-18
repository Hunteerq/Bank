<%@ page import="org.springframework.jdbc.core.JdbcTemplate" %>
<%@ page import="org.springframework.jdbc.datasource.DriverManagerDataSource" %>
<html lang="en">
<head>
    <title>Login page</title>
</head>
<body>
<%
    if(session.isNew()){
        session.setAttribute("login",null);
        session.setAttribute("password",null);
        session.setAttribute("invalidLogin","false");
        session.setAttribute("invalidPassword","false");
    }
    session.setMaxInactiveInterval(60);
    if(session.getAttribute("login")!=null && session.getAttribute("password")!=null) {
%>
<script>
    window.open("http://localhost:8080/dashboard","_self")
</script>
<%
    }else{

%>
<%
    if(session.getAttribute("invalidLogin").equals("true")){
%>
User doesn't exists
<%
    }
%>
<form action="password" method="post">
    Login:<br>
    <input type="text" name="login"><br>
    <input type="Submit" value="Next">
</form>
<%
}
%>
</body>
</html>