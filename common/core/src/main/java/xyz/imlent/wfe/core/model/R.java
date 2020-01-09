package xyz.imlent.wfe.core.model;

import lombok.ToString;

/**
 * @author wfee
 */
@ToString
public class R {
    private int code;
    private Boolean success;
    private String msg;
    private Object data;

    public R() {

    }

    private R(Boolean success) {
        this.success = success;
    }

    public static R success() {
        return new R(Boolean.TRUE).setResultCode(ResultCode.SUCCESS);
    }

    public static R fail() {
        return new R(Boolean.FALSE).setResultCode(ResultCode.FAILURE);
    }

    public static R fail(String msg) {
        return R.fail().setMsg(msg);
    }

    public static R fail(IResultCode resultCode, String msg) {
        return R.fail().setCode(resultCode.getCode()).setMsg(msg);
    }

    public static R fail(int resultCode, String msg) {
        return R.fail().setCode(resultCode).setMsg(msg);
    }

    public static R success(String msg) {
        return R.success().setMsg(msg);
    }

    public static R success(Object data) {
        return R.success().setData(data);
    }

    public static R success(int resultCode, String msg) {
        return R.success().setCode(resultCode).setMsg(msg);
    }

    public static R success(int resultCode, String msg, Object data) {
        return R.success().setCode(resultCode).setMsg(msg).setData(data);
    }

    public Boolean getSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public R setCode(IResultCode resultCode) {
        this.code = resultCode.getCode();
        return this;
    }

    public R setCode(int code) {
        this.code = code;
        return this;
    }

    public R setMsg(IResultCode resultCode) {
        this.msg = resultCode.getMessage();
        return this;
    }

    public R setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public R setData(Object data) {
        this.data = data;
        return this;
    }

    public R setResultCode(IResultCode resultCode) {
        setCode(resultCode);
        setMsg(resultCode);
        return this;
    }
}
