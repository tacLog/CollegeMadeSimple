var mongoose = require('mongoose');

var NumbersSchema = new mongoose.Schema({
	title: {type: String, default: "Welcome"},
	jobs: {type: Number, default: 0},
	/*
	scholarships: {type: Number, default: 0},
	other: {type: Number, default: 0},
	grants: {type: Number, default: 0},
	parents: {type: Number, default: 0},
	job: {type: Number, default: 0},
	utilities: {type: Number, default: 0},
	other: {type: Number, default: 0},
	books: {type: Number, default: 0},
	food: {type: Number, default: 0},
	rent: {type: Number, default: 0},
	*/
});
mongoose.model('Numbers', NumbersSchema);