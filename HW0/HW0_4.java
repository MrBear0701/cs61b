package mrbear;

/*
这是一项特别具有挑战性的练习，但强烈推荐。

编写一个函数windowPosSum(int[] a, int n)，
将每个元素 a[i] 替换为 a[i] 到 a[i + n] 的总和，但前提是 a[i] 为正值。
如果由于到达数组末尾而没有足够的值，我们将仅对已有的值进行求和。

例如，假设我们windowPosSum使用数组a = {1, 2, -3, 4, 5, 4},
和进行调用n = 3。在这种情况下，我们会：

将 a[0] 替换为 a[0] + a[1] + a[2] + a[3]。
将 a[1] 替换为 a[1] + a[2] + a[3] + a[4]。
不要对 a[2] 做任何事情，因为它是负数。
将 a[3] 替换为 a[3] + a[4] + a[5]。
将 a[4] 替换为 a[4] + a[5]。
不要对 a[5] 执行任何操作，因为 a[5] 之后没有值。
因此，调用后的结果windowPosSum将是{4, 8, -3, 13, 9, 4}。

 */








public class HW0_4 {
    public static void windowPosSum(int[] a, int n) {
        /** your code here */
        for (int i = 0; i < a.length; i++) {
            if (a[i]>0)
            {
                for (int j = i+1; j <=i+n ; j++) {
                    if (j==a.length)
                        break;
                    a[i]+=a[j];
                }
            }
            else
                continue;
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 2, -3, 4, 5, 4};
        int n = 3;
        windowPosSum(a, n);

        // Should print 4, 8, -3, 13, 9, 4
        System.out.println(java.util.Arrays.toString(a));
    }
}





























