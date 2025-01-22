package Combined;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class Code1 {

    // Node class for BST
    static class Node {
        int data;
        Node left, right;

        Node(int item) {
            data = item;
            left = right = null;
        }
    }

    // Function to insert a new node in the BST
    Node insert(Node root, int key) {
        if (root == null) {
            return new Node(key);
        }
        if (key < root.data) {
            root.left = insert(root.left, key);
        } else if (key > root.data) {
            root.right = insert(root.right, key);
        }
        return root;
    }

    // Function to visualize BST as a string (in-order traversal)
    String visualize(Node root) {
        if (root == null) {
            return "";
        }
        return visualize(root.left) + root.data + " " + visualize(root.right);
    }

    // Endpoint to handle the form submission and return BST visualization
    @PostMapping("/convert")
    public String convertToBST(@RequestParam("arrayInput") String arrayInput, Model model) {
        List<String> stringNumbers = Arrays.asList(arrayInput.split(","));
        Node root = null;

        for (String str : stringNumbers) {
            try {
                int number = Integer.parseInt(str.trim());
                root = insert(root, number);
            } catch (NumberFormatException e) {
                model.addAttribute("error", "Invalid input: " + str);
                return "index"; // Redirect back to the index page
            }
        }

        String bstVisualization = visualize(root).trim();
        model.addAttribute("bst", bstVisualization);
        return "index2"; // Return the name of the HTML file to render
    }

    @GetMapping("/")
    public String home() {
        return "index";  // Return the main index.html
    }
}
