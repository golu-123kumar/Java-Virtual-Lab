package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @GetMapping("/")
    public String mainPage() {
        return "index"; // Maps to index.html
    }

    @GetMapping("/index1")
    public String indexPage() {
        return "index2"; // Maps to index1.html
    }

    @GetMapping("/bst")
    public String bstPage() {
        return "bst"; // Maps to bst.html
    }

    @GetMapping("/po2")
    public String po2() {
        return "po2"; // Maps to po2.html for displaying postfix result
    }

    @GetMapping("/po3")
    public String po3() {
        return "po3"; // Maps to po3.html for displaying prefix result
    }

    @GetMapping("/po4")
    public String po4() {
        return "po4"; // Maps to po4.html for displaying gray code result
    }

    @GetMapping("/po5")
    public String po5() {
        return "po5"; // Maps to po5.html for displaying substrings result
    }

    @GetMapping("/po6")
    public String po6() {
        return "po6"; // Maps to po6.html for another result page
    }

    @GetMapping("/page2")
    public String page2() {
        return "page2"; // Maps to page2.html for infix to postfix conversion
    }

    @GetMapping("/page3")
    public String page3() {
        return "page3"; // Maps to page3.html for infix to prefix conversion
    }

    @GetMapping("/page4")
    public String page4() {
        return "page4"; // Maps to page4.html for binary to gray code conversion
    }

    @GetMapping("/page5")
    public String page5() {
        return "page5"; // Maps to page5.html
    }

    // --------------------------- POSTFIX CONVERSION (page2) ---------------------------
    @PostMapping("/convert")
    @ResponseBody
    public String convertToPostfix(@RequestParam String infix) {
        if (infix == null || infix.trim().isEmpty()) {
            return "<div style='text-align:left;'>Invalid input</div>"; // Handle empty input
        }
        try {
            return formatResponseAsHtml(infixToPostfix(infix)); // Convert and return formatted HTML
        } catch (Exception e) {
            return "<div style='text-align:left;'>Error in processing the expression: " + e.getMessage() + "</div>";
        }
    }

    private String formatResponseAsHtml(String steps) {
        StringBuilder htmlResponse = new StringBuilder("<div style='text-align:left;'>"); // Start a div for left alignment
        String[] stepLines = steps.split("\n"); // Split the steps into lines

        for (String line : stepLines) {
            // Split each line to separate step number and operation
            String[] parts = line.split(": ");
            if (parts.length == 2) {
                String stepNumber = parts[0]; // Step number
                String operation = parts[1];   // Operation
                // Format the output with colors
                htmlResponse.append(stepNumber).append(": <span style='color:blue;'>").append(operation).append("</span><br/>");
            }
        }
        
        htmlResponse.append("</div>"); // Close the div
        return htmlResponse.toString(); // Return the formatted HTML
    }

    private String infixToPostfix(String expression) {
        StringBuilder postfix = new StringBuilder(); 
        Stack<Character> stack = new Stack<>();     
        StringBuilder steps = new StringBuilder();  
        int stepCounter = 1;                        

        for (char c : expression.toCharArray()) {
            if (Character.isLetterOrDigit(c)) { 
                postfix.append(c);               
            } else if (c == '(') {               
                stack.push(c);                  
            } else if (c == ')') {               
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()); 
                }
                stack.pop(); 
            } else {  
                while (!stack.isEmpty() && getPrecedence(c) <= getPrecedence(stack.peek())) {
                    postfix.append(stack.pop()); 
                }
                stack.push(c); 
            }

            
            steps.append("Step ").append(stepCounter).append(": ").append(postfix).append("\n");
            stepCounter++; 
        }

        
        while (!stack.isEmpty()) {
            postfix.append(stack.pop());
            steps.append("Step ").append(stepCounter).append(": ").append(postfix).append("\n");
            stepCounter++; 
        }

        return steps.toString(); 
    }

    
    private int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
        }
        return -1; // For any other characters
    }


    // --------------------------- PREFIX CONVERSION (page3) ---------------------------
    @PostMapping("/convertPrefix")
@ResponseBody
public String convertPrefix(@RequestParam String infix) {
    if (infix == null || infix.trim().isEmpty()) {
        return ""; // Return an empty string for invalid input
    }
    return infixToPrefix(infix); // Convert and return prefix result
}

