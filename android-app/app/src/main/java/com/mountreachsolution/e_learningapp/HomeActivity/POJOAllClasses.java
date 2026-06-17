package com.mountreachsolution.e_learningapp.HomeActivity;

public class POJOAllClasses {

    String id,classname,subjectname;

    public POJOAllClasses(String id, String classname, String subjectname) {
        this.id = id;
        this.classname = classname;
        this.subjectname = subjectname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }
}
