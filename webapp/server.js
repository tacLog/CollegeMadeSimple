var express = require('express');
var app = express();
var mongojs = require('mongojs');
var db = mongojs('userlist', ['userlist']);

app.use(express.static(__dirname + "/public"));

app.get('/userlist', function(req, res) {
   console.log("received GET req");

   db.userlist.find(function(err, docs){
         console.log(docs);
         res.json(docs);
   });

});

app.listen(3000);
console.log("Server running on port 3000");