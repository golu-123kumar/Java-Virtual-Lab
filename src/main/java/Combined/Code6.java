package Combined;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Code6 {
    
    // Method to generate all possible substrings
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
