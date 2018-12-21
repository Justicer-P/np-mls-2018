package com.sf.marathon.np.index.clause;

/**
 * 描述：条件操作符
 */
public enum CriteriaOper {

    // 等于
    EQ(),
    // 大于
    GT(),
    // 大于或等于
    GE(),
    // 小于
    LT(),
    // 小于或等于
    LE(),
    // 不等于
    NEQ(),
    // 包含
    IN(),
    NOT_IN(),
    // 模糊匹配
    LIKE(),
    LCRA(),//左闭右开
    LARC(),//左开右闭
    LARA(),//左开右开
    LCRC(),//左闭右闭
    // 左括号
    LEFT_BRACKET(),
    // 右括号
    RIGHT_BRACKET(),
    // 或
    OR(),
    AND(),//与
    NULL(),//空
    NOT_NULL(),//非空
    GEO_BOUND,//地理位置范围
    NESTED;
}
