package LMS.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Created by kingj on 2/22/2017.
 */
public abstract class BaseDAO {

    @Autowired
    JdbcTemplate template;

    private Integer pageNo;

    private Integer pageSize = 10;

    /**
     * @return the pageNo
     */
    public Integer getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo the pageNo to set
     */
    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * @return the pageSize
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize the pageSize to set
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }


}
