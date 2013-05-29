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

  /**
   * Uses example tree from http://hamberg.no/erlend/posts/2012-01-17-BK-trees.html
   */
  def "test simple tree building 2"() {
    given:
    def dictionary = [
      'cat',
      'cut',
      'hat',
      'man',
      'hit'
    ]

    when:
    def tree = new BKTree(dictionary)
    def nodes = tree.getNodes()

    then:
    nodes.size() == 5

    nodes[0].word == 'cat'
    nodes[0].children.size() == 2
    nodes[0].children[1] == nodes[1]
    nodes[0].children[2] == nodes[3]

    nodes[1].word == 'cut'
    nodes[1].children.size() == 1
    nodes[1].children[2] == nodes[2]

    nodes[2].word == 'hat'
    nodes[2].children.size() == 0

    nodes[3].word == 'man'
    nodes[3].children.size() == 1
    nodes[3].children[3] == nodes[4]

    nodes[4].word == 'hit'
    nodes[4].children.size() == 0
  }

  def "test build tree from string"() {
    given:
    def words = "Oh say can you see, by the dawn's early light, what so proudly we hailed, by the twilight's last gleaming?"

    when:
    def tree = new BKTree(words)

    then:
    tree.getNodes().toString() == ['Oh', 'say', 'can', 'see', 'you', 'what', 'by', 'the', 'so', 'we', "dawn's", 'hailed', 'early', 'light', 'last', 'proudly', "twilight's", 'gleaming'].toString()
  }

  def "test duplicate words filtered when building tree"() {
    given:
    def dictionary = ['the', 'cat', 'in', 'the', 'hat']

    when:
    def tree = new BKTree(dictionary)

    then:
    tree.getNodes().toString() == ['the', 'cat', 'in', 'hat'].toString()
  }

  def "test query"() {
    given:
    def words = "Oh say can you see, by the dawn's early light, what so proudly we hailed, by the twilight's last gleaming?"
    def tree = new BKTree(words)

    expect:
    result == tree.query(term, limit)

    where:
    term    | limit | result
    'Oh'    | 0     | ['Oh']
    'Saw'   | 2     | ['say', 'can']
    'Zebra' | 1     | []
    'earl'  | 4     | ['Oh', 'say', 'can', 'see', 'you', 'what', 'by', 'the', 'so', 'we', 'hailed', 'early', 'last']
  }
}
