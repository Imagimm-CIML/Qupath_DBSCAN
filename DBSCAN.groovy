import org.apache.commons.math3.ml.clustering.DBSCANClusterer
import org.apache.commons.math3.ml.clustering.DoublePoint
eps = 100
minPts = 2
clusterer = new DBSCANClusterer(eps, minPts)

points=[]
getDetectionObjects().each{
    def roi = it.getROI()
    roiIJ = IJTools.convertToIJRoi(roi, 0, 0, 1)
    x = roi.getCentroidX()
    y = roi.getCentroidY()
    point = new DoublePoint([x,y] as double[])
    points << point
}
fireHierarchyUpdate()


cluster = clusterer.cluster(points)
print cluster.size()
for(c in cluster) {
    print "***************"+c
    for(cc in c.getPoints()) print cc
}