package com.xinchen.project.core.hbase.spring;

import org.apache.hadoop.hbase.client.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.transaction.support.ResourceTransactionManager;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * Synchronization manager handling the tracking of Hbase resources (specifically tables). Used either manually or through {@link HbaseInterceptor} to bind a table to the thread.
 * Each subsequent call made through {@link HbaseTemplate} is aware of the table bound and will use it instead of retrieving a new instance.
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/9/4 14:14
 */
public class HbaseSynchronizationManager {
    private static final Logger logger = LoggerFactory.getLogger(HbaseSynchronizationManager.class);

    private static final ThreadLocal<Map<String, Table>> resources = new NamedThreadLocal<>("Bound resources");

    /**
     * Checks whether any resource is bound for the given key.
     *
     * @param key key to check
     * @return whether or not a resource is bound for the given key
     */
    public static boolean hasResource(Object key) {
        Object value = doGetResource(key);
        return (value != null);
    }

    /**
     * Returns the resource (table) associated with the given key.
     *
     * @param key association key
     * @return associated resource (table)
     */
    public static Table getResource(Object key) {
        return doGetResource(key);
    }

    /**
     * Actually checks the value of the resource that is bound for the given key.
     */
    private static Table doGetResource(Object actualKey) {
        Map<String, Table> tables = resources.get();
        if (tables == null) {
            return null;
        }
        return tables.get(actualKey);
    }

    /**
     * Binds the given resource for the given key to the current thread.
     * @param key the key to bind the value to (usually the resource factory)
     * @param value the value to bind (usually the active resource object)
     * @throws IllegalStateException if there is already a value bound to the thread
     * @see ResourceTransactionManager#getResourceFactory()
     */
    public static void bindResource(String key, Table value) throws IllegalStateException {
        Assert.notNull(value, "Value must not be null");
        Map<String, Table> map = resources.get();
        // set ThreadLocal Map if none found
        if (map == null) {
            map = new LinkedHashMap<String, Table>();
            resources.set(map);
        }
        Table oldValue = map.put(key, value);
        if (oldValue != null) {
            throw new IllegalStateException("Already value [" + oldValue + "] for key [" + key
                    + "] bound to thread [" + Thread.currentThread().getName() + "]");
        }
        if (logger.isTraceEnabled()) {
            logger.trace("Bound value [" + value + "] for key [" + key + "] to thread ["
                    + Thread.currentThread().getName() + "]");
        }
    }

    /**
     * Unbinds a resource for the given key from the current thread.
     * @param key the key to unbind (usually the resource factory)
     * @return the previously bound value (usually the active resource object)
     * @throws IllegalStateException if there is no value bound to the thread
     * @see ResourceTransactionManager#getResourceFactory()
     */
    public static Table unbindResource(String key) throws IllegalStateException {
        Table value = doUnbindResource(key);
        if (value == null) {
            throw new IllegalStateException("No value for key [" + key + "] bound to thread ["
                    + Thread.currentThread().getName() + "]");
        }
        return value;
    }

    /**
     * Unbinds a resource for the given key from the current thread.
     *
     * @param key the key to unbind (usually the resource factory)
     * @return the previously bound value, or <code>null</code> if none bound
     */
    public static Object unbindResourceIfPossible(Object key) {
        return doUnbindResource(key);
    }

    /**
     * Actually remove the value of the resource that is bound for the given key.
     */
    private static Table doUnbindResource(Object actualKey) {
        Map<String, Table> map = resources.get();
        if (map == null) {
            return null;
        }
        Table value = map.remove(actualKey);
        // Remove entire ThreadLocal if empty...
        if (map.isEmpty()) {
            resources.remove();
        }

        if (value != null && logger.isTraceEnabled()) {
            logger.trace("Removed value [" + value + "] for key [" + actualKey + "] from thread ["
                    + Thread.currentThread().getName() + "]");
        }
        return value;
    }

    /**
     * Returns the bound tables (by name).
     *
     * @return names of bound tables
     */
    public static Set<String> getTableNames() {
        Map<String, Table> map = resources.get();
        if (map != null && !map.isEmpty()) {
            return Collections.unmodifiableSet(map.keySet());
        }
        return Collections.emptySet();
    }
}
