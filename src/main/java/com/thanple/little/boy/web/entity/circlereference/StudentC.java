package com.thanple.little.boy.web.entity.circlereference;

/**
 * Create by Thanple at 2019/3/3 下午2:42
 */
public class StudentC {

    private StudentA studentA ;

    public void setStudentA(StudentA studentA) {
        this.studentA = studentA;
    }

    public StudentC() {
    }

    public StudentC(StudentA studentA) {
        this.studentA = studentA;
    }
}
