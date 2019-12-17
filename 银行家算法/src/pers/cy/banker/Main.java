package pers.cy.banker;

import pers.cy.banker.factory.Factory;
import pers.cy.banker.service.BankerService;
import pers.cy.banker.service.IOService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static IOService ioService = Factory.getIOServiceInstance();
    private static BankerService bankerService = Factory.getBankerServiceInstance();


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);

        // available[j] = K 表示系统中现有Rj资源K个
        int[] available = new int[100];
        // max[i][j] = K 表示进程i需要Rj资源的最大数目为K
        int[][] max = new int[100][100];
        // allocation[i][j] = K 表示进程i当前已经分得了Rj资源K个
        int[][] allocation = new int[100][100];
        // need[i][j] = K 表示进程i还需要Rj资源K个方能完成任务
        int[][] need = new int[100][100];

        init(available, max, allocation, need);

        while (true) {
            System.out.println("\n1. 初始化\n");
            System.out.println("2. 查看当前资源分配表\n");
            System.out.println("3. 请求资源\n");
            System.out.println("4. 退出\n");

            System.out.print("\n请输入选择：");
            int select = scan.nextInt();

            switch (select) {
                case 1:
                    break;
                case 2:
                    ioService.outputResourceDistribution(available, max, allocation, need);
                    break;
                case 3:
                    bankerService.resourceRequestCheck(available, max, allocation, need);
                    break;
                case 4:
                    System.exit(0);
                default:
                    System.out.println("输入有误,请重新输入");
                    break;
            }
        }
    }

    /**
     * 初始化输入数据
     * @param available
     * @param max
     * @param allocation
     * @param need
     */
    public static void init(int[] available, int[][] max, int[][] allocation, int[][] need) {
        max[0][0] = 7;
        max[0][1] = 5;
        max[0][2] = 3;

        max[1][0] = 3;
        max[1][1] = 2;
        max[1][2] = 2;

        max[2][0] = 9;
        max[2][1] = 0;
        max[2][2] = 2;

        max[3][0] = 2;
        max[3][1] = 2;
        max[3][2] = 2;

        max[4][0] = 4;
        max[4][1] = 3;
        max[4][2] = 3;

        allocation[0][0] = 0;
        allocation[0][1] = 1;
        allocation[0][2] = 0;

        allocation[1][0] = 2;
        allocation[1][1] = 0;
        allocation[1][2] = 0;

        allocation[2][0] = 3;
        allocation[2][1] = 0;
        allocation[2][2] = 2;

        allocation[3][0] = 2;
        allocation[3][1] = 1;
        allocation[3][2] = 1;

        allocation[4][0] = 0;
        allocation[4][1] = 0;
        allocation[4][2] = 2;

        need[0][0] = 7;
        need[0][1] = 4;
        need[0][2] = 3;

        need[1][0] = 1;
        need[1][1] = 2;
        need[1][2] = 2;

        need[2][0] = 6;
        need[2][1] = 0;
        need[2][2] = 0;

        need[3][0] = 0;
        need[3][1] = 1;
        need[3][2] = 1;

        need[4][0] = 4;
        need[4][1] = 3;
        need[4][2] = 1;

        available[0] = 3;
        available[1] = 3;
        available[2] = 2;
    }
}
