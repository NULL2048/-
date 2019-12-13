package pers.cy.scheduling.service;

import pers.cy.scheduling.entity.FeedbackQueue;
import pers.cy.scheduling.entity.PCB;
import pers.cy.scheduling.entity.Process;
import pers.cy.scheduling.factory.Factory;
import pers.cy.scheduling.util.Constant;
import pers.cy.scheduling.util.DeepCopyBySerialization;

import java.io.IOException;
import java.util.*;

public class MFQService implements Constant {
    private IOService ioService = Factory.getIOServiceInstance();

    public void MFQ(List<Process> processList) throws IOException, ClassNotFoundException, InterruptedException {
        Scanner scan = new Scanner(System.in);

        System.out.println("\n多级反馈队列调度算法");
        System.out.println();

        System.out.print("请输入多级反馈队列数：");
        int n = scan.nextInt();
        System.out.println();

        System.out.print("请输入第一个就绪队列的时间片大小：");
        int q = scan.nextInt();
        System.out.println();

        // 输出作业情况
        ioService.outputJobCondition(processList);
        System.out.println();

        // 创建n个就绪队列
        List<FeedbackQueue> feedbackQueueLists = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            FeedbackQueue feedbackQueue = new FeedbackQueue(q);
            feedbackQueueLists.add(feedbackQueue);

            // 每一级队列的时间片都是上一级的一倍
            q *= 2;
        }

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
            arriveMemoryListener(tempList, feedbackQueueLists, processCount, t);

