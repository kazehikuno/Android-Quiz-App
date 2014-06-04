package com.softwarezygote.waterproject.app;

/**
 * Created by Hays on 5/28/2014.
 * A class to maintain a single question as an object.
 */
public class Question {

     // private variables
    int id;
    int ans;
    String ques;
    String A;
    String B;
    String C;
    String D;

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
    public Question(int pAns, String pQues, String pA,
                    String pB, String pC, String pD){
        this.ans = pAns;
        this.ques = pQues;
        this.A = pA;
        this.B = pB;
        this.C = pC;
        this.D = pD;
    }


    // getters and setters
    // getting ID
    public int getID(){
        return this.id;
    }
    // setting id
    public void setID(int pid){
        this.id = pid;
    }

    // getting ans
    public int getAns(){
        return this.ans;
    }
    // setting ans
    public void setAns(int pAns){
        this.ans = pAns;
    }

    // getting ques
    public String getQues(){
        return this.ques;
    }
    // setting ques
    public void setQues(String pQues){
        this.ques = pQues;
    }

    // getting choice A
    public String getA(){
        return this.A;
    }
    // setting choice A
    public void setA(String pA){
        this.A = pA;
    }

    // getting choice B
    public String getB(){
        return this.B;
    }
    // setting choice B
    public void setB(String pB){
        this.B = pB;
    }

    // getting choice C
    public String getC(){
        return this.C;
    }
    // setting choice C
    public void setC(String pC){
        this.C = pC;
    }

    // getting choice D
    public String getD(){
        return this.D;
    }
    // setting choice D
    public void setD(String pD){
        this.D = pD;
    }
}
