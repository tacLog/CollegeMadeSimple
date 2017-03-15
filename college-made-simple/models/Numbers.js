var mongoose = require('mongoose');
var GenerateSchema = require('generate-schema');

var schema = GenerateSchema.mongoose(
{
	"ID":"NONE",
	"numbers": [
	{"id" : "error",
	"total":"50",

	"value1": "10",
	"title1": "Sample Title",
	"value2": "10",
	"title2": "Sample Title",
	"value3": "10",
	"title3": "Sample Title",
	"value4": "10",
	"title4": "Sample Title",
	"value5": "10",
	"title5": "Sample Title"

},
{"id" : "scholarships",
"total":"50",

"value1": "10",
"title1": "Sample Title",
"value2": "10",
"title2": "Sample Title",
"value3": "10",
"title3": "Sample Title",
"value4": "10",
"title4": "Sample Title",
"value5": "10",
"title5": "Sample Title"

},
{"id" : "otherIn",
"total":"50",

"value1": "10",
"title1": "Sample Title",
"value2": "10",
"title2": "Sample Title",
"value3": "10",
"title3": "Sample Title",
"value4": "10",
"title4": "Sample Title",
"value5": "10",
"title5": "Sample Title"

},
{"id" : "grants",
"total":"50",

"value1": "10",
"title1": "Sample Title",
"value2": "10",
"title2": "Sample Title",
"value3": "10",
"title3": "Sample Title",
"value4": "10",
"title4": "Sample Title",
"value5": "10",
"title5": "Sample Title"

},
{"id" : "parents",
"total":"50",

"value1": "10",
"title1": "Sample Title",
"value2": "10",
"title2": "Sample Title",
"value3": "10",
"title3": "Sample Title",
"value4": "10",
"title4": "Sample Title",
"value5": "10",
"title5": "Sample Title"

},
{"id" : "job",
"total":"50",

"value1": "10",
"title1": "Sample Title",
"value2": "10",
"title2": "Sample Title",
"value3": "10",
"title3": "Sample Title",
"value4": "10",
"title4": "Sample Title",
"value5": "10",
"title5": "Sample Title"

},
{"id" : "utilities",
"total":"50",

"value1": "10",
"title1": "Sample Title",
"value2": "10",
"title2": "Sample Title",
"value3": "10",
"title3": "Sample Title",
"value4": "10",
"title4": "Sample Title",
"value5": "10",
"title5": "Sample Title"
},

{"id" : "personal",
"total":"150",

"value1": "10",
"title1": "Sample Title",
"value2": "10",
"title2": "Sample Title",
"value3": "10",
"title3": "Sample Title",
"value4": "10",
"title4": "Sample Title",
"value5": "10",
"title5": "Sample Title"

},
{"id" : "food",
"total":"50",

"value1": "10",
"title1": "Sample Title",
"value2": "10",
"title2": "Sample Title",
"value3": "10",
"title3": "Sample Title",
"value4": "10",
"title4": "Sample Title",
"value5": "10",
"title5": "Sample Title"

},
{"id" : "rent",
"total":"50",

"value1": "10",
"title1": "Sample Title",
"value2": "10",
"title2": "Sample Title",
"value3": "10",
"title3": "Sample Title",
"value4": "10",
"title4": "Sample Title",
"value5": "10",
"title5": "Sample Title"

},
{"id" : "tuition",
"total":"50",

"value1": "10",
"title1": "Sample Title",
"value2": "10",
"title2": "Sample Title",
"value3": "10",
"title3": "Sample Title",
"value4": "10",
"title4": "Sample Title",
"value5": "10",
"title5": "Sample Title"

},
{"id" : "supplies",
"total":"50",

"value1": "10",
"title1": "Sample Title",
"value2": "10",
"title2": "Sample Title",
"value3": "10",
"title3": "Sample Title",
"value4": "10",
"title4": "Sample Title",
"value5": "10",
"title5": "Sample Title"

},
{"id" : "transportation",
"total":"50",

"value1": "10",
"title1": "Sample Title",
"value2": "10",
"title2": "Sample Title",
"value3": "10",
"title3": "Sample Title",
"value4": "10",
"title4": "Sample Title",
"value5": "10",
"title5": "Sample Title"

}
]
}
);
var NumbersSchema = new mongoose.Schema(
	schema);
mongoose.model('Numbers', NumbersSchema);