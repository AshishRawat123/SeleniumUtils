package com.Utilities;

import java.io.File;

import resource.Contants;

public class NSUtils {

	public static boolean is_Downloaded(String path, String File_Name) {

		path = (path==null)?Contants.Download_Folder:path;
		File file = new File(path);
		String[] fileList = file.list();
		for(String filez : fileList) {
			if(filez.equalsIgnoreCase(File_Name)) {
				//Delete File for future purpose
				new File(path+"//"+filez).delete();
				return true;
			}
		}
		return false;
	}
}
