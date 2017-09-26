package com.bonc.usdp.algorithm.dbscan;

import com.bonc.usdp.entity.CharacterHelper;

import java.util.*;

public class Clusterer<V extends CharacterHelper> {

    /**
     * maximum distance of values to be considered as cluster
     */
    private double epsilon = 1f;

    /**
     * minimum number of members to consider cluster
     */
    private int minimumNumberOfClusterMembers = 2;

    /**
     * distance metric applied for clustering
     */
    private DistanceMetric<V> metric = null;

    /**
     * internal list of input values to be clustered
     */
    private ArrayList<V> inputValues = null;

    public Clusterer(final Collection<V> inputValues,
                     int minNumElements,
                     double maxDistance,
                     DistanceMetric<V> metric) {
        setInputValues(inputValues);
        setMinimalNumberOfMembersForCluster(minNumElements);
        setMaximalDistanceOfClusterMembers(maxDistance);
        setDistanceMetric(metric);
    }

    private void setDistanceMetric(final DistanceMetric<V> metric) {
        if (metric == null) {
            throw new ClustererException("Distance metric has not been specified (null).");
        }
        this.metric = metric;
    }

    private void setInputValues(final Collection<V> collection) {
        if (collection == null) {
            throw new ClustererException("List of input values is null.");
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
     * Determines the neighbours of a given input value.
     *
     * @param inputValue Input value for which neighbours are to be determined
     * @return list of neighbours
     * @throws ClustererException ClustererException
     */
    private List<V> getNeighbours(final V inputValue) {
        List<V> neighbours = new LinkedList<>();
        for (V candidate :inputValues ) {
            if (metric.calculateDistance(inputValue, candidate) <= epsilon) {
                neighbours.add(candidate);
            }
        }
        return neighbours;
    }

    /**
     * Merges the elements of the right collection to the left one and returns
     * the combination.
     *
     * @param neighbours1 left collection
     * @param neighbours2 right collection
     * @return Modified left collection
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
     * Applies the clustering and returns a collection of clusters (i.e. a list
     * of lists of the respective cluster members).
     *
     * @return a collection of clusters
     * @throws ClustererException ClustererException
     */
    public List<List<V>> performClustering() {

        if (inputValues == null) {
            throw new ClustererException("List of input values is null.");
        }

        if (inputValues.isEmpty()) {
            throw new ClustererException("List of input values is empty.");
        }

        if (inputValues.size() < 2) {
            throw new ClustererException("Less than two input values cannot be clustered. Number of input values: "
                    + inputValues.size());
        }

        if (epsilon < 0) {
            throw new ClustererException("Maximum distance of input values cannot be negative. Current value: "
                    + epsilon);
        }

		if (minimumNumberOfClusterMembers < 2) {
			throw new ClustererException("Clusters with less than 2 members don't make sense. Current value: "
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
