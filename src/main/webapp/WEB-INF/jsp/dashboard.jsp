<%@ page import="org.springframework.jdbc.core.JdbcTemplate" %>
<%@ page import="org.springframework.jdbc.datasource.DriverManagerDataSource" %>
<html lang="en">
<head>
    <title>Dashboard</title>
</head>
<body>
<%
    if(session.isNew()){
        session.setAttribute("login",null);
        session.setAttribute("password",null);
        session.setAttribute("invalidLogin","false");
        session.setAttribute("invalidPassword","false");
%>
<script>
    window.open("http://localhost:8080/log-in","_self")
</script>
<%
    }
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("org.postgresql.Driver");
    dataSource.setUrl("jdbc:postgresql://localhost:5432/bank");
    dataSource.setUsername("postgres");
    dataSource.setPassword("kmb35");
    JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
    session.setMaxInactiveInterval(60);
    if((session.getAttribute("login")!=null && session.getAttribute("password")!=null) || (session.getAttribute("login")!=null && request.getParameter("pass")!=null)) {
        String login = (String)session.getAttribute("login");
        String password;
        if(session.getAttribute("password")==null){
            password=request.getParameter("pass");
        }else{
            password=(String)session.getAttribute("password");
        }
        //if(jdbcTemplate.queryForInt("SELECT username FROM login WHERE username LIKE '"+login+"' AND password LIKE '"+password+"'")){
        if(login.equals("elo") && password.equals("siema")){
        session.setAttribute("password",password);
        session.setAttribute("invalidPassword","false");
%>
Logged in
<form action="sign-out">
    <input type="submit" value="Log out">
</form>
<%
}else{
            if(session.getAttribute("login")!=null && request.getParameter("pass")!=null){
            session.setAttribute("invalidPassword","true");
%>
<script>
    window.open("http://localhost:8080/password","_self")
</script>
<%
            }else{

%>
<script>
    window.open("http://localhost:8080/sign-out","_self")
</script>
<%
        }}}
%>
</body>
</html>