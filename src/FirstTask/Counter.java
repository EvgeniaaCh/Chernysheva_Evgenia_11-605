package FirstTask;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Dmitry on 10.06.2017.
 */
public class Counter {
    public static void main(String[] args) {
        Scanner fileReader = null;
        try {
            fileReader = new Scanner(new File("D:\\hackaton\\InformaticExam\\resources\\in.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Site> sites = new ArrayList<>();
        while (fileReader.hasNext()) {
            sites.add(newSite(fileReader));
        }
        writeData(sites);
    }

    private static Site newSite(Scanner fileReader){
        String inString = fileReader.nextLine();
        String[] inStrings = inString.split(" ");
        String siteName = inStrings[0];
        long usersCount = 0;
        for(int i=1;i<inStrings.length;i++){
            usersCount=usersCount+Long.parseLong(inStrings[i]);
        }
        usersCount=usersCount/7;
        return new Site(siteName, usersCount);
    }

    private static void writeData(ArrayList<Site> sites){
        try {
            FileWriter fileWriter = new FileWriter("D:\\hackaton\\InformaticExam\\resources\\out.txt");
            for(Site site:sites){
                fileWriter.write(site.getName()+" "+site.getUsersCount()+"\n");
                fileWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
