<%@ page import="java.util.List" %>
<%@ page import="LMS.Service.BorrowerService" %>
<%@ page import="LMS.Entity.*" %>
<%
    List<Book> books =(List<Book>) request.getAttribute("books");
    List<Book_Loan> book_loans= (List<Book_Loan>) request.getAttribute("book_loans");
    Borrower borrower= (Borrower) request.getAttribute("borrower");

%>


<script>
    function searchBooks(){
        $.ajax({
            url: "searchBooks",
            method: "post",
            data: {
                searchString: $('#searchString').val()
            }
        }).done(function( bookTable ) {
            $('#booksTable').html(bookTable);
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
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">GCIT-Borrower</a>
        </div>
        <!-- Top Menu Items -->

        <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->

        <!-- /.navbar-collapse -->
    </nav>


    <div id="page-wrapper">
        <div class="container-fluid">

            <div><h2>Check Out Book</h2></div>

            <table class="table table-striped" id="booksTable" >
                <tr>
                    <th>#</th>
                    <th>Book Name</th>
                    <th>Publisher</th>
                    <th>Genre</th>
                    <th>Author</th>
                    <th>Check Out</th>
                </tr>
                <%
                    for (Book a : books) {
                %>
                <tr>
                    <td><%=books.indexOf(a) + 1%></td>
                    <td><%=a.getTitle()%></td>
                    <td><%if(a.getPublisher()!=null && !a.getPublisher().getPublisherName().isEmpty())
                        out.print(a.getPublisher().getPublisherName());%></td>
                    <td><%
                        if (a.getGenres() != null && !a.getGenres().isEmpty()) {
                            List<Genre> genres = a.getGenres();
                            for(int i=0;i<genres.size();i++)
                            {
                                out.println(genres.get(i).getGenre_name());
                                if(i<genres.size()-1)
                                    out.println(", ");
                            }
                        }%></td>
                    <td><%
                        if (a.getAuthors() != null && !a.getAuthors().isEmpty()) {
                            List<Author> authors = a.getAuthors();
                            for(int i=0;i<authors.size();i++)
                            {
                                out.println(authors.get(i).getAuthorName());
                                if(i<authors.size()-1)
                                    out.println(", ");
                            }
                        }%></td>
                    <td><button type="button" class="btn btn-sm btn-warning"
                                onclick="javascript:location.href='checkOutBook?bookId=<%=a.getBookId()%>&cardNo=<%=borrower.getCardNo()%>&branchId=<%=book_loans.get(0).getBranchId()%>'">Check Out</button></td>
                </tr>
                <% }%>
            </table>

            <div>
                <h2>Return a book</h2>

            </div>
    <table class="table table-striped" id="bookLoansTable" >
        <tr>
            <th>#</th>
            <th>Book</th>
            <th>Date Out</th>
            <th>Due Date</th>
            <th>Date In</th>
            <th>Return the Book</th>
        </tr>
        <%
            if(book_loans!=null)
            for (Book_Loan a : book_loans) {
        %>
        <tr>
            <td><%=book_loans.indexOf(a) + 1%></td>
            <td><%=a.getBook().getTitle()%></td>
            <td><%=a.getDateOut()%></td>
            <td><%=a.getDueDate()%></td>
            <td><%=a.getDateIn()%></td>
            <td><button type="button" class="btn btn-sm btn-warning"
                        onclick="javascript:location.href='returnBook?bookId=<%=a.getBookId()%>&branchId=<%=a.getBranchId()%>&cardNo=<%=a.getCardNo()%>&dateOut=<%=a.getDateOut()%>'">Return</button></td>
        </tr>
        <% }%>
    </table>

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