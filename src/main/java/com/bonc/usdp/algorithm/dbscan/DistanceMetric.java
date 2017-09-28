package com.bonc.usdp.algorithm.dbscan;


public interface DistanceMetric<V> {

    /**
     * 计算距离
     */
    double calculateDistance(V val1, V val2);

    /**
     * 检查是否满足条件，必须被调用，且必须先于 calculateDistance 被调用
     */
    boolean check(V val1, V val2);

}
