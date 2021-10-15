package com.settleup.model;

public class SettleUp {

    String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getAmountToGive() {
        return isAmountToGive;
    }

    public void setAmountToGive(Boolean amountToGive) {
        isAmountToGive = amountToGive;
    }

    public Boolean getAmountToTake() {
        return isAmountToTake;
    }

    public void setAmountToTake(Boolean amountToTake) {
        isAmountToTake = amountToTake;
    }

    Integer amount;
    Boolean isAmountToGive;
    Boolean isAmountToTake;


}
