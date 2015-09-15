package com.mycompany.aparapiexam;

import java.util.Date;
import lombok.Data;
import org.supercsv.cellprocessor.ParseDate;
import org.supercsv.cellprocessor.ParseDouble;
import org.supercsv.cellprocessor.ift.CellProcessor;

@Data
public class StockCsvBean {
    
    /** 各要素フォーマット定義 */
    public static final CellProcessor[] processors = new CellProcessor[] {
            new ParseDate("yyyy/MM/dd"),         // date
            new ParseDouble(),
            new ParseDouble(),
            new ParseDouble(),
            new ParseDouble()            
    };
    
    private Date date;
    private double start;
    private double high;
    private double low;
    private double end;
}
