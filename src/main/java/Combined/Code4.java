package Combined;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Code4 {

    private boolean isValidBinary(String binary) {
        return binary.matches("[01]+"); // Matches only strings containing 0s and 1s
    }

    // Method to convert binary to gray code
    private String convertBinaryToGray(String binary) {
        StringBuilder gray = new StringBuilder();
        gray.append(binary.charAt(0)); // The first bit remains the same

        // Calculate remaining bits
        for (int i = 1; i < binary.length(); i++) {
            int bit = binary.charAt(i) - '0'; // Convert char to int
            int previousBit = binary.charAt(i - 1) - '0';
            gray.append(bit ^ previousBit); // XOR and append
        }

        return gray.toString();
    }

    // Endpoint to handle the form submission and return gray code as plain text
    @PostMapping("/convertBinaryToGray")
    @ResponseBody
    public String convert(@RequestParam("binary") String binary) {
        if (!isValidBinary(binary)) {
            return "Error: Input must be a binary number (only 0s and 1s).";
        }
        String grayCode = convertBinaryToGray(binary);
        return grayCode; // Return gray code result
    }

    @GetMapping("/page4")
    public String page4() {
        return "page4"; // Return the page4.html
    }
}
