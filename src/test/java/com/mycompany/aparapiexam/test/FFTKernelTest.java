package com.mycompany.aparapiexam.test;

import com.mycompany.aparapiexam.FFTKernel;
import org.junit.Test;

public class FFTKernelTest {

    @Test
    public void testFFT2() {
        int size = 8;
        
        FFTKernel fftKernel = new FFTKernel(size);
        
        float[] real = new float[size];
        float[] imag = new float[size];
        
        for (int cnt = 0; cnt < size; cnt++) {
            real[cnt] = (float) (
                1.0 * Math.sin(2 * Math.PI * cnt / size)
                + 2.0 * Math.cos(2 * Math.PI * cnt / size)
                + 3.0 * Math.sin(4 * Math.PI * cnt / size)
                + 4.0 * Math.cos(4 * Math.PI * cnt / size)
                + 5.0 * Math.sin(6 * Math.PI * cnt / size)
                + 6.0 * Math.cos(6 * Math.PI * cnt / size)
            );

            imag[cnt] = 0.0f;
        }
        
        fftKernel.fft(real, imag);
        
        real = fftKernel.getReal();
        imag = fftKernel.getImag();
        
        for (int cnt = 0; cnt < real.length; cnt++) {
            System.out.println(String.format("%d %f \t+ %fi", cnt, real[cnt], imag[cnt]));
        }
        
        float[] cos = fftKernel.getCos();
        float[] sin = fftKernel.getSin();
        
        System.out.println("* cos\t\tsin");
        for (int cnt = 0; cnt < cos.length; cnt++) {
            System.out.println(String.format("%d %f\t%f", cnt, cos[cnt], sin[cnt]));
        }
        
        System.out.println(fftKernel.getExecutionMode());        
    }
    
    @Test
    public void testReverse() {
        int size = 8;
        
        FFTKernel fftKernel = new FFTKernel(size);
        
        float[] real = new float[size];
        float[] imag = new float[size];
        
        for (int cnt = 0; cnt < size; cnt++) {
            real[cnt] = (float) (
                1.0 * Math.sin(2 * Math.PI * cnt / size)
                + 2.0 * Math.cos(2 * Math.PI * cnt / size)
                + 3.0 * Math.sin(4 * Math.PI * cnt / size)
                + 4.0 * Math.cos(4 * Math.PI * cnt / size)
                + 5.0 * Math.sin(6 * Math.PI * cnt / size)
                + 6.0 * Math.cos(6 * Math.PI * cnt / size)
            );

            imag[cnt] = 0.0f;
        }
        
        fftKernel.fft(real, imag);
        
        fftKernel.ifft(fftKernel.getReal(), fftKernel.getImag());
        
        float[] resReal = fftKernel.getReal();
        float[] resImag = fftKernel.getImag();
        
        System.out.println("* fft->ifft\t\t\toriginal");
        for (int cnt = 0; cnt < resReal.length; cnt++) {
            System.out.println(String.format("%d %f\t+%fi\t%f\t+%fi", cnt, resReal[cnt], resImag[cnt], real[cnt], imag[cnt]));
        }
        
        System.out.println(fftKernel.getExecutionMode());        
    }
}
