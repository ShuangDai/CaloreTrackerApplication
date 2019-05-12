package com.example.a5046assign2;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class LocalData {
    @PrimaryKey(autoGenerate = true)
    public int dailyStepId;

    @ColumnInfo(name = "steps")
    public int steps;

    public LocalData(int steps) {
        this.steps = steps;
    }

    public int getDailyStepId() {
        return dailyStepId;
    }

    public void setDailyStepId(int dailyStepId) {
        this.dailyStepId = dailyStepId;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }
}
