package pers.cy.scheduling.service;

import pers.cy.scheduling.entity.Constant;
import pers.cy.scheduling.entity.Process;
import pers.cy.scheduling.factory.Factory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FCFSService implements Constant {
    private IOService ioService = Factory.getIOServiceInstance();

    public void FCFS(List<Process> processList) {
        // 输出作业情况
        ioService.outputJobCondition(processList);

        // 创建就绪队列
        LinkedList<Process> readyList = new LinkedList<>();

        
    }
}
