package method;

/**
 *
 * @author lqik2004
 * 制作录入单据的编号，输入ID，输入ID+年+月+日+当日顺序编号(三位编号)
 * 把tag设置为静态共享数据
 * 希望实现的功能是，以后可以根据这个数据制作成一个当日输入流程图；
 */
public class idMake {

    public String idMake(String ID) {
        String x=ID;
        judge();
        String tagNew=String.valueOf(tag);
        String[] taglib={"00","0",""};//自动补零算法
        tagNew=taglib[tagNew.length()-1]+tagNew;
        String IDX=x+getDate.getYear()+getDate.getMonth()+getDate.getDay()+tagNew;
        return IDX;
    }

    private void judge() {
        if (judge == getDate.dayIndex()) {
            tag++;
        } else {
            tag = 0;
            judge = getDate.dayIndex();
        }
    }  
    /**
     * 对两个变量使用的说明：
     * 这两个变量主要的作用就是为了达到1）在同一天的编号是顺序排列的，2）在不同天从0开始刷新
     * 没经过一个judge(),就会使得tag做出相应的更改，从而更新tagNew，从而达到目的
     */
    public static int tag =Integer.parseInt(propertiesRW.proIDMakeRead("tag"));
    public static int judge = Integer.parseInt(propertiesRW.proIDMakeRead("judge"));
            
}
