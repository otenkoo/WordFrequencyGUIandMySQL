package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;
import application.Counter;

class WordTest {

	@Test
	void TestMoreThan20Words() {
		List<String> testList = new ArrayList<String>();
		Counter testObject = new Counter();
		
		//Frequency 3
		testList.add("q");
		testList.add("w");
		testList.add("e");
		testList.add("r");
		testList.add("t");
		testList.add("y");
		
		testList.add("q");
		testList.add("w");
		testList.add("e");
		testList.add("r");
		testList.add("t");
		testList.add("y");
		
		testList.add("q");
		testList.add("w");
		testList.add("e");
		testList.add("r");
		testList.add("t");
		testList.add("y");
		
		//Frequency 2
		testList.add("u");
		testList.add("i");
		testList.add("o");
		testList.add("p");
		testList.add("a");
		testList.add("s");
		testList.add("d");
		testList.add("f");
		testList.add("g");
		testList.add("h");
		testList.add("j");
		testList.add("k");
		testList.add("l");
		testList.add("z");

		testList.add("u");
		testList.add("i");
		testList.add("o");
		testList.add("p");
		testList.add("a");
		testList.add("s");
		testList.add("d");
		testList.add("f");
		testList.add("g");
		testList.add("h");
		testList.add("j");
		testList.add("k");
		testList.add("l");
		testList.add("z");
		
		//Frequency 1
		testList.add("v");
		
		List<Entry<String,Integer>> results = testObject.GetWordFrequencyFromList(testList);
		
		if(results.size() > 20) {
			fail("Result has too many words in it.");
		}
		
		for (Entry<String, Integer> result: results) {
			if(result.getKey() == "q" ||
					result.getKey() == "w" ||
					result.getKey() == "e" ||
					result.getKey() == "r" ||
					result.getKey() == "t" ||
					result.getKey() == "y" )
			{
				if (result.getValue() != 3) {
					fail("Function is not counting correctly.");
				}
			}
			else if(result.getKey() == "u" ||
					result.getKey() == "i" ||
					result.getKey() == "o" ||
					result.getKey() == "p" ||
					result.getKey() == "a" ||
					result.getKey() == "s" ||
					result.getKey() == "d" ||
					result.getKey() == "f" ||
					result.getKey() == "g" ||
					result.getKey() == "h" ||
					result.getKey() == "j" ||
					result.getKey() == "k" ||
					result.getKey() == "l" ||
					result.getKey() == "z" )
			{
				if (result.getValue() != 2) {
					fail("Function is not counting correctly.");
				}
				
			}
			else {
				fail("Incorrect words are in the list");
			}
		}
		return;
	}

	@Test
	void TestLessThan20Words() {
		List<String> testList = new ArrayList<String>();
		Counter testObject = new Counter();
		
		testList.add("the");
		testList.add("the");
		testList.add("a");
		testList.add("a");
		testList.add("to");
		testList.add("to");
		testList.add("to");
		
		List<Entry<String,Integer>> results = testObject.GetWordFrequencyFromList(testList);
		
		assertEquals(3,results.size());
	}
	
	@Test
	void TestInvalidUrl() {
		
		Counter testObject = new Counter();
		
		try {
			testObject.GrabUrlText("invalidUrl.testfail");
			fail("Error should have been thrown");
		}
		catch(Exception e) {
			return;
		}
	}
	
	@Test
	void TestValidUrl() {
		
		Counter testObject = new Counter();
		
		try {
			testObject.GrabUrlText("https://www.gutenberg.org/files/1065/1065-h/1065-h.htm");
		}
		catch(Exception e) {
			fail("Error should not have been thrown a valid url has been sent.");
		}
	}
}
