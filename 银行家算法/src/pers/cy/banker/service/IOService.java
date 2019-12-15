package pers.cy.banker.service;



import pers.cy.banker.util.Constant;
import pers.cy.banker.util.DeepCopyBySerialization;

import java.io.IOException;
import java.util.*;

public class IOService implements Constant {
    /**
     * 初始化资源分配表
     * @return 返回输入的作业信息
     */
    public void input(int[] available, int[][] max, int[][] allocation, int[][] need) {
//        Scanner scan = new Scanner(System.in);
//
//        System.out.println("Max:");
//        for (int i = 0; i < PROCESS_COUNT; i++) {
//            scan.nextInt();
//        }
    }

    /**
     * 输出当前资源分配表
     */
    public void outputResourceDistribution(int[] available, int[][] max, int[][] allocation, int[][] need) {
        System.out.println("\tMax\t\t |\tAllo\t |\tNeed\t |\tAvalia");
        for (int i = 0; i < PROCESS_COUNT; i++) {
            System.out.print("P" + i);
            for (int j = 0; j < RESOURCE_COUNT; j++) {
                System.out.print("\t" + max[i][j]);
            }
            System.out.print("|");

            for (int j = 0; j < RESOURCE_COUNT; j++) {
                System.out.print("\t" + allocation[i][j]);
            }
            System.out.print("|");

            for (int j = 0; j < RESOURCE_COUNT; j++) {
                System.out.print("\t" + need[i][j]);
            }
            System.out.print("|");

            if (i == 0) {
                for (int j = 0; j < RESOURCE_COUNT; j++) {
                    System.out.print("\t" + available[j]);
                }
            }

            System.out.println();
        }


    }

    /**
     * 输出安全序列
     * @param available
     * @param max
     * @param allocation
     * @param need
     * @param processSecuritySequence
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void outputSecuritySequence(int[] available, int[][] max, int[][] allocation, int[][] need, Stack<Integer> processSecuritySequence) throws IOException, ClassNotFoundException {
        // 深拷贝work，用来输出work+allocation
        int[] work = DeepCopyBySerialization.deepCopy(available);

        int[] securitySequence = new int[100];
        int size = processSecuritySequence.size() - 1;
        // 将安全序列放到数组中
        for (int i = size; i >= 0; i--) {
            securitySequence[i] = processSecuritySequence.pop();
        }

        System.out.println("\tWork\t |\tNeed\t |\tAllo\t |\tW+A\t\t |\tFinish");
        for (int k = 0; k < PROCESS_COUNT; k++) {
            int i = securitySequence[k];
            System.out.print("P" + i);
            for (int j = 0; j < RESOURCE_COUNT; j++) {
                System.out.print("\t" + work[j]);
            }
            System.out.print("|");

            for (int j = 0; j < RESOURCE_COUNT; j++) {
                System.out.print("\t" + need[i][j]);
            }
            System.out.print("|");

            for (int j = 0; j < RESOURCE_COUNT; j++) {
                System.out.print("\t" + allocation[i][j]);
            }
            System.out.print("|");


            for (int j = 0; j < RESOURCE_COUNT; j++) {
                work[j] = work[j] + allocation[i][j];
                System.out.print("\t" + work[j]);
            }

            System.out.print("|");

            System.out.println("\t" + true);

            System.out.println();
        }
    }
}
