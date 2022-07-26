# grid-api
Provides RESTful API for sge engine

***Configuring IDEA to run grid-engine in Docker container and be able to attach the debug process***
1. Push the green triangle icon in the upper left corner of the Dockerfile tab (Docker IDEA plugin should be pre-installed).
2. Specify the Tag name of image and Container name in Edit Run Configuration window.
3. Modify the Run options adding "bind ports" and specify binding ports 8080:8080 and 5005:5005
   in the corresponding fields of the Edit Run Configuration window.
4. Scroll down to the bottom part "Before launch" of the Edit Run Configuration window and use "+" icon
   to add Gradle tasks "clean" and "assemble" to the list.
5. Apply the changes and push the "Run Dockerfile" button.

    