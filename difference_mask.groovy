//Start here with our baseline object, all other annotations will be compared to it.

tissueAnnotation = getAnnotationObjects().find{it.getPathClass() == getPathClass("MZ")}
tissueGeom = tissueAnnotation.getROI().getGeometry()

//Cycle through ALL OTHER annotations and subtract them from the tissue

getAnnotationObjects().findAll{it.getPathClass() != getPathClass("MZ")}.each{anno->
    currentGeom = anno.getROI().getGeometry()
    //Note the ! which means we are looking for NOT intersects
    tissueGeom = tissueGeom.difference(currentGeom)
}

//Create the new object
tissueROI = GeometryTools.geometryToROI(tissueGeom, ImagePlane.getDefaultPlane())
newTissue = PathObjects.createAnnotationObject( tissueROI, getPathClass("Tissue After Subtraction") )
addObject(newTissue)

fireHierarchyUpdate()