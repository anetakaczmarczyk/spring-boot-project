package org.example.springbootproject;

import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class TeacherCSVService {
    public void saveTeachersToCSV(List<Teacher> teachers) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("teachers.csv"))) {
            writer.write("Name,Surname,Condition,BirthYear,Salary\n");

            for (Teacher teacher : teachers) {
                writer.write(
                        teacher.name + "," +
                                teacher.surname + "," +
                                teacher.condition + "," +
                                teacher.yearOfBirth + "," +
                                teacher.salary + "," + "\n");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
