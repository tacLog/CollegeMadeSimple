var express = require('express');
var router = express.Router();
var passport = require('passport');
var adder = require('cloneextend');

//set up our database
var mongoose = require('mongoose');
var Numbers = mongoose.model('Numbers');
var User = mongoose.model('User');

var jwt = require('express-jwt');
var auth = jwt({secret: 'SECRET', userProperty: 'payload'});

var test = require('../public/data/test.json');
router.get('/numbers', auth, function(req, res, next) {
	Numbers.find(function(err, numbers){
		if(err) {return next(err);}

		res.json(numbers);
	});
});
/*
router.param('id', function(req, res, next, id) {
  var query = Numbers.findOne({'author': id});

  query.exec(function (err, numbers){
    if (err) { return next(err); }
    if (!numbers) { return next(new Error('can\'t find numbers for user: '+id)); }

    req.post = numbers;
    res.json(numbers);
    return next();
  });
});
*/ 
router.get('/numbers/user', auth, function(req, res, next) {
	//console.log('Starting reqest');
	var id = req.payload.username;
	//console.log(id);
	var query = Numbers.find({'author': id}).sort({'_id':-1}).limit(10);

  	query.exec(function (err, results){
    if (err) { return next(err); }
    if (!results) { return next(new Error('can\'t find results for user: '+id)); }
    //console.log(results);
    //console.log(results[0].numbers.length);
    //req.post = results;
    if (results[0].numbers.length==0){
      results = test;
    }
    res.json(results);
  });
});

router.post('/numbers', auth, function(req, res, next) {
	var numbers = new Numbers();
  //console.log(req.body);
  adder.add(numbers.numbers,req.body.numbers);
  numbers.VID = req.body.VID;
  //numbers.numbers = req.body.numbers;
  //console.log(numbers);
	numbers.author = req.payload.username;
  
	numbers.save(function(err, numbers){
		if(err){ 
      console.log('I found you');
      return next(err); }

		res.json(numbers);
	});
});

router.post('/register', function(req, res, next){
  if(!req.body.username || !req.body.password){
    return res.status(400).json({message: 'Please fill out all fields'});
  }

  var user = new User();

  user.username = req.body.username;

  user.setPassword(req.body.password)

  user.save(function (err){
    if(err){ return next(err); }

    return res.json({token: user.generateJWT()})
  });
});

router.post('/login', function(req, res, next){
  if(!req.body.username || !req.body.password){
    return res.status(400).json({message: 'Please fill out all fields'});
  }

  passport.authenticate('local', function(err, user, info){
    if(err){ return next(err); }

    if(user){
      return res.json({token: user.generateJWT()});
    } else {
      return res.status(401).json(info);
    }
  })(req, res, next);
});


/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

module.exports= router;
