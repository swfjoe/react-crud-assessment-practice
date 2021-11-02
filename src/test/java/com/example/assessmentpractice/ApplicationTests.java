package com.example.assessmentpractice;

import com.example.assessmentpractice.Model.TodoItem;
import com.example.assessmentpractice.Repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {
    @Autowired
    MockMvc mvc;

    @Autowired
    TodoRepository newTodoRepository;

    @BeforeEach
    public void setupTest() {
        TodoItem item1 = new TodoItem();
        item1.setContent("Test Item 1!");
        this.newTodoRepository.save(item1);
    }

    @Transactional
    @Rollback
    @Test
    void testGetAllItems() throws Exception {
        TodoItem item2 = new TodoItem();
        item2.setContent("New Test Item!!");
        this.newTodoRepository.save(item2);

        this.mvc.perform(get("/api/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[1].content", is("New Test Item!!")));
    }

    @Transactional
    @Rollback
    @Test
    void testCreateNewTodoItem() throws Exception {
        String newTodoItem = "{\"content\":\"New Test Item!!\"}";

        this.mvc.perform(post("/api/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTodoItem))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is("New Test Item!!")));
    }

    @Transactional
    @Rollback
    @Test
    void testPatchModifiesTodoItem() throws Exception {
        TodoItem item2 = new TodoItem();
        item2.setContent("make ice cream");
        this.newTodoRepository.save(item2);
        String updateTodoItem = "{\"content\":\"New Test Item!!\"}";
        Long testId = item2.getId();

        this.mvc.perform(patch(String.format("/api/items/%d", testId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateTodoItem))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is("New Test Item!!")));
    }

    @Transactional
    @Rollback
    @Test
    void testDeleteSingleItem() throws Exception {
        TodoItem item2 = new TodoItem();
        item2.setContent("New Test Item!!");
        this.newTodoRepository.save(item2);
        Long testId = item2.getId();

        this.mvc.perform(delete(String.format("/api/items/%d", testId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", is("New Test Item!!")));
    }

}