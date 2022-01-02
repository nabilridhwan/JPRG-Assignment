/*
    Class:          DIT/FT/1B/05
    Admin Number:   P2007421
    Name:           Nabil Ridhwanshah Bin Rosli
 */


import javax.swing.*;

//  Custom ErrorHandler class to show error message
public class ErrorHandler {

    public static void displayOutOfRangeError(int min, int max){
        showErrorMessage(String.format("Enter a number between %s and %s", min, max));
    }

    public static void showErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
