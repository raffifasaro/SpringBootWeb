package spring.springdevbackend.eventModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer id;

    private LocalDate date;
    private LocalTime time;
    private String text;

    public Event(LocalDate date, LocalTime time, String text) {
        this.date = date;
        this.time = time;
        this.text = text;
    }

    public Event() {

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", text='" + text + '\'' +
                '}';
    }
}

