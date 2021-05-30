package org.iqa.suite.commons.mobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class AdbOperations {
	
	public static String installApplication(String strFullApkFileName){
		return commandExecutor("adb install "+"\""+strFullApkFileName+"\"");  //need to test this line of code on unix system once
	}
	
	public static String unInstallApplication(String strPackageName){
		return commandExecutor("adb uninstall "+"\""+strPackageName+"\""); //need to test this line of code on unix system once
	}
	
	private static String commandExecutor(String strCommand){

		String line,consoleOut="";
		
		try {
			Process process = Runtime.getRuntime().exec(strCommand);			
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while((line=br.readLine())!=null){
				consoleOut= consoleOut+line;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception in executing command - "+e.getMessage()); //logger needs to be implemented
			consoleOut = null;
		}
		return consoleOut;
	}

}
