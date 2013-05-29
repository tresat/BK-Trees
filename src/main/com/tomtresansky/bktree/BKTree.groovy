package com.tomtresansky.bktree


/**
 * See http://java.dzone.com/articles/algorithm-week-bk-trees-part-1
 *
 * @author tom
 */
class BKTree {
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

    println "Creating tree rooted at ${dictionary[0]}"
    root = new BKNode(word:dictionary[0])
    dictionary[1..-1].each { w -> insertWord(w) }
  }

  void insertWord(String newWord) {
    println "Inserting $newWord"
    insertWordRec(root, newWord)
  }

  private void insertWordRec(BKNode node, String newWord) {
    def distance = df.findLevenshteinDistance(node.word, newWord)
    println "Distance between $node.word and $newWord is $distance"

    if (node.children.containsKey(distance)) {
      println "Following edge $distance (${node.children[distance]})"
      insertWordRec(node.children[distance], newWord)
    } else {
      println "Inserting $newWord under $node.word at $distance"
      node.children[distance] = new BKNode(word:newWord)
      print()
    }
  }

  void print() {
    printRec(root)
  }

  private void printRec(BKNode node) {
    println "$node.word -> ${ node.children.each { c -> c.value } }"
    node.children.each { c -> printRec(c.value) }
  }


  static main(String... args) {
    def dictionary = [
      'book',
      'rook',
      'nooks',
      'boon'
    ]

    def tree = new BKTree(dictionary)
    tree.print()
  }
}