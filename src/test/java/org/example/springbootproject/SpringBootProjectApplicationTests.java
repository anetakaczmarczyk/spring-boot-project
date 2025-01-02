package org.example.springbootproject;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@SpringBootTest
@WebMvcTest(Controller.class)
class SpringBootProjectApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private List<Teacher> teachers = new ArrayList<>();

    @Autowired
    private ClassContainer classContainer = new ClassContainer();

    @BeforeEach
    void setup() {
        teachers.clear();
        classContainer.classes.clear();
        classContainer.addClass("Polski", 5);
        classContainer.addClass("Angielski", 10);
        classContainer.addClass("Matematyka", 2);
        classContainer.addClass("Informatyka", 3);
        //Tworzenie nauczycieli
        Teacher t1 = new Teacher("Agnieszka", "Grabowska", TeacherCondition.chory, 1999, 5100);
        Teacher t2 = new Teacher("Angelika", "Ziemna", TeacherCondition.nieobecny, 1985, 6300);
        Teacher t3 = new Teacher("Anna", "Jasielinska", TeacherCondition.delegacja, 2003, 3900);
        Teacher t4 = new Teacher("Martyna", "Szczurowska", TeacherCondition.obecny, 2004, 4200);
        Teacher t5 = new Teacher("Aneta", "Kaczmarczyk", TeacherCondition.obecny, 2002, 4500);
        teachers.add(t1);
        teachers.add(t2);
        teachers.add(t3);
        teachers.add(t4);
        teachers.add(t5);

        //Pobranie wszystkich grup
        ClassTeacher polski = classContainer.classes.get("Polski");
        ClassTeacher angielski = classContainer.classes.get("Angielski");
        ClassTeacher matematyka = classContainer.classes.get("Matematyka");
        ClassTeacher informatyka = classContainer.classes.get("Informatyka");

        //Dodawanie nauczycieli do grup
        angielski.addTeacher(t1);
        angielski.addTeacher(t4);
        matematyka.addTeacher(t2);
        informatyka.addTeacher(t3);
        informatyka.addTeacher(t5);
        informatyka.addTeacher(t4);
    }

    @Test
    public void testAddTeacher() throws Exception {
        String teacherJson = "{ \"name\": \"Krystian\", \"surname\": \"Zielony\", \"condition\": \"obecny\", \"yearOfBirth\": 2003, \"salary\": 5000 }";
        mvc.perform(post("/api/teacher")
                        .contentType("application/json")
                        .content(teacherJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Krystian Zielony added"));
    }
//
    @Test
    public void testDeleteTeacher() throws Exception {
        mvc.perform(delete("/api/teacher/")
                        .param("id", "0"))
                .andExpect(status().isOk())
                .andExpect(content().string("Agnieszka Grabowska removed"));
    }

    @Test
    public void testDeleteTeacherNotFound() throws Exception {
        mvc.perform(delete("/api/teacher/")
                        .param("id", "10"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("teacher not found"));
    }

    @Test
    public void testPrintTeachers() throws Exception {
        mvc.perform(get("/api/teacher"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Aneta Kaczmarczyk - obecny")));
    }

    @Test
    public void testSaveToCsv() throws Exception {
        mvc.perform(get("/api/teacher/csv"))
                .andExpect(status().isOk())
                .andExpect(content().string("Teachers saved to CSV"));
    }

    @Test
    public void showGroups() throws Exception {
        mvc.perform(get("/api/group"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Matematyka")));
    }

    @Test
    public void addGroup() throws Exception{
        String groupJson = """
                {
                    "groupName": "Geografia",
                    "maxNumberOfTeachers": 12
                }
                """;
        mvc.perform(post("/api/group")
                        .contentType("application/json")
                        .content(groupJson))
                .andExpect(status().isOk())
                .andExpect(content().string("Geografia added"));
    }

    @Test
    public void testDeleteGroup() throws Exception {
        mvc.perform(delete("/api/group/")
                        .param("groupName", "Informatyka"))
                .andExpect(status().isOk())
                .andExpect(content().string("Group Informatyka removed"));
    }

    @Test
    public void testDeleteGroupNotFound() throws Exception {
        mvc.perform(delete("/api/group/")
                        .param("groupName", "ABC"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("group not found"));
    }

    @Test
    public void testShowTeachersInGroup() throws Exception {
        mvc.perform(get("/api/group/{groupName}/teacher", "Matematyka"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Angelika Ziemna")));
    }

    @Test
    public void testShowTeachersFillInGroup() throws Exception {
        mvc.perform(get("/api/group/{groupName}/fill", "Angielski"))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("Angielski fill: 20.0%")));
    }

}
