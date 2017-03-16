var mongoose = require('mongoose');
var GenerateSchema = require('generate-schema');

var NumbersSchema = new mongoose.Schema(

{
	author:String,
	ID:String,
	numbers: [
				{id : String,
				total:Number,

				value1: Number,
				title1: String,
				value2: Number,
				title2: String,
				value3: Number,
				title3: String,
				value4: Number,
				title4: String,
				value5: Number,
				title5: String
				}
			]
}
);


mongoose.model('Numbers', NumbersSchema);