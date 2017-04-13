package com.parse.starter.filesCompression;

import com.parse.ParseFile;

/**
 * Abstract class for common methods in compression classes
 */

abstract class Compression {

    public abstract void upload();
    public abstract void download();
    public abstract ParseFile fetchAndView();
}
