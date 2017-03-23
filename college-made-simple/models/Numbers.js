var mongoose = require('mongoose');
var GenerateSchema = require('generate-schema');


var fieldsCats = new mongoose.Schema(
	{
			order:Number,
			year:Number,
			month:Number,
			unit:Number,
			title:String,
			}
	)

var numbersCats = new mongoose.Schema(
	{   id: String,
		totalYear:Number,
		totalUnit:Number,
		totalMonth:Number,
		type:String,
		fields: [fieldsCats]
	}
	);

var NumbersSchema = new mongoose.Schema(

{
	author:String,
	VID:{type:Number, default:0},
	//date:{type:Date, default: Date.now},
	numbers: [numbersCats]
}
);


mongoose.model('Numbers', NumbersSchema);