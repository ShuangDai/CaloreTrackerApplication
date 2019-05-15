package com.example.a5046assign2;

import java.util.Date;

public class Report {
    private String reportId;
    private Date reportDate;
    private int totalCaloriesConsumed;
    private int totalCaloriesBurned;
    private int toralStepsTaken;
    private int goal;
    private UserInfo userId;

    public Report(String reportId, Date reportDate, int totalCaloriesConsumed, int totalCaloriesBurned, int toralStepsTaken, int goal) {
        this.reportId = reportId;
        this.reportDate = reportDate;
        this.totalCaloriesConsumed = totalCaloriesConsumed;
        this.totalCaloriesBurned = totalCaloriesBurned;
        this.toralStepsTaken = toralStepsTaken;
        this.goal = goal;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public int getTotalCaloriesConsumed() {
        return totalCaloriesConsumed;
    }

    public void setTotalCaloriesConsumed(int totalCaloriesConsumed) {
        this.totalCaloriesConsumed = totalCaloriesConsumed;
    }

    public int getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    public void setTotalCaloriesBurned(int totalCaloriesBurned) {
        this.totalCaloriesBurned = totalCaloriesBurned;
    }

    public int getToralStepsTaken() {
        return toralStepsTaken;
    }

    public void setToralStepsTaken(int toralStepsTaken) {
        this.toralStepsTaken = toralStepsTaken;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public UserInfo getUserId() {
        return userId;
    }

    public void setUserId(UserInfo userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Report{" +
                "reportId='" + reportId + '\'' +
                ", reportDate=" + reportDate +
                ", totalCaloriesConsumed=" + totalCaloriesConsumed +
                ", totalCaloriesBurned=" + totalCaloriesBurned +
                ", toralStepsTaken=" + toralStepsTaken +
                ", goal=" + goal +
                ", userId=" + userId +
                '}';
    }
}
