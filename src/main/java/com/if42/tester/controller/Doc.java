package com.if42.tester.controller;

/**
 * Created by Кукуся on 03.07.2014.
 */
class Dog implements Comparable<Dog>{
    int size;

    public Dog(int s) {
        size = s;
    }

    public String toString() {
        return size + "";
    }

    @Override
    public int compareTo(Dog o) {
        return size - o.size;
    }
}