/*
TODO:
    - when you're adding your instructor, you will need to connect that edge to teh root
    node so that it will work properly and the way which you will expect it to work
 */
package com.example.assone;
import static com.example.assone.myUtils.cleanString;

import java.util.*;

public class Graph
{
    private class Vertex
    {
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

        //copy constructor
        private Vertex(Vertex inVert)
        {
            key = inVert.key;
            value = inVert.value;
            connections = inVert.connections;
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

    public void delVertex(String key)
    {
        if(vertices.isEmpty())
        {
            throw  new IllegalArgumentException("ERROR: no vertices have being added as yet: " + vertices.size());
        }
        key = myUtils.cleanString(key);
        vertices.remove(key);
    }

    public Hashtable<String, Vertex> getVertices()
    {
        return new Hashtable<>(vertices);
    }

    public Hashtable<String, Vertex> setVertics(Hashtable<String, Vertex> inVertices)
    {
        //don't need to do any validation as the hashtable class will do validation for us
        vertices = inVertices;
    }

    public Vertex getRootNode()
    {
        return new Vertex(rootNode);
    }

    /***********************************************************************************************
     * PUPROSE: to add a directed edge from one node to another node. Otherwise, if this relationship
     * doesn't exist fail
     ***********************************************************************************************/
    public void addEdge(String fromEdge, String toEdge)
    {
        fromEdge = myUtils.cleanString(fromEdge);
        toEdge = myUtils.cleanString(toEdge);
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
        fromEdge = myUtils.cleanString(fromEdge);
        toEdge = myUtils.cleanString(toEdge);
        //if the vertex doesn't exist, these operations will fail
        Vertex fromVertex = vertices.get(fromEdge);
        Vertex toVertex = vertices.get(toEdge);
        fromVertex.connections.remove(toEdge);
    }

    public Hashtable<String, Vertex> getEdges(String nodeName)
    {
        nodeName = myUtils.cleanString(nodeName);
        Vertex currVertex = vertices.get(nodeName);
        return currVertex.connections;
    }

    private boolean validateRootNode()
    {
        boolean valid = true;
        if(rootNode == null)
        {
            throw new IllegalArgumentException("ERROR: they is admin, must create admin first");
        }

        return valid;
    }

    public String toString()
    {
        throw new IllegalArgumentException("ERROR: to be implemented");
    }
}
