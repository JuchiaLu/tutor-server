package com.juchia.tutor.common.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author darren
 * @description 封装Spring BeanUtils工具类，让其更好用
 */
@UtilityClass
public class MyBeanUtils {


    public <S, T> T copy(S source, Class<T> targetCls) {
        return copy(source,targetCls,null,null);
    }

    public <S, T> T copy(S source, Class<T> targetCls, String ...ignoreProperties) {
        return copy(source,targetCls,null,ignoreProperties);
    }

    public <S, T> T copy(S source, Class<T> targetCls, Class<?> editable,String ...ignoreProperties) {
        if (source == null) {
            return null;
        }
        try {
            T target = targetCls.newInstance();
            copyProperties(source, target,editable,ignoreProperties);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("BEAN_COPY_SYSTEM_EXCEPTION", e);
        }
    }

    public <S, T> List<T> copyList(List<S> source, Class<T> targetCls) {
        return copyList(source,targetCls,null,null);
    }

    public <S, T> List<T> copyList(List<S> source, Class<T> targetCls, String ...ignoreProperties) {
        return copyList(source,targetCls,null,ignoreProperties);
    }

    public <S, T> List<T> copyList(List<S> source, Class<T> targetCls, Class<?> editable, String ...ignoreProperties) {
        if (source == null || source.isEmpty()) {
            return new ArrayList<>();
        }
        return source.stream()
                .map(s-> copy(s,targetCls,editable,ignoreProperties))
                .collect(Collectors.toList());
    }

    private void copyProperties(@NonNull Object source,@NonNull Object target, Class<?> editable, String... ignoreProperties) throws BeansException {

        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
                        "] not assignable to Editable class [" + editable.getName() + "]");
            }
            actualEditable = editable;
        }
        PropertyDescriptor[] targetPds = org.springframework.beans.BeanUtils.getPropertyDescriptors(actualEditable);
        List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

        for (PropertyDescriptor targetPd : targetPds) {
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = org.springframework.beans.BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            Object value = readMethod.invoke(source);
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            //主要的核心在这里：针对Field的类型为Collection类型的处理
                            if(value instanceof Collection){
                                Type writeType = writeMethod.getGenericParameterTypes()[0];
                                Type readType = readMethod.getGenericReturnType();
                                Type writeActualType;
                                //当 source 中 Collection类型的Field的泛型和target中的Field的泛型类型不一致的时候 需要进行深度拷贝
                                if (writeType instanceof ParameterizedType
                                        && readType instanceof ParameterizedType
                                        && (writeActualType = ((ParameterizedType) writeType).getActualTypeArguments()[0]) != ((ParameterizedType) readType).getActualTypeArguments()[0]
                                ){
                                    Collection sourceList = (Collection) value;
                                    Collection actualValue = (Collection) value.getClass().newInstance();//这里只是简单的调用反射来实例化，因此原集合类型必须要有无参构造，比如Arrays.asList创建的集合则无法拷贝，会抛出异常
                                    actualValue.clear();
                                    String prefix = targetPd.getName()+".";
                                    String[] subFilter = ignoreProperties;
                                    if(!CollectionUtils.isEmpty(ignoreList)){
                                        subFilter = ignoreList.stream()
                                                .filter(ignore -> ignore.startsWith(prefix))
                                                .map(ignore -> ignore.substring(prefix.length()))
                                                .toArray(String[]::new);
                                    }
                                    for (Object subSource : sourceList) {
                                        Object subTarget = ((Class) writeActualType).newInstance();
                                        copyProperties(subSource,subTarget,editable,subFilter);
                                        actualValue.add(subTarget);
                                    }
                                    value = actualValue;
                                }
                            }
                            writeMethod.invoke(target, value);
                        }
                        catch (Throwable ex) {
                            throw new FatalBeanException(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }
}
