package Combined;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Stack;

@Controller
public class Code3 { // Changed class name from MainController to Code3

    // Method to reverse the infix expression for prefix conversion
    private String reverseInfix(String infix) {
        StringBuilder reversedInfix = new StringBuilder();
        for (int i = infix.length() - 1; i >= 0; i--) {
            char c = infix.charAt(i);
            if (c == '(') {
                reversedInfix.append(')');
            } else if (c == ')') {
                reversedInfix.append('(');
            } else {
                reversedInfix.append(c);
            }
        }
        return reversedInfix.toString();
    }

    // Method to convert infix to prefix
    public String convertInfixToPrefix(String infix) {
        String reversedInfix = reverseInfix(infix);
        String reversedPostfix = convertInfixToPostfix(reversedInfix);
        String prefix = new StringBuilder(reversedPostfix).reverse().toString(); // Reverse postfix to get prefix
        
        System.out.println("Reversed Infix: " + reversedInfix); // Debugging output
        System.out.println("Reversed Postfix: " + reversedPostfix); // Debugging output
        System.out.println("Prefix Result: " + prefix); // Debugging output
        
        return prefix; // Return the final prefix
    }

    // Method to convert infix to postfix (used for prefix conversion)
    private String convertInfixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : infix.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                postfix.append(c); // Operand directly goes to output
            } else if (c == '(') {
                stack.push(c); // Push left parenthesis
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()); // Pop until left parenthesis
                }
                stack.pop(); // Pop left parenthesis
            } else { // Operator
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    postfix.append(stack.pop()); // Pop operators with higher or equal precedence
                }
                stack.push(c); // Push the current operator
            }
        }

        while (!stack.isEmpty()) {
            postfix.append(stack.pop()); // Pop remaining operators
        }

        return postfix.toString(); // Return the final postfix expression
    }

    // Method to determine the precedence of operators
    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1; // For any unknown operator
        }
    }

    // Endpoint to handle the form submission and return the prefix result as plain text
    @PostMapping("/convertPrefix")
    @ResponseBody
    public String convertPrefix(@RequestParam("infix") String infix) {
        return convertInfixToPrefix(infix); // Return plain text prefix result
    }

    @GetMapping("/page3")
    public String page3() {
        return "page3"; // Return the page3.html
    }
}
