/*
 * Amritpal Singh
 * Word Frequency Analysis
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class WordFrequencyAnalysis{

	static ArrayList<String> processedWords = new ArrayList<String>();// ArrayList that stores all the processed words
																		// (through Java StringTokenizer)
	static HashMap<String, Integer> filteredWords = new HashMap<String, Integer>(); // HashMap that stores all filtered
																					// words (removes words from
																					// processedWords by comparing to
																					// stopwords.txt)
	static LinkedHashMap<String, Integer> sortedWords = new LinkedHashMap<String, Integer>();// Stores all words in
																								// sorted order based on
																								// values (frequency of
																								// words), using
																								// linkedhashmap since
																								// order matters
	static int totalWordsBeforePreProcessing; // variable that increments every time we read a word from corpus.txt
												// prior
												// to filtering

	public static void main(String[] args) throws FileNotFoundException {

		File corpus = new File("corpus.txt");// create new file reference to corpus.txt which holds our text file prior
												// to processing
		File stopWord = new File("stopwords.txt"); // create new file reference to sopwords.txt which holds our given
													// stopwords

		processedWords = processWords(corpus);// calls processWords which returns an ArrayList with all words, removing
												// punctuation which is stored into the processedWords ArrayList

		filteredWords = filterWords(processedWords, stopWord); // calls filterWords which takes the ArrayList of the
																// words without punctuation and removes any words that
																// are present in the stopwords.txt file. It also prints
																// out tasks 1 and 2. For task 2, it prints our HashMap
																// with the words and frequencies into the out.txt file.
																// The resultant HashMap is stored in processedWords.

		sortedWords = sortByValue(filteredWords); // calls sortByValue which sorts the HashMap by the values
													// (frequencies) and prints it out in decreasing order according to
													// user input. Saved in sortedWords.

		System.out.println(" \n------------------------------------------------\nTask 4:"); // call respective methods
																							// to print out statistics
																							// for task 4
		System.out.println("Dataset (total words read):\t\t" + sizeOfDataSet());
		System.out.println("Number of words (after preprocessing):\t" + numberOfWords());
		System.out.println("Number of stop words in dataset:\t" + numberOfStopWords());
		System.out.println("Number of punctuation in dataset:\t" + numberOfPunctuation());

		stopwordFrequencies(corpus, stopWord);// custom method that finds and calculates the frequencies and ratios of
												// stopwords
	}

	/**
	 * @param corpus the file that is read
	 * @return ArrayList<String> processedWords - an ArrayList with all of the words
	 *         after punctuation removal
	 * @throws FileNotFoundException
	 * 
	 * Uses StringTokenizer to remove numbers and punctuation and stores in ArrayList that is returned - O(n)
	 */
	private static ArrayList<String> processWords(File corpus) throws FileNotFoundException {
		Scanner myReader = new Scanner(corpus); // opensScanner to corpus file

		ArrayList<String> processedWords = new ArrayList<String>();
		while (myReader.hasNext()) {
			try {
				StringTokenizer st = new StringTokenizer(myReader.next());// uses string Tokenizer while more input
																			// exists
				totalWordsBeforePreProcessing++; // increments number of words in corpus file
				String nextToken = (st.nextToken("!@#$%^&*()_+{}:<>?`~1234567890-=[];',./));")).toLowerCase(); // removes
																												// punctuation
																												// and
																												// letters
																												// we
																												// use
																												// toLowerCase,
																												// so we
																												// can
																												// compare
																												// to
																												// stopwords.txt
																												// later

				processedWords.add(nextToken);
			} catch (Exception e) {// throws error when we remove punctuation by itself <- happens naturally when
									// usingTokenizer on multiple lines
			}
		}
		myReader.close(); // close input file

		return processedWords; // return ArrayList with words without punctuation
	}

	/**
	 * @param processedWords - the ArrayList<String> with the words after
	 *                       punctuation removal
	 * @param stopwords      - the file with the list of stopwords that we will have
	 *                       to remove
	 * @return HashMap<String, Integer> filteredWords - a HashMap with the
	 *         frequencies of all words after stopword removal
	 * @throws FileNotFoundException 
	 * 
	 * Takes the ArrayList of the words with punctualization removed and returns a HashMap with word, frequency pairs, 
	 * removing words found in stopwords.txt - O(n)
	 */
	private static HashMap<String, Integer> filterWords(ArrayList<String> processedWords, File stopwords)
			throws FileNotFoundException {

		HashMap<String, Integer> filteredWords = new HashMap<String, Integer>(); // create and store words and their
																					// frequencies in a HashMap

		for (String word : processedWords) {
			if (!filteredWords.containsKey(word)) { // if our HashMap doesn't have the string, that means it is new, so
													// we can add it with frequency 1
				filteredWords.put((word), 1);
			} else { // else increment the frequency
				filteredWords.put(word, filteredWords.get(word) + 1);
			}
		}

		Scanner myStopwordsReader = new Scanner(stopwords);
		while (myStopwordsReader.hasNext()) {
			String nextStopword = myStopwordsReader.next();// if the word is present in the HashMap as well as the
															// stopwords file, we will remove it from our HashMap by
															// using the containsKey function
			if (filteredWords.containsKey(nextStopword)) {
				filteredWords.remove(nextStopword);
			}
		}
		myStopwordsReader.close();// close reader

		System.out.println("Task 1:");
		System.out.println("List of all words/tokens after filtering (duplicates removed): "); // simply print out,
																								// before inserting into
																								// HashMap if you want
																								// the words including
																								// duplicates
		for (String word : filteredWords.keySet()) { // Prints out all of the keys present in the HashMap. This means
														// all of the words. Since we are using a HashMap and for the
														// sake of making it easier on the eyes, we only print out the
														// words once (no duplicates)
			String key = word.toString();
			System.out.println(key);
		}

		try {
			FileWriter fileWriter = new FileWriter("out.txt"); // We create a new file named out.txt which holds all of
																// the words and their respective frequencies, by
																// looping through the HashMap using a keySet
			PrintWriter printWriter = new PrintWriter(fileWriter);

			printWriter.printf("%-15s%10s\n", "Word", "Frequency");
			for (String word : filteredWords.keySet()) {
				String key = word.toString();
				int value = filteredWords.get(key);
				printWriter.printf("%-15s%10d\n", key, value);
			}
			printWriter.close();

			System.out.println(" \n------------------------------------------------");
			System.out.println("Task 2:\nSuccessfully Completed\nLook at out.txt file");
		} catch (Exception e) {
		}
		return filteredWords; // returns the HashMap with all of the words without punctualization and any
								// stopwords
	}

	/**
	 * @param filteredWords - the HashMap with all words and their frequencies after
	 *                      stopword and punctuation removal
	 * @return LinkedHashMap<String, Integer> temp - a LinkedHashMap with all of the
	 *         words and their values in sorted descending order
	 *         
	 * Sorts the filteredWords hashMap by value using a linked list and comparator, then returns the ordered 
	 * HashMap in LinkedHashMap form. Also asks user for n and prints out the top n words. - O(nlogn)
	 */
	public static LinkedHashMap<String, Integer> sortByValue(HashMap<String, Integer> filteredWords) { 

		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(filteredWords.entrySet());// converts  the HashMap
																														// into a linkedList
																														// so we can later
																														// use a comparator
																														// to sort the order (Onlogn)

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() { // sorts the list by using the comparator
																				// to compare the values of 2 different
																				// words at a time
			public int compare(Map.Entry<String, Integer> word1, Map.Entry<String, Integer> word2) {
				return (word1.getValue()).compareTo(word2.getValue());
			}
		});

		LinkedHashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
		for (int i = list.size() - 1; i >= 0; i--) {// puts the sorted linkedList into a LinkedHashMap which we will
													// return that contains the words in descending order by frequency.
													// decrements so order is descending rather than ascending

			temp.put(list.get(i).getKey(), list.get(i).getValue());
		}

		System.out.println(" \n------------------------------------------------");
		System.out.println("Task 3:");

		Scanner scan = new Scanner(System.in); // Takes input from the user and prints out the n number of top words by
												// frequency
		System.out.println("Please enter n, the top number of words in the corpus you would like to see:");
		int n = scan.nextInt();

		System.out.println("\nTop n words table:");
		System.out.printf("%-10s%10s%10s\n", "Word", "Frequency", "Ratio");

		for (String word : temp.keySet()) { // we can use a while loop to go through in order and break once we hit the
											// desired number of top words
			String key = word.toString();
			int value = temp.get(key);
			double ratio = ((double) value) / numberOfWords();
			System.out.printf("%-10s%10d%10.5f\n", key, value, ratio);
			n--;
			if (n == 0) {
				break;
			}
		}

		return temp;
	}

	/**
	 * @return returns the size of the data set (total words in corpus file)
	 * 
	 * Returns the size of the data set by using the totalWordsBeforeProcessing variable which is defined earlier, 
	 * that increments while the input file, corpus.txt hasNext() - O(1)
	 */
	public static int sizeOfDataSet() {
		return totalWordsBeforePreProcessing;
	}

	/**
	 * @return returns the number of words (total number of words after stopword and
	 *         punctuation removal)
	 *         
	 * Returns the total number of words through looping through our final filtered words and adding all of 
	 * their frequencies together- O(n)
	 */
	public static int numberOfWords() { 
		int totalWords = 0;
		for (String key : filteredWords.keySet()) {
			totalWords += filteredWords.get(key);
		}
		return totalWords;
	}

	/**
	 * @return returns the total number of stopwords that are present in the
	 *         corpus.txt file
	 *         
	 * Returns the total number of stop words by counting all of the processed words
	 * (words after punctuation removal) and subtracting that value from the total number of 
	 * final filtered words - O(n) - calls number of words     
	 */
	public static int numberOfStopWords() { 

		return processedWords.size() - numberOfWords();
	}

	/**
	 * @return returns the total amount of punctuation, symbols and numbers found in
	 *         the corpus.txt file
	 *         
	 * Returns the amount of punctuation. As seen in the readme where I go more in depth, this doesn't function properly 
	 * as StringTokenizer removes punctuation at end of words without giving any notification. Read readme for more info. 
	 * To do this we can either find the total number of words processed (after punctuation removal) and subtract that from 
	 * the total data set or simply subtract the dataset, the final words and the number of stopwords since totalwords = finalwords
	 * + stopwords + punctuation. O(n) <- since we are calling O(n) functions
	 */
	public static int numberOfPunctuation() {
		// int totalWordsProcessed = 0;
		// for (String word: processedWords.keySet()) {
		// totalWordsProcessed +=processedWords.get(word);
		// }
		// return sizeOfDataSet()-totalWordsProcessed;
		return sizeOfDataSet() - numberOfWords() - numberOfStopWords();
	}

	/**
	 * @param corpus - corpus file to read
	 * @param stop   - stopword file that contains list of stop words
	 * @throws FileNotFoundException
	 * 
	 * Takes the corpus and stopwords files and iterates through each of them to count the frequency of
	 * stopwords, then displays them similar to task 3 - O(nlogn) (comparator)
	 */
	public static void stopwordFrequencies(File corpus, File stop) throws FileNotFoundException {
		HashMap<String, Integer> stopWords = new HashMap<String, Integer>();
		Scanner myReader = new Scanner(stop);

		while (myReader.hasNext()) { // adds all words from stopwords.txt and puts them in stopWords HashMap
			try {
				String nextWord = myReader.next();
				stopWords.put(nextWord, 0);
			} catch (Exception e) {
			}
		}
		myReader.close();

		Scanner myReader2 = new Scanner(corpus); // iterates through corpus and compares the next token using
													// StringTokenizer to the hashMap of the stopword frequencies, and
													// increments depending on the number of times it appears
		while (myReader2.hasNext()) {
			try {
				StringTokenizer st = new StringTokenizer(myReader2.next());
				String nextToken = (st.nextToken("!@#$%^&*()_+{}:<>?`~1234567890-=[];',./));")).toLowerCase();

				if (stopWords.containsKey(nextToken)) {
					stopWords.put(nextToken, stopWords.get(nextToken) + 1);
				}
			} catch (Exception e) {
			}
		}
		myReader2.close();

		System.out.println(" \n------------------------------------------------");
		System.out.println("Custom Method:");

		Scanner scan = new Scanner(System.in); // Takes input from the user and prints out the n number of top stop
												// words by
		// frequency
		System.out.println("Please enter n, the top number of stop words in the corpus you would like to see:");
		int n = scan.nextInt();
		scan.close();

		System.out.println("\nTop n words table:");
		System.out.printf("%-10s%10s%10s\n", "Stop Word", "Frequency", "Ratio");

		List<Map.Entry<String, Integer>> list = new LinkedList<Map.Entry<String, Integer>>(stopWords.entrySet()); // creates linkedlist and uses
																													// a comparator to sort the
																													// hashMap and put it in a
																													// LinkedHashMap depending
																													// on Frequencies

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> word1, Map.Entry<String, Integer> word2) {
				return (word1.getValue()).compareTo(word2.getValue());
			}
		});

		LinkedHashMap<String, Integer> temp = new LinkedHashMap<String, Integer>(); // decrements through the list and
																					// adds the words in reverse order
																					// so they are descending
		for (int i = list.size() - 1; i >= 0; i--) {
			if (list.get(i).getValue() != 0) {
				temp.put(list.get(i).getKey(), list.get(i).getValue());
			}
		}

		for (String word : temp.keySet()) { // loops through the HashMap and prints out n stopwords and their
											// frequencies/ratios
			String key = word.toString();
			int value = temp.get(key);
			double ratio = ((double) value) / numberOfStopWords();
			System.out.printf("%-10s%10d%10.5f\n", key, value, ratio);
			n--;
			if (n == 0) {
				break;
			}
		}
	}
}
