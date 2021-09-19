package edu.test.dtos;

public class CatDto {

    public String name;
    public int age;

    public CatDto(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "CatDto{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
