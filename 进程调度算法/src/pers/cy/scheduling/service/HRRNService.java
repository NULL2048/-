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

public class HRRNService implements Constant {
    private IOService ioService = Factory.getIOServiceInstance();

    public void HRRN(List<Process> processList) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("\n高响应比优先调度算法");
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
        // 记录当前时间
        int t = 0;

        // 当前正在运行的进程
        PCB runProcessPCB = null;

        // 将进程按照到达时间进入就绪队列并准备进行服务,当所有进程完成之后结束循环
        while (completeCount < processCount) {
            // 遍历所有要作业的进程，当到了该进程的到达时间时将该进程放入就绪队列
            for (int i = 0; i < processCount; i++) {
                PCB tempProcessPCB = tempList.get(i).getPcb();
                if (tempProcessPCB.getStatus() == STATUS_OUT) {
                    if (tempProcessPCB.getArrivalTime() == t) {
                        readyList.addLast(tempList.get(i));
                        tempProcessPCB.setStatus(STATUS_WAIT);
                        System.out.println(t + "\t" + "进程" + tempProcessPCB.getProcessName() + "到达内存\n");
                        Thread.sleep(BLOCK_TIME);
                    }
                }
            }

            // 当就绪队列中不为空的时候,取出要执行的进程来执行
            if (readyList.size() != 0 || runProcessPCB != null) {
                // 当前需要执行的进程对象为空，说明需要从就绪队列中找到优先级最大的进程拿出来执行
                if (runProcessPCB == null) {
                    runProcessPCB = getMaxPriorityProcess(readyList, t);
                }

                // 执行进程
                runProcessPCB.run();

                // 当进程执行完毕后将其状态置为完成状态并将当前正在执行的进程对象置为null
                if (runProcessPCB.getServiceTime() == -1) {
                    runProcessPCB.setStatus(STATUS_FINISH);

                    // 记录进程结束时间
                    completeTimeMap.put(runProcessPCB.getProcessName(), t);

                    completeCount++;

                    System.out.println(t + "\t" + "进程" + runProcessPCB.getProcessName() + "结束\n");
                    Thread.sleep(BLOCK_TIME);

                    // 将正在执行的进程对象置为null
                    runProcessPCB = null;

                    if (readyList.size() != 0) {
                        runProcessPCB = getMaxPriorityProcess(readyList, t);

                        // 执行进程
                        runProcessPCB.run();
                    }
                }
            }
            t++;
        }
        // 输出作业完成情况
        ioService.outputJobCompletion(processList, completeTimeMap);
    }

    /**
     * 在就绪队列中获取优先级最大的进程
     * @param readyList 就绪队列
     * @param t 当前时间
     * @return 优先级最大的进程
     * @throws InterruptedException
     */
    private PCB getMaxPriorityProcess(LinkedList<Process> readyList, int t) throws InterruptedException {
        PCB runProcessPCB = null;

        double maxPriority = -Double.MAX_VALUE;
        int maxIndex = -1;
        // 遍历查找服务时间最小的进程
        for (int i = 0; i < readyList.size(); i++) {
            PCB tempPCB = readyList.get(i).getPcb();

            // 计算当前进程的优先级
            double priority = (t - tempPCB.getArrivalTime() + tempPCB.getServiceTime()) / tempPCB.getServiceTime();
            tempPCB.setPriority(priority);

            if (tempPCB.getPriority() > maxPriority) {
                maxPriority = tempPCB.getPriority();
                runProcessPCB = tempPCB;
                maxIndex = i;
            }
        }

        // 从就绪队列中取出要执行的进程
        readyList.remove(maxIndex);

        runProcessPCB.setStatus(STATUS_RUN);
        System.out.println(t + "\t" + "进程" + runProcessPCB.getProcessName() + "开始执行\n");
        Thread.sleep(BLOCK_TIME);

        return runProcessPCB;
    }
}
