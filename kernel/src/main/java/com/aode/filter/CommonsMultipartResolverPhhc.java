package com.aode.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class CommonsMultipartResolverPhhc extends CommonsMultipartResolver{
    @Override
    public boolean isMultipart(HttpServletRequest request) {
        String url = request.getRequestURI();
        if (url!=null && url.contains("/conf/config.do")) {
            return false;
        } else {
            return super.isMultipart(request);
        }
    }
}
