package pers.cy.scheduling.entity;

import java.util.LinkedList;

public class FeedbackQueue {
    // 时间片
    private Integer timeslice;
    // 就绪队列
    private LinkedList<Process> readyList;

    public FeedbackQueue(Integer timeslice) {
        this.timeslice = timeslice;
        this.readyList = new LinkedList<>();
    }

    public Integer getTimeslice() {
        return timeslice;
    }

    public void setTimeslice(Integer timeslice) {
        this.timeslice = timeslice;
    }

    public LinkedList<Process> getReadyList() {
        return readyList;
    }

    public void setReadyList(LinkedList<Process> readyList) {
        this.readyList = readyList;
    }
}
