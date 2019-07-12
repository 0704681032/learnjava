package com.jyy.learnspringboot2.demo;

import java.util.LinkedList;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleParser {

    //${  }
    public static final String PREFIX = "${";
    public static final String SUFFIX = "}";
    static final Properties properties = new Properties();

    static {
        properties.put("name.jyy","金阳阳");
        properties.put("eng","jyy");
        properties.put("en","jy");
        properties.put("XX","m");
        properties.put("N","n");
    }

    public static void main(String[] args) {
        System.out.println(parseString("${name.jyy}"));
        System.out.println(parseString("${name.${eng}}"));
        System.out.println(parseString("${name.${en}y}"));
        System.out.println(parseString("${na${XX}e.${en}y}"));
        System.out.println(parseString("${na${XX}e.${e${N}}y}"));
        System.out.println(parseString("pre--${na${XX}e.${e${N}}y}"));
    }

    public static String parseString(String originStr) {
        Context rootContext = new Context(true);
        parseString(originStr,new AtomicInteger(0),rootContext);//构造ast
        return rootContext.getValue();
    }

    private static void parseString(String originStr, AtomicInteger scanIndex, Context rootContext) {

        while (scanIndex.get() < originStr.length()) {
            String currentStr = originStr.substring(scanIndex.get());//当前需要处理的字符串
            if (currentStr.startsWith(PREFIX)) {
                Context childContext = new Context();
                rootContext.addChildren(childContext);
                scanIndex.addAndGet(PREFIX.length());//前进2
                parseString(originStr,scanIndex,childContext);
            } else {
                int indexP = currentStr.indexOf(PREFIX);
                int indexS = currentStr.indexOf(SUFFIX);
                if (indexP == -1 && indexS == -1) { //都不存在
                    rootContext.addChildren(new StringElemet(currentStr));
                    scanIndex.set(originStr.length());
                    return ;
                } else if (indexP != -1 && indexS != -1) { //都存在
                    int min = indexP < indexS ? indexP : indexS;
                    boolean s = min == indexS ;//后缀在前面
                    if (s) {
                        rootContext.addChildren(new StringElemet(currentStr.substring(0,indexS)));
                        scanIndex.addAndGet(indexS+1);
                        return ;
                    } else {
                        rootContext.addChildren(new StringElemet(currentStr.substring(0,indexP)));
                        scanIndex.addAndGet(indexP);
                    }
                } else { //只有一个存在 那么是 }
                    rootContext.addChildren(new StringElemet(currentStr.substring(0,indexS)));
                    scanIndex.addAndGet(indexS+1);
                    return ;
                }
            }
        }

    }

    public  interface  Elemet {
        String getValue();
    }

    public static class StringElemet implements Elemet {
        private String value ;
        public StringElemet(String value) {
            this.value = value;
        }
        @Override
        public String getValue() {
            return value;
        }
    }
    public static class Context implements Elemet {
        private boolean isRoot ;
        public Context() {

        }
        public Context(boolean isRoot) {
            this.isRoot = isRoot;
        }
        private LinkedList<Elemet> childrens = new LinkedList<>();//可以是StringElemet也可以是Context自身
        public void addChildren(Elemet children){
            childrens.add(children);
        }
        @Override
        public String getValue() {
            StringBuilder sb = new StringBuilder();
            childrens.forEach(e ->{
                sb.append(e.getValue());
            });
            return isRoot ? sb.toString() : properties.getProperty(sb.toString());
        }
    }

}
