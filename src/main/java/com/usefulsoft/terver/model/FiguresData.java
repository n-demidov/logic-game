package com.usefulsoft.terver.model;

public class FiguresData {
    public final int num;
    public int price;
    public int event;
    public double statEv;
    public double statDisp;

    public FiguresData(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "FiguresData{" +
                "num=" + num +
                ", price=" + price +
                ", event=" + event +
                ", statEv=" + statEv +
                ", statDisp=" + statDisp +
                '}';
    }
}
