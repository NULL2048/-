package pers.cy.dypar.entity;

import pers.cy.dypar.util.Constant;

public class Partition implements Constant, Comparable<Partition> {
    // 分区号
    private int num;
    // 分区大小
    private int size;
    // 分区始址
    private int addr;
    // 状态
    private int status;
    // 分区配给的作业名
    private String jobName;

    public Partition(int num, int addr, int size, int status) {
        this.num = num;
        this.size = size;
        this.addr = addr;
        this.status = status;
        this.jobName = "";
    }

    public Partition(int num, String jobName, int addr, int size, int status) {
        this.num = num;
        this.size = size;
        this.addr = addr;
        this.status = status;
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getAddr() {
        return addr;
    }

    public void setAddr(int addr) {
        this.addr = addr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        if (status == STATUS_WAIT) {
            return "\t" + num + "\t\t" + jobName + "\t\t" + addr + "\t\t" + size + "\t\t\t" + "空闲";
        } else {
            return "\t" + num + "\t\t" + jobName + "\t\t" + addr + "\t\t" + size + "\t\t\t" + "已分配";
        }
    }

    @Override
    public int compareTo(Partition o) {
        if (this.size > o.size) {
            return 1;
        } else if (this.size < o.size) {
            return -1;
        } else {
            return 0;
        }
    }
}
