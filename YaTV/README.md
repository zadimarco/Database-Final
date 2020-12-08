#YaTV App

## Running the Program

This program does require that the java jdk is installed to compile and run.


### Database Setup
You will need a MySQL database solution installed, or a database that your computer can connect to
To setup the database, run the `setup.sql` file within mysql
- This will create the database and import some prefilled data
- If you would just like the schema, just run the `dml.sql` file


### Creating a jar
If you would like to create a jar of the program.

Steps:
1. Clone the repo
2. Run `./gradlew jar`
3. This will produce a jar under the build/libs folder
4. Run this jar using `java -jar path/to/jar`


### Running through Gradle
If you just want to run the program

Steps:
1. Clone the repo
2. Run `./gradlew run`

Note:
The input will appear below the gradle `<=========----> 75% EXECUTING [19s]`, which does impact the user experience


### Running through docker
The easiest way to run the program is with docker

Steps:
1. You will need docker and docker-compose installed on your machine ([Docker Desktop](https://www.docker.com/products/docker-desktop) for Windows)
2. Run `docker-compose build` from within this directory
3. Run `docker-compose run --rm yatv`

This will boot you into an environment with the preloaded database

## Video Tutorials
