package org.pinggu.portforu.common.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Response<T> {
    private T data;
    private PageInfo page;

    private Response(T data) {
        this.data = data;
    }

    private Response(T data, PageInfo page) {
        this.data = data;
        this.page = page;
    }

    // 페이징 적용 X
    public static <T> Response<T> of(T data) {
        return new Response<>(data);
    }

    // 페이징 적용
    public static <T> Response<T> of(T data, PageInfo page) {
        return new Response<>(data, page);
    }

}