package com.tomtresansky.bktree


/**
 * See http://java.dzone.com/articles/algorithm-week-bk-trees-part-1
 *
 * @author tom
 */
class BKTree {
  static def DEBUG = false

  BKNode root
  LevenshteinDistanceFinder df = new LevenshteinDistanceFinder()

  private static class BKNode {
    String word
    Map children = [:]

    @Override
    public String toString() {
      return word
    }
  }

  BKTree(List dictionary) {
    assert dictionary.size() >= 1

    if (DEBUG) println "Creating tree rooted at ${dictionary[0]}"
    root = new BKNode(word:dictionary[0])
    dictionary[1..-1].each { w -> insertWord(w) }
  }

  BKTree(String text) {
    this(text.split(/[^(a-zA-Z')]+/).collect())
  }

  void insertWord(String newWord) {
    if (DEBUG) println "Inserting $newWord"
    insertWordRec(root, newWord)
  }

  private void insertWordRec(BKNode node, String newWord) {
    def distance = df.findLevenshteinDistance(node.word, newWord)
    if (DEBUG) println "Distance between $node.word and $newWord is $distance"

    if (node.children.containsKey(distance)) {
      if (DEBUG) println "Following edge $distance (${node.children[distance]})"
      insertWordRec(node.children[distance], newWord)
    } else {
      if (DEBUG) println "Inserting $newWord under $node.word at $distance"
      node.children[distance] = new BKNode(word:newWord)
    }
  }

  void print() {
    printRec(root)
  }

  private void printRec(BKNode node) {
    println "$node.word -> ${ node.children.each { c -> c.value } }"
    node.children.each { c -> printRec(c.value) }
  }

  List getNodes() {
    return getNodesRec(root, [])
  }

  private List getNodesRec(BKNode node, List nodes) {
    nodes << node
    node.children.each { c -> getNodesRec(c.value, nodes) }
    return nodes
  }
}
