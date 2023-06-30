package mrbear;

/*
打印如下图案：
 *
 **
 ***
 ****
 *****
 */


public class HW0_1a {
    public static void main(String[] args) {
        int line = 5;
        for (int i = 0; i <line ; i++) {
            for (int j = 0; j <= i; j++) {
                if (j==i)
                    System.out.println("*");
                else
                    System.out.print("*");
            }
        }

    }
}





























