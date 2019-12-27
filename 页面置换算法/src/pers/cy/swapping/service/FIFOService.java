package pers.cy.swapping.service;

import pers.cy.swapping.entity.PhysicalBlock;
import pers.cy.swapping.factory.Factory;

import java.util.List;
import java.util.Scanner;

public class FIFOService {
    private static IOService ioService = Factory.getIOServiceInstance();

    /**
     * 在先进先出算法中，页面的时间time表示的是当前页面在物理块中存在的时间，而不是距离页面上一次被访问的时间
     * @param physicalBlocks
     */
    public void FIFO(PhysicalBlock[] physicalBlocks) {
        Scanner scan = new Scanner(System.in);

        // 替换指针  指向最老的页面，即下一次调入页面应该放入的物理块号
        int index = 0;

        while (true) {
            System.out.println("输入要访问的逻辑地址(-1退出):");
            int pageNum = scan.nextInt();

            // 如果输入-1则退出
            if (pageNum == -1) {
                return;
            }

            // 判断要访问的页面是否在物理块中
            // 访问页面在物理块中
            if (getPhysicalBlock(physicalBlocks, pageNum) != -1) {
                System.out.printf("页号:%d  该页已在内存中!\n", pageNum);

                // 将物理块中的所有的页面存在时间加一
                timeIncrease(physicalBlocks);

                ioService.outputPhysicalBlocks(physicalBlocks);
            // 物理块中不存在该页面，将该页面换入物理块
            } else {
                System.out.printf("页号:%d  该页不在内存中,调入!\n", pageNum);
                // 物理块的页面号换成要换入的页面
                physicalBlocks[index].setPageNum(pageNum);
                // 初始化页面存在时间
                physicalBlocks[index].setTime(0);

                // 将所有页面存在时间加一
                timeIncrease(physicalBlocks);

                // 取得当前最老的页面所在下标，即下一次换入页面应放入的位置
                index = getOldestPhysicalBlock(physicalBlocks);

                ioService.outputPhysicalBlocks(physicalBlocks);
            }
        }

    }

    /**
     * 查找当前所有物理块中是否存在pageNum这个页
     * @param physicalBlocks
     * @param pageNum
     * @return 存在返回页所在物理块的下标，不存在返回-1
     */
    public int getPhysicalBlock(PhysicalBlock[] physicalBlocks, int pageNum) {
        for (int i = 0; i < physicalBlocks.length; i++) {
            if (physicalBlocks[i].getPageNum() == pageNum) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 取得物理块中最老的页面
     * @param physicalBlocks
     * @return 最老页面所在物理块下标
     */
    public int getOldestPhysicalBlock(PhysicalBlock[] physicalBlocks) {
        int maxIndex = -2;
        int maxTime = -2;
        for (int i = 0; i < physicalBlocks.length; i++) {
            // 如果从头遍历发现了一个没有放入页面的物理块，直接返回该物理块位置，下一次换入页面就放到这个物理块中
            if (physicalBlocks[i].getPageNum() == -1) {
                return i;
            }

            if (physicalBlocks[i].getTime() >= maxTime) {
                maxIndex = i;
                maxTime = physicalBlocks[i].getTime();
            }
        }

        return maxIndex;
    }

    /**
     * 将物理块中的页面的存在时间加一
     * @param physicalBlocks
     */
    public void timeIncrease(PhysicalBlock[] physicalBlocks) {
        for (int i = 0; i < physicalBlocks.length; i++) {
            // 将所有在物理块中的页面的时间加一
            if (physicalBlocks[i].getPageNum() != -1) {
                physicalBlocks[i].timeIncrease();
            }
        }
    }
}
