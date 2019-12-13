package pers.cy.banker.factory;

import pers.cy.banker.service.BankerService;
import pers.cy.banker.service.IOService;

public class Factory {
    public static IOService getIOServiceInstance() {
        return new IOService();
    }

    public static BankerService getBankerServiceInstance() {
        return new BankerService();
    }
}
