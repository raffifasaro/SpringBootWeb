package spring.springdevbackend.repository;

import org.springframework.data.repository.CrudRepository;
import spring.springdevbackend.eventModel.Event;

import java.util.Date;

public interface EventRepository extends CrudRepository<Event, Integer> {

}
