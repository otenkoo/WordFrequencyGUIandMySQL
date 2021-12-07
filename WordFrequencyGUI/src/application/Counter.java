package application;

import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * <mark>Counter</mark> is the class used for all the word counting and sorting that happens
 * behind the scenes of the GUI.<br>
 * In this project it is used to display the 20 most used words in the poem The Raven
 * onto a GUI.
 * <p>
 * Some important things to consider are that HTML elements and HTML class tags WILL vary from project
 * to project.<br> In this case, The Raven was encapsulated within <b>h1, {@literal h2.no-break}, and {@literal div.chapter}</b>.<br>
 * <b>Element</b> data type variables were created to reflect this project.
 * 
 * @author Gener Almestica
 * @version 3.0
 *
 */

public class Counter {
	
	/**
	 * The <mark>getPoemWordFrequency</mark> method takes the argument of a given and valid URL
	 * and creates a sorted List of the 20 most used words in the Text chosen.
	 * <p>
	 * A list entry is created once the chosen HTML has been parsed so that the word frequency
	 * is displayed along side the word when displayed on the GUI.<br>
	 * The <b>{@link #GrabUrlText(String) GrabUrlText}</b> and <b>{@link #GetWordFrequencyFromList(List<String> stringList) GetWordFrequencyFromList}</b>
	 * methods are combined into their final result here to be displayed by the GUI.
	 * 
	 * @param url			an absolute URL giving the base location of chosen Text.
	 * @return				List of Top 20 most used words in descending order.
	 * @throws IOException	If the link is invalid.
	 */

	public List<Entry<String, Integer>> getPoemWordFrequency(String url) throws IOException {

		List<String> text = GrabUrlText(url);
		
		List<Entry<String, Integer>> result = GetWordFrequencyFromList(text);

		return result;

	}
	
	/**
	 * Returns each occurring word in the poem <b>The Raven</b> individually
	 * as an array of Strings. 
	 * <p>
	 * This method only works for The Raven poem as the HTML tags were chosen
	 * specifically for this project.
	 * <p>
	 * In order to cater to your project or web page, you must first find the
	 * Elements containing the information you need from the specified web page.<br>
	 * Then create new variables using the Elements data type. The Elements data
	 * type allows you to search the document you created (in this case "doc") for
	 * specific HTML tags.<br>
	 * If no elements are chosen, the entire web page is then parsed into the document.
	 * For this project, the following Elements were used:
	 * <ul>
	 * <li><b>h1</b>
	 * <li><b>{@literal h2.no-break}</b>
	 * <li><b>{@literal div.chapter}</b>
	 * </ul>
	 * <p>
	 * The appendages following <b><i>h2</i></b> and <b><i>div</i></b> tags indicate the class name of that body tag.
	 * <p>
	 * Once all chosen text is grabbed from the <b>URL</b>, we create a string variable called poem that
	 * combines chosen HTML elements.<br>
	 * The poem variable is then stored to an array that converts all words to lower case,
	 * removes all non alphabetical characters, and splits by the spaces between the words.
	 * 
	 * @param Url			An absolute URL giving the base location of chosen Text.
	 * @return				New array containing each word of chosen Text from start to end.
	 * @throws IOException	If the link is invalid.
	 */

	public List<String> GrabUrlText(String Url) throws IOException {
		// This fetches and parses the linked HTML file based on connected URL and
		// stores into new "doc"
		Document doc = Jsoup.connect(Url + "").get();

		// These variables select specific tags and classes from HTML doc and store to
		// variable name. *UPDATE DIVS AND ELEMENTS AS THEY MAY CHANGE*
		Elements center1 = doc.select("h1");
		// Elements center2 = doc.select("p.center");
		Elements author = doc.select("h2.no-break");
		Elements content = doc.select("div.chapter");

		// Consolidate all elements into 1 string and remove HTML code with .text()
		// appendage
		String poem = center1.text() + " " + author.text() + " " + content.text();

		// Store string into array making all lower case and removing all
		// non-alphabetical characters
		String arrayP[] = poem.toLowerCase().split("\\W+");

		return Arrays.asList(arrayP);
	}
	
	/**
	 * The <mark>GetWordFrequencyMethod</mark> takes the List String called <strong>Text</strong> that was created by
	 * the <mark>GrabUrlText</mark> method and creates a descending list (most to least) of the 20 most used
	 * words in the selected HTML elements.
	 * <p>
	 * A <b>HashMap</b> is created using the argument array stringList sent through a forEach loop,
	 * where the Key is the word and the value assigned with the key is the word frequency.<br>
	 * The value/word frequency increments by +1 every time a repeated occurrence of that word happens.
	 * <p>
	 * A new <b>HashMap</b> is created using stream so that the values in the HashMap may be ordered in descending order.
	 * Once the HashMap is sorted, a new List is created in order to store the HashMap in the desired order.
	 * <p>
	 * An <b><code>if/else</code></b> statement is used to ensure List size is exactly 20 or less elements.
	 * If List happens to be under 20 elements then program is executed, assuming there were not enough elements,
	 * otherwise List will be capped at 20 elements.
	 * 
	 * @param stringList	Array containing all words from poem.
	 * @return 					Array List of Top 20 most used words from poem sorted in descending order.
	 */

	public List<Entry<String, Integer>> GetWordFrequencyFromList(List<String> stringList) {
		// Creates a hash map that counts the frequency of each word
		Map<String, Integer> hsMap = new HashMap<>();
		for (String word : stringList) {

			if (hsMap.containsKey(word)) {
				hsMap.put(word, hsMap.get(word) + 1);
			} else {
				hsMap.put(word, 1);
			}
		}

		// Sorts hash map in descending order using value(word frequency)
		Map<String, Integer> sorted = hsMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

		List<Entry<String, Integer>> result = new ArrayList<Entry<String, Integer>>();

		if (sorted.size() < 20) {
			result = new ArrayList<Entry<String, Integer>>(sorted.entrySet());
		} else {
			result = new ArrayList<Entry<String, Integer>>(sorted.entrySet()).subList(0, 20);
		}

		return result;
	}
	
	
}
