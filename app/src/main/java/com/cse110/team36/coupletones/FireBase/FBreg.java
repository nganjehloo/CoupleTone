package com.cse110.team36.coupletones.FireBase;

/**
 * Created by Duc Le on 5/21/2016.
 */
public class FBreg {
    String SO_ID;
    Boolean relationshipStatus;

    FBreg(){
        relationshipStatus = false;
        SO_ID = "NOSO";
    }

    public void setID(String ID)
    {
        this.SO_ID = ID;
    }

    public void setStatus(Boolean status)
    {
        this.relationshipStatus = status;
    }
}
