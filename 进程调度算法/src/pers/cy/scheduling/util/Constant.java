package pers.cy.scheduling.util;

public interface Constant {
    // 要进行作业的进程数
    int PROCESS_COUNT = 5;
    // 未进入内存状态
    int STATUS_OUT = -1;
    // 就绪状态
    int STATUS_WAIT = 0;
    // 运行状态
    int STATUS_RUN = 1;
    // 完成状态
    int STATUS_FINISH = 2;
}
