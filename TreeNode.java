class TreeNode {
    String medicineName;
    int frequency;
    TreeNode left, right;

    public TreeNode(String medicineName) {
        this.medicineName = medicineName;
        this.frequency = 1; // Initial frequency
        this.left = this.right = null;
    }
}
