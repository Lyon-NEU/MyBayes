package com.neu.lyon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.math.*;

/**
 * A simple implement of a naive Bayes approach to text classification
 * P(A/B)=P(A)P(B/A)/P(B)
 * 
 * @author Lyon
 * @date 2014/06/25
 */
public class Bayes {
	private Set<String> stopword;
	private Map<String, Integer> vocabulary = null; // all the word in all text
	private Set<String> categories = null;
	private Map<String, Integer> totals = null; // all words of every
												// category每个类的单词总数
	private Map<String, Integer> cateWord = null; // word number of every
													// category每个类中每个词出现的次数
	private Map<String, Integer> wordCount = null;

	public Bayes() {
		stopword = new HashSet<String>();
		vocabulary = new HashMap<String, Integer>();
		categories = new HashSet<String>();
		totals = new HashMap<String, Integer>();
		wordCount = new HashMap<String, Integer>();
	}

	/**
	 * 初始化目录类别，加载停用词
	 * 
	 * @param trainingDir
	 *            训练目录
	 * @param stoplist
	 *            停用词文件
	 */
	public void initial(String trainingDir, String stoplist) {
		if (trainingDir.isEmpty() || stoplist.isEmpty()) {
			System.out.println("Directory is null!");
			return;
		}
		File dir = new File(trainingDir);
		File[] cate = dir.listFiles();
		for (File file : cate) {
			categories.add(file.getAbsolutePath());
		}
	}

	/**
	 * training model
	 * 
	 * @param trainingDir
	 * @param category
	 */
	public void train(String trainingDir, String category) throws Exception {
		if (trainingDir.isEmpty()) {
			System.out.println("directory is null");
			return;
		}
		String currentDir = trainingDir + category;
		File dir = new File(currentDir);
		File[] files = dir.listFiles();
		int total = 0;
		// 遍历每个类别下的所有文件，统计单词信息
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			BufferedReader in = new BufferedReader(new FileReader(
					f.getAbsoluteFile()));
			String line;
			while ((line = in.readLine()) != null) {
				String words[] = line.split(" ");
				for (String word : words) {
					word = word.toLowerCase();
					if ((!word.isEmpty()) && (!stopword.contains(word))) {
						vocabulary.put(word, vocabulary.get(word) == 0 ? 1
								: vocabulary.get(word) + 1);
						cateWord.put(word, cateWord.get(word) + 1);
						total += 1;
					}
				}
			}
		}
		totals.put(category, total); // 每个类的总单词数

	}

	/**
	 * read stopword list
	 * 
	 * @param filename
	 */
	public void readStopword(String filename) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(new File(filename)));
			String line;
			while ((line = in.readLine()) != null) {
				stopword.add(line.trim());
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * traval working dir
	 * 
	 * @param rootDir
	 *            : root directory
	 */
	public void travlDir(String rootDir) {
		System.out.println("traving working dir...");
		File dir = new File(rootDir);
		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				categories.add(files[i].getName());
			}
		}
	}

	/**
	 * classify
	 * 
	 * @param filename
	 */
	public double classify(String filename) {
		double result = 0.0;
		BufferedReader in = null;
		Map<String, Double> res = new HashMap<String, Double>();
		for (String category : categories) {
			res.put(category, 0.0);
		}
		try {
			in = BufferedReader(new FileReader(new File(filename)));
			String line;
			while ((line = in.readLine()) != null) {
				String[] words = line.split(" ");
				for (String word : words) {
					word = word.toLowerCase();
					if (vocabulary.containsKey(word)) {
						for (String cat : categories) {
							// res[cat]+=math.log(prob[cat][word]
							res.put(cat, 0.0);
						}
					}
				}
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//res.sort();
		result=res.get(0);
		return result;
	}

	private BufferedReader BufferedReader(FileReader fileReader) {
		// TODO Auto-generated method stub
		return null;
	}
}
