package LMS.DAO;


import LMS.Entity.Publisher;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingj on 2/22/2017.
 */
public class PublisherDAO extends BaseDAO implements ResultSetExtractor<List<Publisher>> {



    public void addPublisher(Publisher publisher) throws SQLException, ClassNotFoundException {
        template.update("insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values (?,?,?)"
                , new Object[] {publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone()});
    }
    public void updatePublisher(Publisher publisher) throws SQLException, ClassNotFoundException {

        template.update("update tbl_publisher set publisherName = ?,publisherAddress=?,publisherPhone=? where publisherId = ?"
                ,new Object[] {publisher.getPublisherName(),publisher.getPublisherAddress(),publisher.getPublisherPhone(),publisher.getPublisherId()});
    }
    public void deletePublisher(int id) throws SQLException, ClassNotFoundException {
        template.update("delete from tbl_publisher where publisherId = ?", new Object[] {id});
    }
    public List<Publisher> readAllPublisher() throws ClassNotFoundException, SQLException{
        return template.query("select * from tbl_publisher", this);
    }

    public Publisher readPublisherByPk(Integer publisherId) throws ClassNotFoundException, SQLException{
        List<Publisher> publishers = template.query("select * from tbl_publisher where publisherId = ?", new Object[]{publisherId},this);
        if(publishers!=null && !publishers.isEmpty()){
            return publishers.get(0);
        }
        return null;
    }
    public List<Publisher> readPublisherByName(String publisherName)throws ClassNotFoundException, SQLException{
        return template.query("select * from tbl_publisher where publisherName like ? ",new Object[]{"%"+publisherName+"%"},this);
    }
    public List<Publisher> readPublishersByPage(Integer pageNo) throws ClassNotFoundException, SQLException{
        setPageNo(pageNo);
        return template.query("select * from tbl_publisher", this);
    }

//    public Integer getPublishersCount() throws ClassNotFoundException, SQLException{
//        return getCount("tbl_publisher");
//    }

    @Override
    public List<Publisher> extractData(ResultSet rs) throws SQLException {
        List<Publisher> publishers = new ArrayList<Publisher>();
        while(rs.next()){
            Publisher p = new Publisher();
            BookDAO bookDAO=new BookDAO();
            p.setPublisherId(rs.getInt("publisherId"));
            p.setPublisherName(rs.getString("publisherName"));
            p.setPublisherPhone(rs.getString("publisherPhone"));
            p.setPublisherAddress(rs.getString("publisherAddress"));
            publishers.add(p);
        }
        return publishers;
    }


}
