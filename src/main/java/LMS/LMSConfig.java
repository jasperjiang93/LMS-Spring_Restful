package LMS;



import LMS.DAO.*;
import LMS.Service.AdminService;
import LMS.Service.BorrowerService;
import LMS.Service.LibrarianService;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import com.gcit.LMS.Service.AdminService;

@EnableTransactionManagement
@Configuration
public class LMSConfig {
	
	private String driver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/library";
	private String userName = "root";
	private String password = "8722";
	
	@Bean
	public BasicDataSource dataSource(){
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driver);
		ds.setUrl(url);
		ds.setUsername(userName);
		ds.setPassword(password);
		
		return ds;
	}
	
	@Bean
	public JdbcTemplate template(){
		JdbcTemplate template = new JdbcTemplate(dataSource());
		return template;
	}
	
	@Bean
	public PlatformTransactionManager txManager(){
		DataSourceTransactionManager tx = new DataSourceTransactionManager(dataSource());
		
		return tx;
	}
	
	@Bean
	public AuthorDAO adao(){return new AuthorDAO();}
	@Bean
	public BookDAO bdao(){
		return new BookDAO();
	}
	@Bean
	public Book_CopyDAO book_copyDAO(){return new Book_CopyDAO();}
	@Bean
	public Book_LoanDAO book_loanDAO(){return new Book_LoanDAO();}
	@Bean
	public BorrowerDAO borrowerDAO(){return new BorrowerDAO();}
	@Bean
	public GenreDAO genreDAO(){return new GenreDAO();}
	@Bean
	public LibraryDAO libraryDAO(){return new LibraryDAO();}
	@Bean
	public PublisherDAO publisherDAO(){return new PublisherDAO();}


	@Bean
	public AdminService adminService(){
		return new AdminService();
	}
	@Bean
	public BorrowerService borrowerService(){return new BorrowerService();}
	@Bean
	public LibrarianService librarianService(){return new LibrarianService();}
}
