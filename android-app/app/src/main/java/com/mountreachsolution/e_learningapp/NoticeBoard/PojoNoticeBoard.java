package com.mountreachsolution.e_learningapp.NoticeBoard;

public class PojoNoticeBoard {
    public String title,description,viewdoc;

    public PojoNoticeBoard(String title, String description, String viewdoc) {
        this.title = title;
        this.description = description;
        this.viewdoc = viewdoc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getViewdoc() {
        return viewdoc;
    }

    public void setViewdoc(String viewdoc) {
        this.viewdoc = viewdoc;
    }
}
