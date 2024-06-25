package Actor;

import java.util.*;
import java.io.*;

public class Subject {
    private String code;
    private String name;
    private int creditHour;
    private boolean flag;
    private int capacity; 
    private ArrayList<Student> registeredStudents;
    private ArrayList<Section> sections;
    
    // Constructor, getters, setters, etc.
    public Subject() {
    }

    public Subject(String code, String name, boolean flag, int creditHour) {
        this.code = code;
        this.name = name;
        this.creditHour = creditHour;
        this.flag = flag;
        this.capacity = 0;
        this.registeredStudents = new ArrayList<>();
        sections=new ArrayList<>();
    }


    public ArrayList<Student> getRegisteredStudents() {
        return registeredStudents;
    }

    public ArrayList<Section> getSections(){
        return sections;
    }


    public void addStudent(Student student) {
        if (registeredStudents.size() < capacity) {
            registeredStudents.add(student);
        } else {
            System.out.println("Cannot add student, capacity reached.");
        }
    }
    
    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getCreditHour() {
        return creditHour;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Subject> readSubjects(Scanner inpFile) {
        ArrayList<Subject> subjects = new ArrayList<>();
        while (inpFile.hasNextLine()) {
            String line = inpFile.nextLine();
            String[] parts = line.split(",");
            if (parts.length == 4) {
                String code = parts[0];
                int creditHour = Integer.parseInt(parts[1]);
                boolean flag = Boolean.parseBoolean(parts[2]);
                String name = parts[3];
                subjects.add(new Subject(code, name, flag, creditHour));
            } else {
                System.out.println("Invalid data format: " + line);
            }
        }
        inpFile.close();
        return subjects;
    }

    public void display() {
        //
        ArrayList<Subject> subjectList = new ArrayList<>();
        //
        System.out.println("***********************************************************************");
        System.out.println("                          LIST OF COURSES");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-12s%-32s%5s\n", "\tCODE", "COURSE NAME", "CREDIT HOUR");
        System.out.printf("%-12s%-32s%5s\n", "\t--------", "-----------------------------", "-----------");
        for (Subject s : subjectList) {
            System.out.printf("\t%-11s%-33s%5d\n", s.getCode(), s.getName(), s.getCreditHour());
        }
        System.out.println();
    }

}
