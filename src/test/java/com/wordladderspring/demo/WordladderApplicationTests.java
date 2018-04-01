package com.wordladderspring.demo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WordladderApplicationTests {

	HashSet dictionary = WordladderApplication.read_Dictionary("dictionary.txt");

	@Test
	public void testReadDictionary() {
		Assert.assertEquals(267751, dictionary.size());
	}

	@Test
	public void testLadderFun() {
		String res1 = "Ladder:    data date cate cade code ";
		String res2 = "Ladder:    awake aware sware stare starn stern steen steep sleep ";
		String res3 = "No word ladder found.";
		Assert.assertEquals(res1, WordladderApplication.wordladder("code", "data", dictionary));
		Assert.assertEquals(res2, WordladderApplication.wordladder("sleep", "awake", dictionary));
		Assert.assertEquals(res3, WordladderApplication.wordladder("metal", "azure", dictionary));
	}

	@Test
	public void testErrInput() {
		String res1 = "The two words must be the same length.";
		String res2 = "The two words must be different.";
		String res3 = "The two words must be found in the dictionary.";
		Assert.assertEquals(res1, WordladderApplication.Fun("hello", "WorD"));
		Assert.assertEquals(res2, WordladderApplication.Fun("hello", "HELLO"));
		Assert.assertEquals(res3, WordladderApplication.Fun("hahaha", "hehehe"));
	}
}
