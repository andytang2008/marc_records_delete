package com;
import java.io.*;
import java.util.ArrayList;
import java.util.*;
public class TestTest2 {
    public String cal(String listfilename,String filename, String pfilename){

    // The name of the file to open.
   /** String targetFileName = "marc.mrk";
	String sourceFileName= "fieldlist.txt";*/
	//String sourceFileName = listfilename;
	
	//String sourceFileName="C:/ApacheTomcat9/customer_data/fieldlist.txt";
	String sourceFileName = listfilename;
	String targetFileName = filename;
	String pfileName = pfilename;


    // This will reference one line at a time
    String line = null;
	
	    try {
			
				BufferedReader inSource = new BufferedReader(new FileReader(sourceFileName));
				String strSource;

				List<String> listSource = new ArrayList<String>();
				while((strSource = inSource.readLine()) != null){
					listSource.add("=001  "+strSource); //adding =001 to garantee that later comparison proceeds smoothly.
				}
				
				//System.out.println(Arrays.toString(listSource));
				
				//System.out.println(Arrays.toString(listSource.toArray()));
				   inSource.close();
               
			
				
			//System.out.println(Arrays.toString(listSource.toArray()));	
				

					try {
						
						System.out.println(Arrays.toString(listSource.toArray()));					
								BufferedReader in = new BufferedReader(new FileReader(targetFileName));
								String str;

								List<String> list = new ArrayList<String>();
								while((str = in.readLine()) != null){
									list.add(str);
								}
								
								// for (String s : list) { System.out.println(s); } //print line by line
								
				List<String> IntermediateLlist = new ArrayList<String>();
				List<String> FinalList = new ArrayList<String>();
								String[] stringArr = list.toArray(new String[0]);
								//String LDRstring="";

								for (int i=0; i<stringArr.length; i++){
									if (stringArr[i].length()!=0){
										IntermediateLlist.add(stringArr[i]);
										
									}
									else{
									Collections.sort(IntermediateLlist);
										if(IntermediateLlist.size()>0){ //to prevent there are 2 empty lines continuously.
											//Collections.swap(IntermediateLlist, 0, IntermediateLlist.size() - 1); //swap the first field and last field.
											//IntermediateLlist.get(IntermediateLlist.size() - 1)
											IntermediateLlist.add(0, IntermediateLlist.get(IntermediateLlist.size() - 1)); //add the last element to 1st location
											IntermediateLlist.remove(IntermediateLlist.size() - 1); // delete the last element of arraylist.
																					//System.out.println(Arrays.toString(listSource.toArray()));
																					System.out.println(IntermediateLlist.get(1));
											if (listSource.contains(IntermediateLlist.get(1))){
													
												}else{
												FinalList.addAll(IntermediateLlist);
												FinalList.add("");
												}
											IntermediateLlist.clear(); //everytime, clear list of IntermediateLlist
											}
													
											
											
									}
										

										
								}
								//System.out.println(Arrays.toString(FinalList.toArray()));

								
								String[] FinalListtoArray = FinalList.toArray(new String[0]); //we have to convert list Marc to array before export them to a file.
								FileWriter fwfirsttime = new FileWriter(pfileName); //write array to a file named file.dat
								for (int i = 0; i < FinalListtoArray.length; i++) {
								  //fw.write(stringArr[i] + "\n");
								  fwfirsttime.write(FinalListtoArray[i] + "\r\n");
								}
								fwfirsttime.close();
							
									

							
								   in.close();

					}
					catch(FileNotFoundException ex) {
						System.out.println(
							"Unable to open file '" + 
							targetFileName + "'");                
					}
					catch(IOException ex) {
						System.out.println(
							"Error reading file '" 
							+ targetFileName + "'");                  
					  }
			  
   			}
			
			catch(FileNotFoundException ex) {
				System.out.println(
					"Unable to open file '" + 
					sourceFileName + "'");                
			}
			catch(IOException ex) {
				System.out.println(
					"Error reading file '" 
					+ sourceFileName + "'");   

			}
			return "Successful!";
	}
}