package CA1;

import java.util.ArrayList;

public class StudentManagement {

    //    Create a student array with a very large number;
    private Student[] students = new Student[100];
    private static int studentSize = 0;
    private static int insertStudentIndex;

    public StudentManagement() {
        Module[] moduleJohn = new Module[2];
        moduleJohn[0] = new Module("SP109B", "ECG", 2, 88);
        moduleJohn[1] = new Module("ST0502", "FOP", 6, 80.5);
        this.createStudent("DISM", "P111111", "John Tan", moduleJohn);

        Module[] modulePeter = new Module[4];
        modulePeter[0] = new Module("ST0501", "FED", 5, 90.5);
        modulePeter[1] = new Module("ST0502", "FOP", 6, 78);
        modulePeter[2] = new Module("ST2413", "FOC", 4, 65.5);
        modulePeter[3] = new Module("SP108B", "ECG", 4, 81.0);
        this.createStudent("DAAA", "P222222", "Peter Goh", modulePeter);

        Module[] moduleJack = new Module[3];
        moduleJack[0] = new Module("ST0503", "BED", 6, 55.5);
        moduleJack[1] = new Module("ST0504", "MAD", 5, 86);
        moduleJack[2] = new Module("ST0502", "FOP", 6, 66);
        this.createStudent("DIT", "P333333", "Jack Lim", moduleJack);
    }


    public void createStudent(String course, String adminNumber, String name, Module[] modules) {
        insertStudentIndex = findIndexOfLastStudent();
        students[insertStudentIndex] = new Student(course, adminNumber, name, modules);
        studentSize++;
    }

    public boolean studentIsNotNull(Student student) {
        return student != null;
    }

    public boolean removeStudent(String studentName) {
        int foundStudentIndex = findIndexOfStudent(studentName);

        if (foundStudentIndex != -1) {
            students[foundStudentIndex] = null;
            studentSize--;
            return true;
        }
        return false;
    }

    public Student searchStudent(String studentName) {
        Student foundStudent = new Student();
        Student[] students = this.getFinalStudentArray();

        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(studentName)) {
                foundStudent = student;
            }
        }

        if(foundStudent.getName().equalsIgnoreCase("")) {
            return null;
        }

        return foundStudent;
    }

    public void modifyStudent(Student student, int type, String newValue) {
        switch (type) {
            case 1:
                student.setCourse(newValue);
                break;
            case 2:
                student.setAdminNumber(newValue.toUpperCase());
                break;
            case 3:
                student.setName(newValue);
                break;
        }
    }

    //    This function tells the program where to insert the student from
    private int findIndexOfLastStudent() {
        int highestIndexNotNull = 0;
//        Go through each student
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
//        If each student is not null, get their index
            if (studentIsNotNull(student)) {
                highestIndexNotNull = i;
            }
        }

