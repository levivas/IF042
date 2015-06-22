package com.if42.tester.entity;

import java.util.List;

public class Tests {

    private List<Test> tests;

    public Tests(List<Test> tests) {
        this.tests = tests;
    }

    public Tests() {
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }
}