            // 用来标记当前多级队列中，不为空的最上面的一级队列的下标
            int i = 0;
            while (true) {
                // 标记当前有没有新的进程进入到一级队列
                // boolean flag = false;
                // 找到当前最上层的不为空的反馈队列，每一次都要从最上层的队列向下找，找到第一个不为空的队列
                for (i = 0; i < feedbackQueueLists.size(); i++) {
                    if (feedbackQueueLists.get(i).getReadyList().size() != 0) {
                        break;
                    }
                }

                // 如果i<feedbackQueueLists.size()说明多级队列中还有不为空的队列，如果没找到，就说明当前所有的就绪队列中没有进程，可以暂时跳出执行模块了
                if (i < feedbackQueueLists.size()) {
                    // 获取当前反馈队列
                    FeedbackQueue feedbackQueue = feedbackQueueLists.get(i);
                    // 获取当前级别的就绪队列
                    LinkedList<Process> readyList = feedbackQueue.getReadyList();
                    // 获取当前级别就绪队列的时间片长度
                    int nowP = feedbackQueue.getTimeslice();

                    // 如果当前就绪队列中有进程等待，则进行执行
                    while (readyList.size() != 0) {
                        // 将就绪队列队首的进程取出执行
                        runProcess = readyList.removeFirst();
                        runProcess.getPcb().setStatus(STATUS_RUN);
                        System.out.println(t + "\t" + "进程" + runProcess.getPcb().getProcessName() + "开始执行");
                        outputMultilevedFeedbackQueue(feedbackQueueLists);
                        Thread.sleep(BLOCK_TIME);

                        // 按照时间片长度来执行进程
                        for (int j = 0; j < nowP; j++) {
                            // 执行进程
                            runProcess.getPcb().run();

                            // 这里又碰到了最开始学习算法时候容易出现的错误，就是相同类型对变量的修改操作一定要紧挨在一起
                            // 比如这个时间增加操作，因为上面是run()执行操作，执行之后伴随着必然要时间向前推移，所以一定要将这个t++紧挨着run()操作，如果在他俩之间插入别的相关操作就会出现问题
                            t++;
                            // 将到达内存的进程放入就绪队列  如果有新的进程进入到了1级队列，就直接结束当前队列的执行，将其放到i级队列队尾，跳出从1级队列开始执行
                            if (arriveMemoryListener(tempList, feedbackQueueLists, processCount, t)) {
//                                runProcess.getPcb().setStatus(STATUS_WAIT);
//                                feedbackQueueLists.get(i).getReadyList().addLast(runProcess);
//                                flag = true;
//                                Thread.sleep(BLOCK_TIME);
//                                break;
                            }

                            // 执行完的进程只有两种情况  要么执行完了，要么没执行完
                            // 当该进程在自己的时间片内执行完成，就抢该进程从就绪队列删除，收回本次时间片，创建一个新的时间片给新的队首进程
                            if (runProcess.getPcb().getServiceTime() <= 0) {
                                // 进程执行完就将该进程从就绪队列中删除
                                runProcess.getPcb().setStatus(STATUS_FINISH);

                                // 记录当前进程的完成时间
                                completeTimeMap.put(runProcess.getPcb().getProcessName(), t);

                                System.out.println(t + "\t" + "进程" +  runProcess.getPcb().getProcessName() + "结束");
                                outputMultilevedFeedbackQueue(feedbackQueueLists);
                                Thread.sleep(BLOCK_TIME);

                                // 将当前进程置为null
                                runProcess = null;

                                completeCount++;

                                // 因为进程在时间片内执行完成，所以要完成本轮时间需要做的监听进程到达情况，然后自增时间后结束掉本次时间片，即跳出循环
                                arriveMemoryListener(tempList, feedbackQueueLists, processCount, t);

                                // 直接结束点本次时间片，跳出循环
                                break;
                            }
                        }

                        // 如果runProcess不为null，就表示当前时间片用完了也没有将进程执行完，输出时间片用完
                        if (runProcess != null) {
                            // 将该进程调到下一级队列中
                            runProcess.getPcb().setStatus(STATUS_WAIT);
                            feedbackQueueLists.get(i + 1).getReadyList().addLast(runProcess);

                            System.out.println(t + "\t" + "进程" +  runProcess.getPcb().getProcessName() + "时间片用完");
                            outputMultilevedFeedbackQueue(feedbackQueueLists);
                            Thread.sleep(BLOCK_TIME);
                        }

//                        if (flag == true) {
//                            break;
//                        }
                    }
                } else {
                    // 如果全都是空队列，就暂时跳出执行模块
                    break;
                }
            }
        }
        // 输出作业完成情况
        ioService.outputJobCompletion(processList, completeTimeMap);
    }

    /**
     * 输出多级就绪队列
     * @param feedbackQueueLists 多级就绪队列
     */
    public void outputMultilevedFeedbackQueue(List<FeedbackQueue> feedbackQueueLists) {
        for (int i = 0; i < feedbackQueueLists.size(); i++) {
            System.out.print("\t\t" + (i + 1) + "级队列：");

            // 取得当前级别的就绪队列
            LinkedList<Process> readyList = feedbackQueueLists.get(i).getReadyList();
            for (Process process : readyList) {
                PCB processPCB = process.getPcb();
                System.out.print(processPCB.getProcessName());
            }
            System.out.println();
        }
        System.out.println();
    }

    /**
     * 监听是否有进程到达内存，将其放到1级队列的末尾
     * @param tempList 要进行作业的进程
     * @param feedbackQueueLists 多级反馈队列
     * @param processCount 要进行作业的进程数
     * @param t 当前时间
     * @throws InterruptedException
     */
    public boolean arriveMemoryListener(List<Process> tempList, List<FeedbackQueue> feedbackQueueLists, int processCount, int t) throws InterruptedException {
        boolean flag = false;
        // 遍历所有要作业的进程，当到了该进程的到达时间时将该进程放入就绪队列
        for (int i = 0; i < processCount; i++) {
            PCB tempProcessPCB = tempList.get(i).getPcb();
            if (tempProcessPCB.getStatus() == STATUS_OUT) {
                if (tempProcessPCB.getArrivalTime() == t) {
                    feedbackQueueLists.get(0).getReadyList().addLast(tempList.get(i));
                    // 将其置为就绪状态
                    tempProcessPCB.setStatus(STATUS_WAIT);
                    System.out.println(t + "\t" + "进程" + tempProcessPCB.getProcessName() + "到达内存");
                    outputMultilevedFeedbackQueue(feedbackQueueLists);
                    flag = true;
                    Thread.sleep(BLOCK_TIME);
                }
            }
        }
        return flag;
    }
}
