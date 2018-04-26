package com.wordladderspring.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.Stack;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@SpringBootApplication
public class WordladderApplication {
	private static final Logger logger = LogManager.getLogger(WordladderApplication.class);

	@RequestMapping("/find")
	public String Fun(String word1, String word2)  {

		HashSet dictionary;
		dictionary = read_Dictionary("dictionary.txt");

		word1 = word1.toLowerCase();
		word2 = word2.toLowerCase();

		if(word1.length() != word2.length()) {
			logger.info("the two words have different length");
			return "The two words must be the same length.";
		}
		else if(word1.equals(word2)){
			logger.info("word1 is equal to word2");
			return "The two words must be different.";
		}
		else if(!(dictionary.contains(word1) && dictionary.contains(word2))) {
			logger.info("word not found in the dictionary");
			return "The two words must be found in the dictionary.";
		}
		else {
			return wordladder(word1, word2, dictionary);
		}
	}

	public static HashSet read_Dictionary(String Dictionary_name) {
		File file = new File("src/main/resources/"+Dictionary_name);
		HashSet tmp_dictionary = new HashSet();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				tmp_dictionary.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
//			System.out.println("Unable to open that file.  Try again.");
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return tmp_dictionary;
	}

	public static String wordladder(String word1, String word2, HashSet dictionary){
		StringBuffer res = new StringBuffer();
		LinkedBlockingQueue<Stack<String>> ladders = new LinkedBlockingQueue<Stack<String>>();
		Stack<String> W1 = new Stack<String>(), W2 = new Stack<String>() ;
		String w1, w2;
		HashSet<String> used_set = new HashSet<String>();
		boolean find = false;
		int size = word1.length();
		W1.push(word1);
		ladders.add(W1);

		while (!find && !ladders.isEmpty()) {
			W1 = ladders.poll();
			w1 = W1.peek();
			for (int i = 0; i < size; i++) {
				if (!find) {
					for (char j = 'a'; j <= 'z'; j++) {
						if (!find) {
							W2 = (Stack<String>) W1.clone();
							w2 = w1;
							StringBuffer tmp = new StringBuffer(w2);
							tmp.setCharAt(i, j);
							w2 = tmp.toString();
							if (!used_set.contains(w2)) {
								if (w2.equals(word2)) {
									find = true;
									W2.push(w2);
									res.append("Ladder:    ");
									while (!W2.isEmpty()) {
										res.append(W2.pop());
										res.append(' ');
									}
								}
								if (dictionary.contains(w2)) {
									W2.push(w2);
									ladders.add(W2);
									used_set.add(w2);
								}
							}
						}
					}
				}
			}
		}
		if (!find) {
			logger.info("no word ladder found");
			res.append("No word ladder found.");
		}
		else {
			logger.info("success");
		}
		return res.toString();
	}
	public static void main(String[] args) {
		SpringApplication.run(WordladderApplication.class, args);
	}
}
