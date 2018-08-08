import com.sun.deploy.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Test {
    public static void main(String[] args) {

        List<List<String> > result = new ArrayList<List<String>>();

        result.add(zuhe("AAB"));
        result.add(zuhe("CDD"));

        arrayZuHe(result);
        //System.out.println("AABC".replaceFirst("A",""));
    }

    public static void arrayZuHe(List<List<String>> lists) {
        List<List<String> > result = new ArrayList<List<String>>();
        arrayZuHeInner(lists,new ArrayList<String>(),0,result);
        System.out.println(result);
        List list2 = result.stream().map((List<String> list) ->
                StringUtils.join(list,""))
                .collect(Collectors.toList());
        System.out.println(list2);
    }

    public static void arrayZuHeInner(List<List<String>> lists,List<String> pre,int dept,List<List<String> > result ) {

        List<String> thisLevel = lists.get(dept);
            for(String s:thisLevel) {
                List<String> pre1 = new ArrayList<>(pre);
                pre1.add(s);
                if ( dept == lists.size() -1 ) {
                    result.add(pre1);
                } else {
                    arrayZuHeInner(lists,pre1,dept+1,result);
                }
            }
    }


    public static List<String> zuhe(String str) {
        List<List<String> > result = new ArrayList<List<String>>();
        zuheInner(str,0,str.length(),new ArrayList<String>(),result);
        //System.out.println(result);
        List list2 = result.stream().map((List<String> list) ->
                StringUtils.join(list,""))
                .collect(Collectors.toList());
        System.out.println(list2);
        return list2;
    }

    public static void zuheInner(String str,int dept,int max,List<String> pre,List<List<String> > result) {
        if ( dept == max ) {
            result.add(pre);
            return ;
        }
        char[]   chars = str.toCharArray();
        Set set = new HashSet<String>();//可以取的列表
        for(char c : chars) {
            set.add(c+"");
        }
        List<String> list = new ArrayList<String>(set);
        for(int i = 0 ; i < list.size() ;i++) {
            String s = list.get(i).toString();
            List<String> pre1 = new ArrayList<>(pre);
            pre1.add(s);

            zuheInner(str.replaceFirst(s,""),dept+1,max,pre1,result);
        }
        //System.out.println(set);
    }


}
