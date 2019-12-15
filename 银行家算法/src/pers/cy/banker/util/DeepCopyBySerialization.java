package pers.cy.banker.util;

import java.io.*;
import java.util.List;

public class DeepCopyBySerialization {
    /**
     * 深拷贝
     * @param temp
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static int[] deepCopy(int[] temp) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(temp);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        int[] temp1 = (int[]) in.readObject();

        return temp1;
    }
}
