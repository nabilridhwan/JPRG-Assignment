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

    public static void main(String[] args) {
        String mainMessage = "Enter your option:\n1. Display all students\n2. Search Student By Name\n3. Search Module by Name\n4. Print Statistic\n5. Admin Login\n6. Exit";
        int userChoice = -5;

        do {
//            Try to convert it to integer, otherwise throw an error
            try {
                userChoice = Integer.parseInt(JOptionPane.showInputDialog(mainMessage));
            } catch (Exception e) {
                displayOutOfRangeError(1, 6);
                userChoice = Integer.parseInt(JOptionPane.showInputDialog(mainMessage));
            }

//          Checks if the user choice is one of the menu
            while (userChoice < 1 || userChoice > 6) {
                displayOutOfRangeError(1, 6);
                userChoice = Integer.parseInt(JOptionPane.showInputDialog(mainMessage));
            }

            if (userChoice == 1) {
                JOptionPane.showMessageDialog(null, studentManagement.getStudents());
            }

            if (userChoice == 2) {
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

            if (userChoice == 3) {
                String moduleName = JOptionPane.showInputDialog("Enter the Module name to search");
                JOptionPane.showMessageDialog(null, studentManagement.getModuleMenu(moduleName));
            }

            if (userChoice == 4) {
                JOptionPane.showMessageDialog(null, studentManagement.getStatistics());
            }

            if (userChoice == 5) {
                String secretPassword = JOptionPane.showInputDialog("Welcome to the Admin Login\nEnter your password: (Enter -1 to exit)");

                final String SECRET_PASSWORD = "hello";
                while (!secretPassword.equals(SECRET_PASSWORD)) {
//                    Wrong password
                    showErrorMessage("Wrong Password");
                    secretPassword = JOptionPane.showInputDialog("Welcome to the Admin Login\nEnter your password: (Enter -1 to exit)");
                    if (secretPassword.equals("-1")) {
                        break;
                    }
                }

//                User does not want to exit
                if (!secretPassword.equals("-1")) {
                        //                A do while loop for admin user choice
                        int adminUserChoice = -5;
                        String mainAdminMessage = "Welcome to the admin page\n1. Add student\n2. Delete student by name\n3. Exit";

                        do {
                            try {
                                adminUserChoice = Integer.parseInt(JOptionPane.showInputDialog(mainAdminMessage));
                            } catch (Exception e) {
                                JOptionPane.showMessageDialog(null, "Enter an number between 1 and 6", "Error", JOptionPane.ERROR_MESSAGE);
                                adminUserChoice = Integer.parseInt(JOptionPane.showInputDialog(mainAdminMessage));
                            }

                            if (adminUserChoice == 1) {
                                ArrayList<Module> modules = new ArrayList<Module>();

//                        Get student course
                                String studentCourse = JOptionPane.showInputDialog("Student's Course");
//                        Get student adminnumber
                                String studentAdminNumber = JOptionPane.showInputDialog("Student's Admin Number");

//                        Get student name
                                String studentName = JOptionPane.showInputDialog("Student's Name");

//                        Get student gpa
                                Double studentGPA = Double.parseDouble(JOptionPane.showInputDialog("Student's GPA"));

//                        Get student modules
                                int moduleChoice = -5;
                                do {

                                    String moduleChoiceMessage = String.format("%s currently has %s module(s).\n1. Add Module\n2. Done", studentName, modules.size());

                                    try {
                                        moduleChoice = Integer.parseInt(JOptionPane.showInputDialog(moduleChoiceMessage));
                                    } catch (Exception e) {
                                        displayOutOfRangeError(1, 2);
                                        moduleChoice = Integer.parseInt(JOptionPane.showInputDialog(moduleChoiceMessage));
                                    }

                                    if (moduleChoice == 1) {
//                                Get module code
                                        String moduleCode = JOptionPane.showInputDialog("Module Code");

//                                Get module name
                                        String moduleName = JOptionPane.showInputDialog("Module Name");

//                                Get credit unit
                                        int creditUnit = Integer.parseInt(JOptionPane.showInputDialog("Credit Unit"));

//                                Get student marks
                                        double studentMarks = Integer.parseInt(JOptionPane.showInputDialog(studentName + " marks for " + moduleName));

                                        Module addModule = new Module(moduleCode, moduleName, creditUnit, studentMarks);
                                        modules.add(addModule);
                                    }
                                } while (moduleChoice != 2);

//                        Create a student
                                studentManagement.createStudent(studentCourse, studentAdminNumber, studentName, studentGPA, modules.toArray(new Module[modules.size()]));
                            }

                            if (adminUserChoice == 2) {
//                        Get the student name
                                String studentName = JOptionPane.showInputDialog("Enter the student's name you want to delete");
                                if (studentManagement.removeStudent(studentName)) {
                                    JOptionPane.showMessageDialog(null, String.format("Removed %s", studentName));
                                } else {
                                    showErrorMessage(String.format("Can't find student '%s' to remove", studentName));
                                }
                            }

                        } while (adminUserChoice != 3);
                }


            }


        } while (userChoice != 6);

        JOptionPane.showMessageDialog(null, "Program terminated, Goodbye!");

    }
}