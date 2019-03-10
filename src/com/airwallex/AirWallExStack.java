package com.airwallex;

import com.airwallex.enu.Operator;
import com.sun.deploy.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * 堆栈
 *
 * @author Gcx
 * @date 2019/03/06
 */
public class AirWallExStack {
    /**
     * 实际的栈
     */
    private static List<String> stack = new ArrayList<>();
    /**
     * 操作记录
     */
    private static List<String> operatorsRecord;

    /**
     * 主函数
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc;
        String inputContent;
        operatorsRecord = new ArrayList<>();
        List<String> contentSplit = null;
        while (true) {
            //获取屏幕输入内容
            sc = new Scanner(System.in);
            inputContent = sc.nextLine();
            //区分数字，和操作符;
            if ((!"".equals(sc)) && sc != null) {
                contentSplit = Arrays.asList(inputContent.split(" "));
                //调用执行方法
                execute(contentSplit, true);
                System.out.println("stack:" + StringUtils.join(stack, " "));
            }
        }
    }

    /**
     * 执行操作
     *
     * @param contentSplit
     * @param isRecordTag
     */
    private static void execute(List<String> contentSplit, boolean isRecordTag) {
        StringBuffer str = new StringBuffer();
        int i = 0;
        for (; i < contentSplit.size(); i++) {
            String content = contentSplit.get(i);
            str.append(content);

            if (isNumeric(content)) {
                //将数字推入堆栈
                stack.add(content);
            } else if (Operator.contains(content)) {
                //执行操作
                try {
                    operator(Operator.getOperator(content));
                } catch (Exception e) {
                    //执行异常，显示错误信息
                    System.out.println("operator" + content + "(position:" + str.toString().length() + "): insucient parameters");
                    StringBuffer result = new StringBuffer();
                    for (i = i + 1; i < contentSplit.size(); i++) {
                        result.append(contentSplit.get(i));
                        if (i == (contentSplit.size() - 1)) {
                            result.append(" ");
                        } else {
                            result.append(" and ");
                        }
                    }
                    System.out.println("(the " + result.toString() + " were not pushed on to the stack due to the previous error)");
                    return;
                }
            }
            str.append(" ");
            //记录除了撤销以外的所有操作
            if (isRecordTag &&
                    (isNumeric(content) || Operator.contains(content)) &&
                    (!Operator.UNDO.getOperator().equals(content))) {
                operatorsRecord.add(content);
            }
        }
    }

    /**
     * 操作执函数
     *
     * @param operator
     */
    public static void operator(Operator operator) {
        switch (operator) {
            case ADD:
                add();
                return;
            case SUBTRACT:
                sub();
                return;
            case MULTIPLY:
                multi();
                return;
            case DIVIDE:
                divide();
                return;
            case SQRT:
                sqrt();
                return;
            case UNDO:
                undo();
                return;
            case CLEAR:
                clear();
                return;
        }
    }

    public static boolean isNumeric(String var) {
        try {
            BigDecimal bigDecimal = new BigDecimal(var);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 撤销
     */
    private static void undo() {
        int max = operatorsRecord.size() - 1;
        String param = operatorsRecord.get(max);
        operatorsRecord.remove(max);
        if (isNumeric(param)) {
            stack.remove(param);
        } else {
            stack.clear();
            execute(operatorsRecord, false);
        }

    }

    /**
     * 清除
     */
    private static void clear() {
        stack.clear();
    }

    /**
     * 开方
     */
    private static void sqrt() {
        int length = stack.size();
        Double sqrt = Math.sqrt(Double.parseDouble(stack.get(length - 1)));
        stack.set(length - 1, new BigDecimal(sqrt + "").setScale(10, RoundingMode.DOWN).stripTrailingZeros().toPlainString());
    }

    /**
     * 加
     */
    private static void add() {
        int length = stack.size();
        BigDecimal result = new BigDecimal(stack.get(length - 2)).add(new BigDecimal(stack.get(length - 1))).setScale(10, RoundingMode.DOWN);
        stack.set(length - 2, result.stripTrailingZeros().toPlainString());
        stack.remove(length - 1);
    }

    /**
     * 减
     */
    private static void sub() {
        int length = stack.size();
        BigDecimal result = new BigDecimal(stack.get(length - 2)).subtract(new BigDecimal(stack.get(length - 1))).setScale(10, RoundingMode.DOWN);
        stack.set(length - 2, result.stripTrailingZeros().toPlainString());
        stack.remove(length - 1);
    }

    /**
     * 乘
     */
    private static void multi() {
        int length = stack.size();
        BigDecimal result = new BigDecimal(stack.get(length - 2)).multiply(new BigDecimal(stack.get(length - 1))).setScale(10, RoundingMode.DOWN);
        stack.set(length - 2, result.stripTrailingZeros().toPlainString());
        stack.remove(length - 1);
    }

    /**
     * 除
     */
    private static void divide() {
        int length = stack.size();
        BigDecimal result = new BigDecimal(stack.get(length - 2)).divide(new BigDecimal(stack.get(length - 1))).setScale(10, RoundingMode.DOWN);
        stack.set(length - 2, result.stripTrailingZeros().toPlainString());
        stack.remove(length - 1);
    }


}
