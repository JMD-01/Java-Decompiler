package com.iconictech;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Scan {

	public static void Scan(ArrayList<String> Methods, ArrayList<String> JarPaths) throws IllegalAccessException, ClassNotFoundException, IOException {
		for(String jarpathstring : JarPaths) {
			ArrayList<String> JarData = Decompile.DecompileJar(jarpathstring); 
				for(String data : JarData) {
					ArrayList<String> SplitArray = new ArrayList<>(
					Arrays.asList(data.split("\n")));
					String ClassName = SplitArray.get(0);
					String PackageName = SplitArray.get(1);
					String JarPath = SplitArray.get(2);
					SplitArray.remove(2);
					SplitArray.remove(1);
					SplitArray.remove(0);
					StringBuilder sb = new StringBuilder();
					for (String s : SplitArray)
					{
					    sb.append(s);
					    sb.append("\n");
					}
					String ClassSourceCode = sb.toString();
					ScanMethod(ClassSourceCode, Methods, ClassName, PackageName,JarPath);
					ArrayList<String> TempDecompiled = new ArrayList<String>();
					TempDecompiled.clear();
					TempDecompiled.add(ClassSourceCode);
					TempDecompiled.add(ClassName);
					TempDecompiled.add(PackageName);
					TempDecompiled.add(JarPath);
					Decompile.DecompiledData.add(TempDecompiled);
				}	
		}
		
	}
	public static void ScanMethod(String classSourceCode, ArrayList<String> Methods, String className, String packageName, String jarPath) {
		for(String checkmethods : Methods) {
			ArrayList<String> tempstring = new ArrayList<>(
					Arrays.asList(checkmethods.split("-")));
			String method = tempstring.get(0);
			String searchstring = tempstring.get(1);
			//Check entire source for method before for looping and wasting resources to find exact line
			StringBuffer s = new StringBuffer(classSourceCode); 
			StringBuffer sd = new StringBuffer(searchstring.trim());
			if(s.indexOf(sd.toString()) > -1) {
				ArrayList<String> SplitArray = new ArrayList<>(
						Arrays.asList(classSourceCode.split("\n")));
				int count = 0;
				for (String string : SplitArray) {
					count++;
					StringBuffer ss = new StringBuffer(string);
		               if(ss.indexOf(sd.toString()) > -1){
		            	  String outputstring = "Method: "+method+"\n"+
		            			  				className+"\n"+
		            			  				packageName+"\n"+
		            			  				jarPath+"\n"+
		            			  				"Found: "+string+"\n"+
		            			  				"Line: "+count+"\n\n";
		            	  gui.textarea.setText(gui.textarea.getText()+outputstring);
		               }
		           }
			}
		}
	}
}
