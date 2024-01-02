package cy.ac.ucy.cs.epl231.ID1123114.Project4;

import java.util.*;

/**
 * Represents an edge in the graph.
 */
class Edge {
    int src, dest;

    /**
     * Constructor for Edge class.
     *
     * @param src  The source vertex of the edge.
     * @param dest The destination vertex of the edge.
     */
    public Edge(int src, int dest) {
        this.src = src;
        this.dest = dest;
    }
}

/**
 * Represents a node in the BFS traversal.
 */
class Node {
    int ver;
    int min_dist;

    /**
     * Constructor for Node class.
     *
     * @param ver      The vertex of the node.
     * @param min_dist The minimum distance to reach the node.
     */
    public Node(int ver, int min_dist) {
        this.ver = ver;
        this.min_dist = min_dist;
    }
}

/**
 * Represents a graph and provides methods for breadth-first search.
 */
class Graph {
    List<List<Integer>> adjList = null;

    /**
     * Constructor for the Graph class.
     *
     * @param edges List of edges representing the connections in the graph.
     * @param n     Number of vertices in the graph.
     */
    Graph(List<Edge> edges, int n) {
        adjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
        }

        for (Edge edge : edges) {
            adjList.get(edge.src).add(edge.dest);
        }
    }
}

/**
 * Represents the Snake and Ladder game with methods for BFS traversal and path printing.
 */
public class SnakeAndLadder {


    /**
     * Performs breadth-first search traversal on the given graph starting from the specified source vertex.
     * Prints the path and minimum number of steps to reach the destination vertex (100).
     *
     * @param g      The graph.
     * @param source The source vertex.
     * @param n      Number of vertices in the graph.
     * @param edges  List of edges representing the connections in the graph.
     * @param ladder Map representing the ladder connections.
     * @param snake  Map representing the snake connections.
     */
    public static void BFS(Graph g, int source, int n, List<Edge> edges, Map<Integer, Integer> ladder, Map<Integer, Integer> snake) {
        // Queue to store nodes for BFS traversal.
        Queue<Node> q = new ArrayDeque<>();
        // Array to track whether a vertex is discovered during BFS.
        boolean[] discovered = new boolean[n + 1];
        // Array to store the parent of each vertex in the BFS traversal.
        int[] parent = new int[n + 1];

        // Mark the source vertex as discovered and enqueue it.
        discovered[source] = true;
        Node node = new Node(source, 0);
        q.add(node);

        // Continue BFS until the queue is empty.
        while (!q.isEmpty()) {
            // Dequeue a node and check if it's the destination vertex.
            node = q.poll();
            if (node.ver == n) {
                // If the destination is reached, print the path and return.
                printPath(parent, node, edges, ladder, snake);
                return;
            }

            // Iterate through adjacent vertices of the current node.
            for (int u : g.adjList.get(node.ver)) {
                // Check if the adjacent vertex is not discovered.
                if (!discovered[u]) {
                    // Mark it as discovered, set its parent, and enqueue it with incremented minimum distance.
                    discovered[u] = true;
                    parent[u] = node.ver;
                    q.add(new Node(u, node.min_dist + 1));
                }
            }
        }
    }

    /**
     * Determines the dice throw required to move from the previous step to the current step.
     * If the current step is a ladder, returns a positive value based on the ladder's configuration.
     * If the current step is a snake, returns a negative value based on the snake's configuration.
     * If neither ladder nor snake, returns the difference between current and previous steps.
     *
     * @param prevStep    The previous step.
     * @param currentStep The current step.
     * @param ladder      Map representing the ladder connections.
     * @param snake       Map representing the snake connections.
     * @return The dice throw value.
     */
    private static int getDiceThrow(int prevStep, int currentStep, Map<Integer, Integer> ladder, Map<Integer, Integer> snake) {
        if (ladder.containsValue(currentStep)) {
            // It's a ladder, return the dice throw based on the ladder configuration
            // Find the ladder with currentStep as the destination
            for (Map.Entry<Integer, Integer> entry : ladder.entrySet()) {
                if (entry.getValue() == currentStep) {
                    // Return the dice throw based on the ladder's configuration
                    return entry.getKey() - prevStep;
                }
            }
        } else if (snake.containsValue(currentStep)) {
            // It's a snake, return the negative of the dice throw
            // Find the snake with currentStep as the destination
            for (Map.Entry<Integer, Integer> entry : snake.entrySet()) {
                if (entry.getValue() == currentStep) {
                    // Return the negative of the dice throw based on the snake's configuration
                    return -(entry.getKey() - prevStep);
                }
            }
        }

        // Default to 0 if it's not a ladder or snake
        return currentStep - prevStep;
    }

