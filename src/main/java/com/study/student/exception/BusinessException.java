package com.study.student.exception;
//运行过程中可能发生、Java 不强制你 try-catch/throws 的异常。
//所以 Spring 项目里的自定义业务异常，很常见就是继承 RuntimeException。
//自定义业务错误
public class BusinessException extends RuntimeException{

    private int code;
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        //父类本身有异常message
        //父类的异常早就有一套getmessage了
    }
    public int getCode() {
        return code;
    }
//    发现是业务错误后 MVC知道是Busineeserror 所以直接向GLOBAL寻求business找错误
}

