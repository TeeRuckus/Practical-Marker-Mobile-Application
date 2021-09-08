/*
TODO:
    - you will need to change teh cleanString() method with the one which is going to be found inside myUtil class,
    and use that instead so that you will not have code duplication throughout your project
 */
package com.example.assone;
import java.util.*;

public class Graph
{
    private class Vertex
    {
        //using protected, so I can edit the class fields directly from parent class and avoids using accessors and mutators
        private String key;
        private Object value;
        private Hashtable<String, Vertex> connections;


        private Vertex()
        {
            key  = "EMPTY";
            value = null;
            connections = new Hashtable<String, Vertex>();
        }

        private Vertex(String inKey, Admin inUser)
        {
            if( validateKey(inKey) && validateUser(inUser))
            {
                key = inKey;
                value = inUser;
            }
        }

        private Vertex(String inKey, Instructor inUser)
        {
            if( validateKey(inKey) && validateUser(inUser))
            {
                key = inKey;
                value = inUser;
            }
        }

        private Vertex(String inKey, Student inUser)
        {
            if( validateKey(inKey) && validateUser(inUser))
            {
                key = inKey;
                value = inUser;
            }
        }

        private Vertex(Vertex inVert)
        {
            key = inVert.key;
            value = inVert.value;
            connections = inVert.connections;
        }

        //TODO: you will actually need to think on how you're going to use this function
        private Hashtable<String, Vertex> getEdges()
        {
            return connections;
        }

        private int size()
        {
            return connections.size();
        }

        private boolean validateKey(String inKey)
        {
            boolean valid = true;
            if(inKey.length() == 0)
            {
                throw new IllegalArgumentException("ERROR: can't have an empty string as a key: " + inKey);
            }
            return valid;
        }

        private boolean validateUser(Admin inUser)
        {
            boolean valid = true;
            if( !(inUser instanceof Admin))
            {
                throw new IllegalArgumentException("ERROR: object must doesn't meet required type: " + inUser);
            }
            return valid;
        }

        private boolean validateUser(Instructor inUser)
        {
            boolean valid = true;
            if( !(inUser instanceof Instructor))
            {
                throw new IllegalArgumentException("ERROR: object must doesn't meet required type: " + inUser);
            }
            return valid;
        }

        private boolean validateUser(Student inUser)
        {
            boolean valid = true;
            if( !(inUser instanceof Student))
            {
                throw new IllegalArgumentException("ERROR: object must doesn't meet required type: " + inUser);
            }
            return valid;
        }

    }

    private Hashtable<String, Vertex> vertices;
    //this is going to be the admin of the whole application, they're going to be stored here
    private Vertex rootNode;

    //DEFAULT CONSTRUCTOR
    public Graph()
    {
        vertices = new Hashtable<String, Vertex>();
        rootNode = null;
    }

    public int size()
    {
        return vertices.size();
    }

    //TODO: you will need to add more addVertex method for the other of vertex types which you can use in the programme
    public void addVertex(String key, Admin value)
    {
        key = cleanString(key);
        Vertex newVert = new Vertex(key, value);
        vertices.put(key, newVert);
    }

    public  void delVertex(String key)
    {
        if(vertices.isEmpty())
        {
            throw  new IllegalArgumentException("ERROR: no vertices have being added as yet: " + vertices.size());
        }
        key = cleanString(key);
        vertices.remove(key);
    }

    /***********************************************************************************************
     * PUPROSE: to add a directed edge from one node to another node. Otherwise, if this relationship
     * doesn't exist fail
     ***********************************************************************************************/
    public void addEdge(String fromEdge, String toEdge)
    {
        fromEdge = cleanString(fromEdge);
        toEdge = cleanString(toEdge);
        //if the vertex doesn't exist, these operations will fail
        Vertex fromVertex = vertices.get(fromEdge);
        Vertex toVertex = vertices.get(toEdge);

        //getting all the connections from the fromVertex, and the putting the to vertex in the connections
        fromVertex.connections.put(toEdge, toVertex);
    }

    /***********************************************************************************************
     * PUPROSE: to delete a directed edge from one node to another node. Otherwise, if this
     * relationship doesn't excist fail
     ***********************************************************************************************/
    public void delEdge(String fromEdge, String toEdge)
    {
        fromEdge = cleanString(fromEdge);
        toEdge = cleanString(toEdge);
        //if the vertex doesn't exist, these operations will fail
        Vertex fromVertex = vertices.get(fromEdge);
        Vertex toVertex = vertices.get(toEdge);
        fromVertex.connections.remove(toEdge);
    }

    public Hashtable<String, Vertex> getEdges(String nodeName)
    {
        nodeName = cleanString(nodeName);
        Vertex currVertex = vertices.get(nodeName);
        return currVertex.connections;
    }

    public String toString()
    {
        throw new IllegalArgumentException("ERROR: to be implemented");
    }

    /***********************************************************************************************
      PURPOSE: children classes are going to use strings for look up functions, and deleting functions
      hence, the purpose of the function is to trim any leading or lagging white spaces, and to make
      the look up string case insenstive
     ***********************************************************************************************/
    protected String cleanString(String inString)
    {
        //deleting all leading and lagging white spaces
        inString = inString.trim();
        inString = inString.toUpperCase();
        return inString;
    }
}