    /**
     * Prints the path from the starting position to the destination vertex (100) along with the minimum number of steps.
     *
     * @param parent The array representing the parent of each vertex in the BFS traversal.
     * @param node   The destination node.
     * @param edges  List of edges representing the connections in the graph.
     * @param ladder Map representing the ladder connections.
     * @param snake  Map representing the snake connections.
     */
    public static void printPath(int[] parent, Node node, List<Edge> edges, Map<Integer, Integer> ladder, Map<Integer, Integer> snake) {
        // List to store the path details.
        List<String> path = new ArrayList<>();
        // Variables to track the current cell and remaining steps.
        int j = 100, cnt = node.min_dist;

        // Traverse the parent array to reconstruct the path.
        for (int i = node.ver; i != 0; i = parent[i]) {
            // Get source and destination cells.
            int src = parent[i];
            int dest = i;
            // Get the dice throw value for the move.
            int diceThrow = getDiceThrow(src, dest, ladder, snake);
            // Add move details to the path list.
            path.add("{" + diceThrow + "}->[" + dest + "|" + cnt + "]");
            cnt--;
        }

        // Reverse the path list to display moves from the starting position.
        Collections.reverse(path);
        // Print the formatted path and the minimum number of steps.
        System.out.println("\nGame --> [0|0]" + String.join("", path));
        System.out.println("Min. Number of Steps: " + node.min_dist);
    }


    /**
     * Prints the paths from vertices 94 to 99 in the graph.
     *
     * @param g      The graph.
     * @param edges  List of edges representing the connections in the graph.
     * @param ladder Map representing the ladder connections.
     * @param snake  Map representing the snake connections.
     */
    public static void printPaths(Graph g, List<Edge> edges, Map<Integer, Integer> ladder, Map<Integer, Integer> snake) {
        int n = 100;  // Assuming a 10x10 board
        for (int i = 94; i < n; i++) {
            if (g.adjList.get(i).size() > 0) {
                System.out.println("[" + i + "]" + bounceBack(g, edges, ladder, snake, i));
            }
        }
    }

    /**
     * Gets the path from the given source vertex to the destination vertex (100) using BFS traversal.
     *
     * @param g      The graph.
     * @param edges  List of edges representing the connections in the graph.
     * @param ladder Map representing the ladder connections.
     * @param snake  Map representing the snake connections.
     * @param source The source vertex.
     * @return The path as a string.
     */
    public static String bounceBack(Graph g, List<Edge> edges, Map<Integer, Integer> ladder, Map<Integer, Integer> snake, int source) {
        StringBuilder path = new StringBuilder();
        int steps = 0;

        while (source != 100) {
            List<Integer> possibleMoves = g.adjList.get(source);

            if (possibleMoves.isEmpty()) {
                break;  // No further moves
            }

            int u = possibleMoves.get(0);  // Take the first possible move
            int diceThrow = getDiceThrow(source, u, ladder, snake);

            path.append("->" + u);
            steps++;

            // Check if it's a snake move, and update source accordingly
            if (snake.containsValue(u)) {
                int tail = getSnakeKey(snake, u);
                // Append all the cells in the snake from head to tail
                for (int i = u - 1; i >= tail; i--) {
                    path.append("->" + i);
                    steps++;
                }
                source = tail;  // Continue with the next cell after the snake's tail
            } else {
                source = u;
            }
        }

        // If the current cell is 100, add the path from 100 back to the current cell
        if (source == 100) {

            int remainingSteps = 6 - steps; // Calculate remaining steps needed to reach 100

            // Adjust the loop to consider only the remaining steps needed
            for (int i = 99; i > 99 - remainingSteps; i--) {

                //Check if the current cell is the head of a snake, and print the tail accordingly
                if (snake.containsKey(i)) {
                    int tail = getSnakeValue(snake, i);
                    path.append("->" + tail);
                    steps++;
                }
                else {
                    path.append("->" + i);
                    steps++;

                }
            }
        }
        return path.toString();
    }

