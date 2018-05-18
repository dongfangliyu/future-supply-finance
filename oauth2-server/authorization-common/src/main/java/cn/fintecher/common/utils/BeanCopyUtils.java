package cn.fintecher.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lijinlong on 2017/08/02.
 */
public abstract class BeanCopyUtils extends BeanUtils {
    public BeanCopyUtils() {
    }

    public static void copyProperties(Object source, Object target, boolean ignoreNullFlag, boolean ignoreNotSeted) throws BeansException {

        copyPropertiesEx(source, target, ignoreNullFlag, ignoreNotSeted, (Class) null, (String[]) null);
    }

    public static void copyProperties(Object source, Object target, boolean ignoreNullFlag, boolean ignoreNotSeted, String... ignoreProperties) throws BeansException {

        copyPropertiesEx(source, target, ignoreNullFlag, ignoreNotSeted, (Class) null, ignoreProperties);
    }

    private static void copyPropertiesEx(Object source, Object target, boolean ignoreNullFlag, boolean ignoreNotSeted, Class<?> editable, String... ignoreProperties) throws BeansException {
        Assert.notNull(source, "Source must not be null");
        Assert.notNull(target, "Target must not be null");
        Class<?> actualEditable = target.getClass();
        if (editable != null) {
            if (!editable.isInstance(target)) {
                throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
            }

            actualEditable = editable;
        }

        PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
        List<String> ignoreList = ignoreProperties != null ? Arrays.asList(ignoreProperties) : null;
        PropertyDescriptor[] var7 = targetPds;
        int var8 = targetPds.length;

        for (int var9 = 0; var9 < var8; ++var9) {
            PropertyDescriptor targetPd = var7[var9];
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());

                if (sourcePd != null) {

                    Method readMethod = sourcePd.getReadMethod();

                    if (ignoreNotSeted == true) {
                        Boolean bHasSeted = checkHasSeted(source, targetPd.getName());
                        if (bHasSeted == false) {
                            readMethod = null;
                        }
                    }

                    if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }

                            Object value = readMethod.invoke(source, new Object[0]);

                            Boolean bRight = true;

                            if (ignoreNullFlag == true && value == null) {
                                bRight = false;
                            }

                            if (bRight == true) {
                                if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                    writeMethod.setAccessible(true);
                                }

                                writeMethod.invoke(target, new Object[]{value});
                            }
                        } catch (Throwable var15) {
                            throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", var15);
                        }
                    }
                }
            }
        }
    }

    private static Boolean checkHasSeted(Object obj, String propertyName) {
        Boolean bHasSeted = true;

        try {
            String hasName = String.format("has%s"
                    , HumpNameOrMethodUtils.str2LowerCase(propertyName));

            Method method = obj.getClass().getDeclaredMethod(hasName);

            if (method != null) {
                Object bResult = method.invoke(obj);
                if (bResult instanceof Boolean) {
                    bHasSeted = (Boolean) bResult;
                }
            }
        } catch (Exception e) {
            //Ignore
        }

        return bHasSeted;
    }

}
