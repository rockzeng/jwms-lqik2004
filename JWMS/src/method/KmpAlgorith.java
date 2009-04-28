package method;

public class KmpAlgorith {

    private static int[] preProcess(char[] B) {
        int size = B.length;
        int[] P = new int[size];
        P[0] = 0;
        int j = 0;
        //每循环一次，就会找到一个回退位置
        for (int i = 1; i < size; i++) {
            //当找到第一个匹配的字符时，即j>0时才会执行这个循环
            //或者说p2中的j++会在p1之前执行（限于第一次执行的条件下）
            //p1
            while (j > 0 && B[j] != B[i]) {
                j = P[j];
            }
            //p2，由此可以看出，只有当子串中含有重复字符时，回退的位置才会被优化
            if (B[j] == B[i]) {
                j++;
            }
            //找到一个回退位置j，把其放入P[i]中
            P[i] = j;
        }
        return P;
    }

    /**
    　　 * KMP实现
    　　 * @param parStr
    　　 * @param subStr
    　　 * @return
    　　 */
    public static boolean  kmp(String parStr, String subStr) {
        boolean rslt=false;
        int subSize = subStr.length();
        int parSize = parStr.length();
        char[] B = subStr.toCharArray();
        char[] A = parStr.toCharArray();
        int[] P = preProcess(B);
        int j = 0;
        int k = 0;
        for (int i = 0; i < parSize; i++) {
            //当找到第一个匹配的字符时，即j>0时才会执行这个循环
            //或者说p2中的j++会在p1之前执行（限于第一次执行的条件下）
            //p1
            while (j > 0 && B[j] != A[i]) {
                //找到合适的回退位置
                j = P[j - 1];
            }
            //p2 找到一个匹配的字符
            if (B[j] == A[i]) {
                j++;
            }
            //输出匹配结果，并且让比较继续下去
            if (j == subSize) {
                rslt=true;
                break;

                //根据程序需要找到一个字串即可，不必再继续比下去了。
                /*j = P[j - 1];
                k++;
                System.out.printf("Find subString '%s' at %d\n", subStr, i - subSize + 1);
                 * */
            }
        }
        return rslt;
       // System.out.printf("Totally found %d times for '%s'.\n\n", k, subStr);
    }

    public static void main(String[] args) {
        boolean s;
        s=kmp("91-101","101");
        System.out.print(s);
    }
}