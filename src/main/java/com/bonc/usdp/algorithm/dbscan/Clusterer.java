package com.bonc.usdp.algorithm.dbscan;

import com.bonc.usdp.entity.CharacterHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Clusterer<V extends CharacterHelper> {

    private double epsilon = 1f;

    private int minimumNumberOfClusterMembers = 2;

    private List<DistanceMetric<V>> metrics;

    private ArrayList<V> inputValues = null;

    public Clusterer(final Collection<V> inputValues,
                     int minNumElements,
                     double maxDistance,
                     List<DistanceMetric<V>> metrics) {
        setInputValues(inputValues);
        setMinimalNumberOfMembersForCluster(minNumElements);
        setMaximalDistanceOfClusterMembers(maxDistance);
        setDistanceMetrics(metrics);
    }

    private void setDistanceMetrics(final List<DistanceMetric<V>> metrics) {
        if (metrics == null) {
            throw new ClustererExp("Distance metric list is empty.");
        }
        this.metrics = metrics;
    }

    private void setInputValues(final Collection<V> collection) {
        if (collection == null) {
            throw new ClustererExp("List of input values is null.");
        }
        this.inputValues = new ArrayList<>(collection);
    }

    private void setMinimalNumberOfMembersForCluster(final int minimalNumberOfMembers) {
        this.minimumNumberOfClusterMembers = minimalNumberOfMembers;
    }

    private void setMaximalDistanceOfClusterMembers(final double maximalDistance) {
        this.epsilon = maximalDistance;
    }

    /**
     * 计算邻居
     */
    private List<V> getNeighbours(final V inputValue) {
        List<V> neighbours = new LinkedList<>();
        for (V candidate :inputValues ) {
            if (calculateDistance(inputValue, candidate) <= epsilon) {
                neighbours.add(candidate);
            }
        }
        return neighbours;
    }

    /**
     * 计算距离
     */
    private double calculateDistance(V val1, V val2) {
        double distance = 0.0;
        for (DistanceMetric<V> metric : metrics) {
            if (metric.check(val1, val2)) {
                distance += metric.calculateDistance(val1, val2);
            } else {
                return Double.MAX_VALUE;
            }
        }
        return distance;
    }

    /**
     * 合并邻居
     */
    private List<V> mergeRightToLeftCollection(final List<V> neighbours1,
                                               final List<V> neighbours2) {
        for (V neighbour : neighbours2) {
            if (!neighbour.isIsclustered()) {
                neighbours1.add(neighbour);
                neighbour.setIsclustered(true);
            }
        }
        return neighbours1;
    }

    /**
     * 聚类计算
     */
    public List<List<V>> performClustering() {

        if (inputValues == null) {
            throw new ClustererExp("List of input values is null.");
        }

        if (inputValues.isEmpty()) {
            throw new ClustererExp("List of input values is empty.");
        }

        if (inputValues.size() < 2) {
            throw new ClustererExp("Less than two input values cannot be clustered. Number of input values: "
                    + inputValues.size());
        }

        if (epsilon < 0) {
            throw new ClustererExp("Maximum distance of input values cannot be negative. Current value: "
                    + epsilon);
        }

		if (minimumNumberOfClusterMembers < 2) {
			throw new ClustererExp("Clusters with less than 2 members don't make sense. Current value: "
                    + minimumNumberOfClusterMembers);
		}

        List<List<V>> resultList = new ArrayList<>();
        List<V> neighbours;
        int index = 0;

        while (inputValues.size() > index) {
            V p = inputValues.get(index);
            if (!p.isProcessed()) {
                p.setProcessed(true);
                neighbours = getNeighbours(p);
                if (neighbours.size() >= minimumNumberOfClusterMembers) {
                    neighbours.forEach(v -> v.setIsclustered(true));
                    int ind = 0;
                    while (neighbours.size() > ind) {
                        V r = neighbours.get(ind);
                        if (!r.isProcessed()) {
                            r.setProcessed(true);
                            List<V> individualNeighbours = getNeighbours(r);
                            if (individualNeighbours.size() >= minimumNumberOfClusterMembers) {
                                neighbours = mergeRightToLeftCollection(neighbours, individualNeighbours);
                            }
                        }
                        ind++;
                    }
                    resultList.add(neighbours);
                }
            }
            index++;
        }
        return resultList;
    }

}
