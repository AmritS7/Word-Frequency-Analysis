Word Frequency Analysis – ReadMe

Name: Amritpal Singh

How I chose which data structures to use:

I used a total of 4 data structures for this assignment. I will explain my logic while choosing them in order:

1 – ArrayList – I decided to use an ArrayList to store the words for Part I, which involved getting all of the words without punctualization and removing the stop words. To do this, I decided it would be best to split this up into two different tasks. The processWords(file) method gets all of the input from the corpus file and removes all punctuality using the StringTokenizer. These words are then added onto the ArrayList. An ArrayList was a good choice because we were only adding all of the words, and the time complexity to add into an ArrayList is O(1). Then, for the rest of part1, I had to print out these words. I thought it would be best to only display these words once without duplicates as that could get really messy, so I combined this with task2 and used a HashMap as explained in the next comment.

2- HashMap – I used the ArrayList and inserted the words into a HashMap, where the key was a word and the value was its frequency and comparing this to what was in. A HashMap was a good choice for this since we had two pieces of information, and since we were both inserting and getting the keys and values to update them. The insert and delete functions for HashMaps are both O(1). From inserting and updating, we are left with the words with their frequencies after punctualization is removed. Then we can simply go through the stopwords.txt file and remove any elements that are present in both the HashMap and file. Next, I knew I also had to sort the data by frequency, and considered using a TreeMap which automatically sorts itself, however it does this by key! And since we needed to sort by frequency (which couldn’t be the key since it is possible to have duplicate frequencies), that means that I had to somehow sort the TreeMap by values, similar to what I do later with the HashMap. Since I am more comfortable with HashMaps, I decided to go with that direction.

3- LinkedList – In order to print out a table in descending order I had to sort the HashMap and put in in the correct order. I thought that the easiest way to sort the HashMap by frequencies (values), I could convert it to a LinkedList and use a Comparator to sort. We can then put these into a LinkedHashMap in correct order. A LinkedList was a good choice since the insert time complexity is O(1) and the complexity of the comparator which sorts by the frequencies is O(nlogn), which is on the better side when it comes to sorting.

4- LinkedHashMap – I needed to use a LinkedHashMap to enable me to print out the table of words, frequencies and ratios in the correct order. A LinkedHashMap keeps the order, while also having an insert and access complexity of O(1).

The three steps:

To display the three steps, I wanted to show the output of my program by using a simple corpus.txt file as follows:

![](media/3c8b3a5659f4aa1f4432ce29e577c12f.png)

![](media/753a7377fa7c01978ff302cc8540bb5e.png)1- Task 1 involved reading the corpus file and outputting the words after punctuation removal and stopword removal. To do this, I used the first two methods where I use a StringTokenizer to remove punctuation and put the words in a HashMap. From here, I remove the words in the HashMap that are found in the stopwords.txt file and print out the results. This prints out all of the words after processing and filtering without any duplicates.

2- In task 2, we had to output the words and their frequencies using the words we obtained from the preprocessing module. This also involved the second method, filterWords where I took the HashMap from task 1 and used a file and printwriter to output these values into the out.txt file.

![](media/d96802e530d6d2f5287a7952714c4242.png)

3- For task 3 we had to take the words and their frequencies and print them out the top n words in table format. For me this involved reformatting the HashMap by converting it to a linkedList to sort and then put it in a LinkedHashMap to maintain order. We ask the user for how many of the topwords they would like and we loop through the LinkedHashMap to print that out (if there are not enough words then it prints out the whole list). This is done in the sortByValue method.

![](media/5c1a4eba879f59faddb0d303a6c8edad.png)

4- For task 4 we had to print out various stats and create our own method. To do this we can simply go through the ArrayLists, HashMaps and other variables we have created. I go more in depth about this in the next section.

![](media/e23fd50fa33c2c4edc54bdb089aacacb.png)

Complexity and explanation of each method:

processWords – O(n) – In this method we use a StringTokenizer to remove the punctuation and store the words in an ArrayList. O(n) because we are going through all of the words in the corpus.txt (input) file.

filterWords – O(n) – This method takes our ArrayList from processWords and adds the words to a HashMap with the key being the word and the value being the frequency. This holds all words present in the corpus (including stopwords). To remove the stopwords, we read the stopwords.txt file and remove words that are present in both the file and the HashMap. We print all the words and the words and their frequencies to the output file. O(n) because we are looping through the ArrayList, the HashMap and the stopwords file (no nested loops).

sortByValue- O(nlogn) – converts the HashMap from filterwords which contains the words and their frequencies into a LinkedList, sorts and converts back to a LinkedHashMap so we can print out the words by their frequencies. O(nlogn) because the elephant is the comparator used on the linkedlist which has time complexity O(nlogn).

sizeOfDataSet – O(1) – returns the total words we read from the corpus file. This was incremented as a variable in the processwords method, so we can simply return that variable.

numberOfWords – O(n) – we return the total number of words by looping through our hashMap of filtered words and adding all of the word frequencies together. Usually less, but O(n) is the worst case if the file contains no punctuation or stopwords and all words have frequency of 1.

numberOfStopWords – O(n) – returns the number of processed words minus the number of words. O(n)since we are calling methods with O(n) time complecxity.

numberOfPunctuation – O(n) – there are two ways to do this method. We can either find all of the words minus punctuation and subtract the numberOfWords or return the sizeOfDataSet – numberOfWords – numberOfStopWords because totalwords = finalwords + stopwords + punctuation. O(n) since we call the previous methods which loop through.

**NOTE:** The punctuation method isn’t accurate. It identifies punctuation or numbers that are separated by spaces. StringTokenizer doesn’t give any notification about punctuation at the end of a word, it simply returns the word without the punctuation. If I were to redo this project, I would look into using the string.split() method instead, so I could get accurate values. To see this, we can look at our results from the three steps section where it recognizes 67… and … , but not Amritpal, or you?

stopwordFrequencies – O(nlogn) – custom method – I created this method because I thought it would be interesting to see different trends with the frequencies of stop words and how that reflects our corpus. For example, if the highest frequency stop words are I. my, me, etc, then that means that our corpus file is likely a first-person novel/journal. However, if those words are towards the bottom of the list, then it may mean that we are dealing with a more scientific publication. To execute this method, I combined what I did in the first three methods by saving the words in the stopwords.txt file in a HashMap, updating the HashMap by incrementing based on the frequencies in the corpus.txt file, and finally sorting and printing it out(using a linkedList and a LinkedHashMap). O(nlogn) because we are using a comparator to sort.

Statistics and DataSet:

Outputs after using Alice’s Adventures in Wonderland as corpus.txt

![](media/3490a9905e65408e0f2e6589846c6b6b.png)

![](media/e8c70a6270caa18013c1ff667996f2ad.png)

![](media/13950d52f9a6dff5193c64929ff39e33.png)

![](media/2cca278fafa6687c3c7263c5bf4fd224.png)

Table Of Statistics:

Note: DataSet = NumberOfWords + NumberOfStopWords + NumberOfPunctuation

26458 = 8127 + 18270 + 61

This is because the total amount of words present in the corpus is the same as all of the filtered words plus all of the stopwords plus all of the punctuation.

| Data Set | Number of Words | Number of Stop Words | Number of Punctuation |
|----------|-----------------|----------------------|-----------------------|
| 26458    | 8127            | 18270                | 61                    |

As we can see, since the number of words + number of stop words + number of punctuation is equal to the data set, the dataset represents exactly half of the pie chat.
