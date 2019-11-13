import React, { Component } from 'react'
import ReactDOM from 'react-dom'
import './index.css'
import './Landing.css';

class Landing extends Component {

    constructor(props) {
        super(props);
        this.state = { apiResponse: "",
                        address: "",
                        address2: ""  
        };
    }

    onClick = () => {
        
        this.address = document.getElementById('address').value;
        // var status = fetch('/api/validAddress');
        
        this.setState.apiResponse = null;
        console.log(this.state.apiResponse);
        const response = fetch('/api/validAddress/'+this.address).then(res => res.text())
        .then(res => this.setState({ apiResponse: res }, function() {
            console.log(res)
            if(this.state.apiResponse === "false") window.alert("Please narrow your search")
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
    
   secondLocation = () => {
    
    document.getElementById("singleButton").style.display = "none";
    document.getElementById("singleLocation").style.display = "none";
    document.getElementById("singleHidden").style.display = "none";

    document.getElementById("add2").style.visibility = "visible";
    document.getElementById("address2").style.visibility = "visible";

    
    document.getElementById("loc1").style.visibility = "visible";
    document.getElementById("loc2").style.visibility = "visible";

    document.getElementById("secondLocation").style.visibility = "hidden";

    document.getElementById("compareButton").style.display = "inline";
    document.getElementById("comparePrompt").style.display = "inline";
    document.getElementById("secPrompt").style.display = "inline";

    document.getElementById("backToOne").style.visibility = "visible";
   }

   revertBack = () => {

    document.getElementById
    
    document.getElementById("singleButton").style.display = "inline";
    document.getElementById("singleLocation").style.display = "inline";
    document.getElementById("singleHidden").style.display = "inline";

    document.getElementById("add2").style.visibility = "hidden";
    document.getElementById("address2").style.visibility = "hidden";

    
    document.getElementById("loc1").style.visibility = "hidden";
    document.getElementById("loc2").style.visibility = "hidden";

    document.getElementById("secondLocation").style.visibility = "visible";

    document.getElementById("compareButton").style.display = "none";
    document.getElementById("comparePrompt").style.display = "none";
    document.getElementById("secPrompt").style.display = "none";

    document.getElementById("backToOne").style.visibility = "hidden";
   }

    compare2 = () => {
        
        this.address = document.getElementById('address').value;
        this.address2 = document.getElementById('address2').value;
        // var status = fetch('/api/validAddress');
        
        this.setState.apiResponse = null;
        console.log(this.state.apiResponse);
        const response = fetch('/api/validAddress/'+this.address).then(res => res.text())
        .then(res => this.setState({ apiResponse: res }, function() { 
            if(this.state.apiResponse === "False") window.alert("Please narrow your search")
            else{
                // figure out transition to next page
                this.props.history.push('/compare2/'+this.address+"?"+this.address2);
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
		<head><link rel="stylesheet" href="//cdn.jsdelivr.net/npm/semantic-ui@2.4.2/dist/semantic.min.css" /></head>
        
        <br></br><br></br><br></br><br></br><br></br><br></br>
        <p>Please enter an address below to see Air, Water, and Natural Disaster statistics for that area! </p>
		<br></br><br></br>
		
        <table align="center" cellPadding="10">
            <tbody>
            <tr>
                <td id="loc1" valign="middle">
                    <h3>Location 1: </h3>
                </td>
                <td valign="middle">
                    <div class="ui focus input"><input id="address" type="text" placeholder="Enter An Address..." /></div>
                </td>
                <td valign="middle">
                    <button id="singleButton" onClick={this.onClick} class="ui animated button">
                        <div id="singleLocation" class="visible content">Get Statistics</div>
                        <div id="singleHidden" class="hidden content"><i aria-hidden="true" class="arrow right icon"></i></div>
                    </button>
                </td>
                <td valign="middle">
                
                </td>
            </tr>
            <tr>
                <td id="loc2" valign="middle">
                    <h3>Location 2: </h3>
                </td>
                <td valign="middle">
                    <div id="add2" class="ui focus input"><input id="address2" type="text" placeholder="Enter An Address..." /></div>
                </td>
                <td valign="middle">
                    <button id="secondLocation" onClick={this.secondLocation} class="ui primary button">Compare Second Location</button>
                </td>
                <td valign="middle">
                    <button id="compareButton" onClick={this.compare2} class="ui animated button">
                        <div id="comparePrompt" class="visible content">Compare Locations</div>
                        <div id="secPrompt" class="hidden content"><i aria-hidden="true" class="arrow right icon"></i></div>
                    </button>
                </td>
            </tr>
            <tr>
                <td>

                </td>
                <td valign="middle">
                    <button id="backToOne" onClick={this.revertBack} class="ui primary button">Back to One Location</button>
                </td>
                <td>
                    
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    )
  }
}
 
export default Landing;
