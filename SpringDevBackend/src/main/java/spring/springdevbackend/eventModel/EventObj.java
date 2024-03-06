package spring.springdevbackend.eventModel;

import jakarta.persistence.Id;

import java.time.LocalTime;
import java.util.Date;

public record EventObj(@Id Date date, LocalTime time, String text) {

    @Override
    public String toString() {
        return "EventObj{" +
                "date=" + date +
                ", time='" + time + '\'' +
                ", Text='" + text + '\'' +
                '}';
    }
}
