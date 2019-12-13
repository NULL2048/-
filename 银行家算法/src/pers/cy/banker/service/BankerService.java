package pers.cy.banker.service;

import com.sun.org.apache.regexp.internal.RE;
import pers.cy.banker.util.Constant;
import pers.cy.banker.util.DeepCopyBySerialization;

import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

public class BankerService implements Constant {
    /**
     * 发起请求校验
     * @param available
     * @param max
     * @param allocation
     * @param need
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void resourceRequestCheck(int[] available, int[][] max, int[][] allocation, int[][] need) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);

        // 要请求资源的进程号
        int process;
        System.out.print("请输入请求资源的进程号:");
        process = scan.nextInt();

        // 要请求的各个资源的数量
        int[] resource = new int[100];
        System.out.print("输入请求资源的数量(" + RESOURCE_COUNT + "类资源,空格隔开):");
        for (int i = 0; i < RESOURCE_COUNT; i++) {
            resource[i] = scan.nextInt();
        }

        // 发送请求前先做一次当前资源分配表的安全性检验，系统安全才能正式发起进程请求
        if (safetyCheck(available, need, allocation)) {
            int i;
            for (i = 0; i < RESOURCE_COUNT; i++) {
                if (resource[i] > need[process][i]) {
                    break;
                }
                if (resource[i] > available[i]) {
                    break;
                }
            }

            if (i >= RESOURCE_COUNT) {
                for (i = 0; i < RESOURCE_COUNT; i++) {
                    available[i] -= resource[i];
                    allocation[process][i] += resource[i];
                    need[process][i] -= resource[i];
                }

                if (!safetyCheck(available, need, allocation)) {
                    available[i] += resource[i];
                    allocation[process][i] -= resource[i];
                    need[process][i] += resource[i];
                }
            }
        }

    }

    /**
     * 安全性检验
     * @param available
     * @param need
     * @param allocation
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public boolean safetyCheck(int[] available, int[][] need,  int[][] allocation) throws IOException, ClassNotFoundException {
        // 深拷贝available计算机现有的资源数给work，每一次安全性校验时都要将当前系统的资源数拷贝给work
        // 他就起到在安全检测过程中临时模拟计算机还能提供每种资源的资源数的一个临时性数组，为了检验在当前进程序列计算机是否能满足所有进程的资源请求
        int[] work = DeepCopyBySerialization.deepCopy(available);

        // finish[i]表示系统是否有足够的资源跟配给进程i   初始都设为false
        boolean[] finish = new boolean[100];

        // 记录计算机可以分配资源的进程数
        int cnt = 0;

        // 安全序列 发现一个符合要求的进程就放入栈中，如果发现栈中的序列不能使所有进程都分配上资源，就将栈顶的进程号出栈，换其他的序列尝试
        Stack<Integer> processSecuritySequence = new Stack<>();

        // 标记当前安全序列中栈顶元素的下一个安全进程不能是哪一个进程，即processFlag中记录的是之前已经尝试过在序列的这个位置行不通的进程号
        int processFlag = -1;

        // 当计算机可以分配给资源的进程数等于所有的进程数的时候，说明当前的安全序列可以保证所有的进程都能分配到资源而不发生死锁
        while (cnt < PROCESS_COUNT) {
            // 遍历所有的进程，找到符合finish[i]等于false，且还需要的资源数数小于等于当前系统可以提供的资源数的进程
            for (int i = 0; i < PROCESS_COUNT; i++) {
                // 当前进程还没有被标记系统能够为其提供足够的资源，所以要在当前系统资源状态下对其进行判断
                if (finish[i] == false) {
                    int j;
                    // 遍历当前进程所需要的所有资源的数量，检查系统是否能够满足其全部需求
                    for (j = 0; j < RESOURCE_COUNT; j++) {
                        // 一旦发现这个进程所需要的资源有当前系统所不能全部提供的，说明当前进程的请求，系统还剩余的资源work不足以满足其需求，直接跳出
                        if (need[i][j] > work[j]) {
                            break;
                        }
                    }
                    // 如果上面的循环是正常结束的，说明系统能够提供当前进程的所需的全部资源，所以要更新系统可利用的资源数量
                    if (j == RESOURCE_COUNT) {
                        // 遍历所有的资源号，更新系统每种资源的最新可利用的数量
                        // 就是拿之前的系统可利用的资源数量work[j]加上i进程之前已经持有的j资源的数量allocation[i][j]，得到的就是最新的系统资源的可利用数量
                        // 原理就是上面判断了当前进程i的全部请求资源系统可以满足的时候，系统就将work中的可以用资源分配给i这个进程了
                        // 然后进程利用完之后，系统需要将这个进程所持有的所有资源全部回收，所以就用分配给进程之前的资源数，加上进程之前一直持有的资源数，就是最后系统将资源回收后的资源总数
                        for (j = 0; j < RESOURCE_COUNT; j++) {
                            work[j] = work[j] + allocation[i][j];
                        }
                        // 表明系统能够满足该进程的资源请求
                        finish[i] = true;

                        // 将该进程放入安全序列中
                        processSecuritySequence.push(i);

                        // 将计算机可以分配资源的进程数加一
                        cnt++;
                    }
                }
            }

            // 当上面的将所有进程遍历一遍之后，如果计算机可以分配资源的进程数不是进程总数，说明当前的进程序列不是安全序列，会造成系统进入不安全状态，需要换一个进程请求顺序
            if (cnt != PROCESS_COUNT) {
                // 
                processFlag = processSecuritySequence.pop();
                for (int i = 0; i < RESOURCE_COUNT; i++) {
                    work[i] = work[i] - allocation[processFlag][i];
                    finish[i] = false;
                }

                cnt = 0;
            } else {
                return true;
            }
        }


        if (cnt == PROCESS_COUNT) {
            return true;
        } else {
            return false;
        }
    }
}
