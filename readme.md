# Slash Course API

The RESTful API which allows users to view all slash courses. Users can also update or delete slash course data from the database.

## Description

There are two resources in the Slash Course API: **/slash\_courses** and **/slash\_courses/{crn}**
* /slash_courses supports **GET** and **POST**.
    * In GET /slash_course, users are allowed to query all slash courses information or to filter the results by giving parameters (by course name, term or department).
    * In POST /slash_course, users are allowed to add a new course resource. 
* /slash_courses/{crn} supports **GET**, **PUT** and **DELETE**
    * In GET /slash_course/{crn}, users are allowed to query one specific slash course information by CRN.
    * In PUT /slash_course/{crn}, users are allowed to update a new course information by CRN.
    * In DELETE /slash_course/{crn}, users are allowed to delete a existed course object by CRN.

## Installation

You'll need to use [Gradle](http://gradle.org/) to build the API and obtain the `ojdbc6_g.jar` file from [Oracle](http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html) and put it into the `bin` directory. In addition, you'll also have to create a `configuration.yaml` file which includes your personal credentials. After finish the process, you can simply run the follow command to activate the API service:<br/>

`gradle run`

By default, the API uses the port `8080`, so in order to test the API locally, you can connect `localhost:8080\` from your browser.

## GET

Retrieve the slash course info from the database.

#### GET all slash courses or by giving filters

Respond to GET requests and filter the result based on filter parameters.

```
> GET /api/v0/slash_course

HTTP/1.1 200 OK
Content-Length: 438
Content-Type: application/json
Date: Wed, 11 May 2016 15:39:47 GMT

[
  {
    "crn": 58736,
    "courseNum": "CS544",
    "courseName": "CS 544. OPERATING SYSTEMS II",
    "slash": 1,
    "term": "Sp16",
    "instructor": {
      "instructorId": 1,
      "lastName": "McGrath",
      "firstName": "Kevin"
    },
    "day": "TR",
    "time": "0830-0950",
    "location": "GLFN AUD",
    "type": "Lecture"
  },
  {
    "crn": 32432,
    "courseNum": "ST511",
    "courseName": "ST 511. METHODS OF DATA ANALYSIS",
    "slash": 1,
    "term": "W16",
    "instructor": {
      "instructorId": 2,
      "lastName": "Emerson",
      "firstName": "Sarah"
    },
    "day": "MWF",
    "time": "0800-0850",
    "location": "MCC 201",
    "type": "Lecture"
  },
  {
    "crn": 55504,
    "courseNum": "CS572",
    "courseName": "CS 572. COMPUTER ARCHITECTURE",
    "slash": 1,
    "term": "Sp16",
    "instructor": {
      "instructorId": 4,
      "lastName": "Chen",
      "firstName": "Lizhong"
    },
    "day": "MWF",
    "time": "1100-1150",
    "location": "GLFN AUD",
    "type": "Lecture"
  },
  {
    "crn": 16695,
    "courseNum": "CS572",
    "courseName": "CS 572. COMPUTER ARCHITECTURE",
    "slash": 1,
    "term": "F16",
    "instructor": {
      "instructorId": 1,
      "lastName": "McGrath",
      "firstName": "Kevin"
    },
    "day": "TR",
    "time": "1400-1520",
    "location": "KIDD 364",
    "type": "Lecture"
  },
  {
    "crn": 13067,
    "courseNum": "CS550",
    "courseName": "CS 550. INTRODUCTION TO COMPUTER GRAPHICS",
    "slash": 1,
    "term": "F16",
    "instructor": {
      "instructorId": 6,
      "lastName": "Bailey",
      "firstName": "Mike"
    },
    "day": "MWF",
    "time": "1300-1350",
    "location": "WNGR 116",
    "type": "Lecture"
  }
]

> GET /api/v0/slash_courses?department=CS&slash=0

HTTP/1.1 200 OK
Content-Length: 291
Content-Type: application/json
Date: Wed, 11 May 2016 15:42:10 GMT

