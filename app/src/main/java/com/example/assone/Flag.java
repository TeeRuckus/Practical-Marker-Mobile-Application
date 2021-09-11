package com.example.assone;

import java.io.Serializable;

public class Flag implements Serializable
{
    private String name;
    private int image;

    public Flag()
    {
        name = "unknown";
        image = 0000;
    }

    public Flag(String inName, int inInt)
    {
        this.name = inName;
        this.image = inInt;
    }

    public String getName()
    {
        return new String(name);
    }

    public int getImage()
    {
        return image;
    }

    /*
    don't really need to do validation here because the flags will always be
    selected from a drop down menu hence, the flags will always be correct
     */
    public void setName(String inName)
    {
        this.name = name;
    }

    public void setImage(int Image)
    {
        this.image = image;
    }

}
