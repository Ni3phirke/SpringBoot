package com.smartbank.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class FileUtil 
{

	public static void AppendLin(Path p,String line) 
	{
		try {
			Files.createDirectories(p.getParent());
			Files.writeString(p, line+ System.lineSeparator(),StandardOpenOption.CREATE,StandardOpenOption.APPEND);
		}catch (IOException e) {
		 System.out.println("Failed to write to file "+e.getMessage());
		}
		
	}
}
