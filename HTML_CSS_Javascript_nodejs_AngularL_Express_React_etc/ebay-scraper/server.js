/*

TODO
	[x] Get mongo/mongoose working 
		[x] Basic setup
		[x] Add user object -- reference => mongoosejs.com/docs/index.html
		[x] Add item object -- pick a better name than item
	[] Add endpoints for
		[] Saved items
	[] Implement password security
	[] Use socketio instead of express
		

*/




//
// # SimpleServer
//
// A simple chat server using Socket.IO, Express, and Async.
//
var http = require('http');
var path = require('path');
var async = require('async');
var socketio = require('socket.io');
var express = require('express');
var bodyParser = require('body-parser');

/* Webpack -- compile react */
import webpackMiddleware from 'webpack-dev-middleware';  
import webpackHotMiddleware from 'webpack-hot-middleware';
import webpack from 'webpack'

//
// ## SimpleServer `SimpleServer(obj)`
//
// Creates a new instance of SimpleServer with the following options:
//  * `port` - The HTTP port to listen on. If `process.env.PORT` is set, _it overrides this value_.
//
var router = express();
var server = http.createServer(router);
var io = socketio.listen(server);
import config from './webpack.config.js';

const compiler = webpack(config); // compile

/* Setup Mongoose */
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/ebay');
var User = require("./models/User");
import Item from "./models/User"; // var Item = require("./models/User");
var Listing = require("./models/User");

router.use(express.static(path.resolve(__dirname, 'public')));
router.use(webpackMiddleware(compiler)); 
router.use(webpackHotMiddleware(compiler));
router.use(bodyParser());

/* Connect to the db */
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function (callback) 
{
	console.log("success");
});

/* Cache */
var messages = [];
var sockets = [];




io.on('connection', function (socket) 
{
	messages.forEach(function (data) 
	{
		socket.emit('message', data);
	});

	sockets.push(socket);

	socket.on('disconnect', function () 
	{
		sockets.splice(sockets.indexOf(socket), 1);
		updateRoster();
	});

	socket.on('message', function (msg) 
	{
		var text = String(msg || '');
		if (!text)
			return;

		socket.get('name', function (err, name) 
		{
			var data = 
			{
				name: name,
				text: text
			};

			broadcast('message', data);
			messages.push(data);
		});
	});

	socket.on('identify', function (name) 
	{
		socket.set('name', String(name || 'Anonymous'), function (err) 
		{
			updateRoster();
		});
	});
});

function updateRoster() 
{
	async.map
	(
		sockets,
		function (socket, callback) 
		{
			socket.get('name', callback);
		},
		function (err, names) 
		{
			broadcast('roster', names);
		}
	);
}

function broadcast(event, data) 
{
	sockets.forEach(function (socket) 
	{
		socket.emit(event, data);
	});
}

server.listen(process.env.PORT || 3000, process.env.IP || "0.0.0.0", function()
{
	var addr = server.address();
	console.log("Chat server listening at", addr.address + ":" + addr.port);
});


/* Add user */
router.post("/user",function(request, response){
	console.log(request.body)
	
	var email = request.body.email;
	var password = request.body.password;
	
    var newUser = new User({ 
    	Username: email, 
	    Password: password, 
	    Items: []
    });
	newUser.save(function (err) {
	  if (err)
	  {
	  	console.log("Error!");
	  	response.send("Failure\n");
	  }
	  else
	  {
	  	console.log("saved user");
	    response.send("OK\n");
	  }
	});
});

/* Update user */
router.put("/user", function(req, res) {
	console.log(req.body);
	var email = req.body.email;
	var password = req.body.password;
	
	
	User.findOne(
		{
			'Username': email,
			'Password': password
		},
		function (err, user) {
			console.log("err: " + err);
			console.log("user: " + user);
			if (err || !user)
			{
				console.log("no");
				res.status(404).send("Incorrect");
				return;
				//res.end();
				//res.status(404).end();
			}
			else 
			{
				res.json(user);
			}
		}
	);
});

