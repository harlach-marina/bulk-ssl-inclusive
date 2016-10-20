<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />

    <!-- SEO -->
    <title>security</title>

    <!-- Bootstrap 3.1.0 -->
    <link href="css/bootstrap.css" rel="stylesheet" />
    <link href="https://fonts.googleapis.com/css?family=Bitter" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Lato" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet" />
</head>
<body>
<header id="section00">
    <div class="container">
        <div class="col-xs-12 col-sm-6 col-md-6 col-lg-4">
            <img src="img/icon-left.png">
        </div>
        <div class="col-xs-12 col-sm-6 col-sm-offset-0 col-md-6 col-md-offset-0 col-lg-4 col-lg-offset-4">
            <img id="icon-right" src="img/icon-right.png">
        </div>
    </div>
</header>
<section id="section01">
    <div class="container">
        <div class="col-xs-10 col-xs-offset-1 col-lg-4 col-lg-offset-8" if="login_bar">
            <button class="btn btn-default">Login</button>
            <button class="btn btn-primary">Sign up</button>
        </div>
    </div>
</section>
<section class="text-center" id="section02">
    <div class="container">
        <article class="col-md-8 col-md-offset-2">
            <h1>We ensure website security</h1>
            <h4>Comprehensive diagnostic and cyber protection of your website</h4>
            <hr>
            <h3>Find out how well your website it protected</h3>
            <div id="test" class="col-md-8 col-md-offset-2">
                <div class="form-group">
                    <input id="site-url" class="form-control" type="url" name="site-url" placeholder="Enter your website URL, *" required>
                    <div class="hidden form-control-feedback-url">Required field cannot be left blank</div>
                </div>
                <div class="form-group">
                    <input id="email-input" class="form-control" type="email" name="email" placeholder="E-mail, *" autocomplete="off" required>
                    <div class="hidden form-control-feedback-email">Please enter a valid email</div>
                </div>
                <button class="btn btn-transparent" type="submit">Test Now</button>
            </div>
        </article>
    </div>
</section>
<footer id="section03">
    <div class="container">
        <div class="row">
            <nav class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-0 col-md-4 col-md-offset-5 col-md-push-3">
                <ul>
                    <li>
                        <a href="#">
                            About us
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            Services
                        </a>
                    </li>
                    <li>
                        <a href="#">
                            Portfolio
                        </a>
                    </li>
                </ul>
            </nav>
            <div class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-0 col-md-3 col-md-offset-0 col-md-pull-9 left-column">&copy; 2016 Top Dev Central</div>
        </div>
    </div>
</footer>
<div class="modal fade" id="thxModal" tabindex="-1" role="dialog" aria-labelledby="warningModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="warningModalLabel">Thank you! Wait for several minutes and check your email, please.</h4>
            </div>
            <div class="modal-body">
                <button type="button" class="btn btn-primary" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>
<!-- jQuery 1.10.2 -->
<script src="js/jquery-1.10.2.min.js"></script>
<!-- jQuery Plugins -->
<script src="js/bootstrap.js"></script>
<script src="js/action.js"></script>
</body>
</html>