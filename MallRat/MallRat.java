/**
 *
 * @author BLAZE
 */
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.FileNotFoundException;

public class MallRat {

    
    static private ArrayList<Item> items = new ArrayList<>();
    static private ArrayList<Tax> taxes = new ArrayList<>();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        //Dave has a Bachelors in reading user input
        Scanner dave = new Scanner(System.in);
        
        //Generate all the items stored
        ArrayList<String[]> lines = getData();
        
        
        for(String[] line : lines){
            if(line[0].equals("Item")){
                items.add(new Item(line[1],Double.valueOf(line[2])));
            }
            else if(line[0].equals("Tax")){
                taxes.add(new Tax(line[1],Double.valueOf(line[2])));
            }
        }
        
       
        
        String input = "";
        
        System.out.println("Mallrat Running...");
        while(!input.equals("exit")){
            System.out.println();
            input = dave.nextLine();
            
            //Keyword Check
            String[] splitInput = input.split(" ");
            switch (splitInput[0]){
                
                //Save
                case "save":
                    
                    
                    System.out.println("Saving...");
                    writeData();
                    System.out.println("Saved.");
                    break;
                //Exit
                    
                    
                case "exit":
                    System.out.println("Goodbye");
                    break;
                    
                case "print":
					//items
					if(splitInput[1].equals("items")){
						for(Item i : items){
							System.out.println(i.getName() + " $" + i.getPrice());
						}
					}
					else if(splitInput[1].equals("receipt")){
						double totalCost = 0;
						for(Item i : items){
							if(i.getQuantity() > 0){
								System.out.println(i.getQuantity() + " " + i.getName() + ": $" + i.getQuantity()*i.getPrice());
								totalCost += i.getQuantity()*i.getPrice();
							}
						}
						System.out.println("----------------------------");
						System.out.println("Total: $" + totalCost);
					}
					else{
						System.out.println("Incorrect Format (\"print\" (info))");
					}
                    break;
                    
                    
                //Add new item
                case "new":
                    try{
                        //New Item
                        if(splitInput[1].equals("Item")){
                        
                        
                            double cost = Double.valueOf(splitInput[3]);
                            cost = Math.round(cost * 100);
                            cost/=100;
                        
                        
                            items.add(new Item(splitInput[2], cost));
                            System.out.println("New Item Added");
                        }
                        else if(splitInput[1].equals("Tax")){
                            System.out.println("New tax");
                        }
                        else{
                            System.out.println("Incorrect Format (\"new\" (type) (name) (cost/rate))");
                        }
                    }
                    catch(Exception e){
                        System.out.println("Incorrect Format (\"new\" (type) (name) (cost/rate))");
                    }
                    break;
                    
                    
                    
                    
                //Dropping Item
                case "drop":
                    try{
                        if(splitInput[1].equals("Item")){
                            boolean foundItem = false;
                            //Check if user inputted * (clear)
                            if(splitInput[2].equals("*")){
                                items.clear();
                                System.out.println("Items Cleared");
                            }
                            else{
                                for(int i = 0; i < items.size(); i++){
                                    if(items.get(i).getName().equals(splitInput[2])){
                                        items.remove(i);
                                        foundItem = true;
                                        i--;
                                        System.out.println("Item Removed");
                                    }
                                }
                                if(!foundItem){
                                System.out.println("Invalid Name");
                                }
                            }
                        }
                        else if(splitInput[1].equals("Tax")){
                            System.out.println("Tax Removed");
                        }
                        else{
                            System.out.println("Incorrect Format (\"drop\" (type) (name))");
                        }
                    }
                    catch(Exception e){
                        System.out.println("Incorrect Format (\"drop\" (type) (name))");
                    }
                    break;
                
                
                
                case "change":
                    try{
                        //Search Items
                        if(splitInput[1].equals("Item")){
                            int index = 0;
                            boolean foundName = false;
                            for(int i = 0; i < items.size(); i++){
                                //Find item index
                                if(items.get(i).getName().equals(splitInput[2])){
                                    foundName = true;
                                    index = i;
                                }
                            }
                            if(foundName){
                                
                                switch(splitInput[3]){
                                    case "price":
                                        items.get(index).setPrice(Double.valueOf(splitInput[4]));
                                        break;
                                    case "name":
                                        items.get(index).setName(splitInput[4]);
                                        break;
                                    default:
                                        System.out.println("Invalid Variable");
                                        break;
                                }
                            
                            }
                            else{
                                System.out.println("Invalid Name");
                            }
                        }
                    }
                    catch(Exception e){
                        System.out.println("Incorrect format (\"change\" \"Item\" (Name) (Var) (Value))");
                    }
                    break;
                    
                //Arithmatic
                default:
                    //Check if user is trying to add or subtract
                    
                    if(splitInput[0].substring(0,1).equals("+") || splitInput[0].substring(0,1).equals("-")){
                        
						try{
                            int arthNum = Integer.valueOf(splitInput[0]);
                            
                            boolean foundName = false;
                            for(int i = 0; i < items.size(); i++){
                                if(items.get(i).getName().equals(splitInput[1])){
                                    items.get(i).setQuantity(items.get(i).getQuantity() + arthNum);
                                    foundName = true;
                                }
                            }
                            if(!foundName){
                                System.out.println("Invalid Name");
                            }
                        }
                        catch(Exception e){}
                    }
                    else{
                        try{
                            int quantity = Integer.valueOf(splitInput[0]);
                            
                            boolean foundName = false;
                            for(int i = 0; i < items.size(); i++){
                                if(items.get(i).getName().equals(splitInput[1])){
                                items.get(i).setQuantity(quantity);
                                foundName = true;
                                }  
                            }
                            if (!foundName){
                                System.out.println("Invalid Name");
                            }
                        }
                        catch(Exception e){}
                    }
                    break;
                    
            }
            
        }
    
    }         
    
    public static ArrayList getData(){
        //Amy is self taught, but quite capable at reading text files
        Scanner amy = null;
        
        
        File file = null;
        ArrayList<String[]> data = new ArrayList<>();
        
        try{
            file = new File("save_data.txt");
            amy = new Scanner(file);
            
            //Get all of the info that's inside the file
            //Organized like: type:name:(price/rate)
        
            while(amy.hasNextLine()){
                data.add(amy.nextLine().split(":"));
            }
            
        }
        catch(FileNotFoundException e){
            System.out.println("File 'save_data' unlocatable. Did you delete it?");
			System.out.println("Enter 'exit' and troubleshoot.");
        }
        finally{
            //End of work day!
            if(amy != null){
                amy.close();
            }
            
        }         
        return data;
    }
    
    /**
     * Writes the data save_data file
     */
    public static void writeData(){
        //Idk anything about Greg he just showed up :\ (he brings donuts on Tuesdays so it's fine)
        PrintWriter greg = null;
        
        try{
            FileOutputStream fileStream = new FileOutputStream("save_data.txt",false);
            greg = new PrintWriter(fileStream);
            
            
            for(Item i : items){
                greg.println("Item:" + i.getName() + ":" + i.getPrice());
            }
            for(Tax t : taxes){
                greg.println("Tax:" + t.getName() + ":" + t.getRate());
            }
        }
        catch(FileNotFoundException e){
            System.out.println("File 'save_data' unlocatable. Did you delete it?");
			System.out.println("Enter 'exit' and troubleshoot.");
        }
        finally{
            //End of work day!
            if(greg != null){
                greg.close();
            }
        }
    }
    
    
    
}