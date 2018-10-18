<%@ page import="org.springframework.jdbc.core.JdbcTemplate" %>
<%@ page import="org.springframework.jdbc.datasource.DriverManagerDataSource" %>
<html lang="en">
<head>
    <title>Login page</title>
</head>
<body>
<%
    session.invalidate();
%>
Logged out
<form action="log-in">
    <input type="submit" value="Back">
</form>
</body>
</html>