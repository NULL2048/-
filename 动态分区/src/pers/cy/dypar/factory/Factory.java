package pers.cy.dypar.factory;

import pers.cy.dypar.service.AllocatedMemoryService;
import pers.cy.dypar.service.IOService;
import pers.cy.dypar.service.RecoveryMemoryService;

public class Factory {
    public static IOService getIOServiceInstance() {
        return new IOService();
    }
    public static AllocatedMemoryService getAllocatedMemoryServiceInstance() {
        return new AllocatedMemoryService();
    }
    public static RecoveryMemoryService getRecoveryMemoryServiceInstance() {
        return new RecoveryMemoryService();
    }
}
