package org.fintx.util;

import org.fintx.lang.Encoding;

import net.sf.cglib.beans.BeanCopier;
import net.sf.cglib.core.Converter;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

import javax.xml.bind.JAXBException;

/*
 * an extension of java.util.Objects that includes all method in java.util.Objects
 */
public class Objects {
    private Objects() {
    }

    // /**
    // *
    // * @Title: deepClone
    // * @Description: (深度克隆方法)
    // * @param src 待克隆对象
    // * @return
    // */
    // // TODO BAO性能不佳 要么用池，要么不用
    // public static Object deepClone(Object src) {
    // Object o = null;
    // try {
    // if (src != null) {
    // ByteArrayOutputStream baos = new ByteArrayOutputStream();
    // ObjectOutputStream oos = new ObjectOutputStream(baos);
    // oos.writeObject(src);
    // oos.flush();
    // oos.close();
    // ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    // ObjectInputStream ois = new ObjectInputStream(bais);
    // o = ois.readObject();
    // ois.close();
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // } catch (ClassNotFoundException e) {
    // e.printStackTrace();
    // }
    // return o;
    // }

    private static ConcurrentMap<String, BeanCopier> beanCopiers = new ConcurrentHashMap<String, BeanCopier>();
    private static final Set<Class<?>> WRAPPER_TYPES = new HashSet<Class<?>>(
            Arrays.asList(Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class));

    public static boolean isWrapperType(Class<?> clazz) {
        return WRAPPER_TYPES.contains(clazz);
    }

