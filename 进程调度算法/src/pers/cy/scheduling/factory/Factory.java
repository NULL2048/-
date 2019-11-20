package pers.cy.scheduling.factory;

import pers.cy.scheduling.service.IOService;

public class Factory {
    public static IOService getIOServiceInstance() {
        return new IOService();
    }
}
