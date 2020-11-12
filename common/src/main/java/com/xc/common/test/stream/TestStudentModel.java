package com.xc.common.test.stream;

import lombok.Data;

@Data
public class TestStudentModel {
    private Integer id;
    private String name;
    private Integer age;
    private double score;

    public TestStudentModel(Integer id,String name, Integer age, Double score) {
        this.id=id;
        this.name = name;
        this.age = age;
        this.score = score;
    }
}
