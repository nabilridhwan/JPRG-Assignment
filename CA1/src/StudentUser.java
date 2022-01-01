import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class StudentUser {
    private static final StudentManagement studentManagement = new StudentManagement();
    private static String SECRET_PASSWORD = "hello";

//    Student search panel
    private static void searchStudentPane() {
        String studentName = JOptionPane.showInputDialog("Enter the Student name to search");

//        If the user did not enter a name, the program will return null
//            While loop if the student name is empty
        while (studentName != null && studentName.isEmpty()) {
            ErrorHandler.showErrorMessage("Please enter a name");
            studentName = JOptionPane.showInputDialog("Enter the Student name to search");
        }

        if (studentName != null) {
            Student foundStudent = studentManagement.searchStudent(studentName.trim());

            // A student is found (because the GPA won't be 0 and the name won't be empty)
            if (foundStudent.getGpa() != 0 && !foundStudent.getName().isEmpty()) {

                StringBuilder returnString = new StringBuilder("<html><head><style>table{text-align: center; border-collapse: collapse;}th{border: 1px solid black;} td{border: 1px solid black;}</style</head><body><table>");
                returnString.append("<tr><th>Course</th>\t<th>Admin #</th>\t<th colspan='3'>Name</th></tr>");
                returnString.append(String.format("<tr><th>%s</th><th>%s</th><th colspan='3'>%s</th></tr>", foundStudent.getCourse(), foundStudent.getAdminNumber(), foundStudent.getName()));

                returnString.append("<tr> <th colspan='5'>Modules Taken</th></tr>");
//            Display student's module
                for (int j = 0; j < foundStudent.getModules().length; j++) {
                    Module module = foundStudent.getModules()[j];


                    returnString.append("<tr><th>Index</th> <th>Module Code</th> <th>Credit Unit</th> <th>Module Name</th> <th>Module Marks</th> </tr>");
                    returnString.append(String.format("<tr> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> <td>%s</td> </th>", j, module.getModuleCode(),
                            module.getCreditUnit(), module.getModuleName(), module.getMarks()));
                }

                returnString.append(String.format("<tr><td colspan='5'>Your GPA: %.2f</td></td>", foundStudent.getGpa()));

                returnString.append("</table></body></html>");

                JOptionPane.showMessageDialog(null, returnString);
            } else {
                ErrorHandler.showErrorMessage(String.format("Cannot find student '%s'", studentName));
            }

        }
    }

//    Search module pane
    private static void searchModulePane(){
        String moduleName = JOptionPane.showInputDialog("Enter the Module name to search");
        while (moduleName != null && moduleName.isEmpty()) {
            ErrorHandler.showErrorMessage("Please enter a valid module name");
            moduleName = JOptionPane.showInputDialog("Enter the Module name to search");
        }

        if (moduleName != null) {
            JOptionPane.showMessageDialog(null, studentManagement.getModuleMenu(moduleName.trim().toUpperCase()));
        }
    }

//    Statistics pane
    private static void statisticsPane(){
        //                Ask the user which statistic they want to see
        String statisticChoice;

        do {

            statisticChoice = JOptionPane.showInputDialog("Enter the statistic you want to see\n1. Students\n2. GPA\n3. Course\n4. Exit");

//                Check if the statistic choice is not cancel or exit

            while (statisticChoice != null && !isInputWithinBounds(statisticChoice, 4)) {
                ErrorHandler.showErrorMessage("Please enter a valid statistic choice");
                statisticChoice = JOptionPane.showInputDialog("Enter the statistic you want to see\n1. Students\n2. GPA\n3. Course\n4. Exit");
            }

//                    Equals 4 because 4 is exit
            if (statisticChoice != null && !statisticChoice.equals("4")) {
                int statisticChoiceInt = Integer.parseInt(statisticChoice);

                switch (statisticChoiceInt) {
                    case 1:
                        JOptionPane.showMessageDialog(null, studentManagement.getStatistics());
                        break;
                    case 2:
                        JOptionPane.showMessageDialog(null, studentManagement.getGPAStatistics());
                        break;
                    case 3:
                        JOptionPane.showMessageDialog(null, studentManagement.getCourseStatistics());
                        break;
                    case 4:
                        break;
                    default:
                        ErrorHandler.showErrorMessage("Please enter a valid statistic choice");
                }
            }
        } while (statisticChoice != null && !statisticChoice.equals("4"));

    }

//    Admin pane
    private static void adminPane() {
        String secretPassword = JOptionPane.showInputDialog("Welcome to the Admin Login\nEnter your password: (Click cancel to exit!)");

        while (secretPassword != null && !secretPassword.equals(SECRET_PASSWORD)) {
            // Wrong password
            ErrorHandler.showErrorMessage("Wrong Password");
            secretPassword = JOptionPane.showInputDialog("Welcome to the Admin Login\nEnter your password: (Click cancel to exit!)");
        }

        // User does not want to exit
        if (secretPassword != null) {
            // do while loop for admin user choice
            String mainAdminMessage = "Welcome to the admin page\n1. Add student\n2. Delete student by name\n3. Modify Student Particulars\n4. Change admin password\n5. Exit";
            String adminUserChoice;

            do {
                // Get the user input
                adminUserChoice = JOptionPane.showInputDialog(mainAdminMessage);

//                If the choice is cancelled, break the loop
                if (adminUserChoice == null) {
                    break;
                }


                // Check if the user is out of bounds, meaning if the user enters any option
                // they are not supposed to enter
                while (!isInputWithinBounds(adminUserChoice, 5)) {

                    // Throw out an error
                    ErrorHandler.displayOutOfRangeError(1, 5);

                    // Ask for the input again but this time it should be set to the adminUserChoice
                    adminUserChoice = JOptionPane.showInputDialog(mainAdminMessage);
                }

                // Convert to integer (because it is a number)
                int adminUserChoiceInt = Integer.parseInt(adminUserChoice);

//                Add student
                if (adminUserChoiceInt == 1) {
                    // Get student course
                    String studentCourse = JOptionPane.showInputDialog("Student's Course");

                    if (studentCourse == null) {
                        break;
                    }

                    while (studentCourse.isEmpty()) {
                        ErrorHandler.showErrorMessage("Do not leave it blank!");
                        studentCourse = JOptionPane.showInputDialog("Student's Course");
                    }

                    // Get student admin number
                    String studentAdminNumberMessage = "Student's Admin Number (e.g. P1234567)";
                    String studentAdminNumber = JOptionPane.showInputDialog(studentAdminNumberMessage);

                    while (studentAdminNumber != null && studentAdminNumber.isEmpty()) {
                        ErrorHandler.showErrorMessage("Do not leave it blank!");
                        // Get student admin number
                        studentAdminNumber = JOptionPane.showInputDialog(studentAdminNumberMessage);
                    }

                    while (studentAdminNumber != null && !studentAdminNumber.matches("^[p|P][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+$")) {
                        ErrorHandler.showErrorMessage("Invalid Admin Number format!");
                        studentAdminNumber = JOptionPane.showInputDialog(studentAdminNumberMessage);
                    }

                    if (studentAdminNumber == null) break;

                    // Get student name
                    String studentName = JOptionPane.showInputDialog("Student's Name");
                    if (studentName == null) {
                        break;
                    }
                    while (studentName.isEmpty()) {
                        ErrorHandler.showErrorMessage("Do not leave it blank!");
                        // Get student name
                        studentName = JOptionPane.showInputDialog("Student's Name");
                    }

                    // Get student modules
                    String moduleChoiceMessage = String.format("How many modules does %s have?", studentName);
                    String studentModuleNumberChoice = JOptionPane.showInputDialog(moduleChoiceMessage);

                    if (studentModuleNumberChoice == null) {
                        break;
                    }

//                        Do a check if it is an integer or is empty, if any of these conditions are met, enter the while loop
                    while (studentModuleNumberChoice != null && (!checkIfInputIsInt(studentModuleNumberChoice) || studentModuleNumberChoice.isEmpty())) {
                        ErrorHandler.showErrorMessage("Do not leave it blank or item is not a number!");
                        studentModuleNumberChoice = JOptionPane.showInputDialog(moduleChoiceMessage);
                    }

                    if (studentModuleNumberChoice == null) break;

                    //                      If it breaks, meaning that it is an integer, and it is not empty, hence we can convert it into an integer
                    int studentModuleNumber = Integer.parseInt(studentModuleNumberChoice);


                    Module[] studentModules = new Module[studentModuleNumber];

                    for (int i = 0; i < studentModules.length; i++) {
                        // Get module code
                        String moduleCode = JOptionPane.showInputDialog(String.format("Module Code for module #%s", (i + 1)));

                        while (moduleCode == null || moduleCode.isEmpty()) {
                            ErrorHandler.showErrorMessage("Invalid input!");
                            moduleCode = JOptionPane.showInputDialog(String.format("Module Code for module #%s", (i + 1)));
                        }

                        // Get module name
                        String moduleName = JOptionPane.showInputDialog(String.format("Module Name for module #%s", (i + 1)));


                        while (moduleName == null || moduleName.isEmpty()) {
                            ErrorHandler.showErrorMessage("Invalid input!");
                            moduleName = JOptionPane.showInputDialog(String.format("Module Name for module #%s", (i + 1)));
                        }

                        // Get credit unit in string first
                        String creditUnit = JOptionPane.showInputDialog(String.format("Credit Unit for %s", moduleName));

//                        Do a check if it is an integer or is empty, if any of these conditions are met, enter the while loop
                        while (creditUnit == null || !checkIfInputIsInt(creditUnit) || creditUnit.isEmpty()) {
                            ErrorHandler.showErrorMessage("Invalid input!");
                            creditUnit = JOptionPane.showInputDialog(String.format("Credit Unit for %s", moduleName));
                        }

//                      If it breaks, meaning that it is an integer and it is not empty, hence we can convert it into an integer
                        int convertedCreditUnit = Integer.parseInt(creditUnit);

                        // Get student marks
                        String studentMarks = JOptionPane.showInputDialog(studentName + " marks for " + moduleName);

                        while (studentMarks == null || !checkIfInputIsDouble(studentMarks) || studentMarks.isEmpty()) {
                            ErrorHandler.showErrorMessage("Invalid input!");
                            studentMarks = JOptionPane.showInputDialog(studentName + " marks for " + moduleName);
                        }

//                        Converted student marks as double
                        double convertedStudentMarks = Double.parseDouble(studentMarks);

                        Module addModule = new Module(moduleCode, moduleName, convertedCreditUnit, convertedStudentMarks);
                        studentModules[i] = addModule;
                    }

                    // Create a student
                    studentManagement.createStudent(studentCourse.trim(), studentAdminNumber.trim(), studentName.trim(), studentModules);

//                    Show a message to the user that they have successfully added the student
                    JOptionPane.showMessageDialog(null, String.format("Student %s has been successfully added", studentName), "Success", JOptionPane.INFORMATION_MESSAGE);
                }

//                Delete student
                if (adminUserChoiceInt == 2) {
                    // Get the student name
                    String studentName = JOptionPane.showInputDialog("Enter the student's name you want to delete");
                    if (studentManagement.removeStudent(studentName)) {
                        JOptionPane.showMessageDialog(null, String.format("Removed %s", studentName));
                    } else {
                        ErrorHandler.showErrorMessage(String.format("Can't find student '%s' to remove", studentName));
                    }
                }

                if (adminUserChoiceInt == 3) {

                    String studentNameToEdit = JOptionPane.showInputDialog("Enter the student's name you want to update");

                    while (studentNameToEdit != null && studentNameToEdit.isEmpty()) {
                        ErrorHandler.showErrorMessage("Do not leave it blank! Enter a student's name to update");
                        studentNameToEdit = JOptionPane.showInputDialog("Enter the student's name you want to update");
                    }

                    int studentIndexToEdit = studentManagement.findIndexOfStudent(studentNameToEdit);

                    while (studentNameToEdit != null && studentIndexToEdit == -1) {
                        ErrorHandler.showErrorMessage("Student not found!");
                        studentNameToEdit = JOptionPane.showInputDialog("Enter the student's name you want to update");
                        studentIndexToEdit = studentManagement.findIndexOfStudent(studentNameToEdit);
                    }


                    if (studentNameToEdit == null) break;

                    String modify = JOptionPane.showInputDialog("What do you want to change?\n1. Course\n2. Admin Number\n3. Name");

//                   Check if modify is an integer and out of bounds
                    while (modify != null && !isInputWithinBounds(modify, 3)) {
                        ErrorHandler.displayOutOfRangeError(1, 3);
                        modify = JOptionPane.showInputDialog("What do you want to change?\n1. Course\n2. Admin Number\n3. Name");
                    }


                    if (modify == null) break;

                    int modifyInt = Integer.parseInt(modify);
//                    1: Name
//                    2: Admin Number
//                    3: Course
                    String valueToModify = JOptionPane.showInputDialog("Enter the new value");

                    if (valueToModify == null) break;

                    if (modifyInt == 2) {
                        while (valueToModify != null && !valueToModify.matches("^[p|P][0-9][0-9][0-9][0-9][0-9][0-9][0-9]+$")) {
                            ErrorHandler.showErrorMessage("Invalid Admin Number format!");
                            valueToModify = JOptionPane.showInputDialog("Enter the new value");
                        }
                    }

//                    Modify it using studentManagement
                    studentManagement.modifyStudent(studentIndexToEdit, modifyInt, valueToModify);
                    JOptionPane.showMessageDialog(null, "Student has been successfully modified");

                }

                if (adminUserChoiceInt == 4) {
//                    Ask for old password
                    String oldPasswordMenu = "Enter your old password";
                    String oldPassword = JOptionPane.showInputDialog(oldPasswordMenu);

                    while (oldPassword != null && !oldPassword.equals(SECRET_PASSWORD)) {
                        ErrorHandler.showErrorMessage("Wrong password!");
                        oldPassword = JOptionPane.showInputDialog(oldPasswordMenu);
                    }

                    if (oldPassword == null) break;

//                    Check if it is correct (it is now correct, proceed to ask for new password)
                    String newPasswordMenu = "Enter your new password";
                    String newPassword = JOptionPane.showInputDialog(newPasswordMenu);

                    while (newPassword != null && (newPassword.length() < 8)) {
                        ErrorHandler.showErrorMessage("Password must be at least 8 characters long!");
                        newPassword = JOptionPane.showInputDialog(newPasswordMenu);
                    }

                    SECRET_PASSWORD = newPassword;

                    JOptionPane.showMessageDialog(null, "Password has been successfully changed");
                    break;
                }

            } while (!adminUserChoice.equals("5"));
        }

    }

//    Checking methods
//    This method checks if choice is within min or max
    private static boolean isInputWithinBounds(String choice, int max) {
        // Checks if the user choice is one of the menu
        String regexStatement = "^[1-" + max + "]$";
        return choice.matches(regexStatement);

    }

//    This method checks if what the user entered is an integer
    private static boolean checkIfInputIsInt(String userChoice) {
        try {
            Integer.parseInt(userChoice);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

//    This method checks if what the user entered is a double
    private static boolean checkIfInputIsDouble(String userChoice) {
        try {
            Double.parseDouble(userChoice);
            return true;
        } catch (Exception e) {
            return false;
        }
    }



    public static void main(String[] args) {
//        Start the program with a new date and time
        Date startTime = new Date();

        System.out.printf("Welcome user, you have entered the system at %s", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        String mainMessage = "Enter your option:\n1. Display all students\n2. Search Student By Name\n3. Search Module by Name\n4. Print Statistic\n5. Admin Login\n6. Exit";
        String userChoice;

        do { // Start of main program loop

//            Main menu
            userChoice = JOptionPane.showInputDialog(mainMessage);

//            Check if user clicks cancel, if so, exit the program
            if (userChoice == null) break;

//            Check for out of bounds, if yes, show the error and ask for the choice again
            while (!isInputWithinBounds(userChoice, 6)) {
                ErrorHandler.displayOutOfRangeError(1, 6);
//                userChoiceInt = Integer.parseInt(JOptionPane.showInputDialog(mainMessage));
                userChoice = JOptionPane.showInputDialog(mainMessage);
            }

            int userChoiceInt = Integer.parseInt(userChoice);

            switch (userChoiceInt) {
//                Get all students by calling a method from studentManagement
                case 1:
                    JOptionPane.showMessageDialog(null, studentManagement.getStudents());
                    break;

//                    Search student pane
                case 2:
                    searchStudentPane();
                    break;

//                    Search module pane
                case 3:
                   searchModulePane();
                    break;

//                    Statistics pane
                case 4:
                    statisticsPane();
                    break;

//                    Admin pane
                case 5:
                    adminPane();
                    break;

            }

        } while (!userChoice.equals("6"));

//        Have an end date
        Date endTime = new Date();

//        Show the user how long they took in the program
        JOptionPane.showMessageDialog(null, String.format("Program terminated. %s Goodbye!", DateUtility.getDurationString(startTime, endTime)));

    }


}