package pers.cy.swapping.entity;

public class PhysicalBlock {
    // 物理块号
    private int blockNum;
    // 页号
    private int pageNum;
    // 该页面自上次被访问以来所经历的时间
    private int time;

    public PhysicalBlock(int blockNum) {
        this.blockNum = blockNum;
        this.pageNum = -1;
        this.time = -1;
    }

    public int getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(int blockNum) {
        this.blockNum = blockNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void timeIncrease() {
        time++;
    }
}
