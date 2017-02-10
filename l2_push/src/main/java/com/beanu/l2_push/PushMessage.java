package com.beanu.l2_push;

import java.util.Map;

/**
 * 将消息整合成Message
 * Created by Beanu on 2017/2/10.
 */

public class PushMessage {
    private int notifyID;  //这个字段用于通知的消息类型，在透传中都是默认0
    private String messageID;
    private String title;
    private String message;
    private Map<String, String> extra;
    private PhoneTarget target;

    public int getNotifyID() {
        return notifyID;
    }

    public void setNotifyID(int notifyID) {
        this.notifyID = notifyID;
    }

    public String getMessageID() {
        return messageID;
    }

    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public PhoneTarget getTarget() {
        return target;
    }

    public void setTarget(PhoneTarget target) {
        this.target = target;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "PushMessage{" +
                "notifyID=" + notifyID +
                ", messageID='" + messageID + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", extra='" + extra + '\'' +
                ", target=" + target +
                '}';
    }
}
