package app;


import java.util.Optional;

import app.Dto.DtoTask;
import app.models.Comment;
import app.models.Task;
import app.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
    @Mock
    public TaskRepository taskRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser(username = "d", password = "d")
    @Test
    public void checkTask() throws Exception {

      long id = 1L;

      mockMvc.perform(
              get("/check-task/{id}", id))
              .andDo(print());

    }
    @WithMockUser(username = "d", password = "d")
    @Test
    public void createTask() throws Exception {
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
    @WithMockUser(username = "d", password = "d")
    @Test
    public void updateTask() throws Exception {
        Optional<Task> task = Optional.of(new Task());
        task.get().setStatus("ok");
        task.get().setText("123");
        long id = 1L;

        mockMvc.perform(
                        post("/update-task/{id}", id)
                                .content(objectMapper.writeValueAsString(task))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").isString())
                .andExpect(jsonPath("$.text").value("123"));
    }
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
}