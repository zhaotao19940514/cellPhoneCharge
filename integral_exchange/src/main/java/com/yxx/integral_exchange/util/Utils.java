package com.yxx.integral_exchange.util;

import sun.misc.Cleaner;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedAction;

public final class Utils {
    private static char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private Utils() {
    }

    public static String getMD5(String pwd) {
        byte[] source = pwd.getBytes();
        String s = null;

        try {
            MessageDigest e = MessageDigest.getInstance("MD5");
            e.update(source);
            byte[] tmp = e.digest();
            char[] str = new char[32];
            int k = 0;

            for(int i = 0; i < 16; ++i) {
                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 15];
                str[k++] = hexDigits[byte0 & 15];
            }

            s = new String(str);
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        return s;
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException var3) {
            System.err.println(var3.getMessage());
        }

    }

    /**
     * 检查
     *
     * @param e   布尔表达式
     * @param msg 如果检查不通过，则跑出异常的信息
     */
    public static void assertCheck(boolean e, String msg) {
        if (!e) {
            throw new RuntimeException(msg);
        }
    }

    /**
     * 获取一系列基础类的包装类
     *
     * @param x
     * @return
     */
    public static Class getPackClass(Class x) {
        if (x.equals(int.class)) {
            x = Integer.class;
        } else if (x.equals(short.class)) {
            x = Short.class;
        } else if (x.equals(float.class)) {
            x = Float.class;
        } else if (x.equals(byte.class)) {
            x = Byte.class;
        } else if (x.equals(char.class)) {
            x = Character.class;
        } else if (x.equals(long.class)) {
            x = Long.class;
        } else if (x.equals(boolean.class)) {
            x = Boolean.class;
        } else if (x.equals(double.class)) {
            x = Double.class;
        }
        return x;
    }

    public static Method getMethod(Class cls, String mname, Class<?>... ptys) {
        try {
            return cls.getMethod(mname, ptys);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Throwable getOurThrowable(Throwable e) {
        Throwable oure = e.getCause();
        if (oure != null) {
            return getOurThrowable(oure);
        }
        return e;
    }

    /**
     * 格式化异常
     * @param ex
     * @return
     */
    public static String fmtThrowable(Throwable ex) {
        ex = getOurThrowable(ex);
        StringBuilder str = new StringBuilder(ex.getClass().getSimpleName());
        str.append(":").append(ex.getMessage());
        StackTraceElement[] outTrace = ex.getStackTrace();
        if(outTrace!=null && outTrace.length>0) {
            str.append(" ……at ");
            for(int i=0;i<outTrace.length && i<10;i++) {
                if (i > 0) {
                    str.append("\n\t");
                }
                str.append(outTrace[i].getFileName())
                        .append(".").append(outTrace[i].getMethodName())
                        .append(":").append(outTrace[i].getLineNumber());
            }
        }
        return str.toString();
    }

    /**
     * 获取版本冲突时冲突类的原始资源位置
     * @param er
     */
    public static String getNoSuchRs(IncompatibleClassChangeError er) {
        Throwable ex = getOurThrowable(er);
        try {
            StackTraceElement[] sts = ex.getStackTrace();
            if(sts.length>0){
                StackTraceElement st = sts[0];
                String fn = st.getFileName();
                String className = st.getClassName();
                Class clazz = Class.forName(className);
                fn +=":"+clazz.getResource(clazz.getSimpleName() + ".class").toString();
                return fn;
            }else{
                String msg = er.getMessage();
                String className = msg.substring(0, msg.lastIndexOf("."));
                Class clazz = Class.forName(className);
                return clazz.getResource(clazz.getSimpleName() + ".class").toString();
            }
        } catch (Throwable e) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化 毫秒时间
     * @param ms
     * @return
     */
    public static String fmtTotalTime(long ms) {
        if (ms == 0) {
            return "0";
        } else if (ms < 1000) {
            return ms + "ms";//1秒以内
        } else if (ms < 1000 * 60) {
            return ms / 1000 + "s " + fmtTotalTime(ms % 1000);//一分钟内
        } else if (ms < 1000 * 3600) {
            return ms / (1000 * 60) + "min " + fmtTotalTime(ms % (1000 * 60));//一小时内
        } else if (ms < 1000 * 3600 * 24) {
            return ms / (1000 * 3600) + "h " + fmtTotalTime(ms % (1000 * 3600));//一天内
        } else {
            return ms / (1000 * 3600 * 24) + "d " + fmtTotalTime(ms % (1000 * 3600 * 24));//大于一天
        }
    }


    /**
     * 将格式化的字节还原成字节数
     * @param str 格式串，例：50m,15g,0.8t
     */
    public static long getBytesNum(String str) {
        String sizeStr = str.toLowerCase().trim();
        if(sizeStr.endsWith("b")){
            sizeStr = sizeStr.substring(0, sizeStr.length() - 1).trim();
        }
        long unit = 1l;
        if (sizeStr.endsWith("k")) {
            sizeStr = sizeStr.substring(0, sizeStr.length() - 1).trim();
            unit = 1024;
        }else if (sizeStr.endsWith("m")) {
            sizeStr = sizeStr.substring(0, sizeStr.length() - 1).trim();
            unit = 1048576;//1024 * 1024;
        } else if (sizeStr.endsWith("g")) {
            sizeStr = sizeStr.substring(0, sizeStr.length() - 1).trim();
            unit = 1073741824;//1024 * 1024 * 1024;
        } else if(sizeStr.endsWith("t")){
            sizeStr = sizeStr.substring(0,sizeStr.length()-1).trim();
            unit = 1099511627776l;//1024 * 1024 * 1024 * 1024;
        } else if(sizeStr.endsWith("p")){
            sizeStr = sizeStr.substring(0,sizeStr.length()-1).trim();
            unit = 1125899906842624l;//1024 * 1024 * 1024 * 1024 * 1024;
        } else if(sizeStr.endsWith("e")){
            sizeStr = sizeStr.substring(0,sizeStr.length()-1).trim();
            unit = 1152921504606846976l;//1024 * 1024 * 1024 * 1024 * 1024 * 1024;
        }else{
            throw new IllegalArgumentException("Not support parse:"+str);
        }
        return (long)(unit * Double.parseDouble(sizeStr));
    }

    /**
     * 格式化 字节大小
     *
     * @param bs
     * @return
     */
    public static String fmtTotalBytes(long bs) {
        if (bs <= 1024) {
            return bs + "b";
        } else if (bs < (1l << 20)) {
            return bs / (1l << 10) + "kb " + fmtTotalBytes(bs % (1l << 10));
        } else if (bs < (1l << 30)) {
            return bs / (1l << 20) + "mb " + fmtTotalBytes(bs % (1l << 20));
        } else if (bs < (1l << 40)) {
            return bs / (1l << 30) + "gb " + fmtTotalBytes(bs % (1l << 30));
        } else if (bs < (1l << 50)) {
            return bs / (1l << 40) + "tb " + fmtTotalBytes(bs % (1l << 40));
        } else {
            return bs / (1l << 50) + "pb " + fmtTotalBytes(bs % (1l << 50));
        }
    }

    public static byte[] expandVolume(byte[] arr, int size) {
        if(size < 1024) {
            size = 1024;
        }

        byte[] b1 = new byte[arr.length + size];
        System.arraycopy(arr, 0, b1, 0, arr.length);
        return b1;
    }

    public static void forceDestory(final Object obj) {
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                try {
                    Method e = obj.getClass().getMethod("cleaner", new Class[0]);
                    e.setAccessible(true);
                    Cleaner cleaner = (Cleaner)e.invoke(obj, new Object[0]);
                    cleaner.clean();
                } catch (Exception var3) {
                    System.err.println(var3.getMessage());
                }
                return null;
            }
        });
    }

    public static long getMinDistancePrimeNum(long num, boolean gl) {
        if(num < 0L) {
            throw new IllegalArgumentException("num must>0!");
        } else {
            long k;
            long j;
            if(gl) {
                while(true) {
                    k = (long)Math.sqrt((double)num);

                    for(j = 2L; j <= k && num % j != 0L; ++j) {
                        ;
                    }

                    if(j > k) {
                        return num;
                    }

                    ++num;
                }
            } else {
                while(num > 0L) {
                    k = (long)Math.sqrt((double)num);

                    for(j = 2L; j <= k && num % j != 0L; ++j) {
                        ;
                    }

                    if(j > k) {
                        return num;
                    }

                    --num;
                }

                throw new IllegalArgumentException("not found!");
            }
        }
    }
}
