<%@ page import="org.springframework.context.ApplicationContext" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page import="LMS.Service.AdminService" %>
<%@ page import="LMS.Entity.Book_Loan" %>
<%@ page import="java.util.List" %>
<%
    ApplicationContext ac = RequestContextUtils.getWebApplicationContext(request);
    AdminService service = (AdminService) ac.getBean("adminService");
    List<Book_Loan> book_loans;

    if (request.getAttribute("book_loans") != null) {
        book_loans = (List<Book_Loan>) request.getAttribute("book_loans");
    } else {
        book_loans = service.readAllBookLoan(1,null);
    }

//    Integer bookLoansCount = service.getBookLoansCount();
//    int pages = 0;
//    if ((bookLoansCount % 10) > 0) {
//        pages = bookLoansCount / 10 + 1;
//    } else {
//        pages = bookLoansCount / 10;
//    }
%>


<script>
    function searchBookLoans(){
        $.ajax({
            url: "searchBookLoans",
            method: "post",
            data: {
                searchString: $('#searchString').val()
            }
        }).done(function( data ) {
            $('#bookLoansTable').html(data);
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
                <li c>
                    <a href="${pageContext.request.contextPath}/admin/author">Author</a>
                </li>
                <li >
                    <a href="${pageContext.request.contextPath}/admin/book">Book</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/publisher">Publisher</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/library">Library</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/borrower">Borrower</a>
                </li>
                <li class="active">
                    <a href="${pageContext.request.contextPath}/admin/change_due">Change Due Date</a>
                </li>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </nav>



    <div id="page-wrapper">
        <div class="container-fluid">
            <%--<nav aria-label="Page navigation">--%>
                <%--<div class="dropdown" style="float:left">--%>
                    <%--<button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">--%>
                        <%--Page--%>
                        <%--<span class="caret"></span>--%>
                    <%--</button>--%>
                    <%--<ul class="dropdown-menu" aria-labelledby="dropdownMenu1">--%>
                        <%--<%--%>
                            <%--for (int i = 1; i <= pages; i++) {--%>
                        <%--%>--%>
                        <%--<li><a href="pageBookLoan?pageNo=<%=i%>"><%=i%></a></li>--%>
                        <%--<%--%>
                            <%--}--%>
                        <%--%>--%>
                    <%--</ul>--%>
                <%--</div>--%>
                <%--<div class="col-md-4">--%>
                    <%--<input type="text" class="form-control"--%>
                           <%--placeholder="Type Author Name to Search!"--%>
                           <%--aria-describedby="basic-addon1" name="searchString" id="searchString" oninput="searchBookLoans()">--%>
                <%--</div>--%>

            <%--</nav>--%>

            <table class="table table-striped" id="bookLoansTable" >
                <tr>
                    <th>#</th>
                    <th>Borrower</th>
                    <th>Book</th>
                    <th>Branch</th>
                    <th>Date Out</th>
                    <th>Due Date</th>
                    <th>Date In</th>
                    <th>Modify Due Date</th>
                </tr>
                <%
                    for (Book_Loan a : book_loans) {
                %>
                <tr>
                    <td><%=book_loans.indexOf(a) + 1%></td>
                    <td><%=a.getBorrower().getName()%></td>
                    <td><%=a.getBook().getTitle()%></td>
                    <td><%=a.getBranch().getLibraryName()%></td>
                    <td><%=a.getDateOut()%></td>
                    <td><%=a.getDueDate()%></td>
                    <td><%=a.getDateIn()%></td>
                    <td><button type="button" class="btn btn-sm btn-warning"
                                onclick="javascript:location.href='editDue?bookId=<%=a.getBook().getBookId()%>&branchId=<%=a.getBranch().getLibraryId()%>&cardNo=<%=a.getBorrower().getCardNo()%>'">Modify</button></td>
               </tr>
                <% }%>
            </table>

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


