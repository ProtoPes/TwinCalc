import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
// Receive input from user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter expression in format: a + b, a - b, a * b, a / b.");
        String expression = scanner.nextLine().toUpperCase();
        scanner.close();
        System.out.println(calc(expression));
    }

    // Parse inputted data
    public static String calc(String input) {
        Expression inputtedData = new Expression();
        inputtedData.splitData = input.split(" ");
        if (inputtedData.splitData.length > 3) {
            try {
                throw new IOException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        inputtedData.operator = inputtedData.splitData[1];
        inputtedData.firstOperand = inputtedData.splitData[0];
        inputtedData.secondOperand = inputtedData.splitData[2];
        String output;

// Create variables for storing data
        int a;
        int b;
        int c = 0;
        String d;

// Check what kind of numbers do we have
        inputtedData.ifFirstArabic = !inputtedData.firstOperand.matches("[IVX]*");
        inputtedData.ifSecondArabic = !inputtedData.secondOperand.matches("[IVX]*");

// Handle if both operators are arabic
        if (inputtedData.ifFirstArabic && inputtedData.ifSecondArabic) {
            a = Integer.parseInt(inputtedData.firstOperand);
            b = Integer.parseInt(inputtedData.secondOperand);
        }
// Handle exception if numbers are different type
        else if (inputtedData.ifFirstArabic != inputtedData.ifSecondArabic) {
            try {
                throw new IOException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
// Handle if both operators are roman and correct
        else {
            a = inputtedData.toArabic(inputtedData.firstOperand);
            b = inputtedData.toArabic(inputtedData.secondOperand);
        }
// Check if numbers are over limit
        if (a > 10 || b > 10 || inputtedData.operator.matches("[^+\\-*/]")) {
            try {
                throw new IOException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
// Making calculations
        switch (inputtedData.operator) {
            case "+" -> c = a + b;
            case "-" -> c = a - b;
            case "*" -> c = a * b;
            case "/" -> c = a / b;
        }
// Output result according to type of numbers

        if ((inputtedData.ifFirstArabic && inputtedData.ifSecondArabic)) {
            output = Integer.toString(c);
        } else if (c > 0) {
            d = inputtedData.toRoman(c);
            output = d;
        } else {
            try {
                throw new IOException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Equals:");
        return output;
    }

    //Storing data
    static class Expression {
        String operator;
        String[] splitData;
        String firstOperand;
        String secondOperand;
        boolean ifFirstArabic;
        boolean ifSecondArabic;

        // Convert roman into arabic
        int toArabic(String romanNumber) {
            char[] romanInArray = romanNumber.toCharArray();
            int convertedIntoArabic = 0;
            if (romanNumber.matches("(V?|X?)I{0,3}")) {
                for (char value : romanInArray) {
                    convertedIntoArabic += Roman.valueOf(Character.toString(value)).toInt();
                }
            } else if (romanNumber.matches("I?(V?|X?)")) {
                convertedIntoArabic = Roman.valueOf(Character.toString(romanInArray[1])).toInt() - Roman.valueOf(Character.toString(romanInArray[0])).toInt();
            } else {
                try {
                    throw new IOException();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return convertedIntoArabic;
        }

        // Convert arabic into roman
        String toRoman(int arabicNumber) {
            StringBuilder convertedIntoRoman = new StringBuilder();
            List<Roman> romanList = Arrays.stream(Roman.values()).toList();
            int counter = 8;
            while ((arabicNumber > 0) && (counter >= 0)) {
                Roman currentSymbol = romanList.get(counter);
                if (currentSymbol.toInt() <= arabicNumber) {
                    convertedIntoRoman.append(currentSymbol.name());
                    arabicNumber -= currentSymbol.toInt();
                } else {
                    counter--;
                }
            }
            return convertedIntoRoman.toString();
        }
    }

    // Handle roman numbers
    enum Roman {
        I(1), IV(4), V(5), IX(9), X(10), XL(40), L(50), XC(90), C(100);
        final int value;

        Roman(int value) {
            this.value = value;
        }

        int toInt() {
            return value;
        }
    }
}
