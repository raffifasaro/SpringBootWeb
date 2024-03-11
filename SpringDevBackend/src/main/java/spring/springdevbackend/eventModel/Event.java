package spring.springdevbackend.eventModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;


@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    private final static ObjectMapper MAPPER = new ObjectMapper();

    private final static Logger LOG = LoggerFactory.getLogger(Event.class);
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

    public static void serialise(final Event event, final String fileName) {
        try (final BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            final String serializedEvent = MAPPER.writeValueAsString(event);
            writer.write(serializedEvent);

        } catch (JsonProcessingException ex) {
            LOG.warn("Writing Event as JSON failed due to: {}", ex.getMessage());

        } catch (FileNotFoundException ex) {
            LOG.warn("Creating File-Writer for path: {} failed due to: {}", fileName, ex.getMessage());

        } catch (IOException ex) {
            LOG.warn("Something went wrong during IO-Operations: {}", ex.getMessage());

        }
    }

    public static Optional<Event> deserialise(final String serialisedEvent) {
        try {
            return Optional.of(MAPPER.readValue(serialisedEvent, Event.class));
        } catch (JsonProcessingException ex) {
            LOG.warn("Deserialising Event-Object failed due to: {}", ex.getMessage());
            return Optional.empty();
        }
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

