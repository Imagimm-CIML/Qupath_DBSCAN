import org.apache.commons.math3.ml.clustering.DBSCANClusterer
import org.apache.commons.math3.ml.clustering.DoublePoint
eps = 0.5
minPts = 10
clusterer = new DBSCANClusterer(eps, minPts)
// needs a collection of DoublePoint
// test with random points
nbPoints = 1000
w=10
h=10
points = []
for(i in 0..nbPoints) {
    x = Math.random()*w
    y = Math.random()*h
    point = new DoublePoint([x,y] as double[])
    points << point
}
cluster = clusterer.cluster(points)
print cluster.size()
for(c in cluster) {
    print "***************"+c
    for(cc in c.getPoints()) print cc
}
