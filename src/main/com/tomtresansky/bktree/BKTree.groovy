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

    root = new BKNode(word:dictionary[0])
    dictionary[1..-1].each { w -> insertWord(w) }
  }

  void insertWord(String newWord) {
    insertWordRec(root, newWord)
  }

  private void insertWordRec(BKNode node, String newWord) {
    def distance = df.findLevenshteinDistance(root.word, newWord)
    // println "Distance between $root.word and $newWord is $distance"

    if (node.children.containsKey(distance)) {
      insertWordRec(node.children[distance], newWord)
    } else {
      node.children[distance] = new BKNode(word:newWord)
      // println "Inserted $newWord under $root.word"
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
      'Some',
      'Awesome',
      'Words',
      'Go',
      'Here',
      'Book',
      'Rook',
      'Hooks'
    ]

    def tree = new BKTree(dictionary)
    tree.print()
  }
}