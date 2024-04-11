package dbSample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbSample.entity.Country;
import dbSample.util.DatabaseManager;

/**
 * DAO役割クラスの一つ
 * - CRUDの処理を担う
 */
public class CountryDAO {
    
    // DB接続と結果取得のための変数
    private PreparedStatement pstmt;
    private ResultSet rs;
    
    public List<Country> getCountryFromName(String name) {
        // メソッドの結果として返すリスト
        List<Country> results = new ArrayList<Country>();
        
        
        try {
            // 1,2. JDBCドライバを読込、DBに接続
            Connection con = DatabaseManager.getConnection();
            
            // 3. ＤＢとやりとりする窓口(Statementオブジェクト)の作成
            String sql = "SELECT * FROM country WHERE Name = ?";
            pstmt = con.prepareStatement(sql);
            
            // 4,5. SELECT文の実行と結果を格納/代入
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            
            // 6. 結果を表示
            while (rs.next()) {
                // 1件ずつCountryオブジェクトを生成して結果を格納（リストに追加）
                Country country = new Country();
                
                country.setName(rs.getString("Name"));
                country.setPopulation(rs.getInt("Population"));
                
                // リストに追加
                results.add(country);
            }
            
        // tryの終了
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally { // 強制クローズ処理
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DatabaseManager.close();
        }
        return results;
    }

}
