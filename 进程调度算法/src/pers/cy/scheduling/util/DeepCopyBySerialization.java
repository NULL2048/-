package pers.cy.scheduling.util;

import pers.cy.scheduling.entity.Process;

import java.io.*;
import java.util.List;

public class DeepCopyBySerialization {
    public static List<Process> deepCopy(List<Process> processList) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(processList);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        List<Process> tempList = (List<Process>) in.readObject();

        return tempList;
    }
}
