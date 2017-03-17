var mongoose = require('mongoose');
var GenerateSchema = require('generate-schema');

var NumbersSchema = new mongoose.Schema(

{
	author:String,
	VID:{type:Number, default:0},
	//date:{type:Date, default: Date.now},
	numbers: [
	{   id: String,
		totalYear:Number,
		totalUnit:Number,
		totalMonth:Number,
		type:String,
		fields: [{
			order:Number,
			year:Number,
			month:Number,
			unit:Number,
			title:String,
			}]
	}
	]
}
);

mongoose.model('Numbers', NumbersSchema);