var mongoose = require('mongoose');
var bcrypt   = require('bcrypt-nodejs');

/*
local            : {
        email        : String,
        password     : String,
    },
    facebook         : {
        id           : String,
        token        : String,
        email        : String,
        name         : String
    },
    twitter          : {
        id           : String,
        token        : String,
        displayName  : String,
        username     : String
    },

*/
var userSchema = mongoose.Schema({

    google           : {
        id           : String,
        token        : String,
        email        : String,
        name         : String
    }

});


// create the model for users and expose it to our app
module.exports = mongoose.model('User', userSchema);