package pers.cy.dypar.service;

import pers.cy.dypar.entity.Partition;
import pers.cy.dypar.factory.Factory;
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

        // 遍历空闲分区链，进行内存分配
        for (Partition partition : freePartitionList) {
            // 比较请求空间和当前分区空前大小，如果当前分区空间足够分配给请求的进程，就准备进行分配，如果不够就遍历下一个分区
            if (partition.getSize() > size) {
                // 比较当前分区如果将空间分配给请求，剩下的空前是否小于规定的最小分区分割大小，如果小于最小分割大小，则直接将整个分区给请求进程，如果不小于最小分割大小，就直接将该分区拆分成两个分区，一个给该进程，一个留在空闲分区表中
                if (partition.getSize() - size <= SIZE) {
                    // 将整个分区分配给请求进程
                    // 虽然partition是从freePartitionList中取出的，但是他也是从partitionList中取出来放进去的，所以实际上他和partitionList中的元素实际上是同一个，对其修改也就对原链表中的元素修改了
                    partition.setStatus(STATUS_ALLOCATED);
                    partition.setJobName(jobName);
                } else {
                    splitPartitions(partitionList, partition, jobName, size);
                }
                System.out.println("分配成功！！！");
                return;
            }
        }

        System.out.println("分配失败！！！");
        return;
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

    /**
     * 从内存分配表中查找指定分区的下标
     * @param partitionList 内存分配表
     * @param num 分区号
     * @return 分区下标
     */
    public int getPartitionIndex(List<Partition> partitionList, int num) {
        int cnt = 0;
        for (Partition partition : partitionList) {
            if (partition.getNum() == num) {
                return cnt;
            }
            cnt++;
        }
        return cnt;
    }

    /**
     * 分割分区
     * 将要分配给内存的分区用原分区号和原始地址
     * 分割出去的分区用原分区号的下一个分区号，此分区不分配
     * 下面的分区的分区号顺次自增1
     * @param partitionList
     * @param partition 要分割的分区
     * @param jobName 要分配给的任务名
     * @param size 要分割出来的大小
     */
    public void splitPartitions(List<Partition> partitionList, Partition partition, String jobName, int size) {
        int index = getPartitionIndex(partitionList, partition.getNum());

        // 记录一下分割出来的新分区的大小
        int nextSize = partition.getSize() - size;

        // 设置要分配的分区  也就是原链表中的这个分区，修改一下他的分区大小为请求分区大小，将其分配出去
        partition.setJobName(jobName);
        partition.setStatus(STATUS_ALLOCATED);
        partition.setSize(size);

        // 增加分割出来的新分区，地址就在上面分区的基础上向下顺延size大小，就将其插入到partition这个分区的下一个就行
        partitionList.add(index + 1, new Partition(partition.getNum() + 1, partition.getAddr() + size, nextSize, STATUS_WAIT));

        // 将后面的所有分区的分区号都加一
        for (int i = index + 2; i < partitionList.size(); i++) {
            Partition temp = partitionList.get(i);
            temp.setNum(temp.getNum() + 1);
        }
    }
}
