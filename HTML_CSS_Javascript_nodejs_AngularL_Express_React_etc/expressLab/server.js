var express = require('express');
var https = require('https');
var http = require('http');
var fs = require('fs');
var url = require('url');
var app = express();
var bodyParser = require('body-parser')
var basicAuth = require('basic-auth-connect');
var auth = basicAuth(function(user, pass) {
    return((user ==='cs201r')&&(pass === 'test'));
});
var options = {
    host: '127.0.0.1',
    key: fs.readFileSync('ssl/server.key'),
    cert: fs.readFileSync('ssl/server.crt')
};
http.createServer(app).listen(80);
https.createServer(options, app).listen(443);
app.use('/', express.static('./html', {maxAge: 60*60*1000}));
app.use(bodyParser());
  
 app.get('/getCity', function (req, res) {
	console.log("hey");
    var urlObj = url.parse(req.url, true, false); 
    console.log(urlObj); 
	res.setHeader('Access-Control-Allow-Origin', 'http://52.34.7.124');
    if(urlObj.pathname == "/getCity/" && urlObj.query["q"] != "") {
        res.writeHead(200);
        var cities = "";
        var jsonresult = [];
        var myReg = new RegExp("^" + urlObj.query["q"]);
        fs.readFile("html/" + urlObj.pathname + 'cities.txt', function (err, data) {
            if(err) {throw(err);}
            cities = data.toString().split("\n");
            for (var i = 0; i < cities.length; i++) {
                var result = cities[i].search(myReg);
                if(result != -1) {
                    jsonresult.push({city:cities[i]});
                }
            }
            res.end(JSON.stringify(jsonresult));
        });
    }
    else 
    { 
        fs.readFile(ROOT_DIR + urlObj.pathname, function (err,data) { 
            if (err) { 
                res.writeHead(404); 
                res.end(JSON.stringify(err)); 
                return;
            } 
            res.writeHead(200); 
            res.end(data); 
        }); 
    }
});

app.get('/comment', function (req, res) {
	console.log("In comment route");
	console.log("In GET"); 
	// Read all of the database entries and return them in a JSON array
	var MongoClient = require('mongodb').MongoClient;
	MongoClient.connect("mongodb://localhost:27017/auth", function(err, db) {
		if(err) throw err;
		db.collection("comments", function(err, comments){
			if(err) throw err;
			comments.find(function(err, items){
				items.toArray(function(err, itemArr){
					console.log("Document Array: ");
					console.log(itemArr);
					var retData = JSON.stringify(itemArr);
					res.writeHead(200);
					res.end(retData);				
				});
			});
		});
	});
});

app.post('/comment', auth, function (req, res) {

	console.log(req.body);
	//console.log("In POST comment route");
	//console.log(req.body.Name);
	//console.log(req.body.Comment);
	// First read the form data
	var jsonData = req.body;
	console.log(jsonData);

	var MongoClient = require('mongodb').MongoClient;
	MongoClient.connect("mongodb://localhost:27017/auth", function(err, db) {
		if(err) throw err;
			db.collection('comments').insert(jsonData,function(err, records) {
				console.log("Record added as "+records[0]._id);
			});
		res.writeHead(200);
		res.end("");
	});
});
