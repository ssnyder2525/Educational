// The component to be rendered
	var Item = React.createClass({
	    getDefaultProps: function() {
            return {
                name: 'Nameless'
            };
        },

    render: function() {
        return (<div>
            <a href="#" class="list-group-item">{this.props.name}</a>
        </div>)
    }
})


export default Item