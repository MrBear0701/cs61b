package mrbear;

/*
public static int max(int[] m)使用到目前为止在本作业中学到的所有知识，
您现在将创建一个具有返回 int 数组最大值的签名的函数。您可以假设所有数字都大于或等于零。

修改下面的代码（也可以 在此处找到），以便max按描述工作。
此外，进行修改，main以便max在给定数组上调用该方法并打印出其最大值
（在本例中，它应该 print 22）。
public class ClassNameHere {
    /** Returns the maximum value from m.
public static int max(int[] m) {
        return 0;
        }
public static void main(String[] args) {
        int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
        }
        }

练习3
将练习 2 的解决方案重写为使用循环for

 */








public class HW0_3 {
    /** Returns the maximum value from m. */
    public static int max(int[] m) {
        int maximum = m[0];
        for (int i = 0; i < m.length; i++) {
            if (m[i]>maximum)
                maximum = m[i];
        }
        return maximum;
    }
    public static void main(String[] args) {

        int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
        int maximum = max(numbers);
        System.out.println(maximum);

    }
}





























