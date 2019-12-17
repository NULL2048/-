package pers.cy.dypar.service;

import pers.cy.dypar.entity.Partition;
import pers.cy.dypar.util.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AllocatedMemoryService implements Constant {
    /**
     * 分配内存
     * @param partitionList 内存分配表
     */
    public void allocatedMemory(List<Partition> partitionList) {
        Scanner scan = new Scanner(System.in);

        System.out.print("作业名:");
        // 申请内存的作业名
        String jobName = scan.next();
        System.out.print("作业占内存大小：");
        // 作业占内存大小
        int size = scan.nextInt();

        // 获得按空间从小到大排序的空闲分区链
        List<Partition> freePartitionList = BF(partitionList);

        // 记录循环次数
        int cnt = 0;
        // 遍历空闲分区链，进行内存分配
        for (Partition partition : freePartitionList) {
            // 比较请求空间和当前分区空前大小，如果当前分区空间足够分配给请求的进程，就准备进行分配，如果不够就遍历下一个分区
            if (partition.getSize() > size) {
                // 比较当前分区如果将空间分配给请求，剩下的空前是否小于规定的最小分区分割大小，如果小于最小分割大小，则直接将整个分区给请求进程，如果不小于最小分割大小，就直接将该分区拆分成两个分区，一个给该进程，一个留在空闲分区表中
                if (partition.getSize() - size <= SIZE) {

                }
            }
            cnt++;
        }
    }

    /**
     * 分配算法：最佳适应算法
     * 它选取的分配分区一定是能分配的最小分区，避免大材小用
     * @param partitionList 内存分配表
     * @return 空闲分区链
     */
    public List<Partition> BF(List<Partition> partitionList) {
        // BF算法要求先将所有空闲分区按其容量从小到大形成一空闲分区连
        // 得到空闲分区链
        List<Partition> freePartitionList = listFreePartition(partitionList);
        // 将空闲分区链排序
        Collections.sort(freePartitionList);

        // 返回排序好的空闲分区链
        return freePartitionList;
    }

    /**
     * 从分区分配表中取出空闲分区链
     * @param partitionList 分区分配表
     * @return 空闲分区链
     */
    public List<Partition> listFreePartition(List<Partition> partitionList) {
        List<Partition> freePartitionList = new ArrayList<>();

        for (Partition partition : partitionList) {
            if (partition.getStatus() == STATUS_WAIT) {
                freePartitionList.add(partition);
            }
        }

        return freePartitionList;
    }
}
