public class StudentManagement {

//    Create a student array with a very large number;
    private Student[] students = new Student[100];
    private static int studentSize = 0;

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
        Student[] students = this.getFinalStudentArray();
        for (int i = 0; i < students.length; i++) {
                if(students[i].getName().equalsIgnoreCase(studentName)){
                    foundStudentIndex = i;
                }
        }

        if(foundStudentIndex != -1){
            students[foundStudentIndex] = null;
            studentSize--;
            return true;
        }

        return false;

    }

    public Student searchStudent(String studentName){
        Student foundStudent = new Student();
        Student[] students = this.getFinalStudentArray();

        for (Student student : students) {
                if (student.getName().equalsIgnoreCase(studentName)) {
                    foundStudent = student;
                }
        }

        return foundStudent;
    }

    public String getStudents() {
        StringBuilder returnString = new StringBuilder("<html><head><style>table{text-align: center; border-collapse: collapse;}th{border: 1px solid black;} td{border: 1px solid black;}</style</head><body><table>");

        Student[] students = this.getFinalStudentArray();

        for (int i = 0; i < students.length; i++) {
            returnString.append(String.format("<tr><th colspan='5'>Student #%s</th></tr>", (i+1)));
            returnString.append("<tr><th>Course</th><th>Admin #</th><th colspan='3'>Name</th></tr>");
                returnString.append(String.format("<tr><td>"+students[i].getCourse()+"</td><td>"+students[i].getAdminNumber()+"</td><td colspan='3'>"+students[i].getName()+"</td></tr>"));

            returnString.append("<tr><th colspan='5'>Module Taken</th></tr>");
            returnString.append(String.format("<tr><td>No.</td><td>Module Code</td><td>Module Credit Unit</td><td>Module Name</td><td>Module Marks</td></tr>"));

            for (int j = 0; j < students[i].getModules().length; j++) {
                    Module module = students[i].getModules()[j];

                returnString.append(String.format("<tr><td>" + (j+1) + "</td><td>" + module.getModuleCode() + "</td><td>" + module.getCreditUnit() + "</td><td>" + module.getModuleName() + "</td><td>" + module.getMarks() +"</td></tr>"));
//                returnString.append(String.format("%s. %s/%s/%s %s\n", j, module.getModuleCode(), module.getCreditUnit(), module.getModuleName(), module.getMarks()));
                }
                returnString.append("<br /> <br />");
        }

        returnString.append("</table></body></html>");


        return returnString.toString();
    }

    public String getModuleMenu(String moduleName){
        StringBuilder returnString = new StringBuilder();
        int totalNumberTakingModule = 0;
        int totalMarks = 0;
        Student[] students = this.getFinalStudentArray();

//        Iterate through each student
        for (Student student: students) {
            if(studentIsNotNull(student)){
                for(Module studentModule: student.getModules()){
                    String mName = studentModule.getModuleName();
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
        Student[] students = this.getFinalStudentArray();


        for(Student student : students){
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

    private Student[] getFinalStudentArray(){
        Student[] finalStudentArray = new Student[studentSize];

        int index = 0;

        for(int i = 0; i < studentSize; i++){
            Student student = this.students[i];
            if(studentIsNotNull(student)){
                finalStudentArray[index] = student;
                index++;
            }
        }

        return finalStudentArray;
    }
}
