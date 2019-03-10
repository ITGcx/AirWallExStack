package com.airwallex.enu;

/**
 * 堆栈操作符
 *
 * @author Gcx
 * @date 2019/03/06
 */
public enum Operator {
    /**
     * 加、减、乘、除、平方根、撤销、清除
     */
    ADD("+"), SUBTRACT("-"), MULTIPLY("*"), DIVIDE("/"), SQRT("sqrt"), UNDO("undo"), CLEAR("clear");

    private String operator;

    Operator(String operator) {
        this.operator = operator;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 检查输入字符
     *
     * @param operator
     * @return
     */
    public static boolean contains(String operator) {
        for (Operator c : Operator.values()) {
            if (c.getOperator().equals(operator)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取字符对应的枚举
     *
     * @param operator
     * @return
     */
    public static Operator getOperator(String operator) {
        switch (operator) {
            case "-":
                return SUBTRACT;
            case "*":
                return MULTIPLY;
            case "/":
                return DIVIDE;
            case "sqrt":
                return SQRT;
            case "undo":
                return UNDO;
            case "clear":
                return CLEAR;
            default:
                return null;
        }
    }

}
