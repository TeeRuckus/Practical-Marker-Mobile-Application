package com.example.assone;

public class Graph
{
    //classfields for the outer class which we can use
    private Hashtable<String, Vertex> vertices;
    private int size;
    //this is going to be the admin of the whole applicatio
    private Vertex rootNode;

    private class Vertex()
    {
        //using protected, so I can edit the class fields directly from parent class and avoids using accessors and mutators
        protected String key;
        protected User value;
        protected Hashtable<String, Vertex> connections;

        private Vertex()
        {

        }
    }

    public Graph()
    {
        vertices = new Hashtable<String, Vertex>();
        size = 0;
        rootNone = null;
    }


    public void addVertex(String key, Vertex value)
    {
    }

    public  void delVertex(String key)
    {

    }

    public void addEdge(String fromEdge, String toEdge)
    {

    }

    public delEdge(Setring fromEdge, String toEdge)
    {

    }

    public getEdges(String nodeName)
    {

    }

}
