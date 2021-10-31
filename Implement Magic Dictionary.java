Design a data structure that is initialized with a list of different words. Provided a string, you should determine if you can change exactly one character in this string to match any word in the data structure.

/*Implement the MagicDictionary class:

MagicDictionary() Initializes the object.
void buildDict(String[] dictionary) Sets the data structure with an array of distinct strings dictionary.
bool search(String searchWord) Returns true if you can change exactly one character in searchWord to match any string in the data structure, otherwise returns false.
 

Example 1:

Input
["MagicDictionary", "buildDict", "search", "search", "search", "search"]
[[], [["hello", "leetcode"]], ["hello"], ["hhllo"], ["hell"], ["leetcoded"]]
Output
[null, null, false, true, false, false]

Explanation
MagicDictionary magicDictionary = new MagicDictionary();
magicDictionary.buildDict(["hello", "leetcode"]);
magicDictionary.search("hello"); // return False
magicDictionary.search("hhllo"); // We can change the second 'h' to 'e' to match "hello" so we return True
magicDictionary.search("hell"); // return False
magicDictionary.search("leetcoded"); // return False
 

Constraints:

1 <= dictionary.length <= 100
1 <= dictionary[i].length <= 100
dictionary[i] consists of only lower-case English letters.
All the strings in dictionary are distinct.
1 <= searchWord.length <= 100
searchWord consists of only lower-case English letters.
buildDict will be called only once before search.
At most 100 calls will be made to search. */
  
class MagicDictionary {
    class TrieNode {
        Map<Character, TrieNode> children;
        boolean endOfWord;
        public TrieNode() {
            children = new HashMap<>();
            endOfWord = false;
        }
    }
    
    //Insert by iteration
    public void insert(TrieNode root, String word) {
        TrieNode curr = root;
        
        for(char ch: word.toCharArray()) {
            TrieNode node = curr.children.get(ch);
            if(node == null) {
                node = new TrieNode();
                curr.children.put(ch, node);
            }
            curr = node;
        }
        
        curr.endOfWord = true;
    }
    
    //Search by iteration
    public boolean searchHelper(TrieNode root, String word) {
        TrieNode curr = root;
        
        for(char ch: word.toCharArray()) {
            TrieNode node = curr.children.get(ch);
            if(node == null) {
                return false;
            }
            curr = node;
        }
        
        return curr.endOfWord;
    }

    /** Initialize your data structure here. */
    public TrieNode root;
    public MagicDictionary() {
        root = new TrieNode();
    }
    
    public void buildDict(String[] dictionary) {
        for(String word: dictionary) {
            insert(root, word);
        }
    }
    
    public boolean search(String searchWord) {
        TrieNode curr = root;
        
        int count = 0;
        char[] word = searchWord.toCharArray();
        for(int i = 0; i < word.length; i++) {
            if(curr == null) {
                return false;
            }
            
            TrieNode node = curr.children.get(word[i]);
            char org = word[i];
            for(char ch: curr.children.keySet()) {
                if(ch == org) continue;
                
                word[i] = ch;
                if(searchHelper(root, new String(word))) {
                    return true;
                }
            }
            word[i] = org;
            curr = node;
        }
        
        return false;
    }
}

/**
 * Your MagicDictionary object will be instantiated and called as such:
 * MagicDictionary obj = new MagicDictionary();
 * obj.buildDict(dictionary);
 * boolean param_2 = obj.search(searchWord);
 */  
