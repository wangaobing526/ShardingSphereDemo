package com.bus.businessdiaryapp.util;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.MDC;

import java.io.Serializable;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.ArrayList;
import org.slf4j.MDC;
@ApiModel("返回对象")
public class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = -1L;
    public static final String OK = "ok";
    @ApiModelProperty("状态 ok成功 非ok失败")
    private String status;
    @ApiModelProperty("traceId")
    private String traceId = MDC.get("X-B3-TraceId");
    @ApiModelProperty("返回信息")
    private String msg;
    @ApiModelProperty("code:0成功 非0失败")
    private int code;
    @ApiModelProperty("返回具体对象信息")
    private T data;

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static <T> JsonResult<T> ok(T data) {
        JsonResult<T> result = new JsonResult();
        result.setStatus("ok");
        result.setMsg("ok");
        result.setData(null == data ? (T)new ArrayList() : data);
        return result;
    }

    public static <T> JsonResult<T> ok() {
        return ok(null);
    }

    public static JsonResult fail(String status, String msg) {
        status = status == null ? "fail" : status;
        if ("ok".equals(status)) {
            throw new RuntimeException("ok is not fail");
        } else {
            JsonResult result = new JsonResult();
            result.setStatus(status);
            result.setMsg(msg);
            result.setCode(-1);
            MDC.clear();
            return result;
        }
    }

    public static JsonResult fail(ExceptionCode serviceStatusEnum) {
        JsonResult result = new JsonResult();
        result.setStatus(serviceStatusEnum.getCode());
        result.setMsg(serviceStatusEnum.getMessage());
        result.setCode(-1);
        MDC.clear();
        return result;
    }

    public static JsonResult badRequest(String msg) {
        return fail("bad_request", msg);
    }

    @JsonIgnore
    public boolean isOk() {
        return "ok".equals(this.status);
    }

    @JsonGetter
    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    public JsonResult() {
    }

    public String getStatus() {
        return this.status;
    }

    public String getTraceId() {
        return this.traceId;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public void setTraceId(final String traceId) {
        this.traceId = traceId;
    }

    public void setMsg(final String msg) {
        this.msg = msg;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof JsonResult)) {
            return false;
        } else {
            JsonResult<?> other = (JsonResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label63: {
                    Object this$status = this.getStatus();
                    Object other$status = other.getStatus();
                    if (this$status == null) {
                        if (other$status == null) {
                            break label63;
                        }
                    } else if (this$status.equals(other$status)) {
                        break label63;
                    }

                    return false;
                }

                Object this$traceId = this.getTraceId();
                Object other$traceId = other.getTraceId();
                if (this$traceId == null) {
                    if (other$traceId != null) {
                        return false;
                    }
                } else if (!this$traceId.equals(other$traceId)) {
                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                if (this.getCode() != other.getCode()) {
                    return false;
                } else {
                    Object this$data = this.getData();
                    Object other$data = other.getData();
                    if (this$data == null) {
                        if (other$data != null) {
                            return false;
                        }
                    } else if (!this$data.equals(other$data)) {
                        return false;
                    }

                    return true;
                }
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof JsonResult;
    }


    public String toString() {
        return "JsonResult(status=" + this.getStatus() + ", traceId=" + this.getTraceId() + ", msg=" + this.getMsg() + ", code=" + this.getCode() + ", data=" + this.getData() + ")";
    }
}
