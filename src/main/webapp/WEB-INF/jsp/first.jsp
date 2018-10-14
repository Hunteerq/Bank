<%@ page import="org.springframework.jdbc.core.JdbcTemplate" %>
<%@ page import="org.springframework.jdbc.datasource.DriverManagerDataSource" %>
<html lang="en">
<head>
    <title>Login page</title>
</head>
<body>
<%
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl("jdbc:postgresql://localhost:5432/bank");
    dataSource.setUsername("postgres");
    dataSource.setPassword("kmb35");
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    String log=request.getParameter("login");
    String pass=request.getParameter("pass");
    if(log==null && pass==null){
%>
<form action="login" method="post">
    Login:<br>
    <input type="text" name="login"><br>
    <input type="Submit" value="Next">
</form>
<%
}
else if(log!=null && pass==null){
//if(jdbcTemplate.queryForInt("SELECT username FROM login WHERE username LIKE '"+log+"'")){
    if(log.equals("elo")){
%>
<form action="login" method="post">
    Haslo:<br>
    <input type="password" name="pass"><br>
    <input type="Submit" value="Next">
</form>
<%
    }else{
%>
Nie ma takiego uzytkownika
<form action="login" method="post">
    Login:<br>
    <input type="text" name="login"><br>
    <input type="Submit" value="Next">
</form>
<%
}
} else {
    //if(jdbcTemplate.queryForInt("SELECT username,password FROM login WHERE password LIKE '"+pass+"'")){
    if(pass.equals("siema")){
%>
Zalogowany
<%
        }else{
%>
Niepoprawne haslo
<form action="login" method="post">
    Haslo:<br>
    <input type="password" name="pass"><br>
    <input type="Submit" value="Next">
</form>
<%
    }}
%>
</body>
</html>