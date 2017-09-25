package com.bonc.usdp.algorithm.dbscan;


public interface DistanceMetric<V> {

	double calculateDistance(V val1, V val2) throws ClustererException;

}
