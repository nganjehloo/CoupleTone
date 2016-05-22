package com.cse110.team36.coupletones.FireBase;

/**
 * Created by Duc Le on 5/21/2016.
 */
public class FBreg {
    private String SO_ID;
    private Boolean relationshipStatus;

    FBreg(){
        relationshipStatus = false;
        SO_ID = "NOID";
    }

    public void setID(String ID)
    {
        this.SO_ID = ID;
    }

    public String getID(){ return SO_ID;}

    public Boolean getRelationshipStatus(){ return relationshipStatus;}

    public void setStatus(Boolean status)
    {
        this.relationshipStatus = status;
    }
}
