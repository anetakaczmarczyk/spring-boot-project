package org.example.springbootproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringBootProjectApplication {
    private static List<Teacher> teachers = new ArrayList<>();
    private static ClassContainer classContainer = new ClassContainer();

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProjectApplication.class, args);

        //Stworzenie classContainer i dodanie do niego grup
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

    @Bean
    public List<Teacher> teachers() {
        return teachers;
    }

    @Bean
    public ClassContainer classes() {return classContainer;}

}

