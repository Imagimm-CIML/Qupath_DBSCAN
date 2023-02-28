# DBSCAN using Qupath
![DBSCAN_original](https://user-images.githubusercontent.com/41480459/221807462-691ec9cb-4749-4757-831f-b5a27e4f5594.jpg)

### 1- Create a Qupath project and put the "commons-math3-3.6.1.jar" file inside the main folder
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

You should obtain this:  

![clssify_beads](https://user-images.githubusercontent.com/41480459/221817329-15713c7a-efd4-477d-9199-1e8db123f983.jpg)

### 7- Launch the script DBSCAN.groovy (drag and drop) on the annotation with the parameter MinPts=3 and distance=100um
![script](https://user-images.githubusercontent.com/41480459/221812388-2bc9e235-c0a6-4ed4-ad8b-d34fdcc522be.jpg)

### 8- You should obtain one cluster of 8 beads (one is outside the cluster). To classify the cluster beads, go in the menu Classify/Object classification/create single measurtements and look for the measurements cluster:  
![classify-cluster](https://user-images.githubusercontent.com/41480459/221818430-1a6f751e-38f1-41ad-abe2-b82bd9017cc0.jpg)

### 9- You can hide the none class (spots that do not belong to cluster) by clicking on spacebar on the None class and change the color of the cluster class by double click on it: 
![hiden_color](https://user-images.githubusercontent.com/41480459/221819041-4703c11e-fed1-43cc-94d7-a0d3b3804d5a.jpg)  

![hidden-noneClass](https://user-images.githubusercontent.com/41480459/221821804-e73f68e8-c50b-4ee2-9e1d-0c1399af7f0c.jpg)

### 10- To conclude, DBSCAN on Qupath is working great and we finf=d the same results with MinPoints =3 if we consider the real numler of neigbor and not counting the spot itself. The border point are belonging to the cluster. You can try on this MinPoints = 4 with distance=120um, you will find no cluster. With MinPoints = 4 and d=120um, you will find a clusyter of 6 points (core = 2 spots and border point = 4 points):   
![qupath_eps=120_MinPts=4](https://user-images.githubusercontent.com/41480459/221823298-e8e4ab18-53ea-4e3f-8076-0735ee1930bf.jpg)
