/*
    Class:          DIT/FT/1B/05
    Admin Number:   P2007421
    Name:           Nabil Ridhwanshah Bin Rosli
 */

import java.util.ArrayList;

public class StudentManagement {

    //    Create a student array with a very large number;
    private Student[] students = new Student[100];
    private static int studentSize = 0;

    //    The constructor must add students to the array
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

    //  Method to create student
    public void createStudent(String course, String adminNumber, String name, Module[] modules) {

//        insertStudentIndex is equal to a method that finds the last index of a student that is not null (out of 1000
        int insertStudentIndex = findIndexOfLastStudent();
        students[insertStudentIndex] = new Student(course, adminNumber, name, modules);

//        Increase the size of the student array (this is to facilitate the search function and other functions that require to get the latest array of students)
        studentSize++;
    }

    //    A method that returns if a student is null or not
    public boolean studentIsNotNull(Student student) {
        return student != null;
    }

    //    Remove student - returns boolean if removed successfully
    public boolean removeStudent(String studentName) {

//        Find the index of student
//        The method returns -1 if the index is not found
        int foundStudentIndex = findIndexOfStudent(studentName);

//        So we do a check if it is found (which is not -1)
        if (foundStudentIndex != -1) {

//            Set the student at the found index to null
            students[foundStudentIndex] = null;
//            Minus the student size
            studentSize--;

//            Return true
            return true;
        }
//        Otheriwse, return false
        return false;
    }

    //    This method search for a student by name and returns the student object
    public Student searchStudent(String studentName) {
//        Create an empty student (no name, gpa and etc)
        Student foundStudent = new Student();

//        This array is to store the students that are found
        Student[] students = this.getFinalStudentArray();

//        Loop through
        for (Student student : students) {
//            If found, set the found student to the student that is found
            if (student.getName().equalsIgnoreCase(studentName)) {
                foundStudent = student;
            }
        }

//        Because empty constructor has no name, so we check if the found student is not null
        if (foundStudent.getName().equalsIgnoreCase("")) {
            return null;
        }

//      reutrn the found student
        return foundStudent;
    }

    //    Modify student
    public void modifyStudent(Student student, int type, String newValue) {

//        The type switch is to know what they want to edit
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

    //    Find the index of student by their name
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

    //    Get the modules of a student formatted in html
    public String getModulesString(Student student) {
        StringBuilder returnString = new StringBuilder();
        for (int j = 0; j < student.getModules().length; j++) {
            Module module = student.getModules()[j];

            returnString.append(String.format("<tr><td>" + (j + 1) + "</td><td>" + module.getModuleCode() + "</td><td>" + module.getCreditUnit() + "</td><td>" + module.getModuleName() + "</td><td>" + module.getMarks() + "</td></tr>"));
//                returnString.append(String.format("%s. %s/%s/%s %s\n", j, module.getModuleCode(), module.getCreditUnit(), module.getModuleName(), module.getMarks()));
        }

        return returnString.toString();
    }

    //    Get the student details formatted in HTML
    public String getStudentString(Student student) {
        StringBuilder returnString = new StringBuilder();
        returnString.append(String.format("<tr><td>" + student.getCourse() + "</td><td>" + student.getAdminNumber() + "</td><td colspan='3'>" + student.getName() + "</td></tr>"));

        return returnString.toString();
    }

    //    This brings both the student and the modules together
    public String getStudents() {
        StringBuilder returnString = new StringBuilder("<html><head><style>table{text-align: center; border-collapse: collapse;}th{border: 1px solid black;} td{border: 1px solid black;}</style</head><body><table>");

        Student[] students = this.getFinalStudentArray();

        for (int i = 0; i < students.length; i++) {
            returnString.append(String.format("<tr><th colspan='5'>Student #%s</th></tr>", (i + 1)));

            returnString.append("<tr><th>Course</th><th>Admin #</th><th colspan='3'>Name</th></tr>");
            returnString.append(getStudentString(students[i]));


//            Show the modules taken
            returnString.append("<tr><th colspan='5'>Module(s) Taken</th></tr>");

//            If the student has modules, show it otherwise show no modules
            if (students[i].getModules().length > 0) {
                returnString.append("<tr><td>No.</td><td>Module Code</td><td>Module Credit Unit</td><td>Module Name</td><td>Module Marks</td></tr>");
                returnString.append(getModulesString(students[i]));
            }else{
                returnString.append("<tr><td colspan='5'>No modules</td></tr>");
            }

            returnString.append("<tr><td colspan='5' style='border: 0'></td></tr>");
        }

        returnString.append("</table></body></html>");


        return returnString.toString();
    }

    //    Gets the module menu (which shows how many students are in the module taken)
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

//        If there is nobody taking the module, return a message
//        Else show the statistics

        if (totalNumberTakingModule != 0) {
            returnString.append(String.format("There are %s students taking %s\n", totalNumberTakingModule, moduleName));
            returnString.append(String.format("The average marks for %s is %.1f", moduleName, ((float) totalMarks / (float) totalNumberTakingModule)));
            return returnString.toString();
        } else {
            return "No student taking " + moduleName;
        }
    }

    //    Get the main statistics
    public String getStatistics() {
        StringBuilder returnString = new StringBuilder();
        int totalNumberOfStudents = 0;

        int totalNumberOfStudentAbove3PointFive = 0;
        int totalNumberOfStudentLessThanOne = 0;
        Student[] students = this.getFinalStudentArray();


//        Loop through each student
        for (Student student : this.getFinalStudentArray()) {
            totalNumberOfStudents++;

            if (student.getGpa() > 3.5) {
                totalNumberOfStudentAbove3PointFive++;
            } else if (student.getGpa() < 1) {
                totalNumberOfStudentLessThanOne++;
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

    //    get GPA statistics which shows highest and lowest gpa of the students
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

    //    Method to remove a module from student
    public void removeModule(Student student, int indexToDelete) {
        Module[] studentModules = student.getModules();

//        The module length is -1 because they removed a module
        int moduleLength = studentModules.length - 1;

//        Create a new array of modules
        Module[] finalStudentModules = new Module[moduleLength];

//        The index is kept track to set to finalStudentModules
        int index = 0;

        if (moduleLength != 0) {
            for (int i = 0; i < studentModules.length; i++) {

//            As long as the index is not the index to delete, set the module to finalStudentModules
                if (indexToDelete != i) {
                    finalStudentModules[index] = studentModules[i];

//                Increase the index
                    index++;
                }
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

    //    This method will return a final array containing students that are not null
//    It leverages from the studentSize that we kept track from adding/removing students
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

    //    This method allows users to add modules for users
    public void addModules(Student student, Module[] modules) {
        Module[] currentStudentModules = student.getModules();
        Module[] newStudentModules = new Module[currentStudentModules.length + modules.length];

        int index = 0;

        for (int i = 0; i < currentStudentModules.length; i++) {
            newStudentModules[index] = currentStudentModules[i];
            index++;
        }

        for (int i = 0; i < modules.length; i++) {
            newStudentModules[index] = modules[i];
            index++;
        }

        student.setModules(newStudentModules);
    }
}
