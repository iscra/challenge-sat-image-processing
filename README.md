## Sat Images API demo

## Implementation

Java source code. Using Spring Boot framework.
API is REST/JSON based web service.

## Installation

Requirements: Java 11, maven (or IDE).

* test

	mvn test

* build executabe jar suitable for execution and deployment

	mvn package

* run locally

	mvn spring-boot:runóó

* or using built jar

	java -Dimage.dir=data -jar target/sat-images-0.0.1-SNAPSHOT.jar

* API documentation can be generated using

	mvn package

It is then found in the target/generated-docs directory in HTML format.


## Satellite images

The property **images.dir** should be set to path to the directory with satellite channel images.

Default is **data**, which refers to **data** subdirectory of the current directory. The property an be set either in a properties file or in the commnd line.

Examples:

	java -Dimage.dir=data -jar target/sat-images-0.0.1-SNAPSHOT.jar
	mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Dimage.dir=data"

## Simple tests

When sample data images from sample-granule.zip are available (data for T33UUP_20180804T100031), then sime sample API tests can be performed with curl: 

	curl localhost:8080/generate-images -XPOST -H "Content-Type: application/json" -d '{"utmZone":33,"latitudeBand":"U","gridSquare":"UP","date":"2018-08-04","channelMap":"VISIBLE"}' -v -o tmp/x-visible.jpg

	curl localhost:8080/generate-images -XPOST -H "Content-Type: application/json" -d '{"utmZone":33,"latitudeBand":"U","gridSquare":"UP","date":"2018-08-04","channelMap":"VEGETATION"}' -v -o tmp/x-vegetation.jpg
 	
	curl localhost:8080/generate-images -XPOST -H "Content-Type: application/json" -d '{"utmZone":33,"latitudeBand":"U","gridSquare":"UP","date":"2018-08-04","channelMap":"WATER_VAPOR"}' -v -o tmp/x-water.jpg

## Notes

Response time is very slow, about 25 seconds. Most time spent reading TIFF files.

I have tried OpenCV for image processing, but it seemed to have issues with opening the satellite TIFF image files.

Caching or even precaching all result images on disk could be very good option, based on total number of images and expected API traffic.

