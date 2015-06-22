package com.if42.tester.entity;


public class GraphPoint {
    private double val;
    private String caption;

    public GraphPoint(double val, String caption) {
        this.val = val;
        this.caption = caption;
    }

    public double getVal() {
        return val;
    }

    public void setValue(float val) {
        this.val = val;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
