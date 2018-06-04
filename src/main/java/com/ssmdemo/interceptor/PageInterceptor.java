package com.ssmdemo.interceptor;

import com.ssmdemo.common.Page;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Properties;

/**
 * 分页拦截器
 * Created by zhuguangchuan on 2018/6/1.
 */
@Intercepts({@Signature(type=StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PageInterceptor implements Interceptor{


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) this.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);

        //获取mapperStatement
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if(!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())){
            return invocation.proceed();
        }

        ParameterHandler parameterHandler = (ParameterHandler) metaObject.getValue("delegate.parameterHandler");
        Map<String,Object> parameterObject = (Map<String, Object>) parameterHandler.getParameterObject();
        Page page = (Page) parameterObject.get("page");
        //不分页
        if(page == null){
            invocation.proceed();
        }

        //查询总数
        String sql = statementHandler.getBoundSql().getSql();
        Connection connection = (Connection) invocation.getArgs()[0];
        PreparedStatement statement = connection.prepareStatement(sql);
        parameterHandler.setParameters(statement);
        ResultSet resultSet = statement.executeQuery();
        resultSet.last();
        int total = resultSet.getRow();
        page.setTotal(total);

        int pageNo = page.getPageNo();
        int pageSize = page.getPageSize();
        String limitSql = new StringBuilder(sql).append(" limit ")
                .append((pageNo -1)*pageSize).append(",").append(pageSize).toString();

        metaObject.setValue("delegate.boundSql.sql",limitSql);
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    //分离代理对象链，获取最终的对象
    public Object realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            MetaObject metaObject = SystemMetaObject.forObject(target);
            return realTarget(metaObject.getValue("h.target"));
        }
        return target;
    }
}
