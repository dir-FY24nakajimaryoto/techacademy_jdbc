package dbSample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



/**
 * プリペアードステートメントを活用したクラス
 * - 動的なSQLを活用しつつ、インジェクション対策になる
 * - 検索するためのSQLに活用
 */
public class DbConnectSample05 {

    public static void main(String[] args) {

        // DB接続と結果取得のための変数
        Connection con = null;
//        Statement stmt = null;
        PreparedStatement pstmt = null;
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
            /**
             * 変更点：プリペアードステートメントpstmtに変更
             */
            String sql = "INSERT INTO city (Name,CountryCode,District,Population) VALUES ('Rafah',?,'Rafah',?)";
            pstmt = con.prepareStatement(sql);
            
            // 4, 5, 6. UPDATEの実行と結果を格納・代入
            System.out.print("CountryCodeを入力してください > ");
            String input = keyIn();
            
            System.out.print("Populationを数字で入力してください > ");
            int num1 = Integer.parseInt(keyIn());
            
            /**
             * 変更点:プリペアードステートメント
             * - pstmtにinputを代入
             */
            pstmt.setString(1, input);
            pstmt.setInt(2,num1);
            
            int count = pstmt.executeUpdate();
            System.out.println(count);
//            rs = pstmt.executeQuery();
            
            
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
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    System.err.println("Statement解除エラー");
                    e.printStackTrace();
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
    
    
    /**
     * キーボードから入力された値をStringで返す
     * - 引数：なし
     * - 戻り値：入力された文字列
     */
    private static String keyIn() {
        String line = null;
        try {
            BufferedReader key = new BufferedReader(new InputStreamReader(System.in));
            line = key.readLine();
        } catch (IOException e) {
            
        }
        return line;
    }

}
