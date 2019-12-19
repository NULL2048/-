package pers.cy.swapping.util;

import pers.cy.swapping.entity.PhysicalBlock;

import java.io.*;

public class DeepCopyBySerialization {
    /**
     * 深拷贝
     * @param temp
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static PhysicalBlock[] deepCopy(PhysicalBlock[] temp) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(temp);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        PhysicalBlock[] temp1 = (PhysicalBlock[]) in.readObject();

        return temp1;
    }
}
