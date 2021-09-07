package com.example.assone;

public class Practical {

    //class fields for the outer class
    private String title;
    private String descrpt;
    private taskNode[] marks;
    private float totalMarks;

    //private inner class only which practical can use
    private class taskNode
    {

        /*inner class class fields. Making classfields protected to avoid making accessors and
        mutators as the practical class in the only calss to use use this inner class
         */
        protected String taskTitle;
        protected String taskDescpt;
        protected float scoredMarks;
        protected int availMarks;

        //DEFUAULT CONSTRUCTOR
        private taskNode();
        {
            taskTitle = "task sample";
            taskDescrpt = "description of the task which has to be completed";
            scoredMarks = 0.0;
            availMarks = 10;

        }

        //ALTERNATE CONSTRUCTOR
        private taskNode(String inTitle, String inDescrpt, float inScore, int inAvailMarks)
        {
            if (validateTitle(inTitle) && validateDescrpt(inDescrpt))
                {
                    if(validateScoredMarks(inScore) && validateMark(inAvailMarks))
                        {
                            taskTitle = inTitle;
                            taskDescrpt = inDescrpt;
                            scoredMarks = inScore;
                            availMarks = inAvailMarks;
                    }
                }
        }

        protected taskNode clone()
        {
            return new taskNode(this);
        }

        protected boolean validateScoredMarks(float inMarks)
        {
            boolean valid = true;

            //comaprisons must be made on the dame data type
            currAvailMarks = float(availMarks);

            if (inMarks > currAvailMarks)
            {
                throw new IllegalArgumentException("Error: maxiumum marks allwoed for this section:"+
                        availMarks.toString());
            }

            return valid;
        }

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

    public taskNode[] getMarks;
    {
        return marks.clone();
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

    public void setmarks(taskNode[] inMarks)
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

    protected boolean validateTitle(String inTitle)
    {

        boolean valid = true;
        if( inTitle.length() == 0)
        {
            throw new IllegalArgumentExcepiton("Error: invalid name: " + inTitle);
        }

        return valid;
    }

    protected boolean validateDescrpt(String inDescrpt)
    {
        boolean valid = true;

        if (inDescrpt.length == 0)
        {
            throw new IllegalArgumentException("Error: short description is empty: " + inDescrpt);
        }

        int  words = countWords(inDescrpt);
        if(words < 200)
        {
            throw new IllegalArgumentException("Error: Short description must be minimum of 200 words"+
                    " current word count: " + words.toString);
        }

        return valid;
    }

    protected boolean validateMark(float inMark)
    {
        boolean valid = true;

        if(inMark < 0)
        {
            throw new IllegalArgumentException("Error: must enter a positive mark: " + inMark);
        }

        return valid; ]
    }

    protected int countWords(String inPara)
    {
        //each word is going to be seperated by white spaces
        String [] words = inPara.split(" ");
        return words.length;
    }

}
