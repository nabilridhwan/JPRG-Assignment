public class StudentManagement {

//    Create a student array with a very large number;
    private Student[] students = new Student[100];
    private static int studentSize = 0;
    private Student[] finalStudentArray;

    public StudentManagement(){
        Module[] moduleNabil = new Module[3];
        moduleNabil[0] = new Module("ST1013", "FOP", 5, 80);
        moduleNabil[1] = new Module("ST1014", "MATH", 6, 80);
        moduleNabil[2] = new Module("ST1015", "FOC", 5, 70);
        this.createStudent("DIT", "2007421", "Nabil", moduleNabil);

        Module[] moduleLincoln = new Module[3];
        moduleLincoln[0] = new Module("ST1013", "FOP", 2, 100);
        moduleLincoln[1] = new Module("ST1014", "MATH", 5, 10);
        moduleLincoln[2] = new Module("ST1015", "JPRG", 5, 10);
        this.createStudent("DIT", "2156849", "Lincoln",  moduleLincoln);

        Module[] moduleXuanRong = new Module[3];
        moduleXuanRong[0] = new Module("ST1013", "FOP", 5, 75);
        moduleXuanRong[1] = new Module("ST1014", "MATH", 5, 80);
        moduleXuanRong[2] = new Module("ST1015", "JPRG", 5, 70);
        this.createStudent("DIT", "2156849", "Xuan Rong", moduleXuanRong);
    }

    public void createStudent(String course, String adminNumber, String name,  Module[] modules){
        students[studentSize] = new Student(course, adminNumber, name, modules);
        studentSize++;
    }

    public boolean studentIsNotNull(Student student){
        return student != null;
    }

//    TODO: Replace every student array with getFinalStudentArray!!!
    public boolean removeStudent(String studentName){
        int foundStudentIndex = -1;
        for (int i = 0; i < finalStudentArray.length; i++) {

            if(studentIsNotNull(students[i])){
                if(students[i].getName().equalsIgnoreCase(studentName)){
                    foundStudentIndex = i;
                }
            }
        }

        if(foundStudentIndex != -1){
            students[foundStudentIndex] = null;
            return true;
        }

        return false;

    }

    public Student searchStudent(String studentName){
        Student foundStudent = new Student();
        for (Student student : students) {
            if(studentIsNotNull(student)){
                if (student.getName().equalsIgnoreCase(studentName)) {
                    foundStudent = student;
                }
            }
        }

        return foundStudent;
    }

    public String getStudents() {
        StringBuilder returnString = new StringBuilder();


        for (int i = 0; i < students.length; i++) {
            if(studentIsNotNull(students[i])){
                returnString.append(String.format("Student #%s\nCourse\tAdmin #\tName\n%s\t\t%s\t\t%s\nModules taken:\n", (i+1), students[i].getCourse(), students[i].getAdminNumber(), students[i].getName()));

                for (int j = 0; j < students[i].getModules().length; j++) {
                    Module module = students[i].getModules()[j];
                    returnString.append(String.format("%s. %s/%s/%s %s\n", j, module.getModuleCode(), module.getCreditUnit(), module.getModuleName(), module.getMarks()));
                }

                returnString.append("\n");
            }
        }

        return returnString.toString();
    }

    public String getModuleMenu(String moduleName){
        StringBuilder returnString = new StringBuilder();
        int totalNumberTakingModule = 0;
        int totalMarks = 0;
//        Iterate through each student
        for (Student student: students) {
            if(studentIsNotNull(student)){
                for(Module studentModule: student.getModules()){
                    String mName = studentModule.getModuleName();
                    System.out.println(mName);
                    if(mName.equals(moduleName)){
                        totalNumberTakingModule ++;
                        totalMarks += studentModule.getMarks();
                    }
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
            if(studentIsNotNull(student)){
                if(!student.getCourse().isEmpty()){
                    totalNumberOfStudents ++;

                    if(student.getGpa() > 3.5){
                        totalNumberOfStudentAbove3PointFive++;
                    }else if(student.getGpa() < 1){
                        totalNumberOfStudentLessThanOne++;
                    }
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

    private void getFinalStudentArray(){
        finalStudentArray = new Student[studentSize];

        int index = 0;

        for(Student student : students){
            if(studentIsNotNull(student)){
                finalStudentArray[index] = student;
                index++;
            }
        }
    }
}
