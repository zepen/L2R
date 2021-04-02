package com;

import java.util.*;

public class MyTest {

    static class Student{
        private int age;
        private int height;
        private String name;

        static int computeScore(int a, int b){
            return 2 * a + 50 * b;
        }


        Student(String name, int age, int height){
            this.age = age;
            this.height = height;
            this.name = name;
        }

        int getAge() {
            return age;
        }

        int getHeight() {
            return height;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String [] agrs){
        List<Student> stus = new ArrayList<Student>(){
            {
                add(new Student("张三", 30, 176));
                add(new Student("李四", 32, 178));
                add(new Student("王五", 16, 181));
            }
        };
        //对users按年龄进行排序
        Collections.sort(stus, new Comparator<Student>() {

            @Override
            public int compare(Student s1, Student s2) {
                // 升序
                return Student.computeScore(s1.getAge(), s1.getHeight()) - Student.computeScore(s2.getAge(), s2.getHeight());
                // 降序
                // return s2.getAge()-s1.getAge();
                // return s2.getAge().compareTo(s1.getAge());
            }
        });
        for (Student s:  stus){
            System.out.println(s.name);
        }
    }
}
