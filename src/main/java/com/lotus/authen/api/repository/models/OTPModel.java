package com.lotus.authen.api.repository.models;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "TBL_OTP")
public class OTPModel implements Serializable {

    @EmbeddedId
    private OTPKey id;

    @Column(name = "count_status")
    int countStatus;

    @Column(name = "expire_time")
    int expireTime;

    @Column(name = "create_date")
    Date createDate;

    public OTPModel() {

    }

    public OTPKey getId() {
        return id;
    }

    public void setId(OTPKey id) {
        this.id = id;
    }

    public int getCountStatus() {
        return countStatus;
    }

    public void setCountStatus(int countStatus) {
        this.countStatus = countStatus;
    }

    public int getExpireTime() { return expireTime; }

    public void setExpireTime(int expireTime) { this.expireTime = expireTime; }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
