
package com.example.calorieapp.Entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Nutrients_ {

    @SerializedName("ENERC_KCAL")
    @Expose
    private Double eNERCKCAL = 0.0;
    @SerializedName("PROCNT")
    @Expose
    private Double pROCNT = 0.0;
    @SerializedName("FAT")
    @Expose
    private Double fAT = 0.0;
    @SerializedName("CHOCDF")
    @Expose
    private Double cHOCDF = 0.0;
    @SerializedName("FIBTG")
    @Expose
    private Double fIBTG = 0.0;

    public Double getENERCKCAL() {
        return eNERCKCAL;
    }

    public void setENERCKCAL(Double eNERCKCAL) {
        this.eNERCKCAL = eNERCKCAL;
    }

    public Double getPROCNT() {
        return pROCNT;
    }

    public void setPROCNT(Double pROCNT) {
        this.pROCNT = pROCNT;
    }

    public Double getFAT() {
        return fAT;
    }

    public void setFAT(Double fAT) {
        this.fAT = fAT;
    }

    public Double getCHOCDF() {
        return cHOCDF;
    }

    public void setCHOCDF(Double cHOCDF) {
        this.cHOCDF = cHOCDF;
    }

    public Double getFIBTG() {
        return fIBTG;
    }

    public void setFIBTG(Double fIBTG) {
        this.fIBTG = fIBTG;
    }

    @Override
    public String toString() {
        return "\n"+"Energy=" + (double)(Math.round(eNERCKCAL*100))/100 +"\n"+
                " Protein=" + (double)(Math.round(pROCNT*100))/100 +"\n"+
                " Fat=" +  (double)(Math.round(fAT*100))/100 +"\n"+
                " Carbs=" +  (double)(Math.round(cHOCDF*100))/100 +"\n"+
                " Fiber =" +  (double)(Math.round(fIBTG*100))/100 ;
    }
}
