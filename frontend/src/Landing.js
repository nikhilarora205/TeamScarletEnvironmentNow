import React, { Component } from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import continuousColorLegend from 'react-vis/dist/legends/continuous-color-legend';


class Landing extends Component {

    
    constructor(props) {
        super(props);
        this.state = { apiResponse: "",
                        address: ""    
        };
    }

    // var Store = require('Store');
    // var Navigatable = require('react-router-component').NavigatableMixin
    
    // var LoginComponent = React.createClass({
    //     mixins: [ Navigatable ],
    
    //     onClick: function() {
    //         Store.login(function(userId){
    //             this.navigate('/user/' + userId)
    //         }.bind(this));
    //     },
    
    //     render: function() {
    //         return <button onClick={this.onClick}>Login</button>;
    //     }
    // });

    onClick = () => {
        
        this.address = document.getElementById('address').value;
        // var status = fetch('/api/validAddress');
        const response = fetch('/api/validAddress').then(res => res.text())
        .then(res => this.setState({ apiResponse: res }));
        console.log(this.state.apiResponse);
        console.log(this.address);

        // check for valid address
        if(this.state.apiResponse === "False"){
            // print out prompt to screen to narrow search
            // console.log("Please narrow your search");
            window.alert("Please narrow your search");
        }
        else {
            // figure out transition to next page
            // this.props.history.push('/compare/'+this.address);
        }

        // this.props.history.push('/compare/'+this.address);
        // fetch("/api/getLocation")
        // .then(function(response) {
        //     console.log("It worked, response is: ", response)
        //     this.props.history.push({
        //         pathname: '/compare'
        //     }); 
        // }).catch(function() {
        //     console.log("error");
        // });
        // var self = this;
        // this.transitionTo('compare');
        // console.log(this.state.apiResponse);
    }

  render() {
    return (
      <div>
        <p>Location: </p>
        <input id ="address"
            type="text"
            placeholder= '100 Orvieto Cove, Liberty Hill, TX, 78642'
            width= '10000px'
            fontSize = '12px'
            textAlign = 'top'
         />
         <p>click this button to get statistics on this location: </p>
         <button onClick={this.onClick}>GO</button>
        <p>click this button to add location: </p>
         <button>ADD LOCATION</button>
    </div>
    )
  }
}
 
export default Landing;
