package com.shilight.cm;

public class Sever {
    private String ser_name;
    private int ser_img;
    private int ser_cpu;
    private int ser_ram;
    private int[] system = {R.drawable.windows,R.drawable.linux};


    public Sever(String ser_name,int system_id,int ser_cpu,int ser_ram){
        this.ser_cpu = ser_cpu;
        this.ser_name = ser_name;
        this.ser_img =system[system_id];
        this.ser_ram = ser_ram;
    }

    public String getName(){
        return ser_name;
    }
    public int getCpu(){
        return ser_cpu;
    }
    public int getRam(){
        return ser_ram;
    }
    public int getSystem(){
        return ser_img;
    }


}
