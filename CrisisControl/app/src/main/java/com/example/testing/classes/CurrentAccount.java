package com.example.testing.classes;

public class CurrentAccount {
    public static final int GUEST_TYPE=-1,NGO_TYPE = 2, ADMIN_TYPE = 0;
    private static int currentAccID;
    private static int accType;
    private static CurrentAccount instance;

    public static CurrentAccount getInstance(){
       return instance;

    }

    public static void initInstance(int currID, int type){
        if(instance==null) {
            currentAccID = currID;
            accType = type;
        }
    }
    public static int getCurrentAccID() {
        return currentAccID;
    }

    public static int getAccType(){return accType;}
}
