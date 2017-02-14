package understand_hw2;

import com.scitools.understand.*;

import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.JApplet;
import javax.swing.JFrame;

import java.net.*;

import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.*;

public class apitest {
public static void main(String[] args) {
	//String variables for holding Java project Path
	String firstJavaProject=null; 
	String secondJavaProject= null;
	JButton open= new JButton();
	JFileChooser chooser = new JFileChooser();
    chooser.setDialogTitle("Select First Java  Project: (Please select a udb file");
    chooser.setCurrentDirectory(new java.io.File("C:/understand"));
    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    int returnVal = chooser.showOpenDialog(open);
    	if(returnVal == JFileChooser.APPROVE_OPTION) 
    	{
    		System.out.println("You chose to open this file: " +
            chooser.getSelectedFile().getName()+" "+
            chooser.getSelectedFile().getAbsolutePath());
       		firstJavaProject=chooser.getSelectedFile().getAbsolutePath();
        }
    chooser.setDialogTitle("Select Second Java  Project");
    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    int returnValue = chooser.showOpenDialog(open);
    	if(returnValue == JFileChooser.APPROVE_OPTION) 
    	{
    		System.out.println("You chose to open this file: " +
            chooser.getSelectedFile().getName()+" "+
            chooser.getSelectedFile().getAbsolutePath());
       		secondJavaProject=chooser.getSelectedFile().getAbsolutePath();
    	}
    
    try{
    //Open the Understand Database for first Java project
    Database db = Understand.open(firstJavaProject);
    Database db2 = Understand.open(secondJavaProject);
    // Graph for first Java project
    DirectedGraph<String, DefaultEdge> graph1 = new DefaultDirectedGraph<>(DefaultEdge.class);
    //Graph for second Java project
    DirectedGraph<String, DefaultEdge> graph2 = new DefaultDirectedGraph<>(DefaultEdge.class);
    // Get a list of all functions, methods in first Java project
    Entity [] funcs = db.ents("function ~unknown ~unresolved, method ~unknown ~unresolved");
    	for(Entity e : funcs)
    	{
    		String vertexV= e.name();
    		//Adding entities as vertex
    		if(!(graph1.containsVertex(vertexV))){
    			graph1.addVertex(vertexV);}
    		//Find all the methods that given entity call
    		Reference [] methodcallRefs = e.refs("define"," parameter",true);
    			for( Reference mcRef : methodcallRefs){
    				Entity mEnt = mcRef.ent();
    					String vertexU= mEnt.name();
    						if(!(graph1.containsEdge(vertexV,vertexU)) && !(vertexV.equals(vertexU))){
    							//Add an edge if there is a reference between two entities
    							graph1.addVertex(vertexU);
    							graph1.addEdge(vertexV, vertexU);}}
        }
 // Print out the graph of first Java project using DFS
    System.out.println("Graph of First Java Program");
    Iterator<String> iter = new DepthFirstIterator<>(graph1);
    String vertex;
    	while (iter.hasNext())
    	{
    		vertex = iter.next();
    		System.out.println(
            "Vertex " + vertex + " is connected to: "
                + graph1.edgesOf(vertex));
         }
    
    	
    	//Finding entities for the second Java project
    	Entity [] funcs2 = db2.ents("function ~unknown ~unresolved, method ~unknown ~unresolved");
    	for(Entity e1 : funcs2)
    	{
    		String vertexV= e1.name();
    		//Adding entities as vertex
    		if(!(graph2.containsVertex(vertexV))){
    			graph2.addVertex(vertexV);}
    		//Find all the methods that given entity calls
    		Reference [] methodcallRefs1 = e1.refs("define"," parameter",true);
    			for( Reference mcRef1 : methodcallRefs1){
    				Entity mEnt1 = mcRef1.ent();
    					String vertexU= mEnt1.name();
    						if(!(graph2.containsEdge(vertexV,vertexU)) && !(vertexV.equals(vertexU))){
    							//Add an edge if there is a reference between two entities
    							graph2.addVertex(vertexU);
    							graph2.addEdge(vertexV, vertexU);}}
        }
 // Print out the graph of second Java project using DFS
    System.out.println("*********************");
    System.out.println("Graph of First Java Program");
    System.out.println("****************************");
    Iterator<String> iter1 = new DepthFirstIterator<>(graph2);
    String vertex1;
    	while (iter1.hasNext())
    	{
    		vertex1 = iter1.next();
    		System.out.println(
            "Vertex " + vertex1 + " is connected to: "
                + graph2.edgesOf(vertex1));
         }
    	
    	
    	
    }catch (UnderstandException e){
    System.out.println("Failed opening Database:" + e.getMessage()+"Cause"+e.getCause());
    System.exit(0);
    }
}


}
