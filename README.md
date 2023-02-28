# DBSCAN using Qupath
![DBSCAN_original](https://user-images.githubusercontent.com/41480459/221807462-691ec9cb-4749-4757-831f-b5a27e4f5594.jpg)

### 1- Create a Qupath project and put the "commons-math3-3.6.1.jar" file inside the main folder
### 2- Load the calibrated image DBSCAN.tif (drag and drop) and double click on it in the images list :
![DBSCAN_calibrated](https://user-images.githubusercontent.com/41480459/221814110-c531f378-4c65-4dc5-90e4-9edfded4a95c.jpg)  
This image contain 8 spots thar are closer than 100um exept one which is further (around 215um).
### 3- Make an annotation around all the spots
![annotation](https://user-images.githubusercontent.com/41480459/221815023-48e75e81-a894-48a0-82dc-0302bdc1f7fd.jpg)

### 4- Detect all the spots in the annotation with Analyse/cell detections (do not create cells detections for that put cell expansion=0) 
![cell-detection](https://user-images.githubusercontent.com/41480459/221813921-ec4f5223-b6ae-4272-934d-da2c889a9d4c.jpg)  
You should obtain 9 detections :
![detections](https://user-images.githubusercontent.com/41480459/221815892-4c27944e-c890-401c-b282-36e2bb3f8ae4.jpg)  
It could be launched in the script by this command : runPlugin('qupath.imagej.detect.cells.WatershedCellDetection', 
'{"detectionImage":"Channel 1","requestedPixelSizeMicrons":1.0,"backgroundRadiusMicrons":30.0,"backgroundByReconstruction":true,
"medianRadiusMicrons":0.0,"sigmaMicrons":1.5,"minAreaMicrons":10.0,"maxAreaMicrons":900.0,"threshold":25.0,"watershedPostProcess":true,
"cellExpansionMicrons":0.0,"includeNuclei":true,"smoothBoundaries":true,"makeMeasurements":true}') 
### 5- Create two classes (beads and cluster) :  to do that right clic and add class
![add_class](https://user-images.githubusercontent.com/41480459/221811011-e6f70a95-bc7a-44b9-88b5-ce847cd01861.jpg)

### 6- Classify the detected spots as beads (because the groovy script is working only on class detections). To do that go in the menu Classify/Object classification/create single measurtements classifier and put the theshold to the minimum to be sure toi select all the spots:
![classifier](https://user-images.githubusercontent.com/41480459/221810259-54957572-417e-4a11-82a5-dbfa86b63006.jpg) 

You should obtain this:  

![clssify_beads](https://user-images.githubusercontent.com/41480459/221817329-15713c7a-efd4-477d-9199-1e8db123f983.jpg)
 
### 7- Launch the script DBSCAN.groovy (drag and drop) on the annotation with the parameter MinPts=3 and distance=100um
![script](https://user-images.githubusercontent.com/41480459/221812388-2bc9e235-c0a6-4ed4-ad8b-d34fdcc522be.jpg)

### 8- You should obtain one cluster of 8 beads (one is outside the cluster). To classify the cluster beads, go in the menu Classify/Object classification/create single measurtements and look for the measurements cluster:  
![classify-cluster](https://user-images.githubusercontent.com/41480459/221818430-1a6f751e-38f1-41ad-abe2-b82bd9017cc0.jpg)

### 9- You can hide the none class (spots that do not belong to cluster) by clicking on spacebar on the None class and change the color of the cluster class by double click on it: 
![hiden_color](https://user-images.githubusercontent.com/41480459/221819041-4703c11e-fed1-43cc-94d7-a0d3b3804d5a.jpg)  

![hidden-noneClass](https://user-images.githubusercontent.com/41480459/221821804-e73f68e8-c50b-4ee2-9e1d-0c1399af7f0c.jpg)

### 10- To conclude, DBSCAN on Qupath is working perfectly and we find the same results as published with MinPoints =3 (and not MinPoints=4) if we consider the real number of neigbors without counting the central point itself. The border points are belonging to the cluster. You can try on this example the following parameters : MinPoints = 4 with distance=120um, you will find no cluster. With MinPoints = 4 and d=120um, you will find a cluster of 6 points (core = 2 points indicated with the red arrow and border points = 4 points):  
![qupath_eps=120_MinPts=4](https://user-images.githubusercontent.com/41480459/221825032-60816e23-f0d1-4fc9-bcfc-f047fcaae450.jpg)

### 11- To go further : to apply DBSCAN on multiple annotations, load the roi.zip (with name of annotated regions) on Qupath and apply the groovy script "DBSCAN_loopRegion.groovy" that contain the loop for the regions. The classifier beads and clusters could be saved in a folder named classifiers/object_classifiers (with threshold values) and reused with the command that could be applied in groovy script :  
runObjectClassifier("beads");  
runObjectClassifier("cluster");  
![classifier_json](https://user-images.githubusercontent.com/41480459/221878177-82c5ae47-3fc4-4ae3-a820-c274615896d8.jpg)