/* Add item */ 
router.post("/item", function(req, res) {
	
	var username = req.body.Username;
	var password = req.body.Password;
	var item = req.body.Item;
	
	User.findOne(
		{
			'Username': username,
			'Password': password
		},
		function (err, user) {
			
			if (err || !user)
			{
				res.end();
				res.status(404).end();
			}
			else 
			{
				// update the user's Items and save
				user.Items.push({ 
			        SearchWord: item.SearchWord, 
			        MinPrice: item.MinPrice, 
			        MaxPrice: item.MaxPrice,
			        Listings: []
				});
				user.save();
				
				// OK == success
				res.json(user);
			}
		}, item // pass item into the callback function
	);
});


/* Update Item */
router.put("/item", function(req, res) {
	
	var username = req.body.Username;
	var password = req.body.Password;
	var item = req.body.Item;
	
	User.findOne(
		{
			'Username': username,
			'Password': password
		},
		function (err, user) {
			
			if (err || !user)
			{
				res.end();
				res.status(404).end();
			}
			else 
			{
				// update the user's Items and save
				
				for (var i = 0; i < user.Items.length; i++)
				{
					if (user.Items[i]._id == user._id)
					{
						user.Items[i]._id == user;
					}
				}
				
				user.save();
				
				// OK == success
				res.json(user);
			}
		}, item // pass item into the callback function
	);
});


/* Delete Item */
router.delete("/item", function(req, res) {
	
	var username = req.body.username;
	var password = req.body.password;
	var item = {
		name: req.body.name,
		minPrice: req.body.minPrice,
		maxPrice: req.body.maxPrice
	}
	console.log(item)
	
	User.findOne(
		{
			'Username': username,
			'Password': password
		},
		function (err, user) {
			
			if (err || !user)
			{
				res.end();
				res.status(404).end();
			}
			else 
			{
				// update the user's Items and save
				for (var i = 0; i < user.Items.length; i++)
				{
					if ( user.Items[i].SearchWord == item.name &&
							user.Items[i].MinPrice == item.minPrice &&
							user.Items[i].MaxPrice == item.maxPrice )
					{
						user.Items.splice(i, 1);
						break;
					}
				}
				user.save();
				
				res.json(user);
			}
		}, item // pass item into the callback function
	);
});


router.get('/Users', function(req, res, next) {
  User.find(function(err, data){
    if(err){ return next(err); }
    res.json(data);
  });
});


router.get('/unreadListings',function(req, res, next) {
	
	console.log("unreadListings");
	var username = req.body.username;
	var itemName = req.body.itemName;
	var listingURLs = req.body.listingURLs;
	
	//listings that have appeared since the user last looked
	var unreadListings = [];
	
	User.findOne(
	{
		'Username': username,
	},
	function (err, user) 
	{
		if (err || !user)
		{
			console.log("error")
			res.end();
			res.status(404).end();
		}
		else 			
		{
			console.log("gud")
			//items the user owns
			var items = user.Items;
			
			
			console.log("items")
			console.log(items)
			for (var i = 0; i < items.length; i++)
			{
				if (items[i].SearchWord == itemName)
				{
					var listings = items[i].Listings;
					
					break;
				}
				
			}
			
			for (var i = 0; i < listingURLs.length; i++)
			{
				var found = false;
				
				for( var prop in listings ) {
			        if( listings.hasOwnProperty( prop ) ) {
			             if( listings[ prop ] === listingURLs[i] )
			             {
			             	found = true;
			             }
			        }
			    }
			    
			    if (! found)
			    {
			    	unreadListings.push(listingURLs[i]);
			    }
			}
			
			console.log(unreadListings);
			
			res.json(unreadListings);
		}		
	});
	
	
});

router.post("/addListings", function(req, res) {
		
	var username = req.body.username;
	var itemName = req.body.itemName;
	var listingURLs = req.body.listingURLs;
	
	
	User.findOne(
	{
		'Username': username,
	},
	function (err, user) 
	{
		if (err || !user)
		{
			res.end();
			res.status(404).end();
		}
		else 			
		{
			var item;
			
			for (var i = 0; i < user.Items.length; i++)
			{
				if (user.Items[i].SearchWord == itemName)
				{
					item = user.Items[i];
					break;
				}
			}
			
				
			for (var i = 0; i < listingURLs.length; i++)
			{
				var newListing = {
					itemName: itemName,
					listingURL: listingURLs[i]
				}
				
				item.Listings.push(newListing);
			}
			
			user.save();
			res.send("OK");
		}
	});
	
});