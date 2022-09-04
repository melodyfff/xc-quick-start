package com.xinchen.project.core.hbase.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.hadoop.hbase.client.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 *
 * AOP interceptor that binds a new Hbase table to the thread before a method call, closing and removing it afterwards in case of any method outcome.
 * If there is already a pre-bound table (from a previous call or transaction), the interceptor simply participates in it.
 * Typically used alongside {@link HbaseSynchronizationManager}.
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/9/4 14:56
 */
public class HbaseInterceptor extends HbaseAccessor implements MethodInterceptor {

    private static final Logger log = LoggerFactory.getLogger(HbaseInterceptor.class);

    private boolean exceptionConversionEnabled = true;
    private String[] tableNames;


    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Assert.notEmpty(tableNames, "at least one table needs to be specified");
    }

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Set<String> boundTables = new LinkedHashSet<String>();

        for (String tableName : tableNames) {
            if (!HbaseSynchronizationManager.hasResource(tableName)) {
                boundTables.add(tableName);
                Table table = HbaseUtils.getHTable(tableName, getConfiguration(), getConnection());
                HbaseSynchronizationManager.bindResource(tableName, table);
            }
        }

        try {
            Object retVal = methodInvocation.proceed();
            return retVal;
        } catch (Exception ex) {
            if (this.exceptionConversionEnabled) {
                throw convertHBaseException(ex);
            }
            else {
                throw ex;
            }
        } finally {
            for (String tableName : boundTables) {
                Table table = (Table) HbaseSynchronizationManager.unbindResourceIfPossible(tableName);
                if (table != null) {
                    HbaseUtils.releaseTable(tableName, table);
                }
                else {
                    log.warn("Table [" + tableName + "] unbound from the thread by somebody else; cannot guarantee proper clean-up");
                }
            }
        }
    }

    private Exception convertHBaseException(Exception ex) {
        return HbaseUtils.convertHbaseException(ex);
    }

    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }

    /**
     * Sets whether to convert any {@link IOException} raised to a Spring DataAccessException,
     * compatible with the <code>org.springframework.dao</code> exception hierarchy.
     * <p>Default is "true". Turn this flag off to let the caller receive raw exceptions
     * as-is, without any wrapping.
     * @see org.springframework.dao.DataAccessException
     *
     * @param exceptionConversionEnabled enable exceptionConversion
     */
    public void setExceptionConversionEnabled(boolean exceptionConversionEnabled) {
        this.exceptionConversionEnabled = exceptionConversionEnabled;
    }
}