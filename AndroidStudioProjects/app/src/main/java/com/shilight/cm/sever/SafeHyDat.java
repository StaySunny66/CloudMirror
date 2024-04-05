package com.shilight.cm.sever;

import com.shilight.cm.CveData;

import java.util.LinkedList;

public class SafeHyDat {
     private LinkedList<CveData> cveData;
     private double core[];
     private int aa[];

     public SafeHyDat() {
          this.cveData = new LinkedList<CveData>();
     }

     public LinkedList<CveData> getCveData() {
          return cveData;
     }

     public void setCveData(LinkedList<CveData> cveData) {
          this.cveData = cveData;
     }

     public double[] getCore() {
          return core;
     }

     public void setCore(double[] core) {
          this.core = core;
     }

     public int[] getAa() {
          return aa;
     }

     public void setAa(int[] aa) {
          this.aa = aa;
     }
}
