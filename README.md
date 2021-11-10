# CS Assignment

To run the service 
1. Use command ./gradlew bootRun on terminal
2. Once the service is runnning hit the endpoint POST http:localhost:8080/api/v1/cs/file/process?url=<absolute_file_path>
(the file must be in the format shown in /resources folder with name logfile.txt)
you can directly copy the file path from resources and use.
3. You can run the test cases just specify the file fath in the constant param in the same file
