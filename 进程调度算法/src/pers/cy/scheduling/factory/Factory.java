package pers.cy.scheduling.factory;

import pers.cy.scheduling.service.FCFSService;
import pers.cy.scheduling.service.HRRNService;
import pers.cy.scheduling.service.IOService;
import pers.cy.scheduling.service.SJFService;

public class Factory {
    public static IOService getIOServiceInstance() {
        return new IOService();
    }

    public static FCFSService getFCFSServiceInstance() {
        return new FCFSService();
    }

    public static SJFService getSJFServiceInstance() {
        return new SJFService();
    }

    public static HRRNService getHRRNServiceInstance() {
        return new HRRNService();
    }
}
