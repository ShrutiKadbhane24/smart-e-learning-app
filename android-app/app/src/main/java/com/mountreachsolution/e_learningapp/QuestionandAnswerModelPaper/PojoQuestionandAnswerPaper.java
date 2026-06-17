package com.mountreachsolution.e_learningapp.QuestionandAnswerModelPaper;

public class PojoQuestionandAnswerPaper {
    public String title,viewdoc;

    public PojoQuestionandAnswerPaper(String title, String viewdoc) {
        this.title = title;
        this.viewdoc = viewdoc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getViewdoc() {
        return viewdoc;
    }

    public void setViewdoc(String viewdoc) {
        this.viewdoc = viewdoc;
    }
}
