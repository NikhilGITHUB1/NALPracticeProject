package com.qa.opencart.exceptions;

import java.awt.*;

public class FrameworkException extends RuntimeException {

    public FrameworkException(String msg){
        super(msg);
    }

    public FrameworkException(String msg, Throwable cause){
        super(msg,cause);
    }

}
