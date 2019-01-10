package com.cloud.kafka.kafkaserver.entity;

import java.util.Date;

public class Message {
    private Long id;
    private String msg;
    private Date sendTime;

    public Message(Long id, String msg, Date sendTime) {
        this.id = id;
        this.msg = msg;
        this.sendTime = sendTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }
}
