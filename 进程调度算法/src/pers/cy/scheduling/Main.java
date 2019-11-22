package pers.cy.scheduling;

import pers.cy.scheduling.service.*;
import pers.cy.scheduling.util.Constant;
import pers.cy.scheduling.entity.PCB;
import pers.cy.scheduling.entity.Process;
import pers.cy.scheduling.factory.Factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main implements Constant {
    private static IOService ioService = Factory.getIOServiceInstance();

    private static FCFSService fcfsService = Factory.getFCFSServiceInstance();

    private static SJFService sjfService = Factory.getSJFServiceInstance();

    private static HRRNService hrrnService = Factory.getHRRNServiceInstance();

    private static RRService rrService = Factory.getRRServiceInstance();

    private static MFQService mfqService = Factory.getMFQServiceInstance();

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        List<Process> processList = null;
        processList = init();

        while (true) {
            System.out.println("\n进程调度模拟程序\n");
            System.out.println("1. 输入作业情况\n");
            System.out.println("2. 显示作业情况\n");
            System.out.println("3. 先来先服务算法\n");
            System.out.println("4. 短作业优先算法\n");
            System.out.println("5. 高响应比优先算法\n");
            System.out.println("6. 时间片轮转算法\n");
            System.out.println("7. 多级反馈队列调度\n");
            System.out.println("8. 算法结果对比\n");
            System.out.println("0. 退出\n");

            System.out.print("\n请输入选择：");
            int select = scan.nextInt();

            switch (select) {
                case 1:
                    processList = ioService.input();
                    break;
                case 2:
                    ioService.outputJobCondition(processList);
                    break;
                case 3:
                    fcfsService.FCFS(processList);
                    break;
                case 4:
                    sjfService.SJF(processList);
                    break;
                case 5:
                    hrrnService.HRRN(processList);
                    break;
                case 6:
                    rrService.RR(processList);
                    break;
                case 7:
                    mfqService.MFQ(processList);
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("输入有误,请重新输入");
                    break;
            }
        }
    }

    public static List<Process> init() {
        List<Process> processList = new ArrayList<>();

        String[] a1 = {"A", "B", "C", "D", "E"};
        double[] a2 = {0, 2, 4, 6, 8};
        double[] a3 = {3, 6, 4, 5, 2};

        for (int i = 0; i < 5; i++) {
            String processName = a1[i];

            double arrivalTime = a2[i];

            double serviceTime = a3[i];

            PCB pcb = new PCB(processName, -1.0, arrivalTime, serviceTime, STATUS_OUT);

            Process process = new Process(pcb, processName + "的程序段", processName + "的数据段");

            processList.add(process);
        }

        return processList;
    }
}
