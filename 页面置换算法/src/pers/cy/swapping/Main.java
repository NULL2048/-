package pers.cy.swapping;

import pers.cy.swapping.entity.PhysicalBlock;
import pers.cy.swapping.factory.Factory;
import pers.cy.swapping.service.FIFOService;
import pers.cy.swapping.service.LRUService;
import pers.cy.swapping.util.DeepCopyBySerialization;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static FIFOService fifoService = Factory.getFIFOServiceInstance();

    private static LRUService lruService = Factory.getLRUServiceInstance();

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);

        System.out.println("页面置换算法模拟程序\n");

        PhysicalBlock[] physicalBlocks = new PhysicalBlock[3];
        physicalBlocks[0] = new PhysicalBlock(0);
        physicalBlocks[1] = new PhysicalBlock(1);
        physicalBlocks[2] = new PhysicalBlock(2);

        PhysicalBlock[] physicalBlocks1 = new PhysicalBlock[3];
        physicalBlocks1[0] = new PhysicalBlock(0);
        physicalBlocks1[1] = new PhysicalBlock(1);
        physicalBlocks1[2] = new PhysicalBlock(2);

        while (true) {
            System.out.println("1. 初始化\n");
            System.out.println("2. FIFO算法\n");
            System.out.println("3. LRU算法\n");
            System.out.println("0. 退出");

            int select = scan.nextInt();

            switch (select) {
                case 1:
                    break;
                case 2:
                    fifoService.FIFO(physicalBlocks);
                    break;
                case 3:
                    lruService.LRU(physicalBlocks1);
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("输入有误,请重新输入");
                    break;
            }
        }
    }
}
