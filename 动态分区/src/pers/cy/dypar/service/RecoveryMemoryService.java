package pers.cy.dypar.service;

import pers.cy.dypar.entity.Partition;
import pers.cy.dypar.util.Constant;

import java.util.List;
import java.util.Scanner;

public class RecoveryMemoryService implements Constant{
    public void recoveryMemory(List<Partition> partitionList) {
        Scanner scan = new Scanner(System.in);

        // 要回收jobName这个作业的分区
        String jobName = scan.next();

        // 记录循环次数
        int cnt = 0;
        for (Partition partition : partitionList) {
            if (jobName.equals(partition.getJobName())) {
                Partition frontPartition = partitionList.get(cnt - 1);
                Partition nextPartition = partitionList.get(cnt + 1);

                // 判断三种合并分区合并情况
                if (cnt != 0 && frontPartition.getStatus() == STATUS_WAIT && nextPartition.getStatus() != STATUS_WAIT) {
                    frontPartition.setSize(frontPartition.getSize() + partition.getSize());
                    partitionList.remove(cnt);
                } else if (cnt != partitionList.size() - 1 && frontPartition.getStatus() != STATUS_WAIT && nextPartition.getStatus() == STATUS_WAIT) {
                    partition.setSize(partition.getSize() + nextPartition.getSize());
                    partition.setStatus(STATUS_WAIT);
                    partitionList.remove(cnt + 1);
                } else if (cnt != 0 && cnt != partitionList.size() - 1 && frontPartition.getStatus() == STATUS_WAIT && nextPartition.getStatus() == STATUS_WAIT) {
                    frontPartition.setSize(frontPartition.getSize() + partition.getSize() + nextPartition.getSize());
                    partitionList.remove(cnt);
                    partitionList.remove(cnt + 1);
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
