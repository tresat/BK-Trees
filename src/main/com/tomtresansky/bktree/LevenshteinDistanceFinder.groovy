package com.tomtresansky.bktree

class LevenshteinDistanceFinder {
  def findLevenshteinDistanceRec(String s, String t) {
    def lenS = s.length()
    def lenT = t.length()

    /* test for degenerate cases of empty strings */
    if (lenS == 0) return lenT;
    if (lenT == 0) return lenS;

    /* test if last characters of the strings match */
    def cost = (s[-1] == t[-1]) ? 0 : 1

    def choppedS = s.size() > 1 ? s[0..-2] : ""
    def choppedT = t.size() > 1 ? t[0..-2] : ""

    /* return minimum of delete char from s, delete char from t, and delete char from both */
    return Math.min(findLevenshteinDistanceRec(choppedS, t) + 1,
        Math.min(findLevenshteinDistanceRec(s, choppedT) + 1,
            findLevenshteinDistanceRec(choppedS, choppedT) + cost))
  }

  def findLevenshteinDistanceFullIterative(String s, String t) {
    def m = s.size()
    def n = t.size()

    // for all i and j, d[i,j] will hold the Levenshtein distance between
    // the first i characters of s and the first j characters of t;
    // note that d has (m+1)*(n+1) values
    List d = []
    (m+1).times { d << [0]*(n+1) }

    // source prefixes can be transformed into empty string by
    // dropping all characters
    [*0..m].each { i -> d[i][0] = i }

    // target prefixes can be reached from empty source prefix
    // by inserting every characters
    [*0..n].each { j -> d[0][j] = j }

    for (j in 1..n) {
      for (i in 1..m) {
        if (s[i-1] == t[j-1]) {
          d[i][j] = d[i-1][j-1]       // no operation required
        } else {
          d[i][j] = Math.min(d[i-1][j] + 1 /* a deletion */, Math.min(d[i][j-1] + 1 /* an insertion */, d[i-1][j-1] + 1 /* a substitution */))
        }
      }
    }

    //d.each { row -> println row }

    return d[m-1][n-1]
  }

  def findLevenshteinDistance(String s, String t) {
    // degenerate cases
    if (s == t) return 0;

    def sSize = s.size()
    def tSize = t.size()

    if (sSize == 0) return tSize;
    if (tSize == 0) return sSize;

    // create two work vectors of integer distances
    List v0 = [*0..(tSize + 1)]
    List v1 = [0]*(tSize + 1)

    for (int i = 0; i < sSize; i++) {
      // calculate v1 (current row distances) from the previous row v0

      // first element of v1 is A[i+1][0]
      //   edit distance is delete (i+1) chars from s to match empty t
      v1[0] = i + 1;

      // use formula to fill in the rest of the row
      for (int j = 0; j < tSize; j++) {
        def cost = (s[i] == t[j]) ? 0 : 1;
        v1[j + 1] = Math.min(v1[j] + 1, Math.min(v0[j + 1] + 1, v0[j] + cost));
      }

      // copy v1 (current row) to v0 (previous row) for next interation
      for (int j = 0; j < v0.size(); j++)
        v0[j] = v1[j];
    }

    return v1[tSize];
  }
}
