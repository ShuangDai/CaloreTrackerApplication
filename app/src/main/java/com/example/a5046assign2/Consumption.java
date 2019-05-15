package com.example.a5046assign2;

import java.util.Date;

public class Consumption {
    private String consumptionId;
    private Date consumptionDate;
    private int servings;
    private Food foodId;
    private UserInfo userId;

    public Consumption(String consumptionId, Date consumptionDate, int servings) {
        this.consumptionId = consumptionId;
        this.consumptionDate = consumptionDate;
        this.servings = servings;
    }

    public String getConsumptionId() {
        return consumptionId;
    }

    public void setConsumptionId(String consumptionId) {
        this.consumptionId = consumptionId;
    }

    public Date getConsumptionDate() {
        return consumptionDate;
    }

    public void setConsumptionDate(Date consumptionDate) {
        this.consumptionDate = consumptionDate;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public Food getFoodId() {
        return foodId;
    }

    public void setFoodId(Food foodId) {
        this.foodId = foodId;
    }

    public UserInfo getUserId() {
        return userId;
    }

    public void setUserId(UserInfo userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Consumption{" +
                "consumptionId='" + consumptionId + '\'' +
                ", consumptionDate=" + consumptionDate +
                ", servings=" + servings +
                ", foodId=" + foodId +
                ", userId=" + userId +
                '}';
    }
}
