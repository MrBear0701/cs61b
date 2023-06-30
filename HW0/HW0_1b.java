package mrbear;

/*
创意练习 1b：画三角形
从Java 可视化工具中的默认程序（也可以 在此处找到）开始，\
使用一种附加方法创建一个程序（除了打开可视化工具时存在的默认 main 方法之外）。

命名这个新方法drawTriangle并为其指定返回类型void（这意味着它根本不返回任何内容）。

该drawTriangle方法应该采用一个名为 的参数N，
并且它应该打印出一个与练习 1a 中的三角形完全相同的三角形，但N 星号高而不是5。

编写完成后DrawTriangle，修改main函数，使其调用 DrawTrianglewith N = 10。
 */


public class HW0_1b {
    public static void main(String[] args) {
        HW0_1b obj = new HW0_1b();
        obj.drawTriangle(10);
    }

    public void drawTriangle(int N)
    {
        for (int i = 0; i <N ; i++) {
            for (int j = 0; j <= i; j++) {
                if (j==i)
                    System.out.println("*");
                else
                    System.out.print("*");
            }
        }
    }
}





