[
  {
    "crn": 53901,
    "courseNum": "CS569",
    "courseName": "CS 569. SELECTED TOPICS IN SOFTWARE ENGINEERING",
    "slash": 0,
    "term": "Sp16",
    "instructor": {
      "instructorId": 3,
      "lastName": "Groce",
      "firstName": "Alex"
    },
    "day": "MWF",
    "time": "1500-1550",
    "location": "BAT 150",
    "type": "Lecture"
  },
  {
    "crn": 10459,
    "courseNum": "CS515",
    "courseName": "CS 515. ALGORITHMS AND DATA STRUCTURES",
    "slash": 0,
    "term": "F16",
    "instructor": {
      "instructorId": 5,
      "lastName": "Amir",
      "firstName": "Nayyeri"
    },
    "day": "MW",
    "time": "1000-1150",
    "location": "KEAR 212",
    "type": "Lecture"
  }
]
```

#### GET by CRN

Respond to GET requests and show course information according to specific course CRN.

```
> GET /api/v0/slash_course/58736

HTTP/1.1 200 OK
Content-Length: 243
Content-Type: application/json
Date: Wed, 11 May 2016 15:41:34 GMT

{
  "crn": 58736,
  "courseNum": "CS544",
  "courseName": "CS 544. OPERATING SYSTEMS II",
  "slash": 1,
  "term": "Sp16",
  "instructor": {
    "instructorId": 1,
    "lastName": "McGrath",
    "firstName": "Kevin"
  },
  "day": "TR",
  "time": "0830-0950",
  "location": "GLFN AUD",
  "type": "Lecture"
}

HTTP/1.1 200 OK
Content-Length: 0
Date: Thu, 12 May 2016 23:13:16 GMT
```

## POST

Create new course object.

##### Create course object with existing Instructor
```
> POST /api/v0/slash_courses HTTP/1.1
Content-Type: application/json

{
  "crn": 58771,
  "courseNum": "CS519",
  "courseName": "CS 519. (Section 005) ST/ COMPUTATIONAL GEOMETRY",
  "slash": 0,
  "term": "Sp16",
  "instructorId": 5,
  "day": "TR",
  "time": "1400-1520",
  "location": "WNGR 285 ",
  "type": "Lecture"
}
```

##### Create course object with new Instructor object
```
> POST /api/v0/slash_courses HTTP/1.1
Content-Type: application/json

{
  "crn": 33955,
  "courseNum": "CS581",
  "courseName": "CS 581. PROGRAMMING LANGUAGES",
  "slash": 0,
  "term": "Sp16",
  "instructor": {
      "lastName": "Erwig",
      "firstName": "Martin"
  },
  "day": "TR",
  "time": "1000-1120",
  "location": "TBA",
  "type": "Lecture"
}

HTTP/1.1 201 Created
Content-Length: 0
Date: Thu, 12 May 2016 23:08:42 GMT
Location: https://localhost:8080/9
```

## PUT

Create or update a course object.

#### PUT by CRN

##### If the course doesn't exist:

```
> PUT /api/v0/slash_courses/17915 HTTP/1.1
Content-Type: application/json

{
  "crn": 17915,
  "courseNum": "CS534",
  "courseName": "CS 534. MACHINE LEARNING",
  "slash": 1,
  "instructor": {
    "lastName": "Fern",
    "firstName": "Xiaoli"
  },
  "day": "TR"
}

HTTP/1.1 201 Created
Content-Length: 0
Date: Tue, 17 May 2016 17:29:41 GMT
Location: https://localhost:8080/222
```

##### If course was in the database:

```
> PUT /api/v0/slash_courses/17915 HTTP/1.1
Content-Type: application/json

{
    "slash": 0,
    "term": "F16",
    "time": "1200-1320",
    "location": "LINC 307",
    "type": "Lecture"
}

HTTP/1.1 200 OK
Content-Length: 0
Date: Tue, 17 May 2016 17:38:12 GMT
```

## DELETE

Delete a course object from the database.

#### DELETE by CRN

Respond to DELETE requests and delete course object according to specific course CRN.

##### If course CRN is valid:

```
> DELETE /api/v0/slash_courses/58736 HTTP/1.1

HTTP/1.1 200 OK
Content-Length: 0
Date: Wed, 11 May 2016 15:33:51 GMT
```

##### If course CRN is invalid: 

```
> DELETE /api/v0/slash_courses/invalidCRN HTTP/1.1

HTTP/1.1 404 Not Found
Content-Length: 0
Date: Wed, 11 May 2016 15:37:47 GMT
```