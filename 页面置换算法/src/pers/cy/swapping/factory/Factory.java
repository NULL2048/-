package pers.cy.swapping.factory;

import pers.cy.swapping.service.FIFOService;
import pers.cy.swapping.service.IOService;
import pers.cy.swapping.service.LRUService;

public class Factory {
    public static IOService getIOServiceInstance() {
        return new IOService();
    }

    public static FIFOService getFIFOServiceInstance() {
        return new FIFOService();
    }

    public static LRUService getLRUServiceInstance() {
        return new LRUService();
    }
}
