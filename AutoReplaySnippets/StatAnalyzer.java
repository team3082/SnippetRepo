package frc.lib;

import java.util.ArrayList;

// util 
import frc.lib.Stat;

public class StatAnalyzer {

    public static void PrintParsedData (Stat stat) {

        ArrayList<Object> data = stat.getDataList();

        for(int i=0; i < data.size(); i++){
			
            System.out.println(stat.m_title + ".add(" + data.get(i) + ");"); 
        
        }

    }

    public static void PrintParsedData (Stat stat, String title) {

        ArrayList<Object> data = stat.getDataList();

        for(int i=0; i < data.size(); i++){
			
            System.out.println(title + ".add(" + data.get(i) + ");"); 
        
        }

    }

}