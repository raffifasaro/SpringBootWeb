package spring.springdevbackend.eventModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalTime;
import java.util.Date;

@Entity
public record Event(@Id @JsonIgnore Integer id, Date date, LocalTime time, String text) {

    @Override
    public String toString() {
        return "EventObj{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", text='" + text + '\'' +
                '}';
    }
}
