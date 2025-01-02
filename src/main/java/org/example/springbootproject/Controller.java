package org.example.springbootproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.MethodNotAllowedException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


// @Controller
// @ResponseBody
@RestController
public class Controller {

    private final List<Teacher> teachers;
    private final ClassContainer classes;

    private final TeacherCSVService teacherCSVService = new TeacherCSVService();

    @Autowired
    public Controller(final List<Teacher> teachers, ClassContainer classes) {
        this.teachers = teachers;
        this.classes = classes;
    }

    @PostMapping("/api/teacher")
    public String addTeacher(@RequestBody Teacher teacher) {
        teachers.add(teacher);
        return teacher.name + " " + teacher.surname + " added";
    }

    @DeleteMapping("/api/teacher/")
    public ResponseEntity<String> delTeacher(@RequestParam int id) {
        System.out.println(this.teachers.toString());
        if (id >= teachers.size() || id < 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("teacher not found");
        }
        else{
        Teacher t = teachers.get(id);
        teachers.remove(id);
        return  ResponseEntity.ok(t.name + " " + t.surname + " removed");
        }
    }

    @GetMapping("/api/teacher")
    public String showTeachers() {
        StringBuilder teachersString = new StringBuilder();
        for (Teacher t : teachers) {
            teachersString.append(t.name).append(" ").append(t.surname).append(" - ").append(t.condition).append(" ");
            teachersString.append("Rok urodzenia: ").append(t.yearOfBirth).append(" ");
            teachersString.append("Wynagrodzenie: ").append(t.salary);
            teachersString.append("\n");
        }
        return teachersString.toString();
    }

    @GetMapping("/api/teacher/csv")
    public ResponseEntity<String> makeCsv() {
        teacherCSVService.saveTeachersToCSV(teachers);
        return ResponseEntity.ok("Teachers saved to CSV");
    }

    @GetMapping("/api/group")
    public String showGroups() {
        StringBuilder groupsString = new StringBuilder();
        for (String className : classes.classes.keySet()){
            groupsString.append(className).append("\n");
        }
        return groupsString.toString();
    }

    @PostMapping("/api/group")
    public String addGroup(@RequestBody ClassTeacher classTeacher) {
        classes.addClass(classTeacher.groupName, classTeacher.maxNumberOfTeachers);
        return classTeacher.groupName + " added";
    }

    @DeleteMapping("/api/group/")
    public ResponseEntity<String> delGroup(@RequestParam String groupName) {
        if (!classes.classes.containsKey(groupName)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("group not found");
        }
        else{
            classes.removeClass(groupName);
            return  ResponseEntity.ok("Group " + groupName + " removed");
        }
    }

    @GetMapping("/api/group/{groupName}/teacher")
    public ResponseEntity<String> showTeachersInGroup(@PathVariable("groupName") String groupName) {
        if (!classes.classes.containsKey(groupName)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("group not found");
        }
        List<Teacher> groupTeachers = classes.classes.get(groupName).teachers;
        StringBuilder teachersString = new StringBuilder();
        for (Teacher t : groupTeachers) {
            teachersString.append(t.name).append(" ").append(t.surname).append(" - ").append(t.condition).append(" ");
            teachersString.append("Rok urodzenia: ").append(t.yearOfBirth).append(" ");
            teachersString.append("Wynagrodzenie: ").append(t.salary);
            teachersString.append("\n");
        }
        return ResponseEntity.ok(teachersString.toString());
    }

    @GetMapping("/api/group/{groupName}/fill")
    public ResponseEntity<String> showTeachersFillInGroup(@PathVariable("groupName") String groupName) {
        if (!classes.classes.containsKey(groupName)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("group not found");
        }
        ClassTeacher classTeacher = classes.classes.get(groupName);
        double fill = (double) classTeacher.teachers.size() / classTeacher.maxNumberOfTeachers*100;
        return ResponseEntity.ok(groupName + " fill: " + fill +"%");
    }


}