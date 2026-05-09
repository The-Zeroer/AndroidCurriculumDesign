package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.audits;

public class AuditItem {
    private int id;
    private String title;
    private String desc;
    private String status;

    public AuditItem(int id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.status = "pending";
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDesc() { return desc; }
    public String getStatus() { return status; }
}