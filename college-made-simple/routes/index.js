var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
//set up our model

var Numbers = mongoose.model('Numbers');

router.get('/numbers', function(req, res, next) {
	Numbers.find().sort('_id','descending').limit(1).find(function(err, numbers){
		if(err) {return next(err);}

		res.json(numbers);
	});
});

router.post('/numbers', function(req, res, next) {
	var numbers = new Numbers(req.body);

	numbers.save(function(err, numbers){
    if(err){ return next(err); }

    res.json(numbers);
  	});
});



//default thing left it allone 
/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

module.exports = router;
