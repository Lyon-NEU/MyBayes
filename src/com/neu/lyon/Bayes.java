package com.neu.lyon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A simple implement of Bayes
 * @author Lyon
 * @date 2014/06/25
 */
public class Bayes {
	private Set<String>stopword;
	private List<String>categories;
	public Bayes()
	{
		stopword=new HashSet<String>();
		categories=new ArrayList<String>();
	}
	public void train()
	{
		
	}
	/**
	 * read stopword list
	 * @param filename
	 */
	public void readStopword(String filename){
		BufferedReader in=null;
		try{
			in=new BufferedReader(new FileReader(new File(filename)));
			String line;
			while((line=in.readLine())!=null){
				stopword.add(line.trim());
			}
			in.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	/**
	 * traval working dir 
	 * @param rootDir: root directory
	 */
	public void travlDir(String rootDir){
		System.out.println("traving working dir...");
		File dir=new File(rootDir);
		File[]files=dir.listFiles();
		for(int i=0;i<files.length;i++){
			if(files[i].isDirectory()){
				//categories.add(files[i]);
			}
		}
	}
}
