package pers.cy.scheduling.entity;

import java.io.Serializable;

public class Process implements Comparable<Process>, Serializable {
    // 进程控制块
    private PCB pcb;
    // 程序段
    private String proceduresSegment;
    // 数据段
    private String dataSegment;

    public Process(PCB pcb, String proceduresSegment, String dataSegment) {
        this.pcb = pcb;
        this.proceduresSegment = proceduresSegment;
        this.dataSegment = dataSegment;
    }

    public PCB getPcb() {
        return pcb;
    }

    public void setPcb(PCB pcb) {
        this.pcb = pcb;
    }

    public String getProceduresSegment() {
        return proceduresSegment;
    }

    public void setProceduresSegment(String proceduresSegment) {
        this.proceduresSegment = proceduresSegment;
    }

    public String getDataSegment() {
        return dataSegment;
    }

    public void setDataSegment(String dataSegment) {
        this.dataSegment = dataSegment;
    }

    /**
     * 根据到达时间进行升序排序
     * @param o
     * @return
     */
    @Override
    public int compareTo(Process o) {
        if (this.pcb.getArrivalTime() > o.pcb.getArrivalTime()) {
            return 1;
        } else if (this.pcb.getArrivalTime() < o.pcb.getArrivalTime()) {
            return -1;
        } else {
            return 0;
        }
    }
}
