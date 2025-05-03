package treegui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TreeTraversalWithGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField inputField;
    private JEditorPane outputArea;
    private JButton buildBtn, inorderBtn, preorderBtn, postorderBtn, resetBtn;
    private BinaryTree tree;

    public TreeTraversalWithGUI() {
        tree = new BinaryTree();

        setTitle("Binary Tree Traversals");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);

        JPanel panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(72, 61, 139), 0, getHeight(), new Color(47, 79, 79));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new BorderLayout(10, 10));

        JLabel headerLabel = new JLabel("Binary Tree Traversals", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        panel.add(headerLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        inputPanel.setBackground(new Color(240, 248, 255));

        JLabel inputLabel = new JLabel("Enter tree nodes in level-order (use 'null' for empty nodes):");
        inputLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        inputPanel.add(inputLabel);

        inputField = new JTextField(30);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        inputPanel.add(inputField);

        buildBtn = new JButton("Build Tree");
        styleButton(buildBtn, new Color(0, 153, 76));
        inputPanel.add(buildBtn);

        resetBtn = new JButton("Reset");
        styleButton(resetBtn, new Color(128, 0, 0));
        inputPanel.add(resetBtn);

        panel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        inorderBtn = new JButton("Inorder");
        styleButton(inorderBtn, new Color(46, 204, 113));
        inorderBtn.setEnabled(false);
        buttonPanel.add(inorderBtn);

        preorderBtn = new JButton("Preorder");
        styleButton(preorderBtn, new Color(52, 152, 219));
        preorderBtn.setEnabled(false);
        buttonPanel.add(preorderBtn);

        postorderBtn = new JButton("Postorder");
        styleButton(postorderBtn, new Color(231, 76, 60));
        postorderBtn.setEnabled(false);
        buttonPanel.add(postorderBtn);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        outputArea = new JEditorPane();
        outputArea.setEditable(false);
        outputArea.setContentType("text/html");
        outputArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        outputArea.setBackground(Color.WHITE);
        outputArea.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        outputArea.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(700, 220));
        panel.add(scrollPane, BorderLayout.EAST);

        buildBtn.addActionListener(e -> {
            String input = inputField.getText().trim();
            if (input.isEmpty()) {
                tree = new BinaryTree();
                outputArea.setText("");
                showError("Please enter tree values.");
                return;
            }

            if (!input.matches("^(\\w+|null)(\\s+(\\w+|null))*$")) {
                tree = new BinaryTree();
                outputArea.setText("");
                showError("Invalid input. Use words/numbers separated by space. Use only 'null' (not variations) for empty nodes.");
                return;
            }

            if (input.equalsIgnoreCase("null")) {
                tree = new BinaryTree();
                outputArea.setText("");
                showError("Tree cannot be built with only 'null'.");
                return;
            }

            tree.buildTreeFromInput(input);
            outputArea.setText("<html><body style='font-family: Segoe UI;'><p style='color: green; font-size: 24px; font-weight: bold;'>Tree built successfully! Choose a traversal method.</p></body></html>");
            inorderBtn.setEnabled(true);
            preorderBtn.setEnabled(true);
            postorderBtn.setEnabled(true);
        });

        resetBtn.addActionListener(e -> {
            inputField.setText("");
            outputArea.setText("");
            tree = new BinaryTree();
            inorderBtn.setEnabled(false);
            preorderBtn.setEnabled(false);
            postorderBtn.setEnabled(false);
        });

        inorderBtn.addActionListener(e -> {
            outputArea.setText(formatDetailedOutput("Inorder Traversal", tree.inorder(), "Inorder Traversal starts with the left subtree, then root, then right subtree."));
        });

        preorderBtn.addActionListener(e -> {
            outputArea.setText(formatDetailedOutput("Preorder Traversal", tree.preorder(), "Preorder Traversal visits root first, then left and right subtrees."));
        });

        postorderBtn.addActionListener(e -> {
            outputArea.setText(formatDetailedOutput("Postorder Traversal", tree.postorder(), "Postorder Traversal visits left and right subtrees first, then the root."));
        });

        add(panel);
        setVisible(true);
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(130, 40));
        button.setBorder(BorderFactory.createLineBorder(bgColor.darker()));
    }

    private void showError(String message) {
        outputArea.setText("<html><body style='font-family: Segoe UI;'>" +
                "<p style='color: red; font-size: 24px; font-weight: bold;'>" + message + "</p>" +
                "</body></html>");
    }

    private String formatDetailedOutput(String traversalType, String result, String explanation) {
        return "<html><body style='font-family: Segoe UI;'>" +
                "<h2 style='color: #2c3e50; font-size: 24px; font-weight: bold;'>" + traversalType + ":</h2>" +
                "<p style='font-size: 16px; color: #16a085;'>" + explanation + "</p>" +
                "<hr style='border: 1px solid #ccc;'/>" +
                "<p style='font-size: 18px; color: #2980b9; font-weight: bold;'>Traversal Result:</p>" +
                "<p style='font-size: 18px; color: #e74c3c; font-weight: bold;'>" + result + "</p>" +
                "</body></html>";
    }

    public static void main(String[] args) {
        new TreeTraversalWithGUI();
    }
}
