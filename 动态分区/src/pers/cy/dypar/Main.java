package pers.cy.dypar;

import pers.cy.dypar.entity.Partition;
import pers.cy.dypar.util.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main implements Constant {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        // 创建初始分区分配表
        List<Partition> partitionList = new ArrayList<>();
        partitionList.add(new Partition(0, 0, 1024, STATUS_WAIT));

        while (true) {
            System.out.println("\n1. 分配内存\n");
            System.out.println("2. 回收内存\n");
            System.out.println("3. 显示内存使用情况\n");
            System.out.println("4. 退出\n");

            System.out.print("\n请输入选择：");
            int select = scan.nextInt();

            switch (select) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("输入有误,请重新输入");
                    break;
            }
        }
    }
}
