package pers.cy.scheduling.factory;

import pers.cy.scheduling.service.*;

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

    public static RRService getRRServiceInstance() {
        return new RRService();
    }

    public static MFQService getMFQServiceInstance() {
        return new MFQService();
    }
}
