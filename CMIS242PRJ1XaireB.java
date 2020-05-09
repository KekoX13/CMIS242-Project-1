/* File Name: BorjaXaireP1 
* Author: Borja X13
* Date: 25 Mar 2020
* UMGC CMIS 242
* Project 1
*/

/**
 * Purpose:
 * This program will generate a window via JFileChooser that allows the user to
 * select a file from which to read. It will then read a file of weights (pounds, 
 * ounces) and find the smallest, the largest and the average of the weights in 
 * the given file.  
 */

package cmis242prj1xaireb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;


public class CMIS242PRJ1XaireB {

    public static class Weight {
        
       //instance variables
	private int pounds;
	private int ounces;
	private final int OZ_TO_LB = 16; 
        
        //constructor that allows pounds and ounces to be initiated
	public Weight(int pounds, int ounces) {
            this.pounds = pounds;
            this.ounces = ounces;
	}//end public Weight(int pounds, int ounces)
        
        	//public method named lessThan, accepts one weight as parameter returns 
        //boolean if object invoked is less than weight in the parameter
	public boolean lessThan(Weight testWeight) {
            return ((this.pounds * OZ_TO_LB)+(this.ounces)) < 
                    ((testWeight.pounds * OZ_TO_LB)+(testWeight.ounces));
	}//end public boolean lessThan(Weight testWeight)
        
        	//public method named addTo, accepts one weight as parameter and adds it 
        //to weight object in which it is invoked. normalize the result
	public void addTo(Weight addWeight) {
            this.pounds += addWeight.pounds;
            this.ounces += addWeight.ounces;
            this.normalize();
	}//end public void addTo(Weight testWeight)
        
        	//public method named divide, divides a Weight object by the integer 
        //divisor given as a parameter
	public void divide(Weight dividend, int divisor) {
            //create an if statement to verify that there is something to divide 
            //by. So if there were no valid weights on the file, this code would
            //return and error message.
            if ((divisor) != 0) {             
                dividend.pounds *= OZ_TO_LB;
                dividend.pounds += dividend.ounces;
                dividend.ounces = dividend.pounds % (divisor * OZ_TO_LB);
                dividend.pounds /= (divisor * OZ_TO_LB);
                dividend.ounces /= divisor;
		dividend.normalize();
            }else{
                System.out.println("Invalid divisor provided");
            }//end if/else ensuring a valid divisor is given
	}//end public void divide(Weight dividend, int divisor)
        
        //toString method. the method will also set the ounces to a three place 
        //decimal as per instructions
        public String toString() {
		return this.pounds + " lbs " + ounces + " oz";
	}//end toString
        
        //toOunces method to change values into ounces
	private int toOunces(){
            if(ounces % 625 != 0){
                return -1;
            }else{
                return ounces /= 625;
            }//end if
	}//end private void toOunces()
        
        	//normalize method to change ounces to lbs and oz 
	private void normalize() {
            if (ounces>OZ_TO_LB) {
                this.pounds += this.ounces/OZ_TO_LB;
                this.ounces = this.ounces % OZ_TO_LB;
		}//end if
	}//end void normalize()
        
    }//end public class Weight
	
    public static void main(String[] args) throws FileNotFoundException {
        
        int pounds;
        int ounces;
        int counter = 0;
        ArrayList <Weight> weightArray = new ArrayList<>();
		
        //the JFileChooser allows for the user to select a file to process. 
        //This opens the dialog box.
        final JFileChooser jfc = new JFileChooser();
	int returnValue = jfc.showOpenDialog(null);
	File selectedFile;
		
            if(returnValue==JFileChooser.APPROVE_OPTION) {
		selectedFile=jfc.getSelectedFile();
		
            //the scanner reads the selected file
            Scanner scn = new Scanner(selectedFile);	
                while (scn.hasNext()){
        		//tests to see if there are too many weights in the file
                    if(counter>=25) {
                        System.out.println("\nThat list of weights exceeds the "
                            + "array's max capacity... Goodbye");
                        System.exit(0);
                    }else{	
                         //reads each line and tokenizes each item so that they 
                         //can be parsed to integers
                         String stringRead = scn.nextLine();                        
                         StringTokenizer st = new StringTokenizer(stringRead, ", " );   
                         pounds = Integer.parseInt(st.nextToken());                   
                         ounces = Integer.parseInt(st.nextToken());
                         //create a weight object that will be added to the arrayList
                         Weight weightIn = new Weight(pounds, ounces);
                         //stores each Weight object created into an index of the array                
                         weightIn.toOunces();                      
                         weightArray.add(weightIn); 
                          counter++;
                     }//end if/else
                }//end while
			scn.close();
            }//end if(returnValue==JFileChooser.APPROVE_OPTION)
            //call the find minimum, maximum and average methods
            System.out.println(findMinimum(weightArray));
            System.out.println(findMaximum(weightArray));
            System.out.println(findAverage(weightArray));
    }//end public static void main(String[] args) throws FileNotFoundException	
	
	//method to find the smallest weight in the file
    private static String findMinimum(ArrayList <Weight> weightArray){
		
	Weight minimumWeight = weightArray.get(0);
            for(int i = 0; i < weightArray.size(); i++) {
		if(weightArray.get(i).lessThan(minimumWeight)) {
                    minimumWeight = weightArray.get(i);
                }//end if
            }//end for loop
		return "The minimum weight is " + minimumWeight;
    }//end private static String findMinimum(ArrayList <Weight> weightArray)
	
	//method to find the largest weight in the file
    private static String findMaximum(ArrayList <Weight> weightArray){
		
	Weight maximumWeight = weightArray.get(0);
            for(int i = 0; i < weightArray.size(); i++) {
		if(!weightArray.get(i).lessThan(maximumWeight)) {
                    maximumWeight = weightArray.get(i);
		}//end if
            }//end for loop
		return "The maximum weight is " + maximumWeight;
    }//end private static String findMaximum(ArrayList <Weight> weightArray)
	
	//method to find the average of the weights in the file
    private static String findAverage(ArrayList <Weight> weightArray){
        
	//create a divisor integer to use in calculating the average
        int divisor = 0;
        //create a new weight object to find the average	
	Weight averageWeight = new Weight(0,0);
        
        //create a for loop to add the weight objects in the arraylist
        for(int i = 0; i < weightArray.size(); i++) {
            //make an if statement that will ensure the weight being added 
            //is a valid weight.
            if(weightArray.get(i).ounces > 15){
                System.out.println("* " + weightArray.get(i) + 
                " was not added because it is not a valid weight");
            }else{
		averageWeight.addTo(weightArray.get(i));
                divisor ++;
                }//end if(weightArray.get(i).ounces > 15)
            }//end for loop
		averageWeight.divide(averageWeight, divisor);
		return "The average weight is " + averageWeight;
	}//end private static String findAverage(Weight [] weightArray)
}//end public class CMIS242PRJ1XaireB
