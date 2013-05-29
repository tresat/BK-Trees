package com.tomtresansky.bktree

import spock.lang.Specification

class BKTreeTest extends Specification {
  /**
   * Uses example tree built in http://blog.notdot.net/2007/4/Damn-Cool-Algorithms-Part-1-BK-Trees
   */
  def "test simple tree building"() {
    given:
    def dictionary = [
      'book',
      'rook',
      'nooks',
      'boon'
    ]

    when:
    def tree = new BKTree(dictionary)
    def nodes = tree.getNodes()

    then:
    nodes.size() == 4

    nodes[0].word == 'book'
    nodes[0].children.size() == 2
    nodes[0].children[1] == nodes[1]
    nodes[0].children[2] == nodes[3]

    nodes[1].word == 'rook'
    nodes[1].children.size() == 1
    nodes[1].children[2] == nodes[2]

    nodes[2].word == 'boon'
    nodes[2].children.size() == 0

    nodes[3].word == 'nooks'
    nodes[3].children.size() == 0
  }
}
