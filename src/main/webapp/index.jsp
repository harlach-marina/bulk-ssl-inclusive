<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <title>CSV Upload</title>
    <link rel="stylesheet" href="css/bootstrap.min.css"/>
    <link rel="stylesheet" href="css/style.css"/>
</head>
<body>
    <div class="container">
        <div class="row q-header">
            <div class=" col-xs-4">
                <a href="http://quadriconsulting.com" target="_blank">
                    <img src="img/logo.jpg" width="270px" alt="Quadri Consulting">
                </a>
            </div>
            <div class="col-xs-8">
                <form action="LogoutServlet" method="post" class="pull-right">
                    <h4>Hi <c:out value='${sessionScope.user}'/>!</h4><button type="submit" id="LogoutBtn" class="btn btn-default navbar-btn pull-right">Logout</button>
                </form>
            </div>
        </div>
        <div class="row q-content">
            <div class="col-xs-offset-4 col-xs-8">
                <h1><a href="uploadCSV">Current task</a></h1><br/><br/>
                <h1>Upload new file</h1>
                <form name="fileSendForm" method="POST" action="uploadCSV" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="fileCSV">File input</label>
                        <input type="file" name="fileCSV" id="fileCSV" accept="text/csv"/>
                    </div>
                    <button type="submit" class="btn btn-default"
                            onclick="
                             this.disabled=true;
                             document.getElementById('LogoutBtn').disabled = true;
                             this.form.submit();">
                        Upload
                    </button>
                </form>
            </div>
        </div>
    </div>

</body>
</html>
