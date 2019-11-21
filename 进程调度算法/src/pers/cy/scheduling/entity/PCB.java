package pers.cy.scheduling.entity;

import pers.cy.scheduling.util.Constant;

import java.io.Serializable;

public class PCB implements Constant, Serializable {
    // 进程名
    private String processName;
    // 优先级
    private Double priority;
    // 到达时间
    private Double arrivalTime;
    // 要求服务时间
    private Double serviceTime;
    // 进程状态
    private Integer status;
    // 等待时间
    private Double waitTime;

    public PCB(String processName, Double priority, Double arrivalTime, Double serviceTime, Integer status) {
        this.processName = processName;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.status = status;
        this.waitTime = 0.0;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Double getPriority() {
        return priority;
    }

    public void setPriority(Double priority) {
        this.priority = priority;
    }

    public Double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Double getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(Double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void run() {
        this.serviceTime--;
    }

    public Double getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Double waitTime) {
        this.waitTime = waitTime;
    }
}
