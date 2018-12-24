package com.system.baseapplibrary.bean;

import java.util.HashMap;

/**
 * 返回事件数据
 */
public class EventCallBackBean {
    public static final  int REFRESH=0;
    public static final  int CLOSE=1;
    public static final  int WHITEDATA=2;
    private int eventNumber;
    private String  eventString;
    private HashMap<String,Object> eventData;

    public EventCallBackBean() {
        eventData=new HashMap<>();
    }

    public int getEventNumber() {
        return eventNumber;
    }

    public void setEventNumber(int eventNumber) {
        this.eventNumber = eventNumber;
    }

    public String getEventString() {
        return eventString;
    }

    public void setEventString(String eventString) {
        this.eventString = eventString;
    }

    public HashMap<String,Object> getEventData() {
        return eventData;
    }

    public void setEventData(HashMap<String,Object> eventData) {
        this.eventData = eventData;
    }
}
