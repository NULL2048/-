package pers.cy.banker.service;



import pers.cy.banker.util.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

    public void outputJobCompletion(int[] available, int[][] max, int[][] allocation, int[][] need) {
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
