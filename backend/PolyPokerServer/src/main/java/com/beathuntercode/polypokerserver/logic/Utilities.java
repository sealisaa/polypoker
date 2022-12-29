package com.beathuntercode.polypokerserver.logic;

public class Utilities {
    public static int getRndIntInRange(int min, int max){
        return (int) (Math.random()*((max-min)+1))+min;
    }
}
