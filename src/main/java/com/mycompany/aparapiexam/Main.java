/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.aparapiexam;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;

/**
 *
 * @author atsushi
 */
public class Main {

    public static void main(String[] args) {
        final float inA[] = new float[]{1.0f, 2.0f, 3.0f, 4.0f};
        final float inB[] = new float[]{0.1f, 0.2f, 0.3f, 0.4f};
        final float result[] = new float[inA.length];

//        for (int i = 0; i < inA.length; i++) {
//            result[i] = inA[i] + inB[i];
//        }
        
        Kernel kernel = new Kernel() {
            @Override
            public void run() {
                int i = getGlobalId();
                result[i] = inA[i] + inB[i];
            }
        };
        Range range = Range.create(result.length);
        kernel.execute(range);

        for (int i = 0; i < result.length; i++) {
            System.out.println(String.format("%d %f", i, result[i]));
        }
        
        System.out.println(kernel.getExecutionMode().name());
        System.out.println(kernel.getExecutionMode().isOpenCL() ? "OpenCL" : "Not OpenCL");
    }
}
