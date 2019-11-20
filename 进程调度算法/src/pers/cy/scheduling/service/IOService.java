package pers.cy.scheduling.service;

import pers.cy.scheduling.entity.Constant;
import pers.cy.scheduling.entity.PCB;
import pers.cy.scheduling.entity.Process;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IOService implements Constant {
    /**
     * 输入作业情况
     * @return 返回输入的作业信息
     */
    public List<Process> input() {
        Scanner scan = new Scanner(System.in);
        List<Process> processList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
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
}
