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
        <c:if test="${not empty param.errorMessage}">
            <div class="row q-content">
                <p class="bg-danger"><c:out value="${param.errorMessage}"/></p>
            </div>
        </c:if>
        <div class="row q-content">
            <div class="col-xs-offset-3 col-xs-8">
                <h1><a href="uploadCSV">Current task</a></h1><br/><br/>
                <h1>Upload text file containing<br/> lots of URLs list <small>(with or without https prefix)</small></h1>
                <form name="fileSendForm" method="POST" action="uploadCSV" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="fileCSV">File input</label>
                        <input type="file" name="fileCSV" id="fileCSV" accept="text/csv"/>
                    </div>
                    <input type="button" class="btn btn-default" value="Upload" onclick="if(validate()) {this.form.submit()}"/>
                </form>
                The result is in your mail box
            </div>
        </div>
    </div>

    <script type="text/javascript">
        function validate() {
            var fileMaxSize = 1048576;
            var isFileSizeValid = true;
            var files = document.getElementById("fileCSV").files;
            if (files) {
                var file = files[0];
                if (file && file.size && file.size > fileMaxSize) {
                    isFileSizeValid = false;
                }
            }
            if(isFileSizeValid) {
                return true;
            } else {
                alert("File size shouldn't be more than 1Mb");
                return false;
            }
        }
    </script>
</body>
</html>
