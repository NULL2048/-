package pers.cy.scheduling.service;

import pers.cy.scheduling.entity.PCB;
import pers.cy.scheduling.entity.Process;
import pers.cy.scheduling.factory.Factory;
import pers.cy.scheduling.util.Constant;
import pers.cy.scheduling.util.DeepCopyBySerialization;

import java.io.IOException;
import java.util.*;

public class RRService implements Constant {
    private IOService ioService = Factory.getIOServiceInstance();

    public void RR(List<Process> processList) throws IOException, ClassNotFoundException, InterruptedException {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n时间片轮转调度算法");
        System.out.println();

        System.out.print("请输入时间片长度：");
        double q = scan.nextDouble();
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
        Process runProcess = null;

        // 将进程按照到达时间进入就绪队列并准备进行服务,当所有进程完成之后结束循环
        while (completeCount < processCount) {
            // 将到达内存的进程放入就绪队列     监听是否有进程到达内存有一个原则，保证每一个时间也就是t都有且只有一次监听操作，多了没用，少了就会出错
            arriveMemoryListener(tempList, readyList, processCount, t);

            if (readyList.size() != 0) {
                // 将就绪队列队首的进程取出执行
                runProcess = readyList.removeFirst();
                runProcess.getPcb().setStatus(STATUS_RUN);
                System.out.println(t + "\t" + "进程" + runProcess.getPcb().getProcessName() + "开始执行");
                outputReadyList(readyList);
                Thread.sleep(BLOCK_TIME);

                // 按照时间片长度来执行进程
                for (int i = 0; i < q; i++) {
                    // 执行进程
                    runProcess.getPcb().run();

                    // 这里又碰到了最开始学习算法时候容易出现的错误，就是相同类型对变量的修改操作一定要紧挨在一起
                    // 比如这个时间增加操作，因为上面是run()执行操作，执行之后伴随着必然要时间向前推移，所以一定要将这个t++紧挨着run()操作，如果在他俩之间插入别的相关操作就会出现问题
                    t++;
                    // 将到达内存的进程放入就绪队列
                    arriveMemoryListener(tempList, readyList, processCount, t);

                    // 执行完的进程只有两种情况  要么执行完了，要么没执行完
                    // 当该进程在自己的时间片内执行完成，就抢该进程从就绪队列删除，收回本次时间片，创建一个新的时间片给新的队首进程
                    if (runProcess.getPcb().getServiceTime() <= 0) {
                        // 进程执行完就将该进程从就绪队列中删除
                        runProcess.getPcb().setStatus(STATUS_FINISH);

                        // 记录当前进程的完成时间
                        completeTimeMap.put(runProcess.getPcb().getProcessName(), t);

                        System.out.println(t + "\t" + "进程" +  runProcess.getPcb().getProcessName() + "结束");
                        outputReadyList(readyList);
                        Thread.sleep(BLOCK_TIME);

                        // 将当前进程置为null
                        runProcess = null;

                        completeCount++;

                        // 因为进程在时间片内执行完成，所以要完成本轮时间需要做的监听进程到达情况，然后自增时间后结束掉本次时间片，即跳出循环
                        arriveMemoryListener(tempList, readyList, processCount, t);
                        // 当在执行时间片过程中进程执行结束，根据RR算法应该立即结束这个进程，然后马上在当前t这个时间点加载新的进程进行执行，所以这里不能加t++，因为上面run()操作已经有了t++了，这里再增加时间就相当于什么也没有干就无缘无故增加时间，就会将时间错误地向前推进
                        //t++;

                        // 直接结束点本次时间片，跳出循环
                        break;
                    }
                }

                // 如果runProcess不为null，就表示当前时间片用完了也没有将进程执行完，输出时间片用完
                if (runProcess != null) {
                    // 没执行完就将该进程执行一个时间片之后插入就绪队列队尾
                    runProcess.getPcb().setStatus(STATUS_WAIT);
                    readyList.addLast(runProcess);

                    System.out.println(t + "\t" + "进程" +  runProcess.getPcb().getProcessName() + "时间片用完");
                    outputReadyList(readyList);
                    Thread.sleep(BLOCK_TIME);
                }
            }

        }
        // 输出作业完成情况
        ioService.outputJobCompletion(processList, completeTimeMap);
    }

    /**
     * 输出就绪队列
     * @param readyList 就绪队列
     */
    public void outputReadyList(List<Process> readyList) {
        System.out.print("\t\t当前就绪队列：");
        for (Process process : readyList) {
            PCB processPCB = process.getPcb();
            System.out.print(processPCB.getProcessName());
        }
        System.out.println("\n");
    }

    /**
     * 监听是否有进程到达内存
     * @param tempList
     * @param readyList
     * @param processCount
     * @param t
     * @throws InterruptedException
     */
    public void arriveMemoryListener(List<Process> tempList, LinkedList<Process> readyList, int processCount, int t) throws InterruptedException {
        // 遍历所有要作业的进程，当到了该进程的到达时间时将该进程放入就绪队列
        for (int i = 0; i < processCount; i++) {
            PCB tempProcessPCB = tempList.get(i).getPcb();
            if (tempProcessPCB.getStatus() == STATUS_OUT) {
                if (tempProcessPCB.getArrivalTime() == t) {
                    readyList.addLast(tempList.get(i));
                    tempProcessPCB.setStatus(STATUS_WAIT);
                    System.out.println(t + "\t" + "进程" + tempProcessPCB.getProcessName() + "到达内存");
                    outputReadyList(readyList);
                    Thread.sleep(BLOCK_TIME);
                }
            }
        }
    }
}
