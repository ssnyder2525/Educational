var fs = require('fs');
var request = require('request');
var http = require('http');
var url = require('url');
var ROOT_DIR = "music/";

http.createServer(function (req, res) { 
     res.setHeader('Access-Control-Allow-Origin', 'http://uskkbfc97d90.ssnyder2525.koding.io');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials', true);
    
    var urlObj = url.parse(req.url, true, false); 
    console.log(urlObj); 
    var query = urlObj.query["q"];
    if(urlObj.pathname == "/getSong" && query !== "") {
        var words = query.split(' ');
        var urlquery = "";
        for (var i = 0; i < words.length; i++) {
            if(i == words.length - 1) {
                urlquery += words[i];
            }
            else {
                urlquery += words[i] + "%20";
            }
        }
        console.log(urlquery);
        request("https://api.spotify.com/v1/search?query=" + urlquery + "&type=track", function(error, response, body) {
            //var jsonresult= JSON.parse(body);
            res.writeHead(200);
            res.end(body);
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
    
}).listen(3000);

