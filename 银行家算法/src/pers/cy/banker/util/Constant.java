package pers.cy.banker.util;

public interface Constant {
    // 要进行资源请求的进程数
    int PROCESS_COUNT = 5;
    // 计算机资源种类数
    int RESOURCE_COUNT = 3;
    // 就绪状态
    int STATUS_WAIT = 0;
    // 运行状态
    int STATUS_RUN = 1;
    // 完成状态
    int STATUS_FINISH = 2;
    // 阻塞时间
    int BLOCK_TIME = 200;
}
