package spring.springdevbackend.repository;

import org.springframework.data.repository.CrudRepository;
import spring.springdevbackend.eventModel.EventObj;

import java.util.Date;

public interface EventRepository extends CrudRepository<EventObj, Date> {

}
