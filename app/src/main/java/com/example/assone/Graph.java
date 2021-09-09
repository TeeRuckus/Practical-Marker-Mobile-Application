/*
TODO:
    - when you're adding your instructor, you will need to connect that edge to teh root
    node so that it will work properly and the way which you will expect it to work
    - you will need to delete every where where you used rootNode, to make your code a lot
    cleaner and easier to maintain
 */
package com.example.assone;
import static com.example.assone.myUtils.cleanString;

import java.util.*;

public class Graph
{
    public class Vertex
    {
        private String key;
        private User value;
        private HashMap<String, Vertex> connections;


        public Vertex()
        {
            key  = "EMPTY";
            value = null;
            connections = new HashMap<String, Vertex>();
        }

        public Vertex(String inKey, Admin inUser)
        {
            if( validateKey(inKey) && validateUser(inUser))
            {
                key = inKey;
                value = inUser;
                connections = new HashMap<String, Vertex>();
            }
        }

        public Vertex(String inKey, Instructor inUser)
        {
            if( validateKey(inKey) && validateUser(inUser))
            {
                key = inKey;
                value = inUser;
                connections = new HashMap<String, Vertex>();
            }
        }

        public Vertex(String inKey, Student inUser)
        {
            if( validateKey(inKey) && validateUser(inUser))
            {
                key = inKey;
                value = inUser;
                connections = new HashMap<String, Vertex>();
            }
        }

        public Vertex(Vertex inVert)
        {
            key = inVert.key;
            value = inVert.value;
            connections = inVert.connections;
        }

        //ACCESSORS
        public String getKey()
        {
            return new String(key);
        }

        public User getValue()
        {
            return value;
        }

        public HashMap<String, Vertex> getConnections()
        {
            return connections;
        }


        //MUTATORS
        public void setKey(String inKey)
        {
            if(validateKey(inKey))
            {
                key = inKey;
            }

        }

        public void setValue(Admin inUser)
        {
            value = inUser;
        }

        public void setValue(Instructor inUser)
        {
            value = inUser;
        }

        public void setValue(Student inUser)
        {
            value = inUser;
        }

        public HashMap<String, Vertex> getEdges()
        {
            return connections;
        }

        public int size()
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

    private HashMap<String, Vertex> vertices;
    //this is going to be the admin of the whole application, they're going to be stored here
    //private Vertex rootNode;

    //DEFAULT CONSTRUCTOR
    public Graph()
    {
        vertices = new HashMap<String, Vertex>();
        //intialising where the admin node is going to be as null
        vertices.put("ADMIN", null);
        //rootNode = null;
    }

    public int size()
    {
        return vertices.size();
    }

    //TODO: you will need to add more addVertex method for the other of vertex types which you can use in the programme
    public void addVertex(Instructor inInstrucor)
    {
        //can't add a vertex if they is not root node in the programme
        if(validateRootNode())
        {
            String key = myUtils.cleanString(inInstrucor.getName());
            Vertex newVert = new Vertex(key, inInstrucor);
            vertices.put(key, newVert);

            //by default the instructor is going to be connected to teh admin node
            Vertex adminNode = vertices.get("ADMIN");
            adminNode.connections.put(myUtils.cleanString(inInstrucor.getName()), newVert);
        }
    }

    public void addVertex(Student inStudent)
    {
        //can't add a vertex if they is no root node
        if(validateRootNode())
        {
            String key = myUtils.cleanString(inStudent.getName());
            Vertex newVert = new Vertex(key, inStudent);
            vertices.put(key, newVert);

            //by default the student must be connected to the root node
            Vertex adminNode = vertices.get("ADMIN");
            adminNode.connections.put(myUtils.cleanString(inStudent.getName()), newVert);
        }

    }

    //if we know the instructor we can connect the student to the instructor instead of making the
    //default connection
    public void addVertex(Student inStudent, String inInstructor)
    {
        //finding the vertex in the current vertices
        inInstructor = myUtils.cleanString(inInstructor);
        Vertex currInstructor = vertices.get(inInstructor);

        if (validateInstructor(currInstructor))
        {
            String studentName = myUtils.cleanString(inStudent.getName());
            Vertex currStudent = new Vertex(studentName, inStudent);
            currInstructor.connections.put(studentName, currStudent);
        }
    }

    public void addVertex(Admin inAdmin)
    {
        //they is going ot be null at the current location
        if(rootExists())
        {
            Vertex adminVert = new Vertex("ADMIN", inAdmin);
            vertices.replace("ADMIN", adminVert);
        }
    }

    //no arguments is going to assumme to get what is at the admin vertex
    public Vertex getVertex()
    {
        return vertices.get("ADMIN");
    }

    public Vertex getVertex(String inVertex)
    {
        //hashmap will throw an error if it doesn't exist in current table
        inVertex = myUtils.cleanString(inVertex);
        return vertices.get(inVertex);
    }

    public Vertex delVertex(String key)
    {
        Vertex delVert = null;
        key = myUtils.cleanString(key);
        //they is always going to be an admin key which exists, so when the vertices is empty
        //it actually only has one node, which is the admin. Which either is null or has something
        //in it, and since you can't delete the admin node this class should break
        if(vertices.size() == 1)
        {
            throw  new IllegalArgumentException("ERROR: no vertices have being added as yet: " + vertices.size());
        }

        key = myUtils.cleanString(key);
        Vertex currVert = vertices.get(key);

        if (currVert.connections.isEmpty())
        {
            //if the vertex is not connected to anything else, just remove the vertex
            delVert = vertices.remove(key)
        }
        else
        {
            //if the vertex is connected to other things, move all the connections to the
            //admin node
        }
        return delVert;
    }

    public HashMap<String, Vertex> getVertices()
    {
        return new HashMap<>(vertices);
    }

    /*public HashMap<String, Vertex> setVertics(HashMap<String, Vertex> inVertices)
    {
        //don't need to do any validation as the HashMap class will do validation for us
        vertices = inVertices;
    }*/

    public Vertex getRootNode()
    {
        //return new Vertex(rootNode);
        return new Vertex(vertices.get("ADMIN"));
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

    public HashMap<String, Vertex> getEdges(String nodeName)
    {
        nodeName = myUtils.cleanString(nodeName);
        Vertex currVertex = vertices.get(nodeName);
        return currVertex.connections;
    }

    private boolean validateInstructor(Vertex inVert)
    {
        boolean valid = true;
        String type = inVert.value.getType();

        if (!(type.equals("INSTRUCTOR")))
        {
            throw new IllegalArgumentException("ERROR: students can only be added to an instructor");
        }
        return valid;
    }
    private boolean validateRootNode()
    {
        boolean valid = true;
        //grab what is at the curren root place
        Vertex rootNode = vertices.get("ADMIN");
        if(rootNode == null)
        {
            throw new IllegalArgumentException("ERROR: they is admin, must create admin first");
        }
        return valid;
    }

    private boolean rootExists()
    {
        boolean valid = true;
        //if they is something here, it means that an admin node has already being created
        if(vertices.get("ADMIN") != null)
        {
            throw new IllegalArgumentException("ERORR: an admin already exist for this application");
        }

        return valid;
    }

    public String toString()
    {
        throw new IllegalArgumentException("ERROR: to be implemented");
    }
}
