package pers.cy.dypar.service;

import pers.cy.dypar.entity.Partition;
import pers.cy.dypar.util.Constant;

import java.lang.reflect.Parameter;
import java.util.List;

public class IOService implements Constant {
    /**
     * 显示内存使用情况
     * @param partitionList
     */
    public void outputMemoryUsage(List<Partition> partitionList) {
        System.out.println("分区号\t作业名\t起始地址\t\t分区大小\t\t状态");
        for (Partition partition : partitionList) {
            System.out.println(partition);
        }
    }
}
