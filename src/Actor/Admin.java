package Actor;
import java.io.*;
import java.util.*;


public class Admin extends Person {
    private static ArrayList<Subject> subjectList;
    private static ArrayList<Student> studentsList;
    private static ArrayList<Lecturer> lecturerList;

    private static HashMap<String,Subject> subjectHashMap;
    private static HashMap<String,Student> studentHashMap;
    private static HashMap<String,Lecturer> lecturerHashMap;
    
    private static HashMap<Subject,ArrayList<Student>> studentTakeSubjectHashMap;
    private static HashMap<Student,ArrayList<Subject>> studentRegisterSubjectHashMap;
    private static HashMap<Student,ArrayList<Subject>> studentDropSubjectHashMap;

    private static Scanner scanner = new Scanner(System.in);
    static{
        subjectList=new ArrayList<Subject>();
        // studentsList=new ArrayList<Student>();
        // lecturerList=new ArrayList<Lecturer>();
        
        subjectHashMap=new HashMap<String,Subject>();
        studentHashMap=new HashMap<String,Student>();
        lecturerHashMap=new HashMap<String,Lecturer>();

        studentTakeSubjectHashMap=new HashMap<Subject,ArrayList<Student>>();
        studentRegisterSubjectHashMap=new HashMap<Student,ArrayList<Subject>>();
        studentDropSubjectHashMap=new HashMap<Student,ArrayList<Subject>>();
    }
    
    public Admin(String id, String name) {
        super(id, name);
    }

