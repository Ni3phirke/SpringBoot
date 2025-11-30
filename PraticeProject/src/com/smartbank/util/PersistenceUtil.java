package com.smartbank.util;

import java.io.*;

public class PersistenceUtil {

	public static void saveBankData(BankData data,File file) throws FileNotFoundException, IOException
	{
		 File parent =file.getParentFile();
		 if(parent!=null) parent.mkdir();
		 
		 try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(file))){
			  oos.writeObject(data);
	            oos.flush();
		 };
	}
	
	public static BankData loadFile(File file) throws IOException, ClassNotFoundException 
	{
		if(!file.exists()) return null;
		
		try(ObjectInputStream ois =new  ObjectInputStream(new FileInputStream(file))){
			Object o=ois.readObject();
			if(o instanceof BankData) {
				return (BankData)o;
			}else {
				  throw new IOException("Invalid data in persistence file");
			}
		}
	}
	  

}
