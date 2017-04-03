# MongoDB Learning

These are my notes for the MongoDB University course: MongoDB for Java Developers MJ101, this page will also include some of my testing with Mongo.

# Table of Contents
1. [Week 1](#week-1)
2. [Week 2](#week-2)
    * [Creation](#creation)
    * [Reading](#reading)
        * [Retrieve using params](#retrieve-using-params)
        * [Retrieve nested document](#retrieve-nested-document)
        * [Retrieve based on array equality](#retrieve-based-on-array-equality)
        * [Cursors and projections](#cursors-and-projections)
        * [Query Operators](#query-operators)
            * [Comparison Operators](#comparison-operators)
            * [Element Operators](#element-operators)
            * [Logical Operators](#logical-operators)
            * [Regex Operators](#regular-expression-operators)
    * [Updating](#updating)
    

# Week 1
Pretty simple concepts about why Mongo, how it scales, how stuff is stored.

# Week 2

## Creation

You can insert with insertOne({<document>}), insertMany([{<doc1>}, {<doc2>}],{<options>}) or update operators (upsert)

```javascript
//Insert one
db.people.insertOne({ _id: "abc", name: "juan", age: 22, body: { lenght: 10, widht: 10 }, 
                    hobbies: ["soccer", "games", "bikes"]})
//Insert many
db.people.insertMany([{ _id: "abcd", name: "lina", age: 22, body: { lenght: 15, widht: 10 }, 
                        hobbies: ["design", "bikes"]}, 
                        { _id: "abcde", name: "luis", age: 26, body: { lenght: 25, widht: 11 }, 
                         hobbies: ["cars", "bikes"]}])
```

## Reading

After that insertion we have:

* `{ _id: "abc", name: "juan", age: 22, body: { lenght: 10, widht: 10 }, hobbies: ["soccer", "games", "bikes"]}`
* `{ _id: "abcd", name: "lina", age: 22, body: { lenght: 15, widht: 10 }, hobbies: ["design", "bikes"]}`
* `{ _id: "abcde", name: "luis", age: 26, body: { lenght: 25, widht: 11 }, hobbies: ["cars", "bikes"]}`

### Retrieve using params
* Juan with:<br> 
```javascript
db.people.find({name: "juan"}).pretty()
```

* Both Juan and Lina with:<br> 
```javascript
db.people.find({age: 22}).pretty()
```

### Retrieve nested document
You need to use quotes and use dot notation.
* Both with: <br>
```javascript
db.people.find({"body.widht": 10}).pretty()
```

### Retrieve based on array equality
You can retrieve like this:
* Entire array equality (order matters):
```javascript
db.people.find({"hobbies": ["soccer", "games", "bikes"]}).pretty()
```
* Based on any element:
```javascript
db.people.find({"hobbies": "bikes"}).pretty()
```
* Based on a scpecific element:
```javascript
db.people.find({"hobbies.0": "soccer"}).pretty()
```
* Using operators (later)

### Cursors and Projections

When you retrieve more than one document, it is always returned in a cursor, which can be iterated:
```javascript
var cursor = db.people.find() 
var doc = function() { 
    return cursor.hasNext() ? cursor.next() : null
}
//Check how many are left in the batch
cursor.objsLeftInBatch()
```
Projections are used to reduce network overhead and processing requirement by limiting the fields that are returned. They are passed as the second parameter in find queries.
```javascript
/*The only exception is _id, if you dont want to see it
  you have to explicitly exclude it { _id: 0 } */
db.people.find({ name: "juan"}, { age: 1}).pretty()
```

### Query operators
[See](https://docs.mongodb.com/manual/reference/operator/)

#### Comparison operators
[Check mongo documentation to see all of the operators.](https://docs.mongodb.com/manual/reference/operator/query-comparison/)

* **$gt** and **$gte:** Greater than
```javascript
db.people.find({ age: { $gt: 22 } }).pretty()
```
* **$lt** and **$lte:** Less than 
```javascript
//This example shows $gte and $lte (greater than equal to)
db.people.find({ age: { $gte: 22, $lte: 25 } }).pretty()
```

* **$eq** and **$ne:** Equal to and not equal to <br>
 _The **`$ne`** also retrieves documents that **DONT** have that field._

 ```javascript
 db.people.find({ name: { $eq: "juan" } }).pretty()
 db.people.find({ name: { $ne: "juan" } }).pretty()
 ```
* **$in** and **$nin:** Matching
 ```javascript
 // $in must be an array
 db.people.find({ hobbies: { $in: ["bikes", "cars"] } }).pretty()
 ```
 
#### Element operators
 [See](https://docs.mongodb.com/manual/reference/operator/query/#element)
* **$exists:** Field exists or not
 ```javascript
 db.people.find({ "body.lenght": { $exists: true } }).pretty()
 ```
* **$type:** If a field is of the specified type
 ```javascript
  //You can find the types in the docs
  db.people.find({ "_id": { $type: "string" } }).pretty()
 ```

#### Logical operators
[See](https://docs.mongodb.com/manual/reference/operator/query/#logical)
 * **$or:**
  ```javascript
  db.people.find({ $or: [ { age: { $gte: 22 }}, { name: { $eq: "juan" }} ] }).pretty()
  ```
 * **$and:** AND is superfluous in most situations, however, it is useful when we need to verify two conditions on the same field (e.g. dirty data).
  ```javascript
   //You can find the types in the docs
   db.people.find({ $and: [ { age: { $gte: 22 }}, { name: { $eq: "juan" }} ] }).pretty()
  ```
  
#### Regular Expression operators
[See](https://docs.mongodb.com/manual/reference/operator/query/#evaluation)
  * **$regex:** 
   ```javascript
    db.people.find({ name: { $regex: /^.*/ } }).pretty()
   ```
  
#### Array operators
[See](https://docs.mongodb.com/manual/reference/operator/query-array/)
  * **$all:** 
   ```javascript
    db.people.find({ hobbies: { $all: ["bikes", "soccer", "games"] } }).pretty()
   ```

  * **$size:** 
   ```javascript
    db.people.find({ hobbies: { $size: 3 } }).pretty()
   ```
   
  * **$elemMatch:** TODO
   ```javascript
   //TODO    
   ```
   
## Updating
 * updateOne
 ```javascript
 /*
  * Field operators
  */
 //Add the field lastname to the document or modify it
 db.people.updateOne({ name: "juan" }, { $set: { lastname: "rodriguez" } })
 //Increment its age
 db.people.updateOne({ name: "luis" }, { $inc: { age: 1 } })
 /*
  * Array operators
  */
 //See the options for this operator
 //We want only 4 hobbies 
 db.people.updateOne({ name: "luis" }, { $push: 
                                            { hobbies: { $each: ["planes", "dancing"], 
                                                         $position: 0,
                                                         $slice: 4 } 
                                            } 
                                        })
 //Upsert
 db.people.updateOne({ name: "daniel" }, { $set: { lastname: "rambo" } }, { upsert: true })
 ```
 * updateMany
  ```javascript
  //I will set the lastname of those who dont have one to rodriguez
  db.people.updateMany({ lastname: { $exists: false }}, { $set: { lastname: "rodriguez" } })
  db.people.updateMany({ name: "luis" }, { $set: { lastname: "sanchez" } })
  ```
 
 * replaceOne
 ```javascript
 var newDaniel = { name: "daniel", lastname: "rambini", age: 19, hobbies: ["nothing"], body: { lenght: 10, widht: 10 }}
 db.people.replaceOne({ name: newDaniel.name }, newDaniel)
 ```
    