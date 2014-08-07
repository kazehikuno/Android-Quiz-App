package com.softwarezygote.waterproject.app;

/**
 * Created by Hays on 5/28/2014.
 * A class to maintain a single question as an object.
 */

public class Question {

    // private variables
    private int id;
    private int ans;
    private String ques;
    private String A;
    private String B;
    private String C;
    private String D;

    // constructors
    public Question(){

    }
    public Question(int pid, int pAns, String pQues, String pA,
                     String pB, String pC, String pD){
        this.id = pid;
        this.ans = pAns;
        this.ques = pQues;
        this.A = pA;
        this.B = pB;
        this.C = pC;
        this.D = pD;
    }

    // getters and setters
    public int getID(){
        return this.id;
    }
    public void setID(int pid){
        this.id = pid;
    }

    public int getAns(){
        return this.ans;
    }
    public void setAns(int pAns){
        this.ans = pAns;
    }

    public String getQues(){
        return this.ques;
    }
    public void setQues(String pQues){
        this.ques = pQues;
    }

    public String getA(){
        return this.A;
    }
    public void setA(String pA){
        this.A = pA;
    }

    public String getB(){
        return this.B;
    }
    public void setB(String pB){
        this.B = pB;
    }

    public String getC(){
        return this.C;
    }
    public void setC(String pC){
        this.C = pC;
    }

    public String getD(){
        return this.D;
    }
    public void setD(String pD){
        this.D = pD;
    }
}
