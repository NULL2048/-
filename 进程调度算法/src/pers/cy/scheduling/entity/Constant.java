package pers.cy.scheduling.entity;

public interface Constant {
    // 未进入内存状态
    int STATUS_OUT = -1;
    // 就绪状态
    int STATUS_WAIT = 0;
    // 运行状态
    int STATUS_RUN = 1;
    // 完成状态
    int STATUS_FINISH = 2;
}
