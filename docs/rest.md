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
* name
* season
* isPublished

## /term/{termId}

#### GET
if isPublished, AU can be student, otherwise au must be Faculty
* id
* name
* season
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
* Component
  * id
* startTime
* endTime
* daysOfWeek {List of enums?}

## /section/{sectionId}

#### GET
-- TODO
#### PUT
-- TODO
#### DELETE
AU must be Scheduler
NO BODY
