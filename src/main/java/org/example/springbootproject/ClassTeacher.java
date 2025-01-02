package org.example.springbootproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClassTeacher {
    public String groupName;
    public ArrayList<Teacher> teachers = new ArrayList<>();
    public int maxNumberOfTeachers;

    public ClassTeacher(String groupName, int maxNumberOfTeachers) {
        this.groupName = groupName;
        this.maxNumberOfTeachers = maxNumberOfTeachers;
    }

    public void addTeacher(Teacher teacher) {
        if (isTeacherInClass(teacher)) {
            System.out.println("\nW tej grupie już istnieje taki nauczyciel");
        }else{

            if (teachers.size() >= maxNumberOfTeachers){
                System.out.println("\nBrak miejsca w grupie");
            }
            else {
                teachers.add(teacher);
            }
        }
    }

    boolean isTeacherInClass(Teacher teacher){
        for (Teacher t : teachers){
            if (t.name.equals(teacher.name) && t.surname.equals(teacher.surname)) {
                return true;
            }
        }
        return false;
    }
    int returnTeacherId(Teacher t){
        for (int i = 0; i < teachers.size(); i++){
            if (t.name.equals(teachers.get(i).name) && t.surname.equals(teachers.get(i).surname)){
                return i;
            }
        }
        return -1;
    }

    public void addSalary(Teacher t, int salary){
        if (isTeacherInClass(t)){
            System.out.println("\nWyplata przed zmiana: " + t.salary);
            t.salary += salary;
            System.out.println("Wyplata po zmianie: " +  t.salary);
        }
        else{
            System.out.println("\nNauczyciel nie znajduje sie w grupie");
        }
    }
    public void removeTeacher(Teacher t){
        int id = returnTeacherId(t);
        if (id != -1){
            teachers.remove(id);
            System.out.println("\nPomyslnie usunieto");
        }
        else{
            System.out.println("Nauczyciel nie znajduje sie w grupie");
        }

    }
  public void changeCondition(Teacher t, TeacherCondition condition) {
      if (isTeacherInClass(t)){
          t.condition = condition;
          System.out.println("\nStan po zmianie: " +  t.condition);
      }
      else{
          System.out.println("\nNauczyciel nie znajduje sie w grupie");
      }
  }
  public void search(String surname){
        Teacher searchTeacher = new Teacher("", surname, TeacherCondition.nieobecny, 0, 0);
        int i = 1;
        for (Teacher t : teachers){
            if (t.compareTo(searchTeacher) == 0){
                System.out.println("\nZnaleziono " + i + " nauczyciela: ");
                t.printing();
                i++;
            }
        }
        if (i == 1){
            System.out.println("\nNie znaleziono nauczyciela z wprowadzonym nazwiskiem");
        }
  }
  public void searchPartial(String s){
        int i = 1;
        for (Teacher t : teachers){
            if (t.name.contains(s) || t.surname.contains(s)){
                System.out.println("\nZnaleziono " + i + " nauczyciela: ");
                t.printing();
                i++;
            }
        }
      if (i == 1){
          System.out.println("\nNie znaleziono nauczyciela zawierajacego podany ciag znakow w imieniu lub nazwisku");
      }
  }

  public void countByCondition(TeacherCondition tc){
        int count = 0;
        for (Teacher t : teachers){
            if (t.condition.equals(tc)){
                count++;
            }
        }
        System.out.println("\nZnaleziono " + count + " nauczycieli o podanym stanie");
  }
  public void summary(){
        teachers.forEach(Teacher::printing);
  }
  public void sortByName(){
      ArrayList<Teacher> sortedTeachers = new ArrayList<>(teachers);
      sortedTeachers.sort(Comparator.comparing(sortedTeacher -> sortedTeacher.name));
      System.out.println("\nPosortowani nauczyciele po imieniu:");
      for (Teacher sortedTeacher : sortedTeachers) {
          sortedTeacher.printing();
      }
  }
  public void sortBySalary(){
        ArrayList<Teacher> sortedTeachers = new ArrayList<>(teachers);
        sortedTeachers.sort(new Comparator<Teacher>() {
            @Override
            public int compare(Teacher o1, Teacher o2) {
                return Integer.compare(o2.salary, o1.salary);
            }
        });
      System.out.println("\nPosortowani nauczyciele po wynagrodzeniu:");
      for (Teacher sortedTeacher : sortedTeachers) {
          sortedTeacher.printing();
      }
  }

  public void max(){
        System.out.println("\nNauczyciel o największym wynagrodzeniu:");
        Collections.max(teachers, Comparator.comparing(sortedTeacher -> sortedTeacher.salary)).printing();
  }

}
