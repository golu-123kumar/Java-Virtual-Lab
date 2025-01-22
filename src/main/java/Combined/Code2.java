package Combined;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Stack;

@Controller
public class Code2 {

    // Method to convert infix expression to postfix
    public String convertInfixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (char c : infix.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                postfix.append(c); // If the character is an operand, add it to the output
            } else if (c == '(') {
                stack.push(c); // If it's a left parenthesis, push it onto the stack
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()); // Pop from stack to output until a left parenthesis is encountered
                }
                stack.pop(); // Pop the left parenthesis
            } else { // Operator
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    postfix.append(stack.pop()); // Pop from stack to output if the operator at the top has greater or equal precedence
                }
                stack.push(c); // Push the current operator onto the stack
            }
        }

        // Pop all remaining operators from the stack
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
        }

        return postfix.toString();
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
                return -1;
        }
    }

    // Endpoint to handle the form submission and return the postfix result as plain text
    @PostMapping("/convert")
    @ResponseBody
    public String convert(@RequestParam("infix") String infix) {
        return convertInfixToPostfix(infix); // Return plain text
    }

    @GetMapping("/")
    public String home() {
        return "index"; // Return the main index.html
    }
}
