package pers.cy.dypar.util;

public interface Constant {
    // 要进行作业的进程数
    int PROCESS_COUNT = 5;
    // 空闲状态
    int STATUS_WAIT = 0;
    // 已分配状态
    int STATUS_ALLOCATED = 1;
    // 不可再分割的剩余分区大小
    int SIZE = 2;
}
