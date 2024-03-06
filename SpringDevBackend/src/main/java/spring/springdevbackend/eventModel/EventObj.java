package spring.springdevbackend.eventModel;

import java.time.LocalTime;
import java.util.Date;

public record EventObj(Date date, LocalTime time, String text) {

    @Override
    public String toString() {
        return "EventObj{" +
                "date=" + date +
                ", time='" + time + '\'' +
                ", Text='" + text + '\'' +
                '}';
    }
}
