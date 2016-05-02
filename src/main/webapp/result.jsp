<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <title>Analysis</title>
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-xs-offset-3 col-xs-6">
                <c:if test="${percent == null || percent ==100}"><h1>Analysis finished</h1></c:if>
                <c:if test="${percent != null && percent < 100}">
                    <div class="row">
                        <div class="col-xs-10">
                            <h1>Analysis in progress ... <c:out value='${percent}'/>%</h1>
                        </div>
                        <div class="col-xs-2">
                            <img style="margin-top: 15px" width="50" height="45" src="img/hour-glass.gif" alt="Hour glass" />
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12">
                            Processing: <c:out value='${currentUrl}'/>
                        </div>
                    </div>

                    <script>
                        setTimeout(function(){
                            window.location.href = window.location.href;
                        }, 5000);
                    </script>
                </c:if>
            </div>
        </div>
        <div class="row">
            <div class="col-xs-offset-3 col-xs-2">
                <a href="/" title="Back to the Main page">Main page</a>
            </div>
        </div>
    </div>
</body>
</html>
