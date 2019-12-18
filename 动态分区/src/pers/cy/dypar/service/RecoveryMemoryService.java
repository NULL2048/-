package pers.cy.dypar.service;

import pers.cy.dypar.entity.Partition;
import pers.cy.dypar.util.Constant;

import java.util.List;
import java.util.Scanner;

public class RecoveryMemoryService implements Constant{
    public void recoveryMemory(List<Partition> partitionList) {
        Scanner scan = new Scanner(System.in);

        System.out.print("输入要回收分区的作业名:");
        // 要回收jobName这个作业的分区
        String jobName = scan.next();

        // 记录循环次数
        int cnt = 0;
        for (Partition partition : partitionList) {
            if (jobName.equals(partition.getJobName())) {
                Partition frontPartition = null;
                Partition nextPartition = null;
                // 取出当前分区的前后两个分区
                if (cnt != 0) {
                    frontPartition = partitionList.get(cnt - 1);
                }
                if (cnt != partitionList.size() - 1) {
                    nextPartition = partitionList.get(cnt + 1);
                }

                // 判断三种合并分区合并情况
                // 如果要回收的分区在链表的中间位置
                if (cnt != 0 && cnt != partitionList.size() - 1 ) {
                    // 分区前后都是空闲分区，将三者进行合并
                    if (frontPartition.getStatus() == STATUS_WAIT && nextPartition.getStatus() == STATUS_WAIT) {
                        frontPartition.setSize(frontPartition.getSize() + partition.getSize() + nextPartition.getSize());
                        // 删掉partition这个分区
                        partitionList.remove(cnt);
                        // 在上面删掉partition这个分区后，cnt这个下标位置就是nextPartition这个分区了，删掉nextPartition这个分区
                        partitionList.remove(cnt);
                    // 只有分区前面的一个分区是空闲分区，将两者进行合并
                    } else if (frontPartition.getStatus() == STATUS_WAIT) {
                        frontPartition.setSize(frontPartition.getSize() + partition.getSize());
                        partitionList.remove(cnt);
                    // 只有分区后面的一个分区是空闲分区，将两者进行合并
                    } else if (nextPartition.getStatus() == STATUS_WAIT) {
                        partition.setSize(partition.getSize() + nextPartition.getSize());
                        partition.setStatus(STATUS_WAIT);
                        // 清空作业名
                        partition.setJobName("");
                        partitionList.remove(cnt + 1);
                    // 分区前后都不是空闲分区，直接将其回收，不进行合并
                    } else {
                        partition.setStatus(STATUS_WAIT);
                        partition.setJobName("");
                    }
                // 当分区在链表的首尾位置
                } else {
                    // 在链表头时，如果下一个分区是空闲分区，将二者进行合并
                    if (cnt == 0 && nextPartition.getStatus() == STATUS_WAIT) {
                        partition.setSize(partition.getSize() + nextPartition.getSize());
                        partition.setStatus(STATUS_WAIT);
                        partition.setJobName("");
                        partitionList.remove(cnt + 1);
                    // 在链表尾时，如果上一个分区是空闲分区，将二者进行合并
                    } else if (cnt == partitionList.size() - 1 && frontPartition.getStatus() == STATUS_WAIT) {
                        frontPartition.setSize(frontPartition.getSize() + partition.getSize());
                        partitionList.remove(cnt);
                    // 如果上面两种情况都不是，直接回收该分区
                    } else {
                        partition.setStatus(STATUS_WAIT);
                        partition.setJobName("");
                    }
                }

                System.out.println("回收成功！！！");
                return;
            }
            cnt++;
        }

        System.out.println("回收失败！！！");
        return;
    }
}
