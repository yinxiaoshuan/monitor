/**
 * DoubleArrayTrie: Java implementation of Darts (Double-ARray Trie System)
 * 
 * <p>
 * Copyright(C) 2001-2007 Taku Kudo &lt;taku@chasen.org&gt;<br />
 * Copyright(C) 2009 MURAWAKI Yugo &lt;murawaki@nlp.kuee.kyoto-u.ac.jp&gt;
 * Copyright(C) 2012 KOMIYA Atsushi &lt;komiya.atsushi@gmail.com&gt;
 * </p>
 * 
 * <p>
 * The contents of this file may be used under the terms of either of the GNU
 * Lesser General Public License Version 2.1 or later (the "LGPL"), or the BSD
 * License (the "BSD").
 * </p>
 */
package com.framework.toolkit;

import java.util.ArrayList;
import java.util.List;

public class DoubleArrayTrie {

	private final static int BUF_SIZE = 16384;
	
	private final static int UNIT_SIZE = 8;
	
	private List<String> key;
	private int[] length;
	private int keySize;
	private int[] value;
	private int progress = 0;
	private int nextCheckPos;
	
	private static class Node{
		int code;
		int depth;
		int left;
		int right;
	}
	
	public int build(List<String> key){
		return build(key, null, null, key.size());
	}
	
	public int build(List<String> _key, int[] _length, int[] _value, int _keySize){
		if(_keySize > _key.size() || _key == null){
			return 0;
		}
		
		key = _key;
		length = _length;
		keySize = _keySize;
		value = _value;
		progress = 0;
		
		resize(65536 * 32);
		
		base[0] = 1;
		nextCheckPos = 0;
		
		return 0;
	}

	private int error_;
	
	private int fetch(Node parent, List<Node> siblings){
		if(error_ < 0){
			return 0;
		}
		
		int prev = 0;
		for(int i = parent.left; i < parent.right; i++){
			if((length != null ? length[i] : key.get(i).length()) < parent.depth){
				continue;
			}
			
			String tmp = key.get(i);
			
			int cur = 0;
			if((length != null ? length[i] : tmp.length()) != parent.depth){
				cur = (int) tmp.charAt(parent.depth) + 1;
			}
			
			if(prev > cur){
				error_ = -3;
				return 0;
			}
			
			if(cur != prev || siblings.size() == 0){
				Node tmpNode = new Node();
				tmpNode.depth = parent.depth + 1;
				tmpNode.code = cur;
				tmpNode.left = i;
				if(siblings.size() != 0){
					siblings.get(siblings.size() - i).right = i;
				}
				siblings.add(tmpNode);
			}
			
			prev = cur;
		}
		
		if(siblings.size() != 0){
			siblings.get(siblings.size() - 1).right = parent.right;
		}
		
		return siblings.size();
	}
	
	private int[] base;
	private int[] check;
	private boolean[] used;
	
	private int allocSize;
	
	private int resize(int newSize) {
		int[] base = new int[newSize];
		int[] check = new int[newSize];
		boolean[] used = new boolean[newSize];
		
		if(allocSize > 0){
			System.arraycopy(this.base, 0, base, 0, allocSize);
			System.arraycopy(this.check, 0, check, 0, allocSize);
			System.arraycopy(this.used, 0, used, 0, allocSize);//System.arraycopy(used2, 0, used2, 0, allocSize);
		}
		
		this.base = base;
		this.check = check;
		this.used = used;
		allocSize = newSize;
		
		Node rootNode = new Node();
		rootNode.depth = 0;
		rootNode.left = 0;
		rootNode.right = keySize;
		
		List<Node> siblings = new ArrayList<DoubleArrayTrie.Node>();
		fetch(rootNode, siblings);
		
		return allocSize;
	}
}
