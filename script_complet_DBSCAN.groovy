import org.apache.commons.math3.ml.clustering.DBSCANClusterer
import org.apache.commons.math3.ml.clustering.DoublePoint

import qupath.imagej.processing.RoiLabeling
import qupath.imagej.tools.IJTools
import qupath.lib.analysis.features.ObjectMeasurements
import qupath.lib.gui.dialogs.Dialogs
import qupath.lib.objects.PathObject
import qupath.lib.objects.PathObjects
import qupath.lib.regions.ImagePlane
import qupath.opencv.ops.ImageOps

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

import static qupath.lib.gui.scripting.QPEx.saveDetectionMeasurements
import static qupath.lib.scripting.QP.*

def imageData = getCurrentImageData()
def hierarchy = imageData.getHierarchy()
def annotations = hierarchy.getAnnotationObjects()

clearDetectionMeasurements()
clearDetections()

//distance to search around an object for another centroid.
double micronsBetweenCentroids = 100
//Minimum number of objects needed to be considered a cluster
int minPts = 3
boolean baseClasses = false

double eps = micronsBetweenCentroids/getCurrentServer().getPixelCalibration().getPixelWidthMicrons()
print eps


// detection
for (annotation in annotations) {
        hierarchy.getSelectionModel().clearSelection();
        selectObjects { p -> p == annotation }
        runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', '{"detectionImage":"Channel 1","requestedPixelSizeMicrons":1.0,"backgroundRadiusMicrons":30.0,"backgroundByReconstruction":true,"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":10.0,"maxAreaMicrons":900.0,"threshold":25.0,"watershedPostProcess":true,"cellExpansionMicrons":0.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}')
        runObjectClassifier("beads");
}



//Get the classes you want to analyze. Avoids Negative and no class by default.
Set classSet = []
List classList = []
if (!baseClasses){
    
    for (object in getDetectionObjects()) {
        c = object.getPathClass()
        if (c != getPathClass("Negative")){
            classSet << c
        }
    }
    
    classList.addAll(classSet.findAll{
        //If you only want one class, use it == getPathClass("MyClassHere") instead
       it != null 
    })
    print classList
}else{
    for (object in getDetectionObjects()) {
        parts = PathClassTools.splitNames(object.getPathClass())
        parts.each{
            if (it != "Negative"){
                classSet << it
            }
        }
    }
    classList.addAll(classSet.findAll{
        //If you only want one sub-class, use it == getPathClass("MyClassHere") instead
       it != null
    })
    
}

classList.each{ c->
    //Storage for stuff we do later. points will hold the XY coordinates as DoublePoint objects
    List<DoublePoint> points = new ArrayList<DoublePoint>()
    //The Map allows us to use the DoublePoint to match the list of coordinates output by DBScan to the QuPath object
    Map< DoublePoint, double> pointMap = [:]
    
    //Get the objects of interest for this class or sub-class
    if(baseClasses){
        batch = getDetectionObjects().findAll{it.getPathClass().toString().contains(c)}
        text = c
    }else{
        batch = getDetectionObjects().findAll{it.getPathClass() == c}
        text = c.getName()
    }
    
    //print batch.size()
    //Prep each object being analyzed for clustering.
    batch.eachWithIndex{d,x->
        //create the unique identifier, if you want to look back at details
        //d.getMeasurementList().putMeasurement("ID",(double)x)
        
        //Reset previous cluster analyses for the given cell
        d?.getMeasurementList().removeMeasurements("Cluster "+text)
        
        //create the linker between the ID and the centroid
        double [] point = [d.getROI().getCentroidX(), d.getROI().getCentroidY()]
        DoublePoint dpoint = new DoublePoint(point)
        //print dpoint
        points[x] = dpoint
        //Key point here, each index (cell in most cases) is tracked and matched to it's XY coordinates
        pointMap[dpoint]= (double)x
    }
    
    //print points if you want to see them all
    def showClosure = {detail ->
        println "Cluster : " + detail.cluster + " Point : " + detail.point + " Label : "+ detail.labels
        print "labels "+(int)detail.labels
        print "cluster"+detail.cluster
        
        //this uses the label (the index from the "batch") to access the correct cell, and apply a measurement with the correct cluster number
        batch[detail.labels]?.getMeasurementList()?.putMeasurement("Cluster "+text,detail.cluster )
        batch[detail.labels]?.getMeasurementList()?.putMeasurement("Cluster Size "+text,detail.clusterSize )
    }
    
    //Main run statements
    
     def imageData = getCurrentImageData()
     def hierarchy = imageData.getHierarchy()
     def annotations = hierarchy.getAnnotationObjects()
    
    for (annotation in annotations) {
        // DBSCAN
        DBSCANClusterer DBScan = new DBSCANClusterer(eps, minPts)
        collectDetails(DBScan.cluster(points), pointMap).each(showClosure)
        // classify
        runObjectClassifier("cluster");
        }
        //fireHierarchyUpdate()
    
}

print "DONE"