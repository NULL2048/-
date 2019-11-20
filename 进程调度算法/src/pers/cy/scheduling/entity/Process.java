package pers.cy.scheduling.entity;

public class Process {
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
}
