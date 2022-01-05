package parking;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author chuangang.hu
 */
public interface IReport {

    int LEVEL = 0;

    /**
     * 将搜索得到的孩子节点存入list集合中去
     * @return list
     */
    List<? extends IReport> getChildren();

    /**
     * 动态的返回各层级结构的字符
     * @return M B P
     */
    String getType();

    /**
     * 计算停车场数目
     * @return int
     */
    int calculateParkingLotCount();

    /**
     * 计算车辆数目
     * @return int
     */
    int calculateCarCount();

    /**
     * 打印报表，呈现报表。
     */
    default void printReport(){
        report(LEVEL);
    }

    /**
     * 打印个人以及子孩子的报表，间隔符是换行符。循环递归打印个人的子节点。
     * @param level-
     * @return M  4  9
     *           B  3  7
     */
    default String report(int level){
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add(addPrefix(level) + reportSelf());
        this.getChildren().forEach(child -> joiner.add(child.report(level + 1)));
        return joiner.toString();
    }

    /**
     * 得出个人的报表。
     * @return M  8  9
     */
    default String reportSelf(){
        StringJoiner joiner = new StringJoiner("  ");
        joiner.add(getType());
        joiner.add(Integer.toString(calculateCarCount()));
        joiner.add(Integer.toString(calculateParkingLotCount()));
        return joiner.toString();
    }
    /**
     * 控制层级间隔，即前缀。Conllections.nCopies可以将前面的数据进行复制并在后面追加字符串。
     * M  4  9
     *  P  5  7
     * @param level-
     * @return string
     */
    default String addPrefix(int level){
        return String.join("", Collections.nCopies(level,"  "));
    }

}
