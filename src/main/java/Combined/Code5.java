package Combined;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Code5 {

    // Method to validate if the input is a valid Gray code
    private boolean isValidGrayCode(String grayCode) {
        return !grayCode.isEmpty() && grayCode.matches("[01]+"); // Ensure it's not empty and contains only 0s and 1s
    }

    // Method to convert Gray code to Binary
    private String convertGrayToBinary(String grayCode) {
        StringBuilder binary = new StringBuilder();
        binary.append(grayCode.charAt(0)); // The first bit is the same

        // Calculate the remaining bits
        for (int i = 1; i < grayCode.length(); i++) {
            int previousBit = binary.charAt(i - 1) - '0'; // Get the previous binary bit
            int currentGrayBit = grayCode.charAt(i) - '0'; // Get the current gray bit
            binary.append(previousBit ^ currentGrayBit); // XOR and append
        }

        return binary.toString();
    }

    // Endpoint to handle the form submission and return binary code as plain text
    @PostMapping("/convertGrayToBinary")
    @ResponseBody
    public ResponseEntity<String> convert(@RequestParam("gray") String grayCode) {
        if (!isValidGrayCode(grayCode)) {
            return ResponseEntity.badRequest().body("Error: Input must be a valid Gray code (only 0s and 1s).");
        }
        String binaryCode = convertGrayToBinary(grayCode);
        return ResponseEntity.ok(binaryCode); // Return binary code result
    }

    // Endpoint to serve the page for Gray to Binary conversion
    @GetMapping("/page5")
    public String page5() {
        return "page5"; // Return the page5.html
    }
}