private String infixToPrefix(String expression) {
    Stack<Character> operators = new Stack<>();
    Stack<String> operands = new Stack<>();
    StringBuilder steps = new StringBuilder(); // To capture intermediate steps
    int stepCounter = 1; // To track step numbers

    for (char c : expression.toCharArray()) {
        if (Character.isLetterOrDigit(c)) {
            operands.push(String.valueOf(c)); // Push operand to stack
        } else if (c == '(') {
            operators.push(c); // Push '(' to operators stack
        } else if (c == ')') {
            while (!operators.isEmpty() && operators.peek() != '(') {
                String operand1 = operands.pop();
                String operand2 = operands.pop();
                char operator = operators.pop();
                String temp = operator + operand2 + operand1;
                operands.push(temp);

                // Append step details in the desired format
                steps.append("Step ").append(stepCounter).append(": ")
                     .append("<span style='color: blue;'>").append(temp).append("</span><br/>");
                stepCounter++; // Increment step counter
            }
            operators.pop(); // Remove '(' from stack
        } else { // Operator
            while (!operators.isEmpty() && precedence(c) <= precedence(operators.peek())) {
                String operand1 = operands.pop();
                String operand2 = operands.pop();
                char operator = operators.pop();
                String temp = operator + operand2 + operand1;
                operands.push(temp);

                // Append step details in the desired format
                steps.append("Step ").append(stepCounter).append(": ")
                     .append("<span style='color: blue;'>").append(temp).append("</span><br/>");
                stepCounter++; // Increment step counter
            }
            operators.push(c); // Push the operator to stack
        }
    }

    while (!operators.isEmpty()) {
        String operand1 = operands.pop();
        String operand2 = operands.pop();
        char operator = operators.pop();
        String temp = operator + operand2 + operand1;
        operands.push(temp);

        // Append step details in the desired format
        steps.append("Step ").append(stepCounter).append(": ")
             .append("<span style='color: blue;'>").append(temp).append("</span><br/>");
        stepCounter++; // Increment step counter
    }

    // Return only steps and final expression without additional text
    return "<div style='text-align: left;'>" + steps.toString() +
           "<strong>final result:<span style='color: blue;'>" + operands.pop() + "</span></strong></div>"; // Return all steps
}

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


    // --------------------------- BINARY TO GRAY CONVERSION (page4) ---------------------------
    @PostMapping("/convertBinaryToGray")
    @ResponseBody

    
    public String convertBinaryToGray(@RequestParam String binary) {
        if (binary == null || binary.trim().isEmpty()) {
            return "Invalid input"; // Handle empty input
        }

        
        return binaryToGray(binary); // Convert and return gray code result
    }

    private String binaryToGray(String binary) {
        StringBuilder gray = new StringBuilder();
        gray.append(binary.charAt(0)); // The first bit is the same as the original binary's first bit

        // Loop through the binary string to compute the gray code
        for (int i = 1; i < binary.length(); i++) {
            gray.append((binary.charAt(i - 1) - '0') ^ (binary.charAt(i) - '0')); // XOR operation
        }

        return gray.toString(); // Return the gray code
    }

    // --------------------------- GRAY TO BINARY CONVERSION (page5) ---------------------------
    @PostMapping("/convertGrayToBinary")
    @ResponseBody
    public String convertGrayToBinary(@RequestParam String gray) {
        if (gray == null || gray.trim().isEmpty()) {
            return "Invalid input"; // Handle empty input
        }
        return grayToBinary(gray); // Convert and return binary result
    }

    private String grayToBinary(String gray) {
        StringBuilder binary = new StringBuilder();
        // The first bit of binary is the same as gray code's first bit
        binary.append(gray.charAt(0));

        // Loop to compute the binary from gray code
        for (int i = 1; i < gray.length(); i++) {
            binary.append((binary.charAt(i - 1) - '0') ^ (gray.charAt(i) - '0')); // XOR operation
        }

        return binary.toString(); // Return the binary code
    }

    // --------------------------- SUBSTRINGS GENERATION (page6) ---------------------------

    private List<String> generateSubstrings(String input) {
        List<String> substrings = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            for (int j = i + 1; j <= input.length(); j++) {
                substrings.add(input.substring(i, j));
            }
        }
        return substrings;
    }

    // Endpoint to handle the form submission and return substrings as plain text
    @PostMapping("/generateSubstrings")
    @ResponseBody
    public ResponseEntity<List<String>> generate(@RequestParam("input") String input) {
        List<String> substrings = generateSubstrings(input);
        return ResponseEntity.ok(substrings); // Return the list of substrings
    }

    // Endpoint to serve the page for substring generation
    @GetMapping("/page6")
    public String page6() {
        return "page6"; // Return the page6.html
    }
    
}    