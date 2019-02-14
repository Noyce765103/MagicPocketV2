package com.example.magicpocketv1.Bean;

import com.example.magicpocketv1.Utils.DateUtil;

import java.util.UUID;

/**
 * 创建账目的实体类，一共有：
 * 金额(money) double
 * 收入/支出(money_type) Type:MONEY_TYPE_OUTCOME表示支出(返回值为1)，MONEY_TYPE_INCOME表示收入(返回值为2)
 * 类别(category) Integer
 * 备注(remark) String
 * 日起(date) String
 */
public class AccountBean {

    private enum Type{
        MONEY_TYPE_OUTCOME,MONEY_TYPE_INCOME
    }

    private double money;
    private Type money_type;
    private Integer category;
    private String remark;
    private String date; //20xx-xx-xx

    private String uuid; //分配一个唯一的标识码

    public AccountBean(){
        uuid = UUID.randomUUID().toString();
        date = DateUtil.getFormattedDate();

    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getMoney_type() {
        if(this.money_type == Type.MONEY_TYPE_OUTCOME)
            return 1;
        else
            return 2;
    }

    public void setMoney_type(int money_type) {
        if(money_type == 1)
            this.money_type = Type.MONEY_TYPE_OUTCOME;
        else
            this.money_type = Type.MONEY_TYPE_INCOME;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
