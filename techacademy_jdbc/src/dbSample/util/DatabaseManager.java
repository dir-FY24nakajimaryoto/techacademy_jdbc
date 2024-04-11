package dbSample.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DAO(Data Access Object)役割クラスの一つ
 * - DBとの接続、切断処理を行う
 */
public class DatabaseManager {
    
    // DB接続と結果取得のための
    private static Connection con;
    
    
    // 接続メソッド
    public static Connection getConnection() throws SQLException, ClassNotFoundException{
        // 1. JDBCドライバのクラスをjavaで読込
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        // 2. DBと接続
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "1192296_kmkrBKF"
                );
        
        return con;
    }
    
    
    public static void close() {
        // 7. 接続を閉じる
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
