package com.parse.starter.filesCompression;

/**
 * Abstract class for common methods in compression classes
 */

abstract class Compression {

    public abstract void upload();
    public abstract void download(String fileName);
}
