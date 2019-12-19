package pers.cy.swapping.service;

import pers.cy.swapping.entity.PhysicalBlock;

public class IOService {
    public void outputPhysicalBlocks(PhysicalBlock[] physicalBlocks) {
        System.out.println("物理块号\t\t页号");
        for (int i = 0; i < physicalBlocks.length; i++) {
            System.out.println("\t" + physicalBlocks[i].getBlockNum() + "\t\t" + physicalBlocks[i].getPageNum());
        }
    }
}
