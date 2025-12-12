package code.web.webgroup9.dao;


import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.StatementException;

public class BaseDao {
        // Thông tin kết nối
        private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "lightup";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Singleton JDBI instance
    private static Jdbi jdbi = null;

    /**
     * Tạo và trả về JDBI instance
     */
    public static Jdbi get() {
        if (jdbi == null) {
            try {
                // Tạo MySQL DataSource
                MysqlDataSource ds = new MysqlDataSource();
                ds.setURL("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE);
                ds.setUser(DB_USER);
                ds.setPassword(DB_PASSWORD);
                ds.setUseSSL(false);
                ds.setServerTimezone("UTC");
                ds.setAllowPublicKeyRetrieval(true);

                // Tạo JDBI instance
                jdbi = Jdbi.create(ds);

                System.out.println("Kết nối JDBI MySQL thành công!");
            } catch (Exception e) {
                System.err.println("Lỗi kết nối JDBI MySQL!");
                e.printStackTrace();
            }
        }
        return jdbi;
    }

    /**
     * Test connection
     */
    public static void main(String[] args) {
        // Test kết nối
        Jdbi jdbi = BaseDao.get();

        if (jdbi != null) {
            try {
                // Test query
                jdbi.withHandle(handle -> {
                    String result = handle.createQuery("SELECT 'Hello JDBI!' as message")
                            .mapTo(String.class)
                            .one();
                    System.out.println("Test query: " + result);
                    return result;
                });

                System.out.println("Test kết nối JDBI thành công!");
            } catch (StatementException e) {
                System.err.println("Lỗi thực thi query!");
                e.printStackTrace();
            }
        } else {
            System.out.println("Test kết nối JDBI thất bại!");
        }
    }
                  }

