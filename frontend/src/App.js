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

    componentDidMount() {
        fetch("https://api.github.com/repos/nikhilarora205/TeamScarletEnvironmentNow/stats/contributors")
          .then(res => res.json())
          .then(
            (result) => {
              this.setState({
                isLoaded: true,
                items: result
                
              });
            },
            // Note: it's important to handle errors here
            // instead of a catch() block so that we don't swallow
            // exceptions from actual bugs in components.
            (error) => {
              this.setState({
                isLoaded: false,
                error
              });
            }
          )
      }


  render() {
    const { error, isLoaded, items } = this.state;
        if (error) {
          return <div>Error: {error.message}</div>;
        } else if (!isLoaded) {
          return <div>Loading...</div>;
        } else {
            //  {items.author}, was trying to just output something to the screen to see that the call was working
            //console.log(this.state)
           // console.log(this.state.items)
           // console.log(this.state.items[0].author.login)
        }
    return (  
        
    <BrowserRouter>
      <div className="App">
      <Navigation />
      <Switch>
        <Route path="/" component={Home} exact/>
        <Route path="/about" component={About}/>
        <Route path="/contact" component={Contact}/>
        <Route path="/compare" component={Compare2}/>
        <Route path="/landing" component={Landing}/>
        <Route component={Error}/>
      </Switch>
      </div>
    </BrowserRouter> 
    );
  }
}
export default App;
