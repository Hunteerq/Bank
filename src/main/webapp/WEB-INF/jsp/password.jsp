<%@ page import="org.springframework.jdbc.core.JdbcTemplate" %>
<%@ page import="org.springframework.jdbc.datasource.DriverManagerDataSource" %>
<html lang="en">
<head>
    <title>Password page</title>
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
    if(session.getAttribute("login")!=null && session.getAttribute("password")!=null) {
%>
<script>
    window.open("http://localhost:8080/dashboard","_self")
</script>
<%
}else{
if(request.getParameter("login")==null && session.getAttribute("invalidPassword").equals("false")){
    session.invalidate();
%>
<script>
    window.open("http://localhost:8080/log-in","_self")
</script>
<%
    }else{
        String login;
        if(!session.getAttribute("invalidPassword").equals("true")){
            login = request.getParameter("login");
        }else{
            login = (String)session.getAttribute("login");
        }
        //if(jdbcTemplate.queryForInt("SELECT username FROM login WHERE username LIKE '"+login+"'")){
        if(login.equals("elo")){
            if(session.getAttribute("invalidPassword").equals("false")){
                session.setAttribute("login",request.getParameter("login"));
            }
            session.setAttribute("invalidLogin","false");
%>
<%
    if(session.getAttribute("invalidPassword").equals("true")){
%>
Invalid password
<%
    }
%>
<form action="dashboard" method="post">
    Password:<br>
    <input type="text" name="pass"><br>
    <input type="Submit" value="Next">
</form>
<%
        }else{
            session.setAttribute("invalidLogin","true");
%>
<script>
    window.open("http://localhost:8080/log-in","_self")
</script>
<%
    }}}
%>
</body>
</html>