public class PCB implements Constant {
    // 进程名
    private String processName;
    // 优先级
    private Integer priority;
    // 到达时间
    private Double arrivalTime;
    // 要求服务时间
    private Double serviceTime;
    // 进程状态
    private Integer status;

    public PCB(String processName, Integer priority, Double arrivalTime, Double serviceTime, Integer status) {
        this.processName = processName;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.status = status;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
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
}
