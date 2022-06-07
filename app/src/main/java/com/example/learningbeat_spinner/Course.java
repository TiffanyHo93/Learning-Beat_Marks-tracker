package com.example.learningbeat_spinner;

public class Course {
    //Define variables to store the information for Course object
    public long id;
    public String sName = "";
    public String sStartDate = "";
    public String sEndDate = "";
    public double dTargetPoint = 0.0;
    public double dAs1 = 0.0;
    public double dAs2 = 0.0;
    public double dAs3 = 0.0;
    public double dMid = 0.0;
    public double dFinal = 0.0;
    public double dTotal = 0.0;

    //Define the construction for Course with id variable
    public Course(long id, String sName, String sStartDate, String sEndDate, double dTargetPoint,
                  double dAs1Point, double dAs2Point, double dAs3Point, double dMid,
                  double dFinal) {
        this.id = id;
        this.sName = sName;
        this.sStartDate = sStartDate;
        this.sEndDate = sEndDate;
        this.dTargetPoint = dTargetPoint;
        this.dAs1 = dAs1Point;
        this.dAs2 = dAs2Point;
        this.dAs3 = dAs3Point;
        this.dMid = dMid;
        this.dFinal = dFinal;
        this.dTotal = setTotal();
    }

    //Define the construction for Course without id variable
    public Course(String sName, String sStartDate, String sEndDate, double dTargetPoint,
                  double dAs1Point, double dAs2Point, double dAs3Point, double dMid,
                  double dFinalEx) {
        this.id = -1;
        this.sName = sName;
        this.sStartDate = sStartDate;
        this.sEndDate = sEndDate;
        this.dTargetPoint = dTargetPoint;
        this.dAs1 = dAs1Point;
        this.dAs2 = dAs2Point;
        this.dAs3 = dAs3Point;
        this.dMid = dMid;
        this.dFinal = dFinalEx;
        this.dTotal = setTotal();
    }

    //Create setTotal method to set the total marks
    public double setTotal()
    {
        return this.dTotal = this.dAs1/100*5 + this.dAs2/100*10 + this.dAs3/100*15 + this.dMid/100*30 +
                        this.dFinal/100*40;
    }

    //Override toString method to display the course's name
    @Override
    public String toString() {
        return this.sName;
    }
}
