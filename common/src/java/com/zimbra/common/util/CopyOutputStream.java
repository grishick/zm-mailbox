/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Server
 * Copyright (C) 2009, 2010, 2013, 2014, 2016 Synacor, Inc.
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software Foundation,
 * version 2 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see <https://www.gnu.org/licenses/>.
 * ***** END LICENSE BLOCK *****
 */
package com.zimbra.common.util;

import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CopyOutputStream extends OutputStream {
    private BufferStream bs;
    private OutputStream os;

    public CopyOutputStream(OutputStream os) { this(os, 0); }

    public CopyOutputStream(OutputStream os, long sizeHint) {
        this(os, sizeHint, Integer.MAX_VALUE);
    }

    public CopyOutputStream(OutputStream os, long sizeHint, int maxBuffer) {
        this(os, sizeHint, maxBuffer, Long.MAX_VALUE);
    }

    public CopyOutputStream(OutputStream os, long sizeHint, int maxBuffer, long
        maxSize) {
        bs = new BufferStream(sizeHint, maxBuffer, maxSize);
        this.os = os;
    }

    public CopyOutputStream(OutputStream os, BufferStream bs) {
        this.bs = bs;
        this.os = os;
    }

    public void close() throws IOException { os.close(); }

    public void flush() throws IOException { os.flush(); }

    public BufferStream getBufferStream() { return bs; }

    public InputStream getInputStream() throws IOException {
        return bs.getInputStream();
    }
    
    public long getSize() { return bs.getSize(); }

    public long readFrom(InputStream is) throws IOException {
        return readFrom(is, Long.MAX_VALUE);
    }
    
    public long readFrom(InputStream is, long len) throws IOException {
        byte tmp[] = new byte[(int)Math.min(len, 32 * 1024)];
        int in;
        long out = 0;
        
        while (len > 0 && (in = is.read(tmp, 0, (int)Math.min(len,
            tmp.length))) > 0) {
            write(tmp, 0, in);
            len -= in;
            out += in;
        }
        return out;
    }
    
    public void release() { bs.close(); }
    
    public byte[] toByteArray() { return bs.toByteArray(); }

    public void write(int data) throws IOException {
        os.write(data);
        bs.write(data);
    }
    
    public void write(byte data[], int off, int len) throws IOException {
        os.write(data, off, len);
        bs.write(data, off, len);
    }
}
