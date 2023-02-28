# DBSCAN using Qupath
![DBSCAN_original](https://user-images.githubusercontent.com/41480459/221807462-691ec9cb-4749-4757-831f-b5a27e4f5594.jpg)

### 1- Create a Qupath project  
### 2- Load the calibrated image DBSCAN.tif (drag and drop) and double click on it in the images list :
![DBSCAN_calibrated](https://user-images.githubusercontent.com/41480459/221814110-c531f378-4c65-4dc5-90e4-9edfded4a95c.jpg)

### 3- Make an annotation around all the spots
![annotation](https://user-images.githubusercontent.com/41480459/221815023-48e75e81-a894-48a0-82dc-0302bdc1f7fd.jpg)

### 4- Detect all the spots in the annotation with Analyse/cell detections (do not create cells detections for that put cell expansion=0) 
![cell-detection](https://user-images.githubusercontent.com/41480459/221813921-ec4f5223-b6ae-4272-934d-da2c889a9d4c.jpg)  
You should obtain 9 detections :
![detections](https://user-images.githubusercontent.com/41480459/221815892-4c27944e-c890-401c-b282-36e2bb3f8ae4.jpg)

### 5- Create two classes (beads and cluster) :  to do that right clic and add class
![add_class](https://user-images.githubusercontent.com/41480459/221811011-e6f70a95-bc7a-44b9-88b5-ce847cd01861.jpg)

### 6- Classify the detected spots as beads (because the groovy script is working only on class detections). To do that go in the menu Classify/Object classification/create single measurtements classifier and put the theshold to the minimum to be sure toi select all the spots:
![classifier](https://user-images.githubusercontent.com/41480459/221810259-54957572-417e-4a11-82a5-dbfa86b63006.jpg)  

### 7- Launch the script DBSCAN.groovy on the annotation with the parameter MinPts=3 and distance=100um
![script](https://user-images.githubusercontent.com/41480459/221812388-2bc9e235-c0a6-4ed4-ad8b-d34fdcc522be.jpg)

### 8- You should obtain one cluster of 8 beads (one is outside the cluster). To classify the cluster beads, go in the menu Classify/Object classification/create single measurtements and look for the measurements cluster
