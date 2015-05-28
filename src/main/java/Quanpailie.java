

import java.util.*;

public class Quanpailie {
    public static void main(String[] args) {
        String s="AABCD";//原字符串
        List<String> result = new ArrayList<String>();//存放结果信息。
        list(s, "", result);//列出字符的组合，放入result
        System.out.println(result.size());;
        System.out.println(result);
    }
    
    /**
     * 列出基础字符串(base)的所有组合
     * @param base 以该字符串作为基础字符串，进行选择性组合。
     * @param buff 所求字符串的临时结果
     * @param result 存放所求结果
     */
    public static void list(String base,String buff,List<String> result){
        if(base.length()<=0){
            result.add(buff);
        }
        for(int i=0;i<base.length();i++){
            list(new StringBuilder(base).deleteCharAt(i).toString(),buff+base.charAt(i),result);
        }
    }

}
