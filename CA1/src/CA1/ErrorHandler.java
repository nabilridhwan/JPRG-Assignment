package CA1;

import javax.swing.*;

public class ErrorHandler {

    public static void displayOutOfRangeError(int min, int max){
        showErrorMessage(String.format("Enter a number between %s and %s", min, max));
    }

    public static void showErrorMessage(String errorMessage){
        JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
