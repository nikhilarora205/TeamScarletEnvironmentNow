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
    
//    secondLocation = () => {
//    	
//    	return ()
//    }
    

  render() {
    return (
      <div>
		<head> <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css" /> </head>
        <p>Please enter an address below to see Air, Water, and Natural Disaster statistics for that area! </p>
        <div class="ui focus input"><input id="address" type="text" placeholder="Enter An Address..." /></div>

		<button onClick={this.onClick} class="ui animated button">
		<div class="visible content">Get Statistics</div>
		<div class="hidden content"><i aria-hidden="true" class="arrow right icon"></i></div>
		</button>
		
		<br></br><br></br>
		
		<button  class="ui animated button">
		<div class="visible content">Compare Second Location</div>
		<div class="hidden content"><i aria-hidden="true" class="arrow right icon"></i></div>
		</button>
    </div>
    )
  }
}
 
export default Landing;
