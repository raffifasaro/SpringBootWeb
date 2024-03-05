package spring.springdevbackend.calenderFormats;

import java.util.Date;

public class EventObj {
    private final String date;
    private final String time;

    private final String text;

    public EventObj(String date, String time, String text) {
        this.date = date;
        this.time = time;
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "EventObj{" +
                "date=" + date +
                ", time='" + time + '\'' +
                ", Text='" + text + '\'' +
                '}';
    }
}
