package com.rw.directories;

public class BooleanTransformer {

    public Boolean transformToBoolean(String detector) {

        if (detector.trim().equals("1")) {
            return true;
        } else{
            return false;
        }

    }

    public String transformToChar(boolean bool){

        if (bool){
            return "1";
        }else{
            return "0";
        }
    }

}
