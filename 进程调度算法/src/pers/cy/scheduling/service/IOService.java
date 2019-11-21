package pers.cy.scheduling.service;

import pers.cy.scheduling.util.Constant;
import pers.cy.scheduling.entity.PCB;
import pers.cy.scheduling.entity.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class IOService implements Constant {
    /**
     * 输入作业情况
     * @return 返回输入的作业信息
     */
    public List<Process> input() {
        Scanner scan = new Scanner(System.in);
        List<Process> processList = new ArrayList<>();

        for (int i = 0; i < PROCESS_COUNT; i++) {
            System.out.println("进程" + i + ":");

            System.out.print("进程名：");
            String processName = scan.next();

            System.out.print("到达时间：");
            double arrivalTime = scan.nextDouble();

            System.out.print("服务时间");
            double serviceTime = scan.nextDouble();

            PCB pcb = new PCB(processName, -1, arrivalTime, serviceTime, STATUS_OUT);

            Process process = new Process(pcb, processName + "的程序段", processName + "的数据段");

            processList.add(process);
        }
        return processList;
    }

    /**
     * 显示作业情况
     * @param processList 作业的信息
     */
    public void outputJobCondition(List<Process> processList) {
        if (processList == null || processList.size() < 5) {
            System.out.println("请输入作业情况！");
            return;
        }

        System.out.print("进程名：\t");
        for (int i = 0; i < 5; i++) {
            System.out.print("\t\t" + processList.get(i).getPcb().getProcessName());
        }
        System.out.print("\n到达时间：");
        for (int i = 0; i < 5; i++) {
            System.out.print("\t\t" + processList.get(i).getPcb().getArrivalTime());
        }
        System.out.print("\n服务时间：");
        for (int i = 0; i < 5; i++) {
            System.out.print("\t\t" + processList.get(i).getPcb().getServiceTime());
        }

        System.out.println();
    }

    public void outputJobCompletion(List<Process> processList, Map<String, Integer> completeTimeMap) {
        System.out.println("进程名\t\t到达时间\t\t服务时间\t\t完成时间\t\t周转时间\t\t带权周转");

        double sumTurnaroundTime = 0;
        double sumWeightedTurnaroundTime = 0;

        for (Process process : processList) {
            PCB processPCB = process.getPcb();

            // 完成时间
            int completeTime = completeTimeMap.get(processPCB.getProcessName());

            // 周转时间 = 完成时间 - 到达时间
            double turnaroundTime = completeTime - processPCB.getArrivalTime();

            // 带权周转时间 = 周转时间 / 服务时间
            double weightedTurnaroundTime = turnaroundTime / processPCB.getServiceTime();

            System.out.print(processPCB.getProcessName() + "\t\t\t");
            System.out.printf("%.1f\t\t\t", processPCB.getArrivalTime());
            System.out.printf("%.1f\t\t\t", processPCB.getServiceTime());
            System.out.print(completeTime + "\t\t\t");
            System.out.printf("%.2f\t\t", turnaroundTime);
            System.out.printf("%.2f\n", weightedTurnaroundTime);

            sumTurnaroundTime += turnaroundTime;
            sumWeightedTurnaroundTime += weightedTurnaroundTime;
        }

        // 平均周转时间和平均带权周转事件
        System.out.printf("\n平均周转时间：%.2f\t\t", sumTurnaroundTime / processList.size());
        System.out.printf("平均带权周转时间：%.2f\n", sumWeightedTurnaroundTime / processList.size());
    }
}
