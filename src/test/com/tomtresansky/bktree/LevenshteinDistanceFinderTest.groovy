package com.tomtresansky.bktree

//@Grab(group='org.spockframework', module='spock-core', version='0.7-groovy-2.0')
import spock.lang.Specification

class LevenshteinDistanceFinderTest extends Specification {
  final LevenshteinDistanceFinder df = new LevenshteinDistanceFinder()

  def "test recursive find method"() {
    expect:
    df.findLevenshteinDistanceRec(s1, s2) == r

    where:
    r | s1          | s2
    3 | "kitten"    | "sitting"
    3 | "Saturday"  | "Sunday"
  }

  def "test full iterative find method"() {
    expect:
    df.findLevenshteinDistanceFullIterative(s1, s2) == r

    where:
    r | s1          | s2
    3 | "kitten"    | "sitting"
    3 | "Saturday"  | "Sunday"
  }

  def "test default quick iterative find method"() {
    expect:
    df.findLevenshteinDistance(s1, s2) == r

    where:
    r | s1          | s2
    3 | "kitten"    | "sitting"
    3 | "Saturday"  | "Sunday"
  }
}