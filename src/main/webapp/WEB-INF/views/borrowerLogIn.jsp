<%@ page import="LMS.Service.BorrowerService" %>
<%@ page import="LMS.Entity.Library" %>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%
    ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
    BorrowerService service = (BorrowerService) ac.getBean("borrowerService");
    List<Library> libraries=service.readAllLibraries();
%>



<!DOCTYPE html>
<!-- saved from url=(0069)https://blackrockdigital.github.io/startbootstrap-sb-admin/index.html -->
<html lang="en"><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="http://54.83.8.59/site/wp-content/uploads/2014/02/favicon.png">
    <title>GCIT-LMS</title>

    <!-- Bootstrap Core CSS -->
    <link href="template/content/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="template/content/sb-admin.css" rel="stylesheet">

    <!-- Morris Charts CSS -->
    <link href="template/content/morris.css" rel="stylesheet">

    <!-- Custom Fonts -->
    <link href="template/content/font-awesome.min.css" rel="stylesheet" type="text/css">

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->


    <style type="text/css">object,embed{                -webkit-animation-duration:.001s;-webkit-animation-name:playerInserted;                -ms-animation-duration:.001s;-ms-animation-name:playerInserted;                -o-animation-duration:.001s;-o-animation-name:playerInserted;                animation-duration:.001s;animation-name:playerInserted;}                @-webkit-keyframes playerInserted{from{opacity:0.99;}to{opacity:1;}}                @-ms-keyframes playerInserted{from{opacity:0.99;}to{opacity:1;}}                @-o-keyframes playerInserted{from{opacity:0.99;}to{opacity:1;}}                @keyframes playerInserted{from{opacity:0.99;}to{opacity:1;}}</style></head>

<body>

<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">GCIT-Borrower</a>
        </div>
        <!-- Top Menu Items -->

        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->

        <!-- /.navbar-collapse -->
    </nav>


    <div id="page-wrapper">
        <div class="container-fluid">
            <div>
                <form action="borrower/getBorrower">
                    <input type="text"   class="selectpicker" placeholder="Please Enter your card number:" name="cardNo" id="cardNo">
                    <select id="branchId" name="branchId" onchange="changeFunc();">
                        <%for(Library a:libraries)
                            out.print("<option value=\""+a.getLibraryId()+"\">"+a.getLibraryName()+"</option>");
                        %>
                    </select>
                    <button type="submit" class="btn btn-sm btn-success">Submit</button>
                </form>
            </div>
            <!-- /.container-fluid -->
            <!-- /#page-wrapper -->
        </div>
    </div>
</div>
<!-- /#wrapper -->

<!-- jQuery -->
<script src="template/content/jquery.js.download"></script>

<!-- Bootstrap Core JavaScript -->
<script src="template/content/bootstrap.min.js.download"></script>

<!-- Morris Charts JavaScript -->
<script src="template/content/raphael.min.js.download"></script>
<script src="template/content/morris.min.js.download"></script>
<script src="template/content/morris-data.js.download"></script>




</body></html>