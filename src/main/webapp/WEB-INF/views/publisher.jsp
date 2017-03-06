<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="LMS.Service.AdminService" %>
<%@ page import="LMS.Entity.Publisher" %>
<%@ page import="java.util.List" %>
<%@ page import="LMS.Entity.Book" %>
<%
    ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
    AdminService service = (AdminService) ac.getBean("adminService");

    List<Publisher> publisherList=service.getAllPublishers();
    List<Book> books1;
    for(Publisher publisher:publisherList)
    {
        books1=service.getBooksByPublisher(publisher.getPublisherId());
        publisher.setBooks(books1);
    }


%>
<script>
    function searchPublisher(){
        $.ajax({
            url: "searchPublisher",
            method: "post",
            data: {
                searchString: $('#searchString').val()
            }
        }).done(function( data) {
            $('#publishersTable').html(data);
        });
    }
</script>




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
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">GCIT-Admin</a>
        </div>
        <!-- Top Menu Items -->

        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <ul class="nav navbar-nav side-nav">
                <li>
                    <a href="${pageContext.request.contextPath}/admin/author">Author</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/book">Book</a>
                </li>
                <li class="active">
                    <a href="${pageContext.request.contextPath}/admin/publisher">Publisher</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/library">Library</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/borrower">Borrower</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/change_due">Change Due Date</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </nav>

    <div id="page-wrapper">
        <div class="container-fluid">

            <div class="row">
                <div class="col-lg-8">
                    <table class="table table-striped" id="publishersTable" >
                        <tr>
                            <th>#</th>
                            <th>Publisher Name</th>
                            <th>Address</th>
                            <th>Phone</th>
                            <th>Books</th>
                            <th>Edit</th>
                            <th>Delete</th>
                        </tr>
                        <%
                            for (Publisher a:publisherList) {
                        %>
                        <tr>
                            <td><%=publisherList.indexOf(a)+1%></td>
                            <td><%=a.getPublisherName()%></td>
                            <td><%=a.getPublisherAddress()%></td>
                            <td><%=a.getPublisherPhone()%></td>
                            <td><%
                                if (a.getBooks() != null && !a.getBooks().isEmpty()) {
                                    List<Book> books = a.getBooks();
                                    for(int i=0;i<books.size();i++)
                                    {
                                        if(books.get(i).getPublisher()==null)
                                            out.println(books.get(i).getTitle());
                                        if(i<books.size()-1)
                                            out.println(", ");
                                    }
                                }%></td>
                            <td><button type="button" class="btn btn-sm btn-warning"
                                        onclick="javascript:location.href='editPublisher?publisherId=<%=a.getPublisherId()%>'">Edit</button></td>
                            <td><button type="button" class="btn btn-sm btn-danger"
                                        onclick="javascript:location.href='deletePublisher?publisherId=<%=a.getPublisherId()%>'">Delete</button></td>
                        </tr>
                        <% }%>
                    </table>
                </div>
                <div class="col-xs-4">
                    <h4>Add a Publisher</h4>
                    <form action="addPublisher" method="post">
                        <p>Enter a new Publisher Name</p> <input style="width:200px" type="text" name="publisherName"> <br />
                        <p>Enter a Address</p> <input type="text" style="width:200px" name="publisherAddress"> <br />
                        <p>Enter a Phone</p> <input type="text" style="width:200px" name="publisherPhone"> <br /><br />
                        <button type="submit" class="btn btn-sm btn-success">Add Publisher</button>
                    </form>
                </div>
            </div>



        </div>
        <!-- /#page-wrapper -->
    </div>
    <!-- /#wrapper -->
</div>
    <!-- jQuery -->
    <script src="template/content/jquery.js.download"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="template/content/bootstrap.min.js.download"></script>

    <!-- Morris Charts JavaScript -->
    <script src="template/content/raphael.min.js.download"></script>
    <script src="template/content/morris.min.js.download"></script>
    <script src="template/content/morris-data.js.download"></script>

</body></html>


