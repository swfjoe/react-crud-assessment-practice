package com.example.assessmentpractice.Repository;
import com.example.assessmentpractice.Model.TodoItem;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface TodoRepository extends CrudRepository <TodoItem, Long> {


}
