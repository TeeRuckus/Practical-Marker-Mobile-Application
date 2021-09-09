/*
TODO:
    - you will need ot implement a function which is going to go through all the pracitcals
    and find out what your mark was for that practical. I.e. what percentage you actuall got for that
    week
    - I am also really considiring chaing the scoredMarks from a float to a double
    - I should also be able to find out how much they scored in each section from this class whcih I am
    making at the current moment
 */
package com.example.assone;

import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Set;

public class Practical {
    public class taskNode
    {

        /*inner class class fields. Making classfields protected to avoid making accessors and
        mutators as the practical class in the only calss to use use this inner class
         */
        protected String taskTitle;
        protected String taskDescrpt;
        protected float scoredMarks;
        protected int availMarks;

        //DEFUAULT CONSTRUCTOR
        public taskNode()
        {
            taskTitle = "task sample";
            taskDescrpt = "description of the task which has to be completed";
            scoredMarks = 0;
            availMarks = 10;

        }

        //ALTERNATE CONSTRUCTOR
        public taskNode(String inTitle, String inDescrpt, float inScore, int inAvailMarks)
        {
            if (validateTitle(inTitle) && validateDescrpt(inDescrpt) && validateMark(inAvailMarks))
                {
                            taskTitle = inTitle;
                            taskDescrpt = inDescrpt;
                            availMarks = inAvailMarks;
                            //we can only validate teh scored marks, once a mark has being set in the class,
                            //as wee need to compare if the scored marks is going to be greater than
                            //the marks which are available
                            if (validateScoredMarks(inScore))
                            {
                                scoredMarks = inScore;
                            }
                }
        }

        //COPY CONSTRUCTOR
        public taskNode(taskNode inTaskNode)
        {
            taskTitle = inTaskNode.taskTitle;
            taskDescrpt = inTaskNode.taskDescrpt;
            scoredMarks = inTaskNode.scoredMarks;
            availMarks = inTaskNode.availMarks;
        }

        public taskNode clone()
        {
            return new taskNode(this);
        }

        //ACCESSORS
        public String getTaskTitle()
        {
            return new String(taskTitle);
        }

        public String getTaskDescrpt()
        {
            return new String(taskDescrpt);
        }

        public float getScoredMarks()
        {
            return scoredMarks;
        }

        public int getAvailMarks()
        {
            return availMarks;
        }

        //MUTATORS
        public void setTaskTitle(String inTitle)
        {
            if ( validateTitle(inTitle))
            {
                taskTitle = inTitle;
            }
        }

        public void setTaskDescrpt(String inDescrpt)
        {
            if ( validateDescrpt(inDescrpt))
            {
                taskDescrpt = inDescrpt;
            }
        }

        public void setScoredMarks(float inScoredMarks)
        {
            if( validateScoredMarks(inScoredMarks))
            {
                scoredMarks = inScoredMarks;
            }
        }

        public void setAvailMarks(int inMark)
        {
            if(validateMark(inMark))
            {
                availMarks = inMark;
            }
        }

        private boolean validateScoredMarks(float inMarks)
        {
            boolean valid = true;

            //comaprisons must be made on the same data type
            float currAvailMarks = (float) availMarks;

            if (inMarks > currAvailMarks)
            {
                throw new IllegalArgumentException("Error: maximum marks allowed for this section:"+
                        availMarks);
            }

            return valid;
        }

    }

    //class fields for the outer class
    private String title;
    private String descrpt;
    private Hashtable<String, taskNode> marks;
    private float totalMarks;

    //default constructor
    public Practical()
    {
        title = "Practical title";
        descrpt = "this is a description of the class";
        marks = new Hashtable<String, taskNode>();
        totalMarks = 100;
    }

    //ALTERNATE CONSTRUCTOR
    public Practical(String inTitle, String inDescrpt, float inMarks)
    {
        if( validateTitle(inTitle) && validateDescrpt(inDescrpt) && validateMark(inMarks))
        {
            title = inTitle;
            descrpt = inDescrpt;
            totalMarks = inMarks;
        }

    }


