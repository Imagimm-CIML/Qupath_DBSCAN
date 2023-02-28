# DBSCAN using Qupath
![DBSCAN_original](https://user-images.githubusercontent.com/41480459/221807462-691ec9cb-4749-4757-831f-b5a27e4f5594.jpg)

### 1- Create a Qupath project  
### 2- Load the calibrated image DBSCAN.tif
![DBSCAN_original](https://user-images.githubusercontent.com/41480459/221812698-b50910b0-3fb5-4b41-a6f4-da9f56a17b50.jpg)
### 3- Make an annotation around all the spots
### 4- Detect all the spots in the annotation with Analyse/cell detections (do not create cells detections for that put cell expansion=0)
### 5- Create two classes (beads and cluster) :  to do that right clic 
![add_class](https://user-images.githubusercontent.com/41480459/221811011-e6f70a95-bc7a-44b9-88b5-ce847cd01861.jpg)

### 6- Classify the detected spots as beads (as the groovy script is working on classify elements). To do that go in the menu Classify/Object classification/create single measurtements classifier and put the theshold to the minimum to be sure toi select all the spots:
![classifier](https://user-images.githubusercontent.com/41480459/221810259-54957572-417e-4a11-82a5-dbfa86b63006.jpg)
### 7- Launch the script DBSCAN.groovy on the annotation with the parameter MinPts=3 and distance=100um
![script](https://user-images.githubusercontent.com/41480459/221812388-2bc9e235-c0a6-4ed4-ad8b-d34fdcc522be.jpg)
