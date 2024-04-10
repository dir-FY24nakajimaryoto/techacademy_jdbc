package dbSample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * CRUDのCUDを実装したクラス
 */
public class DbConnectSample02 {

    public static void main(String[] args) {

        // DB接続と結果取得のための変数
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        
        try {
            // 1. ドライバのクラスを読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. DBと接続
                // jdbc~//<DBアドレス>/<DB名>?~
                // user name
                // root password
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "1192296_kmkrBKF"
                    );
            
            // 3. DBとやりとりする窓口（Statementオブジェクト）の作成
            stmt = con.createStatement();
            
            // 4, 5. SELECTの実行と結果を格納・代入
            String sql = "SELECT * FROM country LIMIT 50";
            rs = stmt.executeQuery(sql); // CRUDのR(executeQuery)

            // 6. 結果を表示
            while (rs.next()) {
                // Name列の値を取得
                String name = rs.getString("Name");
                // Population列の値を取得（追記）
                int population = rs.getInt("Population");
                
                // 取得した値を表示
                System.out.println("Name: "+name);
                System.out.println("Pop: "+population);
            }
            
            // 6-1. データの更新を行う
            sql = "UPDATE country SET Population = 105000 WHERE Code = 'ABW'";
            int count = stmt.executeUpdate(sql); // 変更があった行数を記録
            System.out.println(count);
            // commit(); <- 更新系のSQLを行ったら必ずコミットすること！！(@Lesson16)
            
        } catch (ClassNotFoundException e) { // 例外：ドライバ読み込み
            System.out.println("JDBCドライバのロードに失敗");
            e.printStackTrace();
        } catch (SQLException e) { // 例外：DB接続
            System.out.println("DBに異常が発生");
            e.printStackTrace();
        } finally {
            // 7. 接続を閉じる：後で接続した（開いた）ものから、先に切断する（閉じる）
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラー発生");
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("Statementを閉じるときにエラー発生");
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("DB切断時にエラーが発生");
                    e.printStackTrace(); // メソッドの呼び出し順をトレースして出力
                }
            }
        }
    }

}
