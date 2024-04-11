package dbSample.entity;

/**
 * DTO(Data Transfer Object)役割クラス
 * - DBのworld/countryテーブルのカラムに対応したフィールドを持つ
 * - （今回は練習のためname, populationのみ）
 * - DTOクラス数はDBテーブル数と1対1対応になる
 */
public class Country {
    // フィールド
    private String name;
    private int population;
    
    // 引数なしコンストラクタ
    public Country() {
        
    }
    
    // 引数ありコンストラクタ
    public Country(String name, int population) {
        this.name = name;
        this.population = population;
    }
    
    
    // **** getter/setter **** //

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
    

}
