package treegui;

import java.util.*;

public class BinaryTree {
    Node root;

    // Build tree from input string
    public void buildTreeFromInput(String input) {
        if (input == null || input.trim().isEmpty()) return;

        String[] parts = input.trim().split("\\s+");
        if (parts.length == 0 || parts[0].equalsIgnoreCase("null")) return;

        // First element can be either a number or string
        try {
            // Try parsing as integer
            root = new Node(Integer.parseInt(parts[0]));
        } catch (NumberFormatException e) {
            // If parsing fails, treat it as a string
            root = new Node(parts[0]);
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < parts.length) {
            Node current = queue.poll();

            // Handle left child
            if (i < parts.length && !parts[i].equalsIgnoreCase("null")) {
                try {
                    // Try parsing as integer
                    current.left = new Node(Integer.parseInt(parts[i]));
                } catch (NumberFormatException e) {
                    // If parsing fails, treat it as a string
                    current.left = new Node(parts[i]);
                }
                queue.offer(current.left);
            }
            i++;

            // Handle right child
            if (i < parts.length && !parts[i].equalsIgnoreCase("null")) {
                try {
                    // Try parsing as integer
                    current.right = new Node(Integer.parseInt(parts[i]));
                } catch (NumberFormatException e) {
                    // If parsing fails, treat it as a string
                    current.right = new Node(parts[i]);
                }
                queue.offer(current.right);
            }
            i++;
        }
    }

    // Inorder traversal (left-root-right)
    public String inorder() {
        StringBuilder sb = new StringBuilder();
        Stack<Node> stack = new Stack<>();
        Node current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            sb.append(current.data).append(" ");
            current = current.right;
        }
        return sb.toString().trim();
    }

    // Preorder traversal (root-left-right)
    public String preorder() {
        StringBuilder sb = new StringBuilder();
        if (root == null) return sb.toString();

        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            sb.append(current.data).append(" ");

            if (current.right != null) stack.push(current.right);
            if (current.left != null) stack.push(current.left);
        }
        return sb.toString().trim();
    }

    // Postorder traversal (left-right-root)
    public String postorder() {
        StringBuilder sb = new StringBuilder();
        if (root == null) return sb.toString();

        Stack<Node> stack1 = new Stack<>();
        Stack<Node> stack2 = new Stack<>();

        stack1.push(root);
        while (!stack1.isEmpty()) {
            Node current = stack1.pop();
            stack2.push(current);

            if (current.left != null) stack1.push(current.left);
            if (current.right != null) stack1.push(current.right);
        }

        while (!stack2.isEmpty()) {
            sb.append(stack2.pop().data).append(" ");
        }

        return sb.toString().trim();
    }
}
