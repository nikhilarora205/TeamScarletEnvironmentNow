import React, { Component } from 'react'
import ReactDOM from 'react-dom'
import './index.css'

class Landing extends Component {
  render() {
    return (
      <div>
        <p>Location: </p>
        <input
            type="text"
            placeholder= '100 Orvieto Cove, Liberty Hill, TX, 78642'
            width= '10000px'
            fontSize = '12px'
            textAlign = 'top'
         />
         <p>click this button to get statistics on this location: </p>
         <button>GO</button>
        <p>click this button to add location: </p>
         <button>ADD LOCATION</button>
    </div>
    )
  }
}
 
export default Landing;
