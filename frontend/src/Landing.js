import React, { Component } from 'react'
import ReactDOM from 'react-dom'
import './index.css'

class Landing extends Component {

    constructor(props) {
        super(props);
        this.state = { apiResponse: "",
                        address: ""    
        };
    }

    onClick = () => {
        
        this.address = document.getElementById('address').value;
        // var status = fetch('/api/validAddress');
        
        this.setState.apiResponse = null;
        console.log(this.state.apiResponse);
        const response = fetch('/api/validAddress/'+this.address).then(res => res.text())
        .then(res => this.setState({ apiResponse: res }, function() { 
            if(this.state.apiResponse === "False") window.alert("Please narrow your search")
            else{
                // figure out transition to next page
                this.props.history.push('/compare/'+this.address);
            }
    
    
    }));
        console.log(this.state.apiResponse);
      //  console.log(this.address);

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
