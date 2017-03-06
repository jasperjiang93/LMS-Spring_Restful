<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="LMS.Service.LibrarianService" %>
<%@ page import="LMS.Entity.Book_Copy" %>
<%@ page import="LMS.Entity.Book" %>
<%@ page import="LMS.Entity.Library" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<%    ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
    LibrarianService service = (LibrarianService) ac.getBean("librarianService");
    Book_Copy book_copy=(Book_Copy) request.getAttribute("book_copy");
    Book book=service.getBookByPk(book_copy.getBookId());
    Library library=service.getLibraryByPk(book_copy.getBranchId());
%>



<!DOCTYPE html>
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
<head>
    <div class="jumbotron" align="center">
        <h1>GCIT Library Management System</h1>
    </div>
</head>
<body>
<div class="container" align="center">
    <h3>Hello Librarian - Enter Book Copy Details.</h3>
    <form action="updateCopy" method="post">
        <p>Book: <%=book.getTitle()%></p><br />
        <p>Branch: <%=library.getLibraryName()%></p><br />
        <p>Change number of Copye To:</p>
        <input type="text" name="noOfCopy" value="<%=book_copy.getNoOfCopies()%>"> <br />
        <input type="hidden" name="bookId" value="<%=book_copy.getBookId()%>">
        <input type="hidden" name="branchId" value="<%=book_copy.getBranchId()%>">
        <br>
        <button type="submit" class="btn btn-sm btn-warning">Modify</button>
    </form>
</div>
</body>
</html>


</body>
</html>
