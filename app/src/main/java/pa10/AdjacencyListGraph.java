package pa10;
import java.util.*;

public class AdjacencyListGraph {
private static class Edge{
        int source;
        int destination;
    
        public Edge(int source, int destination) {
            this.source = source;
            this.destination = destination;
        }
    }

    private List<List<Edge>> adjList;
    private int vertices;

    public AdjacencyListGraph(int vertices){
        this.vertices = vertices;
        adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjList.add(new LinkedList<>());
        }
        
    }

    public void addEdge(int v, int w) {
        adjList.get(v).add(new Edge(v, w));
    }

    public String topologicalSort() {
        Stack<Integer> sortedStack = new Stack<>();
        int[] visited = new int[vertices];
        String result = "";
    
        for (int i = 0; i < adjList.size(); i++) {
            if (visited[i] == 0) { 
                if (dfsWithCycleCheck(i, visited, sortedStack)) {
                    return "Graph has a cycle";
                }
            }
        }
    
        while (!sortedStack.isEmpty()) {
            int current = sortedStack.pop();
            result += current + " ";
        }
        return result.trim();
    }
    
    private boolean dfsWithCycleCheck(int curr, int[] visited, Stack<Integer> stack) {
        visited[curr] = 1; // VISITING
    
        for (Edge edge : adjList.get(curr)) {
            if (visited[edge.destination] == 0) {
                if (dfsWithCycleCheck(edge.destination, visited, stack)) {
                    return true;
                }
            } else if (visited[edge.destination] == 1) {
                return true;
            }
        }
    
        visited[curr] = 2; // VISITED
        stack.push(curr);
        return false; // No cycle detected
    }
    

    private void dfs(int curr, Boolean[] visited, Stack<Integer> stack) {
        visited[curr] = true;
    
        for (Edge edge : adjList.get(curr)) {
            if (!visited[edge.destination]) {
                dfs(edge.destination, visited, stack);
            }
        }
    

        stack.push(curr);
    }

    public String kahn(){
        int[] InDegree = new int[vertices];
        int[] sorted = new int[vertices];
        Queue<Integer> queue = new LinkedList<>();
        String result = "";
        for (int i = 0; i < vertices; i++){
            for (Edge e: adjList.get(i)){
                InDegree[e.destination] ++;
            }
        }
        for (int u = 0; u<vertices; u++){
            if (InDegree[u]==0){
                queue.add(u);
            }
        }

        int index = 0; //Index for sorted list
        while (!queue.isEmpty()){
            int u = queue.poll();
            sorted[index] = u;
            index++;
            for (Edge e: adjList.get(u)){
                InDegree[e.destination] --;
                if (InDegree[e.destination]==0){
                    queue.add(e.destination);
                }
            }
        }

        if (index != vertices) {
            return "Graph has a cycle";
        }

        for (int i = 0; i < vertices; i ++){
            result += sorted[i] + " ";
        }
        return result;

    }

    public static void main(String[] args) {

        AdjacencyListGraph graph = new AdjacencyListGraph(6);
        graph.addEdge(5, 2);
        graph.addEdge(5, 0);
        graph.addEdge(4, 0);
        graph.addEdge(4, 1);
        graph.addEdge(2, 3);
        graph.addEdge(3, 1);


        // Perform topological sort
        String topoSortResult = graph.kahn();
        System.out.println("Topological Order: " + topoSortResult);
    }




    
}
