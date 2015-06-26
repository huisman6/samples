package com.youzhixu.sample.algorithm.bigdata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author huisman
 * @createAt 2015年6月26日 下午1:38:30
 * @since 1.0.0
 * @Copyright (c) 2015, youzhixu.com All Rights Reserved.
 */

public class TrieDemo {
	public static void main(String[] args) {
		// 测试代码
		String[] strs =
				new String[] {"type", "this", "thin", "think", "at", "belief", "中华人民共和国", "中国",
						"中华", "中", "共和国", "国"};

		Trie trie = new Trie();

		for (String str : strs) {
			trie.add(str);
		}

		String prefix = "中";
		String hasStr = "中共和国";
		String word = "中";
		System.out.println(prefix + " prefixCount==========>  " + trie.prefixCount(prefix));
		System.out.println("trie has String[" + hasStr + "] ==========>" + trie.exists(hasStr));
		System.out.println("trie wordCount [" + word + "] total count :  " + trie.wordCount(word));

	}

	/**
	 * Trie树常见的操作：插入字符串（构造树）、查找 常用于 字符串出现频率的统计、前缀匹配、字符串查找等
	 */
	private static class Trie implements Serializable {
		private static final long serialVersionUID = 1L;

		/**
		 * 根节点，根节点不保存字符数据
		 */
		private TNode root;


		public Trie() {
			// 初始化根节点
			this.root = new TNode();

		}

		public void add(CharSequence sequence) {
			// 插入字符串
			int len;
			if (null == sequence || (len = sequence.length()) == 0) {
				return;
			}
			// 开始从根节点遍历
			TNode startNode = this.root;

			// 遍历每个字符，构造从根节点开始的路径
			TNode son;
			char val;
			for (int i = 0; i < len; i++) {
				// 首先查找startNode的子节点中是否存在字符charData[i]
				val = sequence.charAt(i);
				son = startNode.getSons().get(val);
				if (son == null) {
					// 不存在则新建节点
					son = new TNode();
					// 字符前缀计数
					son.setPrefixCount(1);

					startNode.getSons().put(val, son);
				} else {
					// 如果存在，则前缀计数+1
					son.setPrefixCount(son.getPrefixCount() + 1);
				}
				// 迭代到下一个字符所在的节点
				startNode = son;
			}
			startNode.setWholeWord(true);
			startNode.setWordCount(startNode.getWordCount() + 1);
		}


		public boolean exists(CharSequence sequence) {
			// 是否包含完整的字符序列
			int len;
			if (null == sequence || (len = sequence.length()) == 0) {
				return false;
			}

			// 开始从根节点遍历
			TNode startNode = this.root;

			// 遍历每个字符，构造从根节点开始的路径
			TNode son;
			for (int i = 0; i < len; i++) {
				// 首先查找startNode的子节点中是否存在字符charData[i]
				son = startNode.getSons().get(sequence.charAt(i));
				if (son == null) {
					// 这条路径上没有此字符串
					return false;
				}
				// 迭代到下一个字符所在的节点
				startNode = son;
			}

			// 字符串沿路径遍历完，未节点查看是否标识了完整的单词
			return startNode.isWholeWord();
		}


		public int wordCount(CharSequence sequence) {
			// 是否包含完整的字符序列
			int len;
			if (null == sequence || (len = sequence.length()) == 0) {
				return 0;
			}

			// 开始从根节点遍历
			TNode startNode = this.root;

			// 遍历每个字符，遍历由根节点 - > 子节点的路径
			TNode son;
			for (int i = 0; i < len; i++) {
				// 首先查找startNode的子节点中是否存在字符charData[i]
				son = startNode.getSons().get(sequence.charAt(i));
				if (son == null) {
					// 这条路径上没有此字符串
					return 0;
				}
				// 迭代到下一个字符所在的节点
				startNode = son;
			}

			// 字符串沿路径遍历完，查看节点计数值
			return startNode.getWordCount();
		}


		public int prefixCount(CharSequence prefix) {
			// 是否包含完整的字符序列
			int len;
			if (null == prefix || (len = prefix.length()) == 0) {
				return 0;
			}

			// 开始从根节点遍历
			TNode startNode = this.root;

			// 遍历每个字符，遍历由根节点 - > 子节点的路径
			TNode son;
			for (int i = 0; i < len; i++) {
				// 首先查找startNode的子节点中是否存在字符charData[i]
				son = startNode.getSons().get(prefix.charAt(i));
				if (son == null) {
					// 这条路径上没有此字符串
					return 0;
				}
				// 迭代到下一个字符所在的节点
				startNode = son;
			}

			// 字符串沿路径遍历完，查看节点前缀计数值
			return startNode.getPrefixCount();
		}

	}



	/**
	 * 树节点
	 */
	static class TNode implements Serializable {
		private static final long serialVersionUID = 1L;

		/**
		 * 经过此节点的字符串数量，即前缀相同的字符串有多少。
		 */
		private int prefixCount;

		/**
		 * 字符串重复的数量，即有多少个字符串是相同的
		 */
		private int wordCount;

		/**
		 * 根节点到此节点的路径是否是一个完整的字符串
		 */
		private boolean wholeWord;

		/**
		 * 子节点 字符和节点的对应关系，树每层节点的字符都是不重复的 <br>
		 * 但是hashmap 会rehash，性能和空间耗费更多
		 */
		private transient Map<Character, TNode> sons;

		public TNode() {
			// 因为字符都不同，所以loadFactor设置为1.0，桶满了再rehash。
			this.sons = new HashMap<>(1 << 3, 1.0F);
			this.prefixCount = 0;
			this.wordCount = 0;
			this.wholeWord = false;
		}

		public int getPrefixCount() {
			return prefixCount;
		}

		public void setPrefixCount(int prefixCount) {
			this.prefixCount = prefixCount;
		}

		public int getWordCount() {
			return wordCount;
		}


		public void setWordCount(int wordCount) {
			this.wordCount = wordCount;
		}

		public boolean isWholeWord() {
			return wholeWord;
		}

		public void setWholeWord(boolean wholeWord) {
			this.wholeWord = wholeWord;
		}

		public Map<Character, TNode> getSons() {
			return sons;
		}

		public void setSons(Map<Character, TNode> sons) {
			this.sons = sons;
		}

		@Override
		public String toString() {
			return "TNode [prefixCount=" + prefixCount + ", wordCount=" + wordCount
					+ ", wholeWord=" + wholeWord + "]";
		}

	}
}
