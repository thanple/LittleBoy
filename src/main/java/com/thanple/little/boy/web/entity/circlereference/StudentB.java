package com.thanple.little.boy.web.entity.circlereference;

/**
 * Create by Thanple at 2019/3/3 下午2:42
 */
public class StudentB {
    private StudentC studentC ;

    public void setStudentC(StudentC studentC) {
        this.studentC = studentC;
    }

    public StudentB() {
    }

    public StudentB(StudentC studentC) {
        this.studentC = studentC;
    }
}
