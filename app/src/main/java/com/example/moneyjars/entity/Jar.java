package com.example.moneyjars.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_jar",foreignKeys = @ForeignKey(entity = User.class,parentColumns = "id",childColumns = "userId"),indices = {@Index(value = "userId")})
public class Jar {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "userId")
    public int userId;

    @ColumnInfo(name = "nameJar")
    public String nameJar;

    @ColumnInfo(name = "amount")
    public String amount;

    @ColumnInfo(name = "percent")
    public int percent;

    @ColumnInfo(name = "income")
    public String income;

    @ColumnInfo(name = "expense")
    public String expense;

    @ColumnInfo(name = "avatar")
    public int avatar;

    public Jar(int userId, String nameJar, String amount, int percent, String income, String expense, int avatar) {
        this.userId = userId;
        this.nameJar = nameJar;
        this.amount = amount;
        this.percent = percent;
        this.income = income;
        this.expense = expense;
        this.avatar = avatar;
    }

    private boolean checked = true;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNameJar() {
        return nameJar;
    }

    public void setNameJar(String nameJar) {
        this.nameJar = nameJar;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