    /**
     * Gets the value of the snake's tail given the head of the snake.
     *
     * @param snake Map representing the snake connections.
     * @param head  The head of the snake.
     * @return The value (tail) of the snake.
     */
    private static int getSnakeValue(Map<Integer, Integer> snake, int head) {
        for (Map.Entry<Integer, Integer> entry : snake.entrySet()) {
            if (entry.getKey() == head) {
                return entry.getValue();
            }
        }
        return head;  // Return the head if the tail is not found
    }

    /**
     * Gets the key (head) of the snake given the tail of the snake.
     *
     * @param snake Map representing the snake connections.
     * @param tail  The tail of the snake.
     * @return The key (head) of the snake.
     */
    private static int getSnakeKey(Map<Integer, Integer> snake, int tail) {
        for (Map.Entry<Integer, Integer> entry : snake.entrySet()) {
            if (entry.getValue() == tail) {
                return entry.getKey();
            }
        }
        return tail;  // Return the tail if the head is not found
    }

    /**
     * Finds the minimum number of moves required to reach the destination (100) in a Snake and Ladder game.
     *
     * @param ladder Map representing the ladder connections.
     * @param snake  Map representing the snake connections.
     * @return The minimum number of moves required.
     */
    public static int findMinimumMoves(Map<Integer, Integer> ladder, Map<Integer, Integer> snake) {
        // Assuming a 10x10 board, where n represents the total number of cells.
        int n = 10 * 10;

        // Create a list to store edges representing connections between cells.
        List<Edge> edges = new ArrayList<>();

        // Iterate over each cell on the board.
        for (int i = 0; i < n; i++) {
            // For each cell, simulate dice throws (1 to 6) to determine possible moves.
            for (int j = 1; j <= 6 && i + j <= n; j++) {
                // Calculate the destination cell based on ladder and snake configurations.
                int src = i;
                int dest = ladder.getOrDefault(i + j, i + j);
                dest = snake.getOrDefault(dest, dest);

                // Add the edge representing the move to the list.
                edges.add(new Edge(src, dest));
            }
        }

        // Create a graph using the generated edges.
        Graph g = new Graph(edges, n);

        // Perform BFS traversal to find the minimum moves and print the path.
        BFS(g, 0, n, edges, ladder, snake);

        // Placeholder return value, actual value based on game logic.
        return 0;
    }


    /**
     * Main method to demonstrate the Snake and Ladder game.
     * Initializes the ladder and snake configurations, creates the game graph, and prints paths along with minimum moves.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Configuration for ladders (start, end positions)
        Map<Integer, Integer> ladder = new HashMap<>();
        ladder.put(1, 38);
        ladder.put(4, 14);
        ladder.put(9, 31);
        ladder.put(21, 42);
        ladder.put(28, 84);
        ladder.put(51, 67);
        ladder.put(72, 91);
        ladder.put(80, 99);

        // Configuration for snakes (head, tail positions)
        Map<Integer, Integer> snake = new HashMap<>();
        snake.put(17, 7);
        snake.put(54, 34);
        snake.put(62, 19);
        snake.put(64, 60);
        snake.put(87, 36);
        snake.put(93, 73);
        snake.put(95, 75);
        snake.put(98, 79);

        // Initialize the total number of cells on the board (assuming a 10x10 board)
        int n = 10 * 10;

        // Create a list to store edges representing connections in the game graph
        List<Edge> edges = new ArrayList<>();

        // Iterate through the board cells to generate edges based on ladder and snake configurations
        for (int i = 94; i < n; i++) {
            for (int j = 1; j <= 6 && i + j <= n; j++) {
                int src = i;
                int dest = ladder.getOrDefault(i + j, i + j);
                dest = snake.getOrDefault(dest, dest);
                edges.add(new Edge(src, dest));
            }
        }

        // Create a graph based on the generated edges
        Graph g = new Graph(edges, n);

        // Print paths for cells that have connections
        System.out.println();
        printPaths(g, edges, ladder, snake);

        // Find and print the minimum moves to reach the destination
        findMinimumMoves(ladder, snake);
    }

}

