<%-- 
    Document   : jeu
    Created on : 3 mai 2021, 19:44:27
    Author     : pauline
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <h1>Hello World!</h1>
    <c:forEach items="${requestScope.joueur}" var="sp">
        <h3>${sp}</h3>
    </c:forEach>
    </body>
</html>
