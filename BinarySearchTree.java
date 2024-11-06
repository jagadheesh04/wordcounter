import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;0
import java.util.Map;

class BinarySearchTree {
    private TreeNode root;

    public void add(String medicineName) {
        root = addRecursive(root, medicineName);
    }
0
    private TreeNode addRecursive(TreeNode node, String medicineName) {
        if (node == null) {
            return new TreeNode(medicineName);
        }
        if (medicineName.equals(node.medicineName)) {
            node.frequency++;
        } else if (medicineName.compareTo(node.medicineName) < 0) {
            node.left = addRecursive(node.left, medicineName);
        } else {
            node.right = addRecursive(node.right, medicineName);
        }
        return node;
    }

    public boolean remove(String medicineName) {
        return removeRecursive(root, medicineName) != null;
    }

    private TreeNode removeRecursive(TreeNode node, String medicineName) {
        if (node == null) {
            return null;
        }
        if (medicineName.equals(node.medicineName)) {
            if (node.left == null && node.right == null) {
                return null;
            }
            if (node.left == null) {
                return node.right;
            }
            if (node.right == null) {
                return node.left;
            }
            String smallestValue = findSmallestValue(node.right);
            node.medicineName = smallestValue;
            node.right = removeRecursive(node.right, smallestValue);
            return node;
        }
        if (medicineName.compareTo(node.medicineName) < 0) {
            node.left = removeRecursive(node.left, medicineName);
        } else {
            node.right = removeRecursive(node.right, medicineName);
        }
        return node;
    }

    private String findSmallestValue(TreeNode root) {
        return root.left == null ? root.medicineName : findSmallestValue(root.left);
    }

    public boolean contains(String medicineName) {
        return containsNode(root, medicineName);
    }

    private boolean containsNode(TreeNode node, String medicineName) {
        if (node == null) {
            return false;
        }
        if (medicineName.equals(node.medicineName)) {
            return true;
        }
        return medicineName.compareTo(node.medicineName) < 0
            ? containsNode(node.left, medicineName)
            : containsNode(node.right, medicineName);
    }

    public List<String> inOrderTraversal() {
        List<String> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    private void inOrderTraversal(TreeNode node, List<String> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.medicineName + ": " + node.frequency);
            inOrderTraversal(node.right, result);
        }
    }

    public void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            saveToFileRecursive(root, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveToFileRecursive(TreeNode node, BufferedWriter writer) throws IOException {
        if (node != null) {
            saveToFileRecursive(node.left, writer);
            writer.write(node.medicineName + "," + node.frequency);
            writer.newLine();
            saveToFileRecursive(node.right, writer);
        }
    }

    public void loadFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String medicineName = parts[0];
                int frequency = Integer.parseInt(parts[1]);
                for (int i = 0; i < frequency; i++) {
                    add(medicineName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // New method to count word frequencies from a file
    public Map<String, Integer> countWordFrequencies(String filename) {
        Map<String, Integer> frequencyMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    frequencyMap.put(word, frequencyMap.getOrDefault(word, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return frequencyMap;
    }

    // Method to return frequencies in descending order
    public List<Map.Entry<String, Integer>> getSortedFrequencies(Map<String, Integer> frequencyMap) {
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(frequencyMap.entrySet());
        sortedList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));
        return sortedList;
    }
}

