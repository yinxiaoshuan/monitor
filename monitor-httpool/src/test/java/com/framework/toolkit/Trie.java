package com.framework.toolkit;

/**
 * 
 * @author root
 *
 */
public class Trie {
	
	protected class Vertex{

		protected char words;
		protected int depth;
		protected int postfixes;
		protected Vertex[] edges;
		
		Vertex(){
			edges = new Trie.Vertex[26];
		}

	}
	
	private Vertex root;

	public Trie(){
		root = new Vertex();
	}
	
	/**
	 * Add a word to the Trie.
	 * @param word
	 */
	public void insert(String word){
		insert(root, word);
	}
	
	private void insert(Vertex node, String word){
		if(word.length() == 0){//If all characters of the word has been added.
			return;
		}

		char c = Character.toLowerCase(word.charAt(0));
		int pos = c - 'a';
		if(node.edges[pos] == null){// no exist.
			node.edges[pos] = new Vertex();
			node.edges[pos].words = c;
			node.edges[pos].postfixes = 1;
			node.edges[pos].depth = node.depth + 1;
		}else{
			node.edges[pos].postfixes++;
		}

		int length = word.length();
		String tail = length == 1 ? "" : word.substring(1);
		insert(node.edges[pos],tail);
	}

	/**
	 * 前缀查找字符串'{@code word}'是否存在.
	 * @param word
	 * @return
	 */
	public boolean exists(String word){
		if(word == null || word.isEmpty()){
			return false;
		}

		Vertex node = root;
		char[] cs = word.trim().toLowerCase().toCharArray();
		for(char c : cs){
			int pos = c - 'a';
			if(node.edges[pos] == null){
				return false;
			}
			node = node.edges[pos];
		}
		return true;
	}

	/**
	 * 统计词频.<p>统计字符串出现的次数.</p>
	 * @param word
	 * @return
	 */
	public int frequency(String word){
		if(word == null || word.isEmpty()){
			return 0;
		}
		
		Vertex node = this.root;
		char[] cs = word.toLowerCase().toCharArray();
		for(char c : cs){
			int pos = c - 'a';
			if(node.edges[pos] == null || node.edges[pos].words != c){
				return 0;
			}
			node = node.edges[pos];
		}
		return node.postfixes;
	}

	// Debug
	public static void trace(Vertex vertex) {
		if(vertex == null){
			return;
		}
		StringBuilder trace = new StringBuilder();
		for(int i = 1; i <= vertex.depth; i++){
			trace.append('\t');
		}

		trace.append("postfix=" + vertex.postfixes);
		trace.append(", depth=" + vertex.depth);
		trace.append(", word=" + vertex.words);

		System.out.println(trace.toString());
		
		for(Vertex edge : vertex.edges){
			trace(edge);
		}
	}

	public static void main(String[] args) {
		Trie trie = new Trie();
		//trie.insert("习近平应约同美国总统特朗普通电话");
		trie.insert("he");
		trie.insert("ha");
		trie.insert("abdc");
		trie.insert("adc");
		trie.insert("ha");//用于统计'ha'字符串出现的次数
		
		Trie.trace(trie.root);
		//trie.insert("he");

		boolean exist = trie.exists("ab");
		System.out.println("'ab' exist: " + exist);
		exist = trie.exists("ac");
		System.out.println("'ac' exist: " + exist);
		exist = trie.exists("ha");
		System.out.println("'ha' exist: " + exist);

		int freq = trie.frequency("ab");
		System.out.println("'ab' freq: " + freq);
		freq = trie.frequency("ac");
		System.out.println("'ac' freq: " + freq);
		freq = trie.frequency("ha");
		System.out.println("'ha' freq: " + freq);
		freq = trie.frequency("h");
		System.out.println("'h' freq: " + freq);
	}

}
