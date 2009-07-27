package com.res0w.jwms.method;

public class KMPAlgorith {

    public static boolean kmp(String target, String pattern) {
        int pLen = pattern.length();
        int tLen = target.length();
        //the fail function
        int failFunc[] = new int[pLen];
        failFunc[0] = -1;
        //build fail function
        for (int i = 1; i < pLen; i++) {
            int j = failFunc[i - 1];
            while (pattern.charAt(i) != pattern.charAt(j + 1) && j >= 0) {
                //recursion
                j = failFunc[j];
            }
            if (pattern.charAt(i) == pattern.charAt(j + 1)) {
                failFunc[i] = j + 1;
            } else {
                failFunc[i] = -1;
            }
        }
        int pPos = 0, tPos = 0;
        while (tPos < tLen && pPos < pLen) {
            if (target.charAt(tPos) == pattern.charAt(pPos)) {
                //match ,then do forward
                tPos++;
                pPos++;
            } else if (pPos == 0) {
                //target go forward
                tPos++;
            } else {
                //target postion don't change,pattern go back
                pPos = failFunc[pPos - 1] + 1;
            }
        }
        if (pPos < pLen) {
            return false;
        } else {
            return true;
        }
    }
}