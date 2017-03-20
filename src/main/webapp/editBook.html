<%@ page import="LMS.Entity.Book" %>
<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="LMS.Service.AdminService" %>
<%@ page import="LMS.Entity.Genre" %>
<%@ page import="LMS.Entity.Author" %>
<%@ page import="LMS.Entity.Publisher" %>
<%@ page import="java.util.List" %>
<
<%
    Book book=(Book)request.getAttribute("book");
    ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
    AdminService service = (AdminService) ac.getBean("adminService");
    List<Genre> genreList=service.readAllGenres();
    List<Author> authorList=service.readAllAuthors();
    List<Publisher> publisherList=service.getAllPublishers();
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
    <h3>Hello Admin - Edit Book Details.</h3>
    <form action="editBook" method="post">
        <p>Enter a new Book Name</p> <input style="width:200px" type="text" required="required" name="bookName" value="<%=book.getTitle()%>"> <br />
        <input type="hidden" name="bookId" value="<%=book.getBookId()%>">
        <p>Select one or more Authors to associate your Book</p>
        <select name="authorIds" style="width:200px" multiple="multiple" size="5">
            <%
                for (Author a : authorList) {
            %>
            <option value=<%=a.getAuthorId()%>><%=a.getAuthorName()%></option>
            <%
                }
            %>
        </select><br />
        <p>Select one or more Genres to associate your Book</p>
        <select name="genreIds" style="width:200px" multiple="multiple" size="5">
            <%
                for (Genre a : genreList) {
            %>
            <option value=<%=a.getGenre_id()%>><%=a.getGenre_name()%></option>
            <%
                }
            %>
        </select><br />
        <p>Select the publisher of your book</p>
        <select name="publisherId" style="width:200px" size="5">
            <%
                for (Publisher a : publisherList) {
            %>
            <option value=<%=a.getPublisherId()%>><%=a.getPublisherName()%></option>
            <%
                }
            %>
        </select><br />

        <button type="submit" class="btn btn-sm btn-success">Edit Book</button>
    </form>
</div>
</body>
</html>