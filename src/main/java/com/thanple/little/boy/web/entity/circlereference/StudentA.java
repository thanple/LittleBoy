package com.thanple.little.boy.web.entity.circlereference;

/**
 * Create by Thanple at 2019/3/3 下午2:41
 */
public class StudentA {
    private StudentB studentB ;

    public void setStudentB(StudentB studentB) {
        this.studentB = studentB;
    }

    public StudentA() {
    }

    public StudentA(StudentB studentB) {
        this.studentB = studentB;
    }
}
