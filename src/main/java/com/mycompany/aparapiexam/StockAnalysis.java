package com.mycompany.aparapiexam;

import com.amd.aparapi.Kernel;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.io.ICsvBeanReader;
import org.supercsv.prefs.CsvPreference;

public class StockAnalysis {

    public static void main(String[] args) {
        try {
            // 株価時系列データの読み取り
            float[] priceData = readStockValues();
            float[] imagData = new float[priceData.length];
            System.out.println("Data Length = " + priceData.length);
            
            // Kernel 初期化
            FFTKernel kernel = new FFTKernel(priceData.length);
            
            // Java Thread Pool で実行
            kernel.setExecutionMode(Kernel.EXECUTION_MODE.JTP);
            long startTime = System.currentTimeMillis();
            for (int cnt = 0; cnt < 100; cnt++) {
                kernel.fft(priceData, imagData);
            }
            long endTime = System.currentTimeMillis();
            System.out.println(kernel.getExecutionMode() + "," + (endTime -startTime));
            
            // GPGPU で実行
            kernel.setExecutionMode(Kernel.EXECUTION_MODE.GPU);
            startTime = System.currentTimeMillis();
            for (int cnt = 0; cnt < 100; cnt++) {
                kernel.fft(priceData, imagData);
            }
            endTime = System.currentTimeMillis();
            System.out.println(kernel.getExecutionMode() + "," + (endTime -startTime));
                
//              float[] sin = kernel.getSin();
//              float[] cos = kernel.getCos();
//              for (int cnt = 0; cnt < sin.length; cnt++) {
//                  System.out.println(
//                          String.format(
//                              "%s,%f", 
//                              cnt == 0 ? "∞" : 
//                              (double)priceData.length/cnt/4, Math.sqrt(sin[cnt]*sin[cnt] + cos[cnt]*cos[cnt])/2.0));
//              }
            
        } catch (IOException ex) {
            Logger.getLogger(StockAnalysis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static float[] readStockValues() throws IOException {
        List<Double> priceList = new LinkedList<>();

        ICsvBeanReader inFile = new CsvBeanReader(
                new FileReader("src/main/resources/NKY.csv"), CsvPreference.EXCEL_PREFERENCE);

        final String[] header = inFile.getHeader(true);
        StockCsvBean stock = null;
        while ((stock = inFile.read(StockCsvBean.class, header, StockCsvBean.processors)) != null) {
            priceList.add(stock.getStart());
            if (stock.getStart() > stock.getEnd()) {
                priceList.add(stock.getHigh());
                priceList.add(stock.getLow());
            } else {
                priceList.add(stock.getLow());
                priceList.add(stock.getHigh());
            }
            priceList.add(stock.getEnd());
        }

        int dataSize = priceList.size();
        
        // 配列長 2^n の float[]  に切り詰める
        int fftSize = (int) Math.pow(2.0, Math.floor(Math.log(dataSize) / Math.log(2.0)));
        
        float[] fftData = new float[fftSize];
        for (int cnt = 0; cnt < fftSize; cnt++) {
            fftData[cnt] = priceList.get(cnt).floatValue();
        }
        
        return fftData;
    }
}
