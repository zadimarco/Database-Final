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
This will require that you setup the database yourself with the `TableCreation` folder
 - Run setup.sql for a simply setup of the database under the name yatv
 - ddl.sql is the test data
 - dml.sql is the schema for the database

Steps:
1. Clone the repo
2. Make necessary changes to the config file (/src/main/resources/META-INF/config.properties)
3. Run `./gradlew jar`
3. This will produce a jar under the build/libs folder
4. Run this jar using `java -jar path/to/jar`


### Running through docker (Recommended)
The easiest way to run the program is with docker

Steps:
1. You will need docker and docker-compose installed on your machine ([Docker Desktop](https://www.docker.com/products/docker-desktop) for Windows)
2. Run `docker-compose build` from within this directory
3. Run `docker-compose run --rm yatv`

This will boot you into an environment with the preloaded database

The database will be preserved until you perform a `docker-compose down`, which will delete all data and stop all processes
If you would like to keep the data but stop the database, run `docker-compose stop`

## Video Tutorials

### [Docker Installation](https://youtu.be/sA2w1Rm_Tzw)

### [Jar Installation](https://youtu.be/MAeAm-94ifs)

### [Task Examples](https://youtu.be/_MPEbdLSrPc)