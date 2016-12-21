/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.text.similarity;

/**
 * A similarity algorithm indicating the length of the longest common subsequence between two strings..
 *
 * <p>
 * The Longest common subsequence algorithm returns the length of the longest subsequence that two strings have in
 * common. Two strings that are entirely different, return a value of 0, and two strings that return a value
 * of the commonly shared length implies that the strings are completely the same in value and position.
 * <i>Note.</i>  Generally this algorithm is fairly inefficient, as for length <i>m</i>, <i>n</i> of the input
 * <code>CharSequence</code>'s <code>left</code> and <code>right</code> respectively, the runtime of the
 * algoirthm is <i>O(m*n)</i>.
 * </p>
 *
 * <p>
 * This implementation is based on the Longest Commons Substring algorithm
 * from <a href="https://en.wikipedia.org/wiki/Longest_common_subsequence_problem">
 * https://en.wikipedia.org/wiki/Longest_common_subsequence_problem</a>.
 * </p>
 *
 * <p>For further reading see:</p>
 *
 * <p>Lothaire, M. <i>Applied combinatorics on words</i>. New York: Cambridge U Press, 2005. <b>12-13</b></p>
 *
 * @since 1.0
 */
public class LongestCommonSubsequence implements SimilarityScore<Integer> {

    public Integer apply(final CharSequence left, final CharSequence right) {
        // Quick return for invalid inputs
        if (left == null || right == null) {
            throw new IllegalArgumentException("Inputs must not be null");
        }
        return longestCommonSubstringLengthArray(left,right)[left.length()][right.length()];
    }

    /**
     *
     * Computes the longestCommonSubsequence between the two strings given.
     *
     * <p>
     * Note, a substring and
     * subsequence are not necessarily the same thing. Indeed, <code>abcxyzqrs</code> and
     * <code>xyzghfm</code> have both the same common substring and subsequence, namely <code>xyz</code>. However,
     * <code>axbyczqrs</code> and <code>abcxyzqtv</code> have the longest common subsequence <code>xyzq</code> because a
     * subsequence need not have adjacent characters.
     * </p>
     *
     * <p>
     * For reference, we give the definition of a subsequence for the reader: a <i>subsequence</i> is a sequence that can be
     * derived from another sequence by deleting some elements without changing the order of the remaining elements.
     * </p>
     *
     * @param left
     * @param right
     * @return lcsLengthArray
     */
    public CharSequence logestCommonSubsequence(final CharSequence left, final CharSequence right) {
        // Quick return
        if (left == null || right == null) {
            throw new IllegalArgumentException("Inputs must not be null");
        }
        StringBuffer longestCommonSubstringArray = new StringBuffer(Math.max(left.length(), right.length()));
        int[][] lcsLengthArray = longestCommonSubstringLengthArray(left, right);
        int i = left.length() - 1;
        int j = right.length() - 1;
        int k = lcsLengthArray[left.length()][right.length()] - 1;
        while (k >= 0) {
            if (left.charAt(i) == right.charAt(j)) {
                longestCommonSubstringArray.append(left.charAt(i));
                i = i - 1;
                j = j - 1;
                k = k - 1;
            } else if (lcsLengthArray[i + 1][j] < lcsLengthArray[i][j + 1]) {
                i = i - 1;
            } else {
                j = j - 1;
            }
        }
        return longestCommonSubstringArray;
    }

    /**
     *
     * Computes the lcsLengthArray for the sake of doing the actual lcs calculation. This is the
     * dynamic programming portion of the algorithm, and is the reason for the runtime complexity being
     * O(m*n), where m=left.length() and n=right.length().
     *
     * @param left
     * @param right
     * @return lcsLengthArray
     */
    public int[][] longestCommonSubstringLengthArray(final CharSequence left, final CharSequence right) {
        int[][] lcsLengthArray = new int[left.length() + 1][right.length() + 1];
        for (int i=0; i < left.length(); i++) {
            for (int j=0; j < right.length(); j++) {
                if (i == 0) {
                    lcsLengthArray[i][j] = 0;
                }
                if (j == 0) {
                    lcsLengthArray[i][j] = 0;
                }
                if (left.charAt(i) == right.charAt(j)) {
                    lcsLengthArray[i + 1][j + 1] = lcsLengthArray[i][j] + 1;
                } else {
                    lcsLengthArray[i + 1][j + 1] = Math.max(lcsLengthArray[i + 1][j], lcsLengthArray[i][j + 1]);
                }
            }
        }
        return lcsLengthArray;
    }


    public int wikiLcs(CharSequence left, CharSequence right) {
        int m = left.length();
        int n = right.length();
        char[] x = left.toString().toCharArray();
        char[] y = right.toString().toCharArray();

        int[][] c = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++) {
            c[i][0] = 0;
        }

        for (int j = 0; j <= n; j++) {
            c[0][j] = 0;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (x[i - 1] == y[j - 1]) {
                    c[i][j] = c[i - 1][j - 1] + 1;

                } else {
                    c[i][j] = Math.max(c[i][j - 1], c[i - 1][j]);
                }
            }
        }

        return c[m][n];
    }

}
