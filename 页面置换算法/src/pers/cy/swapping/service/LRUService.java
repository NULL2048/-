package pers.cy.swapping.service;

import pers.cy.swapping.entity.PhysicalBlock;
import pers.cy.swapping.factory.Factory;

import java.util.Scanner;

public class LRUService {
    private static IOService ioService = Factory.getIOServiceInstance();

    /**
     * 在最近最久未使用算法中，页面的时间time表示的是距离页面上一次被访问的时间，而不是当前页面在物理块中存在的时间
     * @param physicalBlocks
     */
    public void LRU(PhysicalBlock[] physicalBlocks) {
        Scanner scan = new Scanner(System.in);

        // 替换指针  指向最近最久未使用的页面，即下一次调入页面应该放入的物理块号
        int index = 0;

        while (true) {
            System.out.println("输入要访问的逻辑地址(-1退出):");
            int pageNum = scan.nextInt();

            // 如果输入-1则退出
            if (pageNum == -1) {
                return;
            }

            // 判断要访问的页面是否在物理块中
            int i = getPhysicalBlock(physicalBlocks, pageNum);
            // 访问页面在物理块中
            if (i != -1) {
                System.out.printf("页号:%d  该页已在内存中!\n", pageNum);

                // 将当前被访问的页面的时间初始化
                physicalBlocks[i].setTime(0);

                timeIncrease(physicalBlocks);

                // 取得最新最久未使用页面所在下标
                index = getOldestPhysicalBlock(physicalBlocks);

                ioService.outputPhysicalBlocks(physicalBlocks);
            } else {
                System.out.printf("页号:%d  该页不在内存中,调入!\n", pageNum);
                physicalBlocks[index].setPageNum(pageNum);
                physicalBlocks[index].setTime(0);

                timeIncrease(physicalBlocks);

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
     * 取得最新最久未使用页面所在下标
     * @param physicalBlocks
     * @return 最新最久未使用页面所在下标
     */
    public int getOldestPhysicalBlock(PhysicalBlock[] physicalBlocks) {
        int maxIndex = -2;
        int maxTime = -2;
        for (int i = 0; i < physicalBlocks.length; i++) {
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
     * 将除了index下标的物理块中的页面的存在时间加一
     * @param physicalBlocks
     */
    public void timeIncrease(PhysicalBlock[] physicalBlocks) {
        for (int i = 0; i < physicalBlocks.length; i++) {
            if (physicalBlocks[i].getPageNum() != -1) {
                physicalBlocks[i].timeIncrease();
            }
        }
    }
}
