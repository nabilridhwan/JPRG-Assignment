import java.util.ArrayList;
import java.util.Locale;

public class StudentManagement {

    private ArrayList<Student> students = new ArrayList<Student>();

    public StudentManagement(){
        Module[] moduleNabil = new Module[2];
        moduleNabil[0] = new Module("ST1013", "FOP", 5, 50);
        moduleNabil[1] = new Module("ST1014", "MATH", 5, 60);
        this.createStudent("DIT", "2007421", "Nabil", 3.912, moduleNabil);

        Module[] moduleLincoln = new Module[3];
        moduleLincoln[0] = new Module("ST1013", "FOP", 5, 70);
        moduleLincoln[1] = new Module("ST1014", "MATH", 5, 60);
        moduleLincoln[2] = new Module("ST1015", "JPRG", 5, 45);
        this.createStudent("DIT", "2156849", "Lincoln", 0.6, moduleLincoln);

        Module[] moduleXuanRong = new Module[3];
        moduleXuanRong[0] = new Module("ST1013", "FOP", 5, 75);
        moduleXuanRong[1] = new Module("ST1014", "MATH", 5, 80);
        moduleXuanRong[2] = new Module("ST1015", "JPRG", 5, 70);
        this.createStudent("DIT", "2156849", "Xuan Rong", 4, moduleXuanRong);
    }

    public void createStudent(String course, String adminNumber, String name, double gpa, Module[] modules){
        this.students.add(new Student(course, adminNumber, name, gpa, modules));
    }

    public boolean removeStudent(String studentName){
        int foundStudentIndex = -999;
        for (int i = 0; i < students.size(); i++) {
            if(students.get(i).getName().toLowerCase().equals(studentName.toLowerCase())){
                foundStudentIndex = i;
            }
        }

        if(foundStudentIndex != -999){
            this.students.remove(foundStudentIndex);
            return true;
        }

        return false;

    }

    public Student searchStudent(String studentName){
        Student foundStudent = new Student();
        for (int i = 0; i < students.size(); i++) {
            if(students.get(i).getName().toLowerCase().equals(studentName.toLowerCase())){
                foundStudent = students.get(i);
            }
        }

        return foundStudent;
    }

    public String getStudents() {
        StringBuilder returnString = new StringBuilder();

        for (int i = 0; i < students.size(); i++) {
            returnString.append(String.format("Student #%s\nCourse\tAdmin #\tName\n%s\t\t%s\t\t%s\nModules taken:\n", (i+1), students.get(i).getCourse(), students.get(i).getAdminNumber(), students.get(i).getName()));

            for (int j = 0; j < students.get(i).getModules().length; j++) {
                Module module = students.get(i).getModules()[j];
                returnString.append(String.format("%s. %s/%s/%s %s\n", j, module.getModuleCode(), module.getCreditUnit(), module.getModuleName(), module.getMarks()));
            }

            returnString.append("\n");
        }

        return returnString.toString();
    }

    public String getModuleMenu(String moduleName){
        StringBuilder returnString = new StringBuilder();
        int totalNumberTakingModule = 0;
        int totalMarks = 0;
//        Iterate through each student
        for (Student student: this.students) {
            for(Module studentModule: student.getModules()){
                String mName = studentModule.getModuleName();
                System.out.println(mName);
                if(mName.equals(moduleName)){
                    totalNumberTakingModule ++;
                    totalMarks += studentModule.getMarks();
                }
            }
        }

        if(totalNumberTakingModule != 0){
            returnString.append(String.format("There are %s students taking %s\n", totalNumberTakingModule, moduleName));
            returnString.append(String.format("The average marks for %s is %.1f", moduleName, ((float)totalMarks / (float)totalNumberTakingModule)));
            return returnString.toString();
        }else{
            return "No student taking " + moduleName;
        }
    }

    public String getStatistics(){
        StringBuilder returnString = new StringBuilder();
        int totalNumberOfStudents = 0;
        int totalNumberOfStudentAbove3PointFive = 0;
        int totalNumberOfStudentLessThanOne = 0;

        for(Student student : this.students){
//            Check if the student is really a student or not
            if(!student.getCourse().isEmpty()){
                totalNumberOfStudents ++;
            }

            if(student.getGpa() > 3.5){
                totalNumberOfStudentAbove3PointFive++;
            }else if(student.getGpa() < 1){
                totalNumberOfStudentLessThanOne++;
            }
        }

        returnString.append("STATISTIC\n");
        returnString.append("-----------------\n");
        returnString.append(String.format("There are %s students in total\n", totalNumberOfStudents));
        returnString.append("\n");
        returnString.append(String.format("There is/are %s student(s) getting GPA greater than 3.5. This is %.2f %%\n", totalNumberOfStudentAbove3PointFive, (((float) totalNumberOfStudentAbove3PointFive/ (float) totalNumberOfStudents) * 100)));
        returnString.append("\n");
        returnString.append(String.format("There is/are %s student(s) getting GPA less than 1. This is %.2f %%\n", totalNumberOfStudentLessThanOne, (((float)totalNumberOfStudentLessThanOne/(float)totalNumberOfStudents) * 100)));

        return returnString.toString();
    }
}
