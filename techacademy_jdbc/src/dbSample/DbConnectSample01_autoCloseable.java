package dbSample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * 01の実装にtry-with-resourcesを用いたもの
 */
public class DbConnectSample01_autoCloseable {

    public static void main(String[] args) {
        
        try {
            // ドライバのクラスを読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            // SELECT文の実行と結果を格納/代入
            String sql = "SELECT * FROM country LIMIT 50";
            
            /**
             * ここから新規
             * - try-with-resources構文
             * -- try()の()で宣言された変数はtryブロック内がスコープになる（リソースの宣言）
             * -- tryブロックの処理が終了するとAutoCloseableインターフェースにより自動的に閉じられる
             * - 変数（リソース）宣言のみの記述だけで閉鎖まで自動でやってくれるから楽
             */
            try ( // DBと接続
                Connection con = DriverManager.getConnection(
                        "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                        "root",
                        "1192296_kmkrBKF"
                        );
                // DBとやりとりする窓口（Statementオブジェクト）の作成
                Statement stmt = con.createStatement();
                // SQLを発行
                ResultSet rs = stmt.executeQuery(sql);){
                
                // 結果の表示
                while (rs.next()) {
                    // Name列の値を取得
                    String name = rs.getString("Name");
                    // 取得した値を表示
                    System.out.println(name);
                }
               }
            
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバ失敗");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("DB異常");
            e.printStackTrace();
        }
    }
}
