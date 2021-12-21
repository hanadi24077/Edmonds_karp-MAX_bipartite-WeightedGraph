/*
 Alyaa Alkhiamiy 
 Afnan Abdullah Albayti
 Hanadi Abdulrahim Alsulami
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class Q2 {

}

class Edmonds_karp {

    //declare arrays
    private int[][] flow;
    private int[][] capacity;
    private int[] parent;
    private boolean[] Isvisited; //array to store if vertex is Mark visited or still not.
    //declare variable.
    private int n, m;
    static final int numVertex = 6;//Number of vertices.
/////////////////////////"Edmonds_karp" Method//////////////////////////////////    

    public Edmonds_karp(int numOfVerticles, int numOfEdges, WeightedGraph Graph) {
        this.n = numOfVerticles;
        this.m = numOfEdges;
        this.flow = new int[n][n];
        this.capacity = Graph.adjacencylist;
        this.parent = new int[n];
        this.Isvisited = new boolean[n];
    }
///////////////////////"getMaxFlow" Method/////////////////////////////////////////////

    public int getMaxFlow(int source, int sink) {
        int MaxFlow = 0;
        System.out.println("-----------------Maximum flow-----------------");
        System.out.println("Flow augmentation path:\n");
        //while loop
        while (true) {
            String Path = "";

            //loop to Mark all vertices not Isvisited in "Isvisited" array
            int i = 0;
            while (i < n) {

                Isvisited[i] = false;
                ++i;   //increment by 1.
            }//end inner while loop.
            final Queue<Integer> queue = new ArrayDeque<Integer>();
            queue.add(source);//add to "queue".
            Isvisited[source] = true;//mark the first vertex visited.

            boolean check = false;
            int current;
            // loop continue until "queue" become empty.
            while (!queue.isEmpty()) {
                current = queue.poll();//poll the first vertex in Queue.
                //if vertex in currnet=sink
                if (current == sink) {
                    check = true;//update to true and stop.
                    break;
                }//end if
                //while loop
                i = 0;
                while (i < n) {
                    if (Isvisited[i] == false && capacity[current][i] > flow[current][i]) {
                        Isvisited[i] = true;
                        queue.add(i);//add to "queue".
                        parent[i] = current;
                    }//end if
                    ++i;//increment by 1.
                }//end while loop.

            }//end outer while loop.

            if (check == false) {
                break;
            }//end if

            int Path_Flow = capacity[parent[sink]][sink] - flow[parent[sink]][sink];
            i = sink;
            while (i != source) {
                String Dir = " ⇢ ";
                Path_Flow = Math.min(Path_Flow, (capacity[parent[i]][i] - flow[parent[i]][i]));
                if (capacity[parent[i]][i] == 0) {//if Graph[u][vertex]=0
                    Dir = " ⇠ ";
                }//end if
                Path = Dir + (i + 1) + Path;//update path.
                i = parent[i];
            }//end while loop.

            Path = (source + 1) + Path;//compute the "Path".
            System.out.println("  " + Path);//print path
            //while loop
            i = sink;
            while (i != source) {
                flow[parent[i]][i] += Path_Flow;//compute flow
                flow[i][parent[i]] -= Path_Flow;//compute flow
                i = parent[i];//update i
            }//end while loop

            System.out.println("  Flow= " + Path_Flow);//print the flow
            MaxFlow += Path_Flow;//compute "MaxFlow".
            System.out.println("  Updated Flow= " + MaxFlow);//print Updated Flow.
            System.out.println("");//new Line.
        }//end outer while loop.
        System.out.println("  Maximum Flow= " + MaxFlow);//print "Path".
        System.out.println("");//new Line.
        System.out.println("-----------------Minimum cut-----------------");
        System.out.println("Edges in the min-cut:");//print Edges in the min-cut.
        int result = 0;
        //while loop.
        int i = 0;
        while (i < flow.length) {
            int j = 0;
            //while loop.
            while (j < flow.length) {
                if (flow[i][j] > 0 && Isvisited[i] && !Isvisited[j]) {
                    System.out.println("");//new Line
                    System.out.println("  Edge: " + (i + 1) + "," + (j + 1));//print Edge.
                    System.out.println("  Capacity= " + flow[i][j]);//print Capacity.
                    result += flow[i][j];//compute result value.
                    System.out.println("  Updated Capicity of Min-cut= " + result);//print result.
                }//end if
                j++;
            }//end inner while loop.
            i++;
        }//end outer while loop
        return result;//return result
    }//end getMaxFlow Method.
////////////////////////"main" Method///////////////////////////////////////////////

    public static void main(String[] args) {

        WeightedGraph Graph = new WeightedGraph(numVertex);//Object "Graph" from "WeightedGraph" class.
        //add Edges to Graph.
        Graph.addEgde(1, 2, 2);
        Graph.addEgde(1, 3, 7);
        Graph.addEgde(2, 4, 3);
        Graph.addEgde(2, 5, 4);
        Graph.addEgde(4, 6, 1);
        Graph.addEgde(5, 6, 5);
        Graph.addEgde(3, 4, 4);
        Graph.addEgde(3, 5, 2);
        Edmonds_karp g = new Edmonds_karp(6, Graph.adjacencylist.length, Graph);
        //calling getMaxFlow Method.
        System.out.println("\n  The Total Capacity of Min-cut= " + g.getMaxFlow(0, Graph.adjacencylist.length - 1));
    }//end main Method.
}//end Edmonds_karp class.

class MAX_bipartite {

    static final int numVertex = 6;//Number of vertices.
    String[] Applicant_Array = {"Ahmed", "Mahmoud", "Eman", "Fatimah", "Kamel", "Nojood"};// String array of Applicant.
    // String array of Hospital.
    String[] Hospital_Array = {"King Abdelaziz University", "King Fahd", "East Jeddah", "King Fahad Armed Forces",
        "King Faisal Specialist", "Ministry of National Guard"};
    ArrayList<Integer> set_M_applicants = new ArrayList<>();//ArrayList.
    int M_A = Applicant_Array.length;//initialze M_A by Applicant_Array.length.
    int N_H = Hospital_Array.length;//initialze N_H by Hospital_Array.length.
    static WeightedGraph Graph = new WeightedGraph(numVertex);
    int[] Applicant = new int[N_H];// int array of Applicant
//////////////////////////Bipartite_Matching Method/////////////////////////////////

    public boolean Bipartite_Matching(int k, boolean[] Isvisited, int[] Assign_array) {
        int vertex = 0;
        //while loop will continue until vertex >=N_H.
        while (vertex < N_H) {
            //if there is edge and is not Mark Isvisited yet.
            if (Graph.adjacencylist[k][vertex] == 1 && Isvisited[vertex] == false) {
                Isvisited[vertex] = true;//Mark to Isvisited.
                if (Assign_array[vertex] < 0 || Bipartite_Matching(Assign_array[vertex], Isvisited, Assign_array) == true) {
                    Assign_array[vertex] = k;//assign vertex to k
                    System.out.println("\t" + Applicant_Array[k] + " assigned to " + Hospital_Array[vertex]);//print
                    set_M_applicants.set(k, vertex); // add the edge to set_M_applicants.
                    Applicant[vertex] = k;
                    return true;//return true.
                }//end inner if.
            }//end outer if.
            vertex++;//move to next vertex.
        }//end while loop.

        return false;//else return false.
    }//end "Bipartite_Matching" Method
////////////////////////Maximum_Matching Method/////////////////////////////////

    public int Maximum_Matching() {
        int[] Assign_array = new int[N_H];//array to store hospital and applicant that assigned to it.
        int j = 0;
        int Matching_count = 0;
        //while loop.
        while (j < N_H) {
            Assign_array[j] = -1;//at first make the jobs are Available.
            set_M_applicants.add(-1);
            j++;//increment by 1.
        }//end while loop.      
        int u = 0;
        //while loop.
        while (u < M_A) {
            boolean[] Isvisited = new boolean[N_H];
            if (Bipartite_Matching(u, Isvisited, Assign_array) == true) //when u get a hospital
            {
                System.out.println("\n\tSet M applicants{");//print
                int i = 0;
                //while loop.
                while (i < set_M_applicants.size()) {
                    if (set_M_applicants.get(i) > -1) {
                        System.out.print("\n\t(" + Applicant_Array[i] + "," + Hospital_Array[set_M_applicants.get(i)] + ")");//print
                    }//end inner if. 
                    i++;
                }//end inner while.
                System.out.println("}");
                System.out.println("\t----------------------------------------");
                Matching_count++;
            }//end outer if.
            u++;//increment u by one.
        }//end outer while loop.

        return Matching_count;//return Matching_count.
    }//end "Maximum_Matching" Method.
////////////////////////////////main/////////////////////////////////////////

    public static void main(String[] args) {
        System.out.println("----------------Maximum bipartite matching----------------\n");
        MAX_bipartite max_bipartite = new MAX_bipartite();
        //add edges to graph.
        Graph.addEdge_without_weighte(1, 1);
        Graph.addEdge_without_weighte(1, 2);
        Graph.addEdge_without_weighte(2, 6);
        Graph.addEdge_without_weighte(3, 1);
        Graph.addEdge_without_weighte(3, 4);
        Graph.addEdge_without_weighte(4, 3);
        Graph.addEdge_without_weighte(5, 4);
        Graph.addEdge_without_weighte(5, 5);
        Graph.addEdge_without_weighte(6, 6);
        System.out.println("\n\tThe number of Maximum applicants that can be assigned to hospitals= " + max_bipartite.Maximum_Matching() + "\n");//print
    }//end  "main" Method

}//end  "MAX_bipartite" class.

class WeightedGraph {

    //declare a variables
    int Vertices;
    public int[][] adjacencylist;

    //constructor
    WeightedGraph(int vertices) {
        this.Vertices = vertices;
        adjacencylist = new int[vertices][vertices];
    }

    //add adges to the adjacencylist with weight
    public void addEgde(int Source, int Destination, int Weight) {
        adjacencylist[Source - 1][Destination - 1] = Weight;
    }

    //add adges to the adjacencylist without weight
    public void addEdge_without_weighte(int Source, int Destination) {
        adjacencylist[Source - 1][Destination - 1] = 1;
    }

    //method to print graph
    public void print() {
        for (int i = 0; i < Vertices; i++) {
            for (int j = 0; j < Vertices; j++) {
                if (adjacencylist[i][j] != 0) {
                    System.out.println("vertex " + i + " with "
                            + j + " weight " + adjacencylist[i][j]);
                }
            }
        }
    }
}
