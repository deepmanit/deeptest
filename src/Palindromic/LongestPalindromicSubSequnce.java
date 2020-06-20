package Palindromic;

public class LongestPalindromicSubSequnce {
    public int findLPSLength(String st) {
        return findLPSLengthRecursive(st, 0, st.length()-1);
    }
    int findLPSLengthRecursive(String str,int startIndex, int endIndex)
    {
        if(startIndex > endIndex)
            return 0;
        if(startIndex == endIndex)
            return 1;
        if(str.charAt(startIndex) == str.charAt(endIndex))
            return 2+findLPSLengthRecursive(str,startIndex+1,endIndex-1);
        else
        {
            int p1 = findLPSLengthRecursive(str,startIndex+1,endIndex);
            int p2 = findLPSLengthRecursive(str,startIndex,endIndex-1);
            return Math.max(p1,p2);
        }
    }
    public int findLPSLengthTopDown(String st) {
        Integer[][] dp = new Integer[st.length()][st.length()];
        return findLPSLengthRecursive(dp, st, 0, st.length()-1);
    }

    private int findLPSLengthRecursive(Integer[][] dp, String st, int startIndex, int endIndex) {
        if(startIndex > endIndex)
            return 0;

        // every sequence with one element is a palindrome of length 1
        if(startIndex == endIndex)
            return 1;

        if(dp[startIndex][endIndex] == null) {
            // case 1: elements at the beginning and the end are the same
            if(st.charAt(startIndex) == st.charAt(endIndex)) {
                dp[startIndex][endIndex] = 2 + findLPSLengthRecursive(dp, st, startIndex+1, endIndex-1);
            } else {
                // case 2: skip one element either from the beginning or the end
                int c1 =  findLPSLengthRecursive(dp, st, startIndex+1, endIndex);
                int c2 =  findLPSLengthRecursive(dp, st, startIndex, endIndex-1);
                dp[startIndex][endIndex] = Math.max(c1, c2);
            }
        }

        return dp[startIndex][endIndex];
    }
    public int findLPSLengthDP(String st) {
        // dp[i][j] stores the length of LPS from index 'i' to index 'j'
        int[][] dp = new int[st.length()][st.length()];

        // every sequence with one element is a palindrome of length 1
        for (int i = 0; i < st.length(); i++)
            dp[i][i] = 1;

        for (int startIndex = st.length() - 1; startIndex >= 0; startIndex--) {
            for (int endIndex = startIndex + 1; endIndex < st.length(); endIndex++) {
                // case 1: elements at the beginning and the end are the same
                if (st.charAt(startIndex) == st.charAt(endIndex)) {
                    dp[startIndex][endIndex] = 2 + dp[startIndex + 1][endIndex - 1];
                } else { // case 2: skip one element either from the beginning or the end
                    dp[startIndex][endIndex] = Math.max(dp[startIndex + 1][endIndex], dp[startIndex][endIndex - 1]);
                }
            }
        }
        return dp[0][st.length() - 1];
    }
}
