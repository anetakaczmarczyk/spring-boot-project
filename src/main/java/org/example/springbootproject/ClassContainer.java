package org.example.springbootproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassContainer {
    public Map<String, ClassTeacher> classes = new HashMap<>();

    public void addClass(String className, int max){
        if (classes.containsKey(className)){
            System.out.println("\nGrupa " + className + " juz istnieje");
        }else{
            classes.put(className, new ClassTeacher(className, max));
        }
    }

    public void removeClass(String className){
        classes.remove(className);
        System.out.println("\nGrupa " + className + " zostala usunieta");
    }

    public void findEmpty(){
        List<String> emptyClasses = new ArrayList<>();
        for(Map.Entry<String, ClassTeacher> entry : classes.entrySet()){
            if (entry.getValue().teachers.isEmpty()){
                emptyClasses.add(entry.getKey());
            }
        }
        System.out.println("\nPuste grupy:");
        emptyClasses.forEach(System.out::println);
    }

    public void summary(){
        System.out.println("\nGrupy i ich zapelnienie:");
        for (Map.Entry<String, ClassTeacher> entry : classes.entrySet()){
            int numOfTeachers = entry.getValue().teachers.size();
            int max = entry.getValue().maxNumberOfTeachers;
            System.out.println("Grupa " + entry.getKey() + ", zapelnienie: " + (double) numOfTeachers/max *100.0 + "%");
        }
    }
}
