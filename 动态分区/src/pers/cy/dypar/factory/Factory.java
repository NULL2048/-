package pers.cy.dypar.factory;

import pers.cy.dypar.service.AllocatedMemoryService;

public class Factory {
    public static AllocatedMemoryService getAllocatedMemoryServiceInstance() {
        return new AllocatedMemoryService();
    }
}
