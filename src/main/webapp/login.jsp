<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <meta charset="utf-8">
    <title>Login Page</title>
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
    <div class="container">
        <div class="row q-header">
            <div class="col-xs-4">
                <a href="http://quadriconsulting.com" target="_blank">
                    <img src="img/logo.jpg" width="270px" alt="Quadri Consulting">
                </a>
            </div>
            <div class="col-xs-8">
                <form action="AuthServlet" method="post" class="pull-right">
                    <div class="form-inline form-group">
                        <label>Sign in with</label>
                        <select class="form-control" name="authOpts">
                            <option>LinkedIn</option>
                            <option>Heroku</option>
                        </select>
                        <label>account</label>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-default pull-right">Login</button>
                    </div>
                </form>
            </div>
        </div>
        <c:if test="${not empty errorMessage}">
            <div class="row q-content">
                <p class="bg-danger"><c:out value="${errorMessage}"/></p>
            </div>
        </c:if>
        <c:if test="${not empty param.errorMessage}">
            <div class="row q-content">
                <p class="bg-danger"><c:out value="${param.errorMessage}"/></p>
            </div>
        </c:if>
        <div class="row">
            <div class="col-xs-offset-2 col-xs-8">
    
                <h1 align="center">If you want to test many SSL webservers, you've come to the right place</h1> 
            </div>
        </div>
    </div>
    <p>&nbsp;&nbsp;&nbsp;<font size="1">Powered by SSLLABS</font></p>
</body>
</html>
