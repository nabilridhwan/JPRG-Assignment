import javax.swing.*;
import java.util.ArrayList;

public class StudentUser {
    private static final StudentManagement studentManagement = new StudentManagement();

    private static void showErrorMessage(String errorMessage) {
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private static void displayOutOfRangeError(int min, int max) {
        showErrorMessage(String.format("Enter a number between %s and %s", min, max));
    }

    private static void searchStudentPane(){
        String studentName = JOptionPane.showInputDialog("Enter the Student name to search");
        Student foundStudent = studentManagement.searchStudent(studentName);

        //                    A student is found
        if (foundStudent.getGpa() != 0 && !foundStudent.getName().isEmpty()) {
            StringBuilder returnString = new StringBuilder();
            returnString.append(String.format("Course\tAdmin #\tName\n%s\t\t%s\t\t%s\n\nModules taken:\n", foundStudent.getCourse(), foundStudent.getAdminNumber(), foundStudent.getName()));

            for (int j = 0; j < foundStudent.getModules().length; j++) {
                Module module = foundStudent.getModules()[j];
                returnString.append(String.format("%s. %s/%s/%s %s\n", j, module.getModuleCode(), module.getCreditUnit(), module.getModuleName(), module.getMarks()));
            }

            returnString.append(String.format("Your GPA: %f", foundStudent.getGpa()));

            JOptionPane.showMessageDialog(null, returnString);
        } else {
            showErrorMessage(String.format("Cannot find student '%s'", studentName));
        }

    }

    private static void adminPane(){
        String secretPassword = JOptionPane.showInputDialog("Welcome to the Admin Login\nEnter your password: (Enter -1 to exit)");

        final String SECRET_PASSWORD = "hello";
        while (!secretPassword.equals(SECRET_PASSWORD)) {
            if (secretPassword.equals("-1")) {
                break;
            }else{
                //                    Wrong password
                showErrorMessage("Wrong Password");
                secretPassword = JOptionPane.showInputDialog("Welcome to the Admin Login\nEnter your password: (Enter -1 to exit)");
            }
        }

//                User does not want to exit
        if (!secretPassword.equals("-1")) {
            //                A do while loop for admin user choice
            String mainAdminMessage = "Welcome to the admin page\n1. Add student\n2. Delete student by name\n3. Exit";
            String adminUserChoice;

            do {
//                Get the user input
                adminUserChoice = JOptionPane.showInputDialog(mainAdminMessage);

//                While the input itself is not integer
                while(!checkIfInputIsInt(adminUserChoice)){

//                    Throw out an error
                    displayOutOfRangeError(1,3);

//                    Ask for the user input again
                    adminUserChoice = JOptionPane.showInputDialog(mainAdminMessage);
                }

//                Convert this to integer as it exits the loop on top meaning that it is an integer
                int adminUserChoiceInt = Integer.parseInt(adminUserChoice);

//                Check if the user is out of bounds, meaning if the user enters any option they are not supposed to enter
                if(checkOutOfBounds(adminUserChoiceInt, 1, 3, mainAdminMessage)){

//                    Throw out an error
                    displayOutOfRangeError(1 ,6);

//                    Ask for the input again but this time it should be set to the adminUserChoice
                    adminUserChoice = JOptionPane.showInputDialog(mainAdminMessage);
                }


//                TODO: Use setters and getters
                if (adminUserChoiceInt == 1) {
//                        Get student course
                    String studentCourse = JOptionPane.showInputDialog("Student's Course");
//                        Get student admin number
                    String studentAdminNumber = JOptionPane.showInputDialog("Student's Admin Number");

//                        Get student name
                    String studentName = JOptionPane.showInputDialog("Student's Name");

//                        Get student modules
                    int studentModuleNumber = 0;

                    String moduleChoiceMessage = String.format("How many modules does %s have?", studentName);

                    try {
                        studentModuleNumber = Integer.parseInt(JOptionPane.showInputDialog(moduleChoiceMessage));
                    } catch (Exception e) {
                        showErrorMessage("Please enter a number");
                        studentModuleNumber = Integer.parseInt(JOptionPane.showInputDialog(moduleChoiceMessage));
                    }

                    Module[] studentModules = new Module[studentModuleNumber];

                    for(int i = 0; i < studentModules.length; i++){
//                        Get module code
                        String moduleCode = JOptionPane.showInputDialog("Module Code");

//                                Get module name
                        String moduleName = JOptionPane.showInputDialog("Module Name");

//                                Get credit unit
                        int creditUnit = Integer.parseInt(JOptionPane.showInputDialog("Credit Unit"));

//                                Get student marks
                        double studentMarks = Integer.parseInt(JOptionPane.showInputDialog(studentName + " marks for " + moduleName));

                        Module addModule = new Module(moduleCode, moduleName, creditUnit, studentMarks);
                        studentModules[i] = addModule;
                    }

//                        Create a student
                    studentManagement.createStudent(studentCourse, studentAdminNumber, studentName, studentModules);
                }

                if (adminUserChoiceInt == 2) {
//                        Get the student name
                    String studentName = JOptionPane.showInputDialog("Enter the student's name you want to delete");
                    if (studentManagement.removeStudent(studentName)) {
                        JOptionPane.showMessageDialog(null, String.format("Removed %s", studentName));
                    } else {
                        showErrorMessage(String.format("Can't find student '%s' to remove", studentName));
                    }
                }

            } while (!adminUserChoice.equals(Integer.toString(3)));
        }
    }

    private static boolean checkOutOfBounds(int choice, int min, int max, String message){
        //          Checks if the user choice is one of the menu
        return choice < min || choice > max;
    }

    private static boolean checkIfInputIsInt(String userChoice){
        try{
            Integer.parseInt(userChoice);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    public static void main(String[] args) {
        String mainMessage = "Enter your option:\n1. Display all students\n2. Search Student By Name\n3. Search Module by Name\n4. Print Statistic\n5. Admin Login\n6. Exit";
        String userChoice;

        do {
            userChoice = JOptionPane.showInputDialog(mainMessage);

            while(!checkIfInputIsInt(userChoice)){
                userChoice = JOptionPane.showInputDialog(mainMessage);
            }

            int userChoiceInt = Integer.parseInt(userChoice);

            if(checkOutOfBounds(userChoiceInt, 1, 6, mainMessage)){
                displayOutOfRangeError(1 ,6);
                userChoiceInt = Integer.parseInt(JOptionPane.showInputDialog(mainMessage));
            }

            if (userChoiceInt == 1) {
                JOptionPane.showMessageDialog(null, studentManagement.getStudents());
            }

            if (userChoiceInt == 2) {
                searchStudentPane();
            }

            if (userChoiceInt == 3) {
                String moduleName = JOptionPane.showInputDialog("Enter the Module name to search");
                JOptionPane.showMessageDialog(null, studentManagement.getModuleMenu(moduleName));
            }

            if (userChoiceInt == 4) {
                JOptionPane.showMessageDialog(null, studentManagement.getStatistics());
            }

            if (userChoiceInt == 5) {
                adminPane();
            }


        } while (!userChoice.equals(Integer.toString(6)));

        JOptionPane.showMessageDialog(null, "Program terminated, Goodbye!");

    }
}