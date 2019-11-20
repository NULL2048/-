package pers.cy.scheduling;

import pers.cy.scheduling.entity.Process;
import pers.cy.scheduling.factory.Factory;
import pers.cy.scheduling.service.IOService;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static IOService ioService = Factory.getIOServiceInstance();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<Process> processList = null;

        while (true) {
            System.out.println("\n进程调度模拟程序");
            System.out.println("1. 输入作业情况");
            System.out.println("2. 显示作业情况");
            System.out.println("3. 先来先服务算法");
            System.out.println("4. 短作业优先算法");
            System.out.println("5. 高响应比优先算法");
            System.out.println("6. 时间片轮转算法");
            System.out.println("7. 多级反馈队列调度");
            System.out.println("8. 算法结果对比");
            System.out.println("0. 退出");

            System.out.println("\n请输入选择");
            int select = scan.nextInt();

            switch (select) {
                case 1:
                    processList = ioService.input();
                    break;
                case 2:
                    ioService.outputJobCondition(processList);
                    break;
                case 0:
                    System.exit(0);
            }
        }

    }
}
