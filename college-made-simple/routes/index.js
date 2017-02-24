var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');
//set up our model
var passport = require('passport');
var Numbers = mongoose.model('Numbers');

router.get('/numbers', function(req, res, next) {
	Numbers.find(function(err, numbers){
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

// =====================================
// GOOGLE ROUTES =======================
// =====================================
// send to google to do the authentication
// profile gets us their basic information including their name
// email gets their emails
router.get('/auth/google', passport.authenticate('google', { scope : ['profile', 'email'] }));

// the callback after google has authenticated the user
router.get('/auth/google/callback',
	passport.authenticate('google', {
		successRedirect : '/profile',
		failureRedirect : '/'
	}));


/* GET home page. */
router.get('/', function(req, res, next) {
	res.render('index', { title: 'Express' });
});
module.exports = router;


// route middleware to ensure user is logged in
function isLoggedIn(req, res, next) {
	if (req.isAuthenticated())
		return next();

	res.redirect('/');
}