    //COPY CONSTRUCTOR
    public Practical(Practical inPrac)
    {
        title = inPrac.getTitle();
        descrpt = inPrac.getdescrp();
        marks = inPrac.getMarks();
        totalMarks = inPrac.getTotalMark();

    }

    //ACCESSORS
    public String getTitle()
    {
        return new String(title);
    }

    public String getdescrp()
    {
        return new String(descrpt);
    }

    public Hashtable<String, taskNode>  getMarks()
    {
        return new Hashtable<String, taskNode>(marks);
    }

    public float getTotalMark()
    {
        return totalMarks;
    }

    //MUTATORS

    public void setTitle(String inTitle)
    {
        if(validateTitle(inTitle))
        {
            title = inTitle;
        }
    }

    public void setDescrpt(String inDescrpt)
    {
        if(validateDescrpt(inDescrpt))
        {
            descrpt = inDescrpt;
        }
    }

    public void setmarks(Hashtable<String, taskNode> inMarks)
    {
        //this data should have already being validated, so no need to validate again
        marks = inMarks;
    }

    public void setTotalMarks(float inMark)
    {
        if(validateMark(inMark))
        {
            totalMarks = inMark;
        }
    }

    //METHODS IN WHICH WE CAN CHANGE THE STATE OF THE TASKNODE ITSELF
    public void addSection(String inTitle, String inDescrpt, float inScore, int inAvailMarks)
    {
        inTitle = myUtils.cleanString(inTitle);
        taskNode newNode = new taskNode(inTitle, inDescrpt, inScore, inAvailMarks);
        marks.put(inTitle, newNode);
    }

    public taskNode delSection(String inKey)
    {
        if(marks.isEmpty())
        {
            throw new IllegalArgumentException("ERROR: can't delete from an empty mark section");
        }
        inKey = myUtils.cleanString(inKey);
        taskNode removedObj = marks.remove(inKey);

        //we should return what was deleted
        return removedObj;
    }

    public taskNode findSection(String inKey)
    {
        inKey = myUtils.cleanString(inKey);
        //this will throw its own exception if the key doesn't exist in the marks hashtable
        return marks.get(inKey);
    }

    public float getAverage()
    {
        float average = 0;
        int  totalScore = 0;
        Set<String> myKeys = marks.keySet();

        for (String currKey: myKeys)
        {
            taskNode currNode = marks.get(currKey);
            average += currNode.getScoredMarks();
            totalScore += currNode.getAvailMarks();
        }
        return average / (float) totalScore;
    }

    public String toString()
    {
        return "";
    }

    protected boolean validateTitle(String inTitle)
    {

        boolean valid = true;
        if( inTitle.length() == 0)
        {
            throw new IllegalArgumentException("Error: invalid name: " + inTitle);
        }

        return valid;
    }

    protected boolean validateDescrpt(String inDescrpt)
    {
        boolean valid = true;

        if (inDescrpt.length() == 0)
        {
            throw new IllegalArgumentException("Error: short description is empty: " + inDescrpt);
        }

        int  words = countWords(inDescrpt);
        if(words < 50)
        {
            throw new IllegalArgumentException("Error: Short description must be minimum of 200 words"+
                    " current word count: " + words);
        }

        return valid;
    }

    protected boolean validateMark(float inMark)
    {
        boolean valid = true;

        if(inMark <= 0)
        {
            throw new IllegalArgumentException("Error: must enter a positive mark: " + inMark);
        }

        return valid;
    }

    protected boolean validateMark(int inMark)
    {
        boolean valid = true;

        if(inMark <= 0)
        {
            throw new IllegalArgumentException("Error: must enter a positive mark: " + inMark);
        }

        return valid;
    }



    protected int countWords(String inPara)
    {
        //each word is going to be seperated by white spaces
        String [] words = inPara.split(" ");
        return words.length;
    }

}
