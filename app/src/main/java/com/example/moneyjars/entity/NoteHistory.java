package com.example.moneyjars.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "tb_history",foreignKeys = @ForeignKey(entity = Jar.class,parentColumns = "id",childColumns = "jarId"),indices = {@Index(value = "jarId")})
public class NoteHistory  {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "jarId")
    public int jarId;

    @ColumnInfo(name = "nameJar")
    public String nameJar;

    @ColumnInfo(name = "avatarJar")
    public int avatarJar;

    @ColumnInfo(name = "userId")
    public int userId;

    @ColumnInfo(name = "type")
    public int type;

    @ColumnInfo(name = "amount")
    public String amount;

    @ColumnInfo(name = "timeStamp")
    public String timeStamp;

    @ColumnInfo(name = "description")
    public String des;


    public NoteHistory(int jarId, String nameJar,int avatarJar, int userId,int type, String amount, String timeStamp, String des) {
        this.jarId = jarId;
        this.nameJar = nameJar;
        this.avatarJar = avatarJar;
        this.userId = userId;
        this.type = type;
        this.amount = amount;
        this.timeStamp = timeStamp;
        this.des = des;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJarId() {
        return jarId;
    }

    public void setJarId(int jarId) {
        this.jarId = jarId;
    }

    public String getNameJar() {
        return nameJar;
    }

    public void setNameJar(String nameJar) {
        this.nameJar = nameJar;
    }

    public int getAvatarJar() {
        return avatarJar;
    }

    public void setAvatarJar(int avatarJar) {
        this.avatarJar = avatarJar;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
