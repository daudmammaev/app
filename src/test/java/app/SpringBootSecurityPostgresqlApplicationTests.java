package app;


import java.util.Optional;

import app.Dto.DtoTask;
import app.models.Comment;
import app.models.Task;
import app.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@SpringBootTest
public class SpringBootSecurityPostgresqlApplicationTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    public TaskRepository taskRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(username = "d", password = "d")
    @Test
    public void deleteTask() throws Exception {
        Optional<Task> task = Optional.of(new Task());
        task.get().setStatus("ok");
        task.get().setText("123");
        long id = 1L;

        mockMvc.perform(
                        post("/delete/{id}", id)
                                .content(objectMapper.writeValueAsString(task))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }
    @WithMockUser(username = "d", password = "d")
    @Test
    public void TaskAddComment() throws Exception {
        Optional<Task> task = Optional.of(new Task());
        task.get().setStatus("ok");
        task.get().setText("123");
        Comment comment = new Comment();
        comment.setText("312");
        List<Comment> list = new ArrayList<>();
        list.add(comment);
        task.get().setCommets(list);
        long id = 1L;

        mockMvc.perform(
                        post("/task/{id}/comment", id)
                                .content(objectMapper.writeValueAsString(task))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }
    @WithMockUser(username = "d", password = "d")
    @Test
    public void testCheckTask() throws Exception {
        Optional<Task> task = Optional.of(new Task());
        task.get().setStatus("ok");
        task.get().setText("123");
        task.get().setId(1L);
        Mockito.when(taskRepository.findById(Mockito.any())).thenReturn(Optional.of(task.get()));

        mockMvc.perform(
                        get("/check-task/{id}", task.get().getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.text").value("123"))
                .andExpect(jsonPath("$.status").value("ok"));
    }
    @WithMockUser(username = "d", password = "d")
    @Test
    public void testCreateTask() throws Exception {
        Optional<Task> task = Optional.of(new Task());
        task.get().setStatus("ok");
        task.get().setText("123");

        mockMvc.perform(
                        post("/create-task")
                                .content(objectMapper.writeValueAsString(task))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").isString())
                .andExpect(jsonPath("$.text").value("123"));
    }
}