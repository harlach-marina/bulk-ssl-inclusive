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
        <div class="hidden-xs col-sm-6 col-md-6 col-lg-4">
            <img src="img/icon-left.png">
        </div>
        <div class="col-xs-12 col-sm-6 col-sm-offset-0 col-md-6 col-md-offset-0 col-lg-4 col-lg-offset-4">
            <img id="icon-right" src="img/icon-right.png">
        </div>
    </div>
</header>
<%--<section id="section01">--%>
    <%--<div class="container">--%>
        <%--<div class="col-xs-10 col-xs-offset-1 col-lg-4 col-lg-offset-8" if="login_bar">--%>
            <%--<button class="btn btn-default">Login</button>--%>
            <%--<button class="btn btn-primary">Sign up</button>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</section>--%>
<section class="text-center" id="section02">
    <div class="container">
        <article class="col-md-8 col-md-offset-2">
            <h1>We ensure website security</h1>
            <h4>Comprehensive diagnostic and cyber protection of your website</h4>
            <hr>
            <h3>Find out how well your website it protected</h3>
            <div id="test" class="col-md-8 col-md-offset-2">
                <div class="form-group">
                    <input id="site-url" autofocus class="form-control" type="url" name="site-url" placeholder="Enter website URL, *" required>
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
<section id="section03">
    <div class="container">
        <div class="row text-center">
            <div class="col-md-10 col-md-offset-1">
                <h2>Get a detailed report about your digital business</h2>
                <h4>Digital Security Scanner Measurement</h4>
                <img class="img-responsive" src="img/chart.png">
            </div>
        </div>
        <div class="row">
            <div id="pbar-reputation" class="col-md-10 col-md-offset-1">
                <h2>Reputation</h2>
                <div class="col-md-10 col-md-offset-2">
                    <div class="progress">
                        <div class="progress-bar progress-bar-info" data-values="48">0</div>
                    </div>
                    <b>Your reputation is under threat.</b>
                    <p>Search engines such as Google may notify your users to avoid using your site because it contains malware or other threats.</p>
                </div>
            </div>
            <div id="pbar-communication" class="col-md-10 col-md-offset-1">
                <h2>Communication</h2>
                <div class="col-md-10 col-md-offset-2">
                    <div class="progress">
                        <div class="progress-bar progress-bar-warning" data-values="68">0</div>
                    </div>
                    <b><i class="glyphicon glyphicon-warning-sign"></i>  TLS Neg</b>
                    <p>Checks to ensure Email transmission is encrypted and secure from  eavesdroppers.</p>
                </div>
            </div>
            <div class="col-md-10 col-md-offset-1">
                <h2>Compliance</h2>
                <div class="col-md-10 col-md-offset-2">
                    <div class="progress">
                        <div class="progress-bar progress-bar-info" data-values="40">0</div>
                    </div>
                    <b>For your business to be PCI Compliant, you need to stop running SSL 2.0, SSL 3.0 or TLS 1.0. Reconfigure or upgrade to TLS 1.2.</b>
                </div>
            </div>
            <div id="pbar-security" class="col-md-10 col-md-offset-1">
                <h2>Security (Web)</h2>
                <div class="col-md-10 col-md-offset-2">
                    <div class="progress">
                        <div class="progress-bar progress-bar-danger" data-values="8">0</div>
                    </div>
                    <b><i class="glyphicon glyphicon-warning-sign"></i>  Content-Security-Policy</b>
                    <p>Your site is not protect from XSS attacks. You need to prevent user  browser from loading malicious contents.</p>
                    <b><i class="glyphicon glyphicon-warning-sign"></i>  X-Frame-Options</b>
                    <p>Your site isn't defended against attacks like clickjacking.</p>
                    <b><i class="glyphicon glyphicon-warning-sign"></i>  Your reputation is under threat. Search engines such as Google </b>
                    <p>X-XSS-Protection sets the configuration for the cross-site scripting filter built into most browsers. Recommended value "X-XSS-Protection: 1; mode=block".</p>
                    <b><i class="glyphicon glyphicon-warning-sign"></i>  X-Content-Type-Options</b>
                    <p>Your site isn't preventing an attacker from trying to MIME-sniff the content.</p>
                </div>
            </div>
            <div id="pbar-confidentiality" class="col-md-10 col-md-offset-1">
                <h2>Confidentiality</h2>
                <div class="col-md-2">Certificate</div>
                <div class="col-md-10">
                    <div class="progress">
                        <div class="progress-bar progress-bar-danger" data-values="15">0</div>
                    </div>
                </div>
                <div class="col-md-2">Protocol Support</div>
                <div class="col-md-10">
                    <div class="progress">
                        <div class="progress-bar progress-bar-info" data-values="24">0</div>
                    </div>
                </div>
                <div class="col-md-2">Key Exchange</div>
                <div class="col-md-10">
                    <div class="progress">
                        <div class="progress-bar progress-bar-warning" data-values="51">0</div>
                    </div>
                </div>
                <div class="col-md-2">Cipher Strength</div>
                <div class="col-md-10">
                    <div class="progress">
                        <div class="progress-bar progress-bar-danger" data-values="10">0</div>
                    </div>
                </div>
                <div class="col-md-10 col-md-offset-2">
                    <p>Your site uses expired or not trusted certificate.</p>
                    <p>Your site support old or nonsecure protocols.</p>
                    <p>Your site is insecure as a result of  SSL handshake failure.</p>
                </div>
            </div>
            <div class="col-md-10 col-md-offset-1">
                <h2>Integrity</h2>
                <div class="col-md-10 col-md-offset-2">
                    <div class="progress">
                        <div class="progress-bar progress-bar-danger" data-values="18">0</div>
                    </div>
                    <p>Your site is not using strong cipher</p>
                </div>
            </div>
            <div class="col-md-10 col-md-offset-1">
                <h2>Availability</h2>
                <div class="col-md-10 col-md-offset-2">
                    <div class="progress">
                        <div class="progress-bar progress-bar-success" data-values="98">0</div>
                    </div>
                </div>
            </div>
            <div class="col-md-10 col-md-offset-1">
                <h2>Security (DNS)</h2>
                <div class="col-md-10 col-md-offset-2">
                    <div class="progress">
                        <div class="progress-bar progress-bar-success" data-values="80">0</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<section id="section04">
    <div class="container">
        <article class="row">
            <div class="col-xs-4 col-xs-offset-4 col-md-2 col-md-offset-5">
                <a class="btn btn-transparent" id='Go_Top' href="#">Test Now</a>
            </div>
        </article>
    </div>
</section>
<footer id="section05">
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
                <button type="button" autofocus class="btn btn-primary" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="errorModal" tabindex="-1" role="dialog">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">Something went wrong. Try again later</h4>
            </div>
            <div class="modal-body">
                <button type="button" autofocus class="btn btn-warning" data-dismiss="modal">OK</button>
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