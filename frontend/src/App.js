// App.js
import React, { Component } from 'react';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import './App.css';

import Home from './Home';
import About from './About';
import Contact from './Contact';
import Error from './Error';
import Navigation from './Navigation';
import Compare2 from './Compare2';
import Landing from './Landing'

class App extends Component {
        constructor(props) {
        super(props);
        this.state = {
          error: null,
          isLoaded: false,
          items: []
        };
      }


  render() {
    return (  
      <div> 
  
    <BrowserRouter>
      <div className="App">
      <Navigation />
      <Switch>
        <Route path="/" component={Landing} exact/>
        <Route path="/compare" component={Home}/>
        <Route path="/compare2" component={Compare2}/>
        <Route path="/about" component={About}/>
        <Route path="/contact" component={Contact}/>
        <Route component={Error}/>
      </Switch>
      </div>
    </BrowserRouter> 

    </div>
    );
  }
}
export default App;
