package LMS.Service;


import LMS.DAO.LibraryDAO;
import LMS.Entity.Book;
import LMS.Entity.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;


public class BorrowerService {
    @Autowired
    LibraryDAO libraryDAO;

    @Transactional
    public List<Library> readAllLibraries() throws SQLException, ClassNotFoundException {return libraryDAO.readAllLibaray();}



}
