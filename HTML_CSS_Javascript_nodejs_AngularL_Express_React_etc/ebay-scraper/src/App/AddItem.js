/*
TODO
	[] Pop-up when item clicked
	[] Function from parent class for updating the itemcontainer
	[] Convert jQuery to react
*/

/* React Dependancies */
var React = require('react');


/* The small clickable item on the left sidebar */
var SmallItem = React.createClass({ 
	requestAuctions() { // upon clicking, make a new ajax request for that data and repopulate the item container
		this.props.requestAuctions(this.props.name, this.props.minPrice, this.props.maxPrice);
	},
	
	deleteItem(event) {
		event.stopPropagation();
		this.props.deleteItem(this.props.name, this.props.minPrice, this.props.maxPrice);
	},
	
    render() {
        return (
        	<div>
            	<div className="list-group-item" onClick={this.requestAuctions}>
            		{this.props.name}
            		<div className="deleteButton" onClick={this.deleteItem}>X</div>
            		<br/>
            		From ${this.props.minPrice}  To  ${this.props.maxPrice}
            	</div>
        	</div>
        );
    }
});

var AddItem = React.createClass({
	getInitialState () {
		var items;
		
		if (this.props.items)
		{
			items = this.props.items;
		}
		else
		{
			items = [];
		}
		
		return {items: items};
	},
	
	addItem () { // Update this to use react, lets try to use jquery for just requests. It'll make code cleaner/more readable
		if($("#searchWord").val() == "" || $("#minPrice").val() == "" || $("#maxPrice").val() == "")
		{
			alert("Please Fill All Fields");
			return;
		}
		$("#popup").modal("hide");
		this.props.masterAddItem($("#searchWord").val(), $("#minPrice").val(), $("#maxPrice").val());
		$('#searchWord').val("");
		$('#minPrice').val("");
		$('#maxPrice').val("");
	},
	
	render() {
		
		var requestAuctions = this.props.requestAuctions;
		var deleteItem = this.props.deleteItem;
		var items = this.state.items.map(function(item) {
			return <SmallItem name={item.searchWord} minPrice={item.minPrice} maxPrice={item.maxPrice} requestAuctions={requestAuctions} deleteItem={deleteItem}/>
		}, requestAuctions)
		return (
			<div className="col-md-3">
			<br />
				<p className="lead">Items</p>
				<div id="itemList" className="list-group">
					<button className="btn btn-success" type="button" data-toggle="modal" data-target="#popup">New Search</button>
				</div>
				<div id='items'>
				{items}
				</div>
				
				<div id="popup" className="modal fade" role="dialog">
				  <div className="modal-dialog">
				
				    <div className="modal-content">
				      <div className="modal-header">
				        <button type="button" className="close" data-dismiss="modal">&times;</button>
				        <h4 className="modal-title">Add New Search</h4>
				      </div>
				      <div className="modal-body">
				      	<div>
				        	<input id="searchInput" className="form-control required" placeholder="Search term" id="searchWord" type="text"/>
				        </div>
				        <br />
				        <div>
				        	Price Range: <input id="priceMin" className="form-control required" placeholder="Min Price" id="minPrice" type="number"/> 
				        	 To <input id="priceMax" className="form-control required" placeholder="Max Price" id="maxPrice" type="number"/>
				      	</div>
				      </div>
				      <div className="modal-footer">
				        <button type="button" className="btn btn-primary" onClick={this.addItem}>Add</button>
				        <button type="button" className="btn btn-default" data-dismiss="modal">Close</button>
				      </div>
				    </div>
				
				  </div>
				</div>
			</div>
		);
	}
});



export default AddItem;