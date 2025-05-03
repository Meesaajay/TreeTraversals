package treegui;

class Node {
    Object data;  // Use Object to handle both String and Integer data types
    Node left, right;

    public Node(Object data) {
        this.data = data;
        this.left = this.right = null;
    }
}
