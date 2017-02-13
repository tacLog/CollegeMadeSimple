var port = 8080
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

app.listen(port);
console.log("Server running on port"+port);
