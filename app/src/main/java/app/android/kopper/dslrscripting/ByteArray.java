package app.android.kopper.dslrscripting;

/**
 * Created by kopper on 2015-02-07.
 * (C) Copyright 2015 kopperek@gmail.com
 * <p/>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-2.1.html
 * <p/>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */
public class ByteArray {
    private byte[] array;
    private int offset;
    private Object size;

    public ByteArray(int len) {
        array=new byte[len];
        offset=0;
    }

    public ByteArray(byte[] data) {
        array=data;
        offset=0;
    }

    public void put(long value, int bytes) {
        for(int a=0;a<bytes;a++) {
            array[offset++]=(byte)value;
            value=value>>8;
        }
    }

    @Override
    public String toString() {
        StringBuffer result=new StringBuffer();
        for(int a=0;a<array.length;a++)
            result.append(String.format("%2x",array[a]).replaceAll(" ","0")+" ");
        return(result.toString());

    }

    public int getSize() {
        return array.length;
    }

    public byte[] getArray() {
        return array;
    }

    public long get(int bytes) {
        long result=get(offset,bytes);
        offset+=bytes;
        return(result);
    }

    public long get(int offset,int bytes) {
        long result=0;
        for(int a=bytes-1;a>=0;a--)
            result=(result<<8)+((array[offset+a])&0xff);
        return(result);
    }
}
