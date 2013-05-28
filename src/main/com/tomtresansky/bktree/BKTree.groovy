package com.tomtresansky.bktree

class BKTree {
    BKNode root

    private static class BKNode {
        String word
        Map children = [:]

        BKNode(String word) {
            this.word = word
        }
    }

    BKTree(List dictionary) {
        assert dictionary.size() >= 1

        root = new BKNode(dictionary[0])
        dictionary[1..-1].each { w -> insertWord(w) }
    }

    void insertWord(String word) {
        LevenshteinDistanceFinder df = new LevenshteinDistanceFinder()

        df.findLevenshteinDistance
    }

    void print() {
        printRec(root)
    }

    void printRec(BKNode node) {
        println "$node.word -> ${ node.children.each { c -> c.word } }"
        node.children.each { c -> printRec(c) }
    }
}