    /**
     * Make sure there is #no cycle reference# in the from parameter
     * 
     * @param from the object to be clone (make sure no cycle reference in the object)
     * @return the clone object
     */
    public static <T> T deepClone(T from) {
        try {
            if (null == from) {
                return null;
            }
            BeanCopier copier = getCopier(from.getClass(), from.getClass());
            if (from.getClass().isArray()) {
                int length = Array.getLength(from);
                @SuppressWarnings("unchecked")
                T clone = (T) Array.newInstance(from.getClass().getComponentType(), length);
                for (int i = 0; i < length; i++) {
                    Array.set(clone, i, deepClone(Array.get(from, i)));
                }
                return clone;
            } else if (isWrapperType(from.getClass())) {
                return from;
            } else {
                @SuppressWarnings("unchecked")
                T clone = (T) from.getClass().newInstance();
                copier.copy(from, clone, new Converter() {
                    @Override
                    public Object convert(Object pojo, @SuppressWarnings("rawtypes") Class fieldType, Object fieldName) {
                        return deepClone(pojo);
                    }
                });
                return clone;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static BeanCopier getCopier(Class<?> clz1, Class<?> clz2) {
        if (beanCopiers.containsKey(clz1.getName() + clz2.getName()))
            return beanCopiers.get(clz1.getName() + clz2.getName());
        beanCopiers.putIfAbsent(clz1.getName() + clz2.getName(), BeanCopier.create(clz1, clz2, true));
        return beanCopiers.get(clz1.getName() + clz2.getName());

    }

    public static void copyProperties(Object from, Object to) {
        BeanCopier bc = getCopier(from.getClass(), to.getClass());
        bc.copy(from, to, new Converter() {

            @Override
            public Object convert(Object value, @SuppressWarnings("rawtypes") Class target, Object context) {
                if (null != value && target.getName().equals("java.lang.String")) {
                    return value.toString();
                } else if (null != value && target.isAssignableFrom(value.getClass())) {
                    return value;
                }
                return null;
            }

        });
    }

    /**
     * Returns {@code true} if the arguments are equal to each other and {@code false} otherwise. Consequently, if both arguments are {@code null}, {@code true}
     * is returned and if exactly one argument is {@code null}, {@code
     * false} is returned. Otherwise, equality is determined by using the {@link Object#equals equals} method of the first argument.
     *
     * @param a an object
     * @param b an object to be compared with {@code a} for equality
     * @return {@code true} if the arguments are equal to each other and {@code false} otherwise
     * @see Object#equals(Object)
     */
    public static boolean equals(Object a, Object b) {
        return java.util.Objects.equals(a, b);
    }

    /**
     * Returns {@code true} if the arguments are deeply equal to each other and {@code false} otherwise.
     *
     * Two {@code null} values are deeply equal. If both arguments are arrays, the algorithm in {@link Arrays#deepEquals(Object[], Object[]) Arrays.deepEquals}
     * is used to determine equality. Otherwise, equality is determined by using the {@link Object#equals equals} method of the first argument.
     *
     * @param a an object
     * @param b an object to be compared with {@code a} for deep equality
     * @return {@code true} if the arguments are deeply equal to each other and {@code false} otherwise
     * @see Arrays#deepEquals(Object[], Object[])
     * @see java.util.Objects#equals(Object, Object)
     */
    public static boolean deepEquals(Object a, Object b) {
        return java.util.Objects.deepEquals(a, b);
    }

    /**
     * Returns the hash code of a non-{@code null} argument and 0 for a {@code null} argument.
     *
     * @param o an object
     * @return the hash code of a non-{@code null} argument and 0 for a {@code null} argument
     * @see Object#hashCode
     */
    public static int hashCode(Object o) {
        return java.util.Objects.hashCode(o);
    }

    /**
     * Generates a hash code for a sequence of input values. The hash code is generated as if all the input values were placed into an array, and that array
     * were hashed by calling {@link Arrays#hashCode(Object[])}.
     *
     * <p>
     * This method is useful for implementing {@link Object#hashCode()} on objects containing multiple fields. For example, if an object that has three fields,
     * {@code x}, {@code
     * y}, and {@code z}, one could write:
     *
     * <blockquote>
     * 
     * <pre>
     * &#064;Override
     * public int hashCode() {
     *     return java.util.Objects.hash(x, y, z);
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * <b>Warning: When a single object reference is supplied, the returned value does not equal the hash code of that object reference.</b> This value can be
     * computed by calling {@link #hashCode(Object)}.
     *
     * @param values the values to be hashed
     * @return a hash value of the sequence of input values
     * @see Arrays#hashCode(Object[])
     * @see List#hashCode
     */
    public static int hash(Object...values) {
        return java.util.Objects.hash(values);
    }

    /**
     * Returns the result of calling {@code toString} for a non-{@code
     * null} argument and {@code "null"} for a {@code null} argument.
     *
     * @param o an object
     * @return the result of calling {@code toString} for a non-{@code
     * null} argument and {@code "null"} for a {@code null} argument
     * @see Object#toString
     * @see String#valueOf(Object)
     */
    public static String toString(Object o) {
        return java.util.Objects.toString(o);
    }

    /**
     * Returns the result of calling {@code toString} on the first argument if the first argument is not {@code null} and returns the second argument otherwise.
     *
     * @param o an object
     * @param nullDefault string to return if the first argument is {@code null}
     * @return the result of calling {@code toString} on the first argument if it is not {@code null} and the second argument otherwise.
     * @see java.util.Objects#toString(Object)
     */
    public static String toString(Object o, String nullDefault) {
        return java.util.Objects.toString(o, nullDefault);
    }

    /**
     * Returns 0 if the arguments are identical and {@code
     * c.compare(a, b)} otherwise. Consequently, if both arguments are {@code null} 0 is returned.
     *
     * <p>
     * Note that if one of the arguments is {@code null}, a {@code
     * NullPointerException} may or may not be thrown depending on what ordering policy, if any, the {@link Comparator Comparator} chooses to have for
     * {@code null} values.
     *
     * @param <T> the type of the objects being compared
     * @param a an object
     * @param b an object to be compared with {@code a}
     * @param c the {@code Comparator} to compare the first two arguments
     * @return 0 if the arguments are identical and {@code
     * c.compare(a, b)} otherwise.
     * @see Comparable
     * @see Comparator
     */
    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        return java.util.Objects.compare(a, b, c);
    }

    /**
     * Checks that the specified object reference is not {@code null}. This method is designed primarily for doing parameter validation in methods and
     * constructors, as demonstrated below: <blockquote>
     * 
     * <pre>
     * public Foo(Bar bar) {
     *     this.bar = java.util.Objects.requireNonNull(bar);
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param obj the object reference to check for nullity
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj) {
        return java.util.Objects.requireNonNull(obj);
    }

    /**
     * Checks that the specified object reference is not {@code null} and throws a customized {@link NullPointerException} if it is. This method is designed
     * primarily for doing parameter validation in methods and constructors with multiple parameters, as demonstrated below: <blockquote>
     * 
     * <pre>
     * public Foo(Bar bar, Baz baz) {
     *     this.bar = java.util.Objects.requireNonNull(bar, "bar must not be null");
     *     this.baz = java.util.Objects.requireNonNull(baz, "baz must not be null");
     * }
     * </pre>
     * 
     * </blockquote>
     *
     * @param obj the object reference to check for nullity
     * @param message detail message to be used in the event that a {@code
     *                NullPointerException} is thrown
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    public static <T> T requireNonNull(T obj, String message) {
        return java.util.Objects.requireNonNull(obj, message);
    }

    /**
     * Returns {@code true} if the provided reference is {@code null} otherwise returns {@code false}.
     *
     * @param obj a reference to be checked against {@code null}
     * @return {@code true} if the provided reference is {@code null} otherwise {@code false}
     *
     * @see java.util.function.Predicate
     * @since 1.8
     */
    public static boolean isNull(Object obj) {
        return java.util.Objects.isNull(obj);
    }

    /**
     * Returns {@code true} if the provided reference is non-{@code null} otherwise returns {@code false}.
     *
     * @param obj a reference to be checked against {@code null}
     * @return {@code true} if the provided reference is non-{@code null} otherwise {@code false}
     *
     * @see java.util.function.Predicate
     * @since 1.8
     */
    public static boolean nonNull(Object obj) {
        return java.util.Objects.nonNull(obj);
    }

    /**
     * Checks that the specified object reference is not {@code null} and throws a customized {@link NullPointerException} if it is.
     *
     * <p>
     * Unlike the method {@link #requireNonNull(Object, String)}, this method allows creation of the message to be deferred until after the null check is made.
     * While this may confer a performance advantage in the non-null case, when deciding to call this method care should be taken that the costs of creating the
     * message supplier are less than the cost of just creating the string message directly.
     *
     * @param obj the object reference to check for nullity
     * @param messageSupplier supplier of the detail message to be used in the event that a {@code NullPointerException} is thrown
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     * @since 1.8
     */
    public static <T> T requireNonNull(T obj, Supplier<String> messageSupplier) {
        return java.util.Objects.requireNonNull(obj, messageSupplier);
    }

    public static class Xml {
        private static ObjectsXml convertor = new ObjectsXml(null, true, Encoding.UTF_8, false, null);

        /**
         * Convert bean object to xml string
         * 
         * @param <T> the object type
         * @param object the object to be converted
         * @return String xml of bean
         * @throws JAXBException when there is exception during the conversion
         */

        public static <T> String toString(final T object) throws JAXBException {
            return convertor.toString(object);
        }

        /**
         * Convert xml string to object of the clazz
         * 
         * @param <T> the object type
         * @param xml xml to be convert to object
         * @param clazz target class type of object
         * @return target object
         * @throws JAXBException when there is exception during the conversion
         */

        public static <T> T toObject(final String xml, final Class<T> clazz) throws JAXBException {
            return convertor.toObject(xml, clazz);
        }

        public static ObjectsXml custom(Map<String, String> namespacePrefixMapper, boolean formatted, Encoding encoding, boolean fragment,
                String headers) {
            return new ObjectsXml(namespacePrefixMapper, formatted, encoding, fragment, headers);
        }

    }

    public static class Text {
        private static ObjectsText convertor = new ObjectsText(Encoding.UTF_8, '|', "\r\n", false);

        /**
         * Convert bean object to text string
         * 
         * @param <T> the object type
         * @param bean to be convert to text
         * @return String text of bean
         * @throws ReflectiveOperationException when there is exception during conversion
         */

        public static <T> String toString(final T bean) throws ReflectiveOperationException {
            return convertor.toString(bean);
        }

        /**
         * Convert text string to bean of clazz
         * 
         * @param <T> the object type
         * @param text text to be convert to object
         * @param clazz target class type of object
         * @return target object
         * @throws ReflectiveOperationException when there is exception during conversion
         */

        public static <T> T toObject(final String text, final Class<T> clazz) throws ReflectiveOperationException {
            return convertor.toObject(text, clazz);
        }

        public static ObjectsText custom(final Encoding encoding, final Character separate, final String linkbreak, final boolean withname,
                final Character associate) {
            return new ObjectsText(encoding, separate, linkbreak, withname, associate);
        }
    }
}
