/*

TODO
	[] 

*/



/* React Dependancies */
var React = require('react')



var Auction = React.createClass ({
	render() {
		return (
			<div className="col-sm-4 col-lg-4 col-md-4">
				<div className="thumbnail">
					<img src={this.props.thumbnail} alt="" />
					<div className="caption">
						<h4>${this.props.cost}</h4>
						<h4><a href={this.props.url} target="_blank">{this.props.title}</a>
						</h4>
					</div>
				</div>
			</div>
		);
	}
});


var ItemContainer = React.createClass ({
	render() {
		var auction_list = this.props.auctions.map(function(auction) {
			return (
				<Auction title={auction.title} 
						description={auction.description} 
						num_ratings={auction.num_ratings} 
						cost={auction.cost} 
						thumbnail={auction.thumbnail}
						url = {auction.url}
						/>
			)
		});
		return (
			<div className="row">
				{auction_list}
			</div>
		);
	}
});



export default ItemContainer;

