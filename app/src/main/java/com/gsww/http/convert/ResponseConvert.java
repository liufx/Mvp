package com.gsww.http.convert;


import com.gsww.base.BaseResponse;
import com.gsww.http.exception.ApiException;

import io.reactivex.functions.Function;

/**
 * 解析BaseResponse
 */
public class ResponseConvert<E> implements Function<BaseResponse<E>, E> {
    @Override
    public E apply(BaseResponse<E> baseResponse) {
        if (!"1000".equals(baseResponse.getCode())) {
            // 手动抛出异常
            throw new ApiException(baseResponse.getCode(), baseResponse.getMsg());
        }

        return baseResponse.getDatas();
    }
}