    public void displaySubjectSections() {
        HashMap<String, String> subjectNames = new HashMap<>();

        // Read subject names from subjectList.csv
        try (BufferedReader br = new BufferedReader(new FileReader("src/subjectList.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String code = parts[0];
                String name = parts[3];
                subjectNames.put(code, name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display subject sections with course names
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-10s %-10s %-6s %-30s\n", "Code", "Capacity", "Section", "Name");
        System.out.println("-----------------------------------------------------");
        try (BufferedReader br = new BufferedReader(new FileReader("src/subjectsection.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String code = parts[0];
                String credits = parts[1];
                String open = parts[2];
                String name = subjectNames.getOrDefault(code, "Unknown");
                System.out.printf("%-10s %-10s %-6s %-30s\n", code, credits, open, name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("-----------------------------------------------------");
    }
       // 1. List Subjects
    public void listSubjects() {
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("                          LIST OF SUBJECTS");
        System.out.println("-----------------------------------------------------------------------\n");
        System.out.printf("%-12s%-32s%5s\n", "\tCODE", "COURSE NAME", "CREDIT HOUR");
        System.out.printf("%-12s%-32s%5s\n", "\t--------", "-----------------------------", "-----------");
        for (Subject s : subjectList) {
            System.out.printf("\t%-11s%-33s%5d\n", s.getCode(), s.getName(), s.getCreditHour());
        }
        System.out.println();
    }

    // 2. Manage Subject Sections
    public void manageSubjectSections() {
        displaySubjectSections(); // Call the method to display the CSV contents
        System.out.println("Enter the subject code to manage sections: ");
        String subjectCode = scanner.next();
        Subject subject = subjectHashMap.get(subjectCode);
        if (subject != null) {
            System.out.println("Managing sections for: " + subject.getName());
            System.out.println("1. Open Section");
            System.out.println("2. Close Section");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            boolean flag = (choice == 1);
            if (choice == 1 || choice == 2) {
                subject.setFlag(flag);
                updateSubjectSectionCSV(subjectCode, flag);
                System.out.println("Section for " + subject.getName() + " is now " + (flag ? "open" : "closed"));
                displaySubjectSections(); // Display the updated CSV contents
            } else {
                System.out.println("Invalid choice.");
            }
        } else {
            System.out.println("Subject not found.");
        }
    }

    // Method to update the subject section CSV file
    private void updateSubjectSectionCSV(String subjectCode, boolean flag) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/subjectsection.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(subjectCode)) {
                    parts[2] = String.valueOf(flag);
                    line = String.join(",", parts);
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/subjectsection.csv"))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
           // 3. Set Student Capacity
    public void setStudentCapacity() {
        displaySubjectSections(); // Call the method to display the CSV contents
        System.out.println("Enter the subject code to set capacity: ");
        String subjectCode = scanner.next();
        Subject subject = subjectHashMap.get(subjectCode);
        if (subject != null) {
            System.out.println("Enter new capacity for: " + subject.getName() + " (must be below 16):");
            int capacity = scanner.nextInt();
            if (capacity < 16) {
                updateSubjectSection(subjectCode, null, capacity);
                System.out.println("Capacity set to " + capacity + " for " + subject.getName());
                displaySubjectSections(); // Display the updated CSV contents
            } else {
                System.out.println("Capacity must be below 16. Please try again.");
            }
        } else {
            System.out.println("Subject not found.");
        }
    }

    // Method to update the subject section CSV file
    private void updateSubjectSection(String subjectCode, Boolean flag, Integer capacity) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/subjectsection.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(subjectCode)) {
                    if (flag != null) {
                        parts[2] = String.valueOf(flag);
                    }
                    if (capacity != null) {
                        parts[1] = String.valueOf(capacity);
                    }
                    line = String.join(",", parts);
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/subjectsection.csv"))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
    // 4. Drop Subject/Course
    public void dropSubjectCourse() {
        readStudentDropSubject();
        readStudentTakeSubject();
        
        System.out.println("***********************************************************************");
        System.out.println("                    CONFIRM SUBJECT DROP?");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-20s%-32s\n", "\tMATRIKS NO", "COURSE CODE");
        System.out.printf("%-20s%-32s\n", "\t--------","---------------------");
        for (Map.Entry<Student, ArrayList<Subject>> entry : studentDropSubjectHashMap.entrySet()) {
            for(Subject s:entry.getValue())
                System.out.printf("\t%-20s\t%-32s\n",entry.getKey().getId() , s.getCode());
        }

        String tempMatriksNo="";
        boolean correct=false;
        while(!correct){
            System.out.print("\n\n\t\tENTER Matriks NO : ");
            Scanner sc=new Scanner(System.in);
            tempMatriksNo=sc.nextLine();
            if(studentRegisterSubjectHashMap.get(studentHashMap.get(tempMatriksNo))!=null){
                correct=true;
            }
            if(!correct){
                System.out.println("\n\n\tNo such student request to drop any subject ! ! !");
                System.out.println("\tPlease Try again . . . ");
            }
        }
        
            
        
        System.out.println("***********************************************************************");
        System.out.println("                    CONFIRM SUBJECT DROP");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-20s%-32s\n", "\tMATRIKS NO", "COURSE CODE");
        System.out.printf("%-20s%-32s\n", "\t--------","---------------------");
        
        for (Map.Entry<Student, ArrayList<Subject>> entry : studentDropSubjectHashMap.entrySet()) {
            if(entry.getKey().getId().equals(tempMatriksNo))
                for(Subject s:entry.getValue()) 
                    System.out.printf("\t%-20s\t%-32s\n",entry.getKey().getId() , s.getCode());
        }

        String tempSubjectCode="";
        correct=false;
    
        System.out.print("\n\n\t\tENTER Subject Code : ");
        Scanner sc=new Scanner(System.in);
        tempSubjectCode=sc.nextLine();
        for(Subject s:studentDropSubjectHashMap.get(studentHashMap.get(tempMatriksNo))){
            if(s.getCode().equals(tempSubjectCode))
                correct=true;
        }
        if(!correct){
            System.out.println("\n\n\tThe Student did not register that subject ! ! !");
            System.out.println("\tPlease Try again . . . ");
        }
        else
            System.out.println("\n\n\tSubject added to Student's Subject List");
        

        if(correct){
            // Remove the course from studenttakesubject hashmap
            studentTakeSubjectHashMap.get(subjectHashMap.get(tempSubjectCode)).remove(studentHashMap.get(tempMatriksNo));

            // rewrite the student take subject file
            try (FileWriter writer = new FileWriter("src/studentTakeSubject.csv")) {
                for (Map.Entry<Subject, ArrayList<Student>> entry : studentTakeSubjectHashMap.entrySet()) {
                    for(Student s:entry.getValue()){
                        writer.write(s.getId()+", "+entry.getKey().getCode()+"\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Remove the course from student drop hashmap
            studentDropSubjectHashMap.get(studentHashMap.get(tempMatriksNo)).remove(subjectHashMap.get(tempSubjectCode));

            // rewrite the dropping subject file
            try (FileWriter writer = new FileWriter("src/DroppingSubject.csv")) {
                for (Map.Entry<Student, ArrayList<Subject>> entry : studentDropSubjectHashMap.entrySet()) {
                    for(Subject s:entry.getValue()){
                        writer.write(entry.getKey().getId()+","+"FALSE"+","+s.getCode()+","+s.getName()+"\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    // 5. Confirm Course Registrations
    public void confirmCourseRegistrations() {
        readStudentRegSubject();
        readStudentTakeSubject();
        
        System.out.println("***********************************************************************");
        System.out.println("                    CONFIRM COURSE REGISTRATION");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-20s%-32s\n", "\tMATRIKS NO", "COURSE CODE");
        System.out.printf("%-20s%-32s\n", "\t--------","---------------------");
        for (Map.Entry<Student, ArrayList<Subject>> entry : studentRegisterSubjectHashMap.entrySet()) {
            for(Subject s:entry.getValue())
                System.out.printf("\t%-20s\t%-32s\n",entry.getKey().getId() , s.getCode());
        }

        String tempMatriksNo="";
        boolean correct=false;
        while(!correct){
            System.out.print("\n\n\t\tENTER Matriks NO : ");
            Scanner sc=new Scanner(System.in);
            tempMatriksNo=sc.nextLine();
            if(studentRegisterSubjectHashMap.get(studentHashMap.get(tempMatriksNo))!=null){
                correct=true;
            }
            if(!correct){
                System.out.println("\n\n\tNo such student register any subject ! ! !");
                System.out.println("\tPlease Try again . . . ");
            }
        }
        
            
        
        System.out.println("***********************************************************************");
        System.out.println("                    CONFIRM COURSE REGISTRATION");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-20s%-32s\n", "\tMATRIKS NO", "COURSE CODE");
        System.out.printf("%-20s%-32s\n", "\t--------","---------------------");
        
        for (Map.Entry<Student, ArrayList<Subject>> entry : studentRegisterSubjectHashMap.entrySet()) {
            if(entry.getKey().getId().equals(tempMatriksNo))
                for(Subject s:entry.getValue()) 
                    System.out.printf("\t%-20s\t%-32s\n",entry.getKey().getId() , s.getCode());
        }

        String tempSubjectCode="";
        correct=false;
    
        System.out.print("\n\n\t\tENTER Subject Code : ");
        Scanner sc=new Scanner(System.in);
        tempSubjectCode=sc.nextLine();
        for(Subject s:studentRegisterSubjectHashMap.get(studentHashMap.get(tempMatriksNo))){
            if(s.getCode().equals(tempSubjectCode))
                correct=true;
        }
        if(!correct){
            System.out.println("\n\n\tThe Student did not register that subject ! ! !");
            System.out.println("\tPlease Try again . . . ");
        }
        else
            System.out.println("\n\n\tSubject added to Student's Subject List");
        

        if(correct){
            // Add the course to the student's registered course
            try (FileWriter writer = new FileWriter("src/studentTakeSubject.csv",true)) {
                writer.write(tempMatriksNo+", "+tempSubjectCode+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Remove the course from studentRegister hashmap
            studentRegisterSubjectHashMap.get(studentHashMap.get(tempMatriksNo)).remove(subjectHashMap.get(tempSubjectCode));
            
            //Rewrite/remove the course from studentRegisterSubject.csv
            try (FileWriter writer = new FileWriter("src/studentRegisterSubject.csv")) {
                for (Map.Entry<Student, ArrayList<Subject>> entry : studentRegisterSubjectHashMap.entrySet()) {
                    
                    for(Subject s:entry.getValue()){
                        writer.write(entry.getKey().getId()+", "+s.getCode()+"\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
        //DO NOT CLOSE SCANNER BROSKI
        
    }


    //6. List Registered Students
    public void listRegisteredStudents() {
        
        System.out.println("***********************************************************************");
        System.out.println("                          LIST OF STUDENTS");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-20s%-32s\n", "\tMATRIKS NO", "NAME");
        System.out.printf("%-20s%-32s\n", "\t--------","-----------------------------");
        for(Student s:studentsList){
            System.out.printf("\t%-20s%-32s\n",s.getId(),s.getName());
        }
        System.out.println();
    
    }


    //7. List Registered Lecturers
    public void listRegisteredLecturers() {
        System.out.println("***********************************************************************");
        System.out.println("                          LIST OF LECTURERS");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-20s%-32s\n", "\tSTAFF NO", "NAME");
        System.out.printf("%-20s%-32s\n", "\t--------","-----------------------------");
        for(Lecturer l:lecturerList){
            System.out.printf("\t%-12s\t%-32s\n",l.getId(),l.getName());
        }
        
        System.out.println();
        
    }

    //TODO 8. Close Subjects
    public void closeSubjects() {
        readSubjectList();
        readStudentTakeSubject();

        System.out.println("***********************************************************************");
        System.out.println("                          LIST OF SUBJECTS");
        System.out.println("***********************************************************************\n");
        System.out.printf("%-20s%-32s\n", "\tSUBJECT CODE", "TOTAL REGISTERED STUDENT");
        System.out.printf("%-20s%-32s\n", "\t--------","-----------------------------");
        for(Map.Entry<Subject,ArrayList<Student>> entry:studentTakeSubjectHashMap.entrySet()){
            
            System.out.printf("\t%-20s\t%-32s\n",entry.getKey().getCode(),entry.getValue().size());                
            
        }
        Scanner sc=new Scanner(System.in);
        System.out.print("\n\n\tEnter Subject Code to close : ");
        String tempCode=sc.next();
        
        if(subjectHashMap.get(tempCode)!=null){
            //set flag to false
            subjectHashMap.get(tempCode).setFlag(false);
            //TODO remove student from subject?
            for(Student s:studentTakeSubjectHashMap.get(subjectHashMap.get(tempCode))){
                
            }

            //Rewrite/remove the course from studentRegisterSubject.csv
            try (FileWriter writer = new FileWriter("src/subjectList.csv")) {
                for(Subject s:subjectList){
                    writer.write(s.getCode()+","+s.getCreditHour()+","+s.getFlag()+","+s.getName()+"\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("\n\n\tSubject successfully closed ! ! ! ");
        }
        else
            System.out.println("\n\n\tSubject does not exist");
    }



    // private static void readStudentList() throws IOException{
    //     Scanner inpFile = new Scanner(new File("src/studentList.csv"));
    //     inpFile.useDelimiter(",|\\n");
    //     while (inpFile.hasNext()) {
    //         String matrikxno = inpFile.next();
    //         String name = inpFile.nextLine();
    //         name = name.substring(1);
    //         Student student = new Student(matrikxno, name);
    //         studentsList.add(student);
    //     }
    //     inpFile.close();
    // }
    // private static void readLecturerList() throws IOException{
    //     Scanner inpFile = new Scanner(new File("src/lecturerList.csv"));
    //     inpFile.useDelimiter(",|\\n");
    //     while (inpFile.hasNext()) {
    //         String staffNo = inpFile.next();
    //         String name = inpFile.nextLine();
    //         name = name.substring(1);
    //         Lecturer lecturer = new Lecturer(staffNo, name);
    //         lecturerList.add(lecturer);
    //     }
    //     inpFile.close();
    // }
    public static void getLists(ArrayList<Student> studList,ArrayList<Lecturer> lectList){
        
        studentsList=studList;
        lecturerList=lectList;
        // subjectList=subjList; main does not provide subject list
        ArrayListtoHashMap();
    }

    private static void readSubjectList() {
        subjectList.clear();
        try{
            Scanner inpFile = new Scanner(new File("src/subjectList.csv"));
            inpFile.useDelimiter(",|\\n");
            while (inpFile.hasNext()) {
                String code = inpFile.next();
                int creditHr = inpFile.nextInt();
                boolean flag = inpFile.nextBoolean();
                String name = inpFile.nextLine();
                name = name.substring(1);
                Subject subject = new Subject(code,name,flag,creditHr);
                subjectList.add(subject);
            }
            inpFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    

    private static void ArrayListtoHashMap(){
    

        readSubjectList(); //main does not provide subject list

        subjectArrListToHashMap();
        studentArrListToHashMap();
        lecturerArrListToHashMap();

        readStudentRegSubject();
        readStudentTakeSubject();
        readStudentDropSubject();

        
    }

    private static void subjectArrListToHashMap(){
        for(Subject s:subjectList){
            subjectHashMap.put(s.getCode(), s);
        }
    }
    private static void studentArrListToHashMap(){
        for(Student s:studentsList){
            studentHashMap.put(s.getId(),s);
        }
    }
    private static void lecturerArrListToHashMap(){
        for(Lecturer l:lecturerList){
            lecturerHashMap.put(l.getId(), l);
        }
    }

    private static void readStudentRegSubject(){
        studentRegisterSubjectHashMap.clear();
        try{
            Scanner inpFile = new Scanner(new File("src/studentRegisterSubject.csv"));
            inpFile.useDelimiter(",|\\n");
            while (inpFile.hasNext()) {
                String matriksNo = inpFile.next();
                String subjectCode = inpFile.nextLine();
                subjectCode=subjectCode.substring(2);

                Student tempStud=studentHashMap.get(matriksNo);
                Subject tempSubject=subjectHashMap.get(subjectCode);

                if(studentRegisterSubjectHashMap.get(tempStud)==null){
                    ArrayList<Subject> subjectToRegister=new ArrayList<>();
                    subjectToRegister.add(tempSubject);
                    studentRegisterSubjectHashMap.put(tempStud,subjectToRegister);
                }
                else{
                    boolean exist=false;
                    for(Subject s:studentRegisterSubjectHashMap.get(tempStud)){
                        if(s.equals(tempSubject))
                            exist=true;
                    }
                    if(!exist)
                        studentRegisterSubjectHashMap.get(tempStud).add(tempSubject); 
                }
                
            }
            inpFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private static void readStudentTakeSubject() {
        studentTakeSubjectHashMap.clear();
        try{

            Scanner inpFile = new Scanner(new File("src/studentTakeSubject.csv"));
            inpFile.useDelimiter(",|\\n");
            while (inpFile.hasNext()) {
                String matriksno = inpFile.next();
                String code = inpFile.nextLine();
                code = code.substring(2);
                
                if(studentTakeSubjectHashMap.get(subjectHashMap.get(code))==null)
                    studentTakeSubjectHashMap.put(subjectHashMap.get(code), new ArrayList<Student>());

                studentTakeSubjectHashMap.get(subjectHashMap.get(code)).add(studentHashMap.get(matriksno));
                
            }
            inpFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }


    private static void readStudentDropSubject() {
        studentDropSubjectHashMap.clear();
        try{

            Scanner inpFile = new Scanner(new File("src/DroppingSubject.csv"));
            inpFile.useDelimiter(",|\\n");
            while (inpFile.hasNext()) {
                String matriksno = inpFile.next();
                inpFile.next();
                String code = inpFile.next();
                inpFile.nextLine();

                if(studentDropSubjectHashMap.get(studentHashMap.get(matriksno))==null)
                    studentDropSubjectHashMap.put(studentHashMap.get(matriksno), new ArrayList<Subject>());
                boolean exist=false;
                for(Subject s:studentDropSubjectHashMap.get(studentHashMap.get(matriksno))){
                    if(s.getCode().equals(code))
                        exist=true;
                }
                if(!exist)
                    studentDropSubjectHashMap.get(studentHashMap.get(matriksno)).add(subjectHashMap.get(code));
                
            }
            inpFile.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    


}