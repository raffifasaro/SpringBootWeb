package spring.springdevbackend.calenderFormats;

import java.util.Date;

public class EventObj {
    private final Date date;
    private final String time;

    private final String Text;

    public EventObj(Date date, String time, String text) {
        this.date = date;
        this.time = time;
        Text = text;
    }

    public Date getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getText() {
        return Text;
    }

    @Override
    public String toString() {
        return "EventObj{" +
                "date=" + date +
                ", time='" + time + '\'' +
                ", Text='" + Text + '\'' +
                '}';
    }
}
