package qengine.program;

import java.util.HashMap;

public class Dictionary {

    /**
     * Create a Singleton Instance of the Dictionary
     */
    private static Dictionary instance = new Dictionary();

    /**
     * Separate the words and the predicates in two subtype of our Dictionary
     * (words U predicates) == Dictionary
     */
    private HashMap<Integer, String> words;
    private HashMap<String, String> predicates;

    /**
     * Counter to get a unique id for each word|predicate we add
     */
    private int wordsIdCounter;
    private int predicatesIdCounter;

    // ========================================================================

    private Dictionary(){
        words = new HashMap<Integer, String>();
        predicates = new HashMap<String, String>();
        wordsIdCounter = 0;
        predicatesIdCounter = 0;
    }

    public static Dictionary getInstance() {
        return instance;
    }

    public HashMap<Integer, String> getWords(){
        return words;
    }

    public String getWordByKey(int key){
        return words.get(key);
    }

    public int getWordByValue(String value){
        for(int i = 0; i < words.size(); i++){
            if(words.get(i).equals(value)){
                return i;
            }
        }
        return -1;
    }

    public String getPredicateByKey(String key){
        return predicates.get(key);
    }

    public String getPredicateByValue(String value){
        for(int i = 0; i < predicates.size(); i++){
            if(predicates.get("p"+i).equals(value)){
                return "p"+i;
            }
        }
        return null;
    }

    public void addWord(String word) {
        if (!words.containsValue(word)) {
            words.put(wordsIdCounter, word);
            wordsIdCounter++;
        }
    }

    public void addPredicate(String predicate) {
        if (!this.predicates.containsValue(predicate)) {
            predicates.put("p" + predicatesIdCounter, predicate);
            predicatesIdCounter++;
        }
    }
}
