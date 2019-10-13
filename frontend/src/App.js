import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';

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
            <div>
                Successful API call
                {this.state.items.map((messageObj) => {
                    return(
                    <div>
                <p>Author: {messageObj.author.login}</p>
                <p>Commits: {messageObj.total}</p>
                </div>
                    );
            }
                )}
            </div>
          );
        }
      }


export default App;