//        Return the index + 1, that will be the index to insert from
        return highestIndexNotNull + 1;
    }

    public int findIndexOfStudent(String studentName) {
        int foundStudentIndex = -1;
        for (int i = 0; i < students.length; i++) {
            if (studentIsNotNull(students[i])) {
                if (students[i].getName().equalsIgnoreCase(studentName)) {
                    foundStudentIndex = i;
                }
            }
        }

        return foundStudentIndex;
    }

    public String getModulesString(Student student) {
        StringBuilder returnString = new StringBuilder();
        for (int j = 0; j < student.getModules().length; j++) {
            Module module = student.getModules()[j];

            returnString.append(String.format("<tr><td>" + (j + 1) + "</td><td>" + module.getModuleCode() + "</td><td>" + module.getCreditUnit() + "</td><td>" + module.getModuleName() + "</td><td>" + module.getMarks() + "</td></tr>"));
//                returnString.append(String.format("%s. %s/%s/%s %s\n", j, module.getModuleCode(), module.getCreditUnit(), module.getModuleName(), module.getMarks()));
        }

        return returnString.toString();
    }

    public String getStudentString(Student student){
        StringBuilder returnString = new StringBuilder();
        returnString.append(String.format("<tr><td>" + student.getCourse() + "</td><td>" + student.getAdminNumber() + "</td><td colspan='3'>" + student.getName() + "</td></tr>"));

        return returnString.toString();
    }

    public String getStudents() {
        StringBuilder returnString = new StringBuilder("<html><head><style>table{text-align: center; border-collapse: collapse;}th{border: 1px solid black;} td{border: 1px solid black;}</style</head><body><table>");

        Student[] students = this.getFinalStudentArray();

        for (int i = 0; i < students.length; i++) {
            returnString.append(String.format("<tr><th colspan='5'>Student #%s</th></tr>", (i + 1)));

            returnString.append("<tr><th>Course</th><th>Admin #</th><th colspan='3'>Name</th></tr>");
            returnString.append(getStudentString(students[i]));

            returnString.append("<tr><th colspan='5'>Module(s) Taken</th></tr>");
            returnString.append("<tr><td>No.</td><td>Module Code</td><td>Module Credit Unit</td><td>Module Name</td><td>Module Marks</td></tr>");

            returnString.append(getModulesString(students[i]));
            returnString.append("<tr><td colspan='5' style='border: 0'></td></tr>");
        }

        returnString.append("</table></body></html>");


        return returnString.toString();
    }

    public String getModuleMenu(String moduleName) {
        StringBuilder returnString = new StringBuilder();
        int totalNumberTakingModule = 0;
        int totalMarks = 0;
        Student[] students = this.getFinalStudentArray();

//        Iterate through each student
        for (Student student : students) {
            for (Module studentModule : student.getModules()) {
                String mName = studentModule.getModuleName();
                if (mName.equals(moduleName)) {
                    totalNumberTakingModule++;
                    totalMarks += studentModule.getMarks();
                }
            }
        }

        if (totalNumberTakingModule != 0) {
            returnString.append(String.format("There are %s students taking %s\n", totalNumberTakingModule, moduleName));
            returnString.append(String.format("The average marks for %s is %.1f", moduleName, ((float) totalMarks / (float) totalNumberTakingModule)));
            return returnString.toString();
        } else {
            return "No student taking " + moduleName;
        }
    }

    public String getStatistics() {
        StringBuilder returnString = new StringBuilder();
        int totalNumberOfStudents = 0;

        int totalNumberOfStudentAbove3PointFive = 0;
        int totalNumberOfStudentLessThanOne = 0;
        Student[] students = this.getFinalStudentArray();


        for (Student student : this.getFinalStudentArray()) {
//            Check if the student is really a student or not
            if (!student.getCourse().isEmpty()) {
                totalNumberOfStudents++;

                if (student.getGpa() > 3.5) {
                    totalNumberOfStudentAbove3PointFive++;
                } else if (student.getGpa() < 1) {
                    totalNumberOfStudentLessThanOne++;
                }
            }
        }

        double percentageOfStudentsAbove3point5 = ((float) totalNumberOfStudentAbove3PointFive / totalNumberOfStudents) * 100;
        double percentageOfStudentsBelow1 = ((float) totalNumberOfStudentLessThanOne / totalNumberOfStudents) * 100;

        returnString.append("STATISTIC\n");
        returnString.append("-----------------\n");
        returnString.append(String.format("There are %s students in total\n", totalNumberOfStudents));
        returnString.append("\n");
        returnString.append(String.format("There is/are %s student(s) getting GPA greater than 3.5. This is %.2f %%\n", totalNumberOfStudentAbove3PointFive, percentageOfStudentsAbove3point5));
        returnString.append("\n");
        returnString.append(String.format("There is/are %s student(s) getting GPA less than 1. This is %.2f %%\n", totalNumberOfStudentLessThanOne, percentageOfStudentsBelow1));

        return returnString.toString();
    }

    public String getGPAStatistics() {
        StringBuilder returnString = new StringBuilder();
        double lowestGPA = 4.0;
        double highestGPA = 0.0;

        Student lowestGPAStudent = null;
        Student highestGPAStudent = null;

        Student[] students = this.getFinalStudentArray();
        for (int i = 0; i < students.length; i++) {
            Student student = students[i];
            if (student.getGpa() < lowestGPA) {
                lowestGPA = student.getGpa();
                lowestGPAStudent = student;
            }

            if (student.getGpa() > highestGPA) {
                highestGPA = student.getGpa();
                highestGPAStudent = student;
            }
        }

        returnString.append("GPA STATISTICS\n");
        returnString.append("-----------------\n");
        returnString.append(String.format("The lowest GPA is %.4f by %s from %s\n", lowestGPA, lowestGPAStudent.getName(), lowestGPAStudent.getCourse()));
        returnString.append(String.format("The highest GPA is %.4f by %s from %s\n", highestGPA, highestGPAStudent.getName(), highestGPAStudent.getCourse()));

        return returnString.toString();

    }

    public void removeModule(Student student,int indexToDelete){
        Module[] studentModules = student.getModules();
        int moduleLength = studentModules.length - 1;
        Module[] finalStudentModules = new Module[moduleLength];

        int index = 0;

        for(int i = 0; i < studentModules.length; i++){
            if(indexToDelete != i){
                finalStudentModules[index] = studentModules[i];
                index++;
            }
        }

        student.setModules(finalStudentModules);
    }

    //    Return the number of students in a certain course
    public String getCourseStatistics() {
        StringBuilder returnString = new StringBuilder();
        ArrayList<String> courseList = new ArrayList<>();

        for (Student student : this.getFinalStudentArray()) {
            if (!student.getCourse().isEmpty()) {
//                Check if the course is already in the list
                if (!courseList.contains(student.getCourse())) {
                    courseList.add(student.getCourse());
                }
            }
        }


        int[] numberOfStudentTakingCourse = new int[courseList.size()];
        for (Student student : this.getFinalStudentArray()) {
            int index = courseList.indexOf(student.getCourse());
            numberOfStudentTakingCourse[index]++;
        }

        returnString.append("COURSE STATISTICS\n");
        returnString.append("-----------------\n");
        returnString.append("Course : Number of Students\n");
        returnString.append("-----------------\n");

        for (int i = 0; i < courseList.size(); i++) {
            returnString.append(String.format("%s : %s\n", courseList.get(i), numberOfStudentTakingCourse[i]));
        }

        return returnString.toString();

    }

    private Student[] getFinalStudentArray() {
        Student[] finalStudentArray = new Student[studentSize];

        int index = 0;

        for (int i = 0; i < students.length; i++) {
            Student student = this.students[i];
            if (studentIsNotNull(student)) {
                finalStudentArray[index] = student;
                index++;
            }
        }

        return finalStudentArray;
    }
}
