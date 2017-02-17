# Champagne Scheduling Software Rest Spec

All endpoints can only be accessed by authenticated users
 (referred to as AU from here out) with the exception of a post to /prss which
 is open for all users.

All endpoints are also prefixed with /api


Levels of user are:
Scheduler
Faculty
Student

# Person Resources

## /prss
#### POST
Used to create a new person.
* firstName {String}
* lastName {String}
* username {String}
* password {String}
* role - (optional) AU must be admin to specify role other than student

#### GET
returns an array of People, AU must be faculty or above

Fields are the same as /prss/{prsId}

## /prss/{prsId}

#### GET
Must be admin or prsId must be AU
* firstName
* lastName
* username
* role

#### PUT
Same fields as for GET, except email cannot be modified and modifying role is only
allowed by admin.

#### DELETE
Removes the person in question, AU must be the person or staff






## Room Type Resources
## /roomType

#### POST
Used to create a new room type.
* type {String}

#### GET
returns an array of room types
* type {String}



## /roomType/{roomTypeID}

#### GET
Returns a single room type
* type {String}

#### PUT 
Same fields as for GET

#### DELETE
Removes the room type in question


# Equipment Resources

## /equip
#### POST
Used to create new equipment.
* name {String}
* description {String} 

#### GET
returns an array of all Equipment for that department
AU will also be admin

Fields are the same as /equip/{equipID}


## /equip/{equipID}

#### GET
Returns an equipment instance
* name {String}
* description {String}
* id {int}

#### PUT 
Same fields as for GET, AU must be scheduler

#### DELETE
Removes the equipment instance
AU must be scheduler



_**AU must be a scheduler to modify or edit rooms**_

# Room Resources
## /room
#### POST
Used to create a new room.
* roomNumber {String}
* capacity{Integer} 
* roomType RoomType {
    id: {RoomType.id}
  }
* equipment Array<Equipment>
 * id: Equipment.id

#### GET
returns an array of Rooms for that department
AU will also be scheduler and faculty

Fields are the same as /room/{roomID}


## /room/{roomID}

#### GET
Returns a single room 
Same fields as for POST, AU must be scheduler

#### PUT 
Same fields as for GET, AU must be admin

#### DELETE
Removes the room in question
AU must be admin


## Component Type Resources (could nest inside components)
## /component/type
#### POST
* name {String}
* description {String}

#### GET
returns an array of the component types with the same values as POST /component/type

## /component/type/{cmpTypeId}

## GET
* name {String}
* description {String}
#### PUT
* name {String}
* description {String}

#### DELETE
deletes the resource in question

# Course Resources

## /course
#### POST
AU must be scheduler.
* name {String}
* units {int}
* components {array} - (optional) array of components


#### GET
AU must be faculty or higher.
Fields are the same as /course POST 
Return a list of courses.

## /course/{crsId}
#### GET
AU must be faculty or higher.
Fields are the same as /course POST with the addition of:
* id {int}
Returns a single course.

#### PUT
AU must be scheduler.
Fields are the same as /course POST

#### DELETE
AU must be scheduler.
NO BODY

## Component Resources

## /course/{crsId}/component
#### POST
AU must be scheduler.
Creates a component attached to a course.
* workUnits {int} - faculty work units
* hours {int} - hours per component for the week
* componentType {int}

#### GET
AU must be faculty or higher
Returns a list of components for a course

## /course/{crsId}/component/{cmpId}
#### GET
AU must be faculty or higher.
Fields are the same as /component POST with the addition of:
* id {int}

#### PUT
AU must be scheduler.
Fields are the same as /component POST

#### DELETE
AU must be scheduler.
NO BODY

# Term Resources
## /term

#### POST
(optional) QueryPrm baseTermId
* name {String} - name and year of term
* isPublished {boolean} - published or not published

## /term/{termId}

#### GET
if isPublished, AU can be student, otherwise au must be Faculty
* id
* name
* isPublished

#### PUT
AU must be Scheduler
Same fields as get without id

#### DELETE
User must be Scheduler

# Section Resources

## /term/{termId}/section

#### POST
AU must be Scheduler 
* name {String}
* comp {Component}
  * id
* prof {Person}
* startTime {Date}
* endTime {Date}

#### GET
AU must be faculty or higher.
Fields are the same as /section POST 
Return a list of sections.

## /section/{sectionId}
#### GET
AU must be faculty or higher.
Fields are the same as /section POST with the addition of:
* id {int}
Returns a single section.

#### PUT
AU must be scheduler.
Fields are the same as /section POST

#### DELETE
AU must be scheduler.
NO BODY

#Filter Queries
AU must be scheduler.

## /filter

#### GET
Returns a list of all data tables that can be filtered.

## /filter{?table}
* table {enum} - Name of selected CSV file
  - demand - Historic Demand Data
  - cohort - Student Cohort Data
  - plan - Student Plan Data

#### GET
Returns specified table from database.

## /filter?table=demand&{term, course, courselvl, util}
* term {Term} (optional)
  - id {Term.id}
* course {Course} (optional)
  - id {Course.id}
* courselvl {enum} (optional)
  - 1 - returns all courses starting with this number
  - 2 - returns all courses starting with this number
  - 3 - returns all courses starting with this number
  - 4 - returns all courses starting with this number
  - 5 - returns all courses starting with this number
* util {int} (optional) - returns all courses with a Utilization greater than or equal to input

#### GET
Returns filtered table according to inputs.

## /filter?table=cohort&{term, course, courselvl, class, major}
* term {Term} (optional)
  - id {Term.id}
* course {Course} (optional)
  - id {Course.id}
* courselvl {enum} (optional)
  - 1 - returns all courses starting with this number
  - 2 - returns all courses starting with this number
  - 3 - returns all courses starting with this number
  - 4 - returns all courses starting with this number
  - 5 - returns all courses starting with this number
* class {enum} (optional)
  - fresh - returns all Students that are Freshman
  - soph - returns all Students that are Sophomores
  - jun - returns all Students that are Juniors
  - sen - returns all Students that are Seniors
* major {enum} (optional)
  - CSC - Returns all Students that are CSC majors
  - SE - Returns all Students that are SE majors
  - CPE - Returns all Students that are CPE majors

#### GET
Returns filtered table according to inputs.

## /filter?table=plan&{term, college, department, course, courselvl, courseid, subject, catalognum, title, component, requirement, demand, offer, capacity, unmet, percentunmet}
* term {Term}
  - id {Term.id}
* college {String}
* department {String} (optional)
* course {Course} (optional)
  - id {Course.id}
* courselvl {enum} (optional)
  - 1 - returns all courses starting with this number
  - 2 - returns all courses starting with this number
  - 3 - returns all courses starting with this number
  - 4 - returns all courses starting with this number
  - 5 - returns all courses starting with this number
* courseid {int} (optional)
* subject {String} (optional)
* catalognum {int} (optional)
* title {String} - Returns all Courses with this String in their name
* component {Component} (optional)
  - id {Component.id}
* requirement {String} (optional) - Returns all Courses with this requirement type. ex: COOP
* demand {int} (optional) - Returns all Courses with this many or more seats demanded.
* offer {int} (optional) - Returns all Courses with this many or more Sections offered.
* capacity {int} (optional) - Returns all Courses with this many or more seats available.
* unmet {int} (optional) - Returns all Courses with this many or more seats demanded.
* percentunmet {int} (optional) - Returns all Courses with this much or more percent of unmet demand.

#### GET
Returns filtered table according to inputs.