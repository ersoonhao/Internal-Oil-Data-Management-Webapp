
Oil Data Visualiser for New Zealand and China

Frontend built using ReactJS & Redux, backend built using Spring Boot & Java, database running on Amazon AWS RDS

CICD: AWS CODESTAR

Frontend also accessible via: http://oop3-env.eba-gu8cge2p.ap-southeast-1.elasticbeanstalk.com

Build & Run
Step 1: Open Terminal

Step 1: Runmvn clean install

Builds & packages the backend
Step 2: Run mvn spring-boot:run

Do note that the Scrapers have been deactivated as they are connected to the RDS
Backend will start to run
Step 3: Open new terminal

Step 4: cd src/main/app

Step 5: npm run start to start the frontend

Open localhost:3000 to view the frontend
Connect the backend to another database.
Step 6: This project's backend currently relies on our dedicated AWS RDS instance for demo purpose.

The database endpoints can be modified in application.properties
Uncomment the whole ScheduledTask.java as well as line 23 in ProjectApplication.java for the webscrappers to run on start up and periodically scrape the China and New Zealand oil data.

Contributors: @nvTran @asher935 @givemewaffles
