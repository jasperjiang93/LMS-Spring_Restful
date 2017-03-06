package LMS.DAO;

import LMS.Entity.Book_Copy;
import LMS.Entity.Library;

import java.util.List;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingj on 2/22/2017.
 */
public class LibraryDAO extends BaseDAO implements ResultSetExtractor<List<Library>> {
    public void addLibrary(Library library)throws SQLException, ClassNotFoundException{
        template.update("insert into tbl_library_branch(branchName,branchAddress) values(?,?)",new Object[]{library.getLibraryName(),library.getLibraryAdderess()});
    }
    public void updateLibaray(Library library)throws SQLException, ClassNotFoundException{
        template.update("update tbl_library_branch set branchName=?, branchAddress=? where branchId=?",
                new Object[]{library.getLibraryName(),library.getLibraryAdderess(),library.getLibraryId()});
    }
    public void deleteLibrary(int id)throws SQLException, ClassNotFoundException{
        template.update("delete from tbl_library_branch where branchId=?",new Object[]{id});
    }

//    public Integer getLibrariesCount() throws ClassNotFoundException, SQLException{
//        return getCount("tbl_library_branch");
//    }
    public List<Library> readAllLibaray()throws SQLException, ClassNotFoundException{
        return  template.query("select * from tbl_library_branch",this);
    }
    public List<Library> readLibrariesByName(String branchName)throws SQLException, ClassNotFoundException{
        return  template.query("select * from tbl_library_branch where branchName like ?",new Object[]{"%"+branchName+"%"},this);
    }

    public Library readLibraryByPk(Integer libraryId) throws ClassNotFoundException, SQLException{
        List<Library> libraries = template.query("select * from tbl_library_branch where branchId = ?", new Object[]{libraryId},this);
        if(libraries!=null && !libraries.isEmpty()){
            return libraries.get(0);
        }
        return null;
    }

    public List<Library> readLibrariesByPage(Integer pageNo)throws SQLException, ClassNotFoundException{
        setPageNo(pageNo);
        return template.query("select * from tbl_library_branch",this);
    }



    public void updateNumberOfCopies(Book_Copy book_copy)throws SQLException, ClassNotFoundException{
        template.update("update tbl_book_copies set noOfCopies=? where bookId=? and branchId=?",
                new Object[]{book_copy.getNoOfCopies(),book_copy.getBookId(),book_copy.getBranchId()});
    }
    public void insertNumberOfCopies(Book_Copy book_copy)throws SQLException, ClassNotFoundException{
        template.update("insert tbl_book_copies values(?,?,?)",
                new Object[]{book_copy.getBookId(),book_copy.getBranchId(),book_copy.getNoOfCopies()});
    }


    @Override
    public List<Library> extractData(ResultSet rs) throws SQLException {
        List<Library> libraries = new ArrayList<Library>();
        BookDAO bdao = new BookDAO();
        while(rs.next()){
            Library a = new Library();
            a.setLibraryId(rs.getInt("branchId"));
            a.setLibraryName(rs.getString("branchName"));
            a.setLibraryAdderess(rs.getString("branchAddress"));
            libraries.add(a);
        }
        return libraries;
    }


}
