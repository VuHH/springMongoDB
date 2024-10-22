package com.mongodb.SpringMongodb.dto;

public class AgeGroupCount {
    private int age;
    private long count;

    public AgeGroupCount(int age, long count) {
        this.age = age;
        this.count = count;
    }

    public AgeGroupCount() {
    }

    // Getters và Setters
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
