package spring.springbackend.repository;

import org.springframework.data.repository.CrudRepository;
import spring.springbackend.eventModel.Event;

public interface EventRepository extends CrudRepository<Event, Integer> {

}
