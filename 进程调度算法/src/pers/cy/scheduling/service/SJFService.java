package pers.cy.scheduling.service;

import pers.cy.scheduling.entity.PCB;
import pers.cy.scheduling.entity.Process;
import pers.cy.scheduling.factory.Factory;
import pers.cy.scheduling.util.Constant;
import pers.cy.scheduling.util.DeepCopyBySerialization;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SJFService implements Constant {
    private IOService ioService = Factory.getIOServiceInstance();

    public void SJF(List<Process> processList) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("短作业优先算法");
        System.out.println();

        // 输出作业情况
        ioService.outputJobCondition(processList);
        System.out.println();

        // 创建就绪队列
        LinkedList<Process> readyList = new LinkedList<>();

        // 深克隆processList   采用序列化方式   这样使用每种算法的时候都可以直接用这个最初始的没有修改过的作业数据，直接克隆一份对克隆体进行操作就行
        List<Process> tempList = DeepCopyBySerialization.deepCopy(processList);

        // 记录已完成进程数
        int completeCount = 0;
        // 记录进程总数
        int processCount = processList.size();
        // 记录进程的完成时间
        Map<String, Integer> completeTimeMap = new HashMap<>();
        // 记录当前事件
        int t = 0;

        // 当前正在运行的进程
        PCB runProcessPCB = null;

        // 将进程按照到达时间进入就绪队列并进行服务,当所有进程完成之后结束循环
        while (completeCount < processCount) {
            // 遍历所有要作业的进程，当到了该进程的到达时间时将该进程放入就绪队列
            for (int i = 0; i < processCount; i++) {
                PCB tempProcessPCB = tempList.get(i).getPcb();
                if (tempProcessPCB.getStatus() == STATUS_OUT) {
                    if (tempProcessPCB.getArrivalTime() == t) {
                        readyList.addLast(tempList.get(i));
                        tempProcessPCB.setStatus(STATUS_WAIT);
                        System.out.println(t + "\t" + "进程" + tempProcessPCB.getProcessName() + "到达内存\n");
                    }
                }
            }

            // 当就绪队列中不为空的时候,取出要执行的进程来执行
            if (readyList.size() != 0) {
                // 当前需要执行的进程对象为空，说明需要从就绪队列中找到服务时间最小的进程拿出来执行
                if (runProcessPCB == null) {
                    double minServiceTime = Double.MAX_VALUE;
                    int minIndex = -1;
                    // 遍历查找服务时间最小的进程
                    for (int i = 0; i < readyList.size(); i++) {
                        PCB tempPCB = readyList.get(i).getPcb();
                        if (tempPCB.getServiceTime() < minServiceTime) {
                            minServiceTime = tempPCB.getServiceTime();
                            runProcessPCB = tempPCB;
                            minIndex = i;
                        }
                    }
                    // 从就绪队列中取出要执行的进程
                    readyList.remove(minIndex);

                    runProcessPCB.setStatus(STATUS_RUN);
                    System.out.println(t + "\t" + "进程" + runProcessPCB.getProcessName() + "开始执行\n");
                    Thread.sleep(1000);
                }

                // 执行进程
                runProcessPCB.run();

                // 当进程执行完毕后将其从就绪队列中删除并将新队首进程开始执行
                if (runProcessPCB.getServiceTime() == -1) {
                    runProcessPCB.setStatus(STATUS_FINISH);
                    // 记录进程结束时间
                    completeTimeMap.put(runProcessPCB.getProcessName(), t);

                    readyList.removeFirst();
                    completeCount++;

                    System.out.println(t + "\t" + "进程" + runProcessPCB.getProcessName() + "结束\n");
                    Thread.sleep(1000);

                    if (readyList.size() != 0) {
                        runProcessPCB = readyList.element().getPcb();
                        runProcessPCB.setStatus(STATUS_RUN);
                        runProcessPCB.run();
                        System.out.println(t + "\t" + "进程" + runProcessPCB.getProcessName() + "开始执行\n");
                        Thread.sleep(1000);
                    }

                }
            }

            t++;
        }

        // 输出作业完成情况
        ioService.outputJobCompletion(processList, completeTimeMap);
    }
}
