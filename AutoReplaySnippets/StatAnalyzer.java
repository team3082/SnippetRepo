package frc.robot;

import java.util.ArrayList;

// util 
import frc.lib.Stat;

public class StatAnalyzer {

    public static void PrintParsedData (Stat stat, String lsName) {

        ArrayList<Object> data = stat.getDataList();

        for(int i=0; i < data.size(); i++){
			
            System.out.println(lsName + ".add(" + data.get(i) + ");"); 
        
        }

    }

    public static void Space () {

        System.out.println("                                            "); 

    }

}