package LMS.DAO;

import LMS.Entity.*;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingj on 2/22/2017.
 */
public class BorrowerDAO extends BaseDAO implements ResultSetExtractor<List<Borrower>> {

    public void addBorrower(Borrower borrower)throws SQLException, ClassNotFoundException{
        template.update("insert into tbl_borrower(name,address,phone) values(?,?,?)",
                new Object[]{borrower.getName(),borrower.getAddress(),borrower.getPhone()});
    }
    public void updateBorrower(Borrower borrower)throws SQLException, ClassNotFoundException{
        template.update("update tbl_borrower set name=?,address=?,phone=? where cardNo=?",
                new Object[]{borrower.getName(),borrower.getAddress(),borrower.getPhone(),borrower.getCardNo()});
    }
    public void deleteBorrower(int cardNo)throws SQLException, ClassNotFoundException{
        template.update("delete from tbl_borrower where cardNo=?",new Object[]{cardNo});
    }
    public List<Borrower> readBorrowersByPage(Integer pageNo) throws ClassNotFoundException, SQLException{
        setPageNo(pageNo);
        return template.query("select * from tbl_borrower", this);
    }

    public List<Borrower> readBorrowersByName(String borrowerName) throws ClassNotFoundException, SQLException{
        return template.query("select * from tbl_borrower where name like ?", new Object[]{"%"+borrowerName+"%"},this);
    }
    public List<Borrower> readAllBorrower()throws SQLException, ClassNotFoundException{
        return  template.query("select * from tbl_borrower",this);
    }
    public Borrower readBorrowerByPk(Integer cardNo) throws ClassNotFoundException, SQLException{
        Borrower borrower;
        List<Borrower> borrowers = template.query("select * from tbl_borrower where cardNo = ?", new Object[]{cardNo},this);
        if(borrowers!=null && !borrowers.isEmpty()){
            borrower=borrowers.get(0);
            return borrower;
        }
        return null;
    }

//    public Integer getBorrowersCount() throws ClassNotFoundException, SQLException{
//        return getCount("tbl_borrower");
//    }



    @Override
    public List<Borrower> extractData(ResultSet rs) throws SQLException {
        List<Borrower> borrowers=new ArrayList<Borrower>();
        BookDAO bookDAO=new BookDAO();
        while(rs.next())
        {
            Borrower borrower=new Borrower();
            borrower.setAddress(rs.getString("address"));
            borrower.setCardNo(rs.getInt("cardNo"));
            borrower.setName(rs.getString("name"));
            borrower.setPhone(rs.getString("phone"));
            borrowers.add(borrower);
        }
        return borrowers;
    }


}
