# Shoping Cart Manager
A simple test task  

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

## Prerequisites
Any enviroment with a console can run it. It uses h2 databases so I suggest you use Maven with provided pom.xml. Or ofcource you can do it with any way you are familiar with.

## Installing
Just add h2 dependency (Suggestion: use provided pom and Maven) and run "runThisOne" class.

## Deployment
In order to use this in a live system:

First: change "ConnectionManager" so it connects to your required database.

Second: In "Printer" class you can change the out put from console to any other GUI required, it is also possible to do so if you pass the required variables in "runThisOne" istead of "Printer" into your GUI.

Third: UserID is not defined but there is the ability to define it. just change "fakeUserID" variable in "Accountant" class to what you need.

Last: This program shuts down after One cycle so based on where you are going to use it you can change that by putting a loop in "runThisOne" class.

## Built With
* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning
Versioning might not mean much yet here so just ignore it:D

# Authors
* Ashkan Tavassoli

# License
No License
