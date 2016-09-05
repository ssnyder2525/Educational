var mongoose = require('mongoose');
//mongoose.connect('mongodb://localhost/ebay');

var mongoosedb = mongoose.createConnection(process.env.IP, "blahblah");
mongoosedb.on('error', console.error.bind(console, 'connection error:')); 


var ConditionSchema = new mongoose.Schema({
    Value: Number
});


var Item = mongoose.Schema(
    { 
        SearchWord: String, 
        MinPrice: Number, 
        MaxPrice: Number,
        Currency: {
            type: String,
            default: "USD"
        },
        Shipping: String,
        BestOfferOnly: {
            type: Boolean,
            default: false
        },
        CharityOnly: {
            type: Boolean,
            default: false
        },
        ExcludeAutoPay: {
            type: Boolean,
            default: false
        },
        ExpeditedShippingType: String,
        FeaturedOnly: {
            type: Boolean,
            default: false
        },
        FreeShippingOnly: {
            type: Boolean,
            default: false
        },
        GetItFastOnly: {
            type: Boolean,
            default: false
        },
        ListingType: "String",
        
        Listings: [{
            itemName: String,
            listingURL: String
        }]
    }
);


var UserSchema = new mongoose.Schema({
    Username: String, 
    Password: String, 
    Items: [Item]
});


module.exports = mongoose.model('User', UserSchema);
