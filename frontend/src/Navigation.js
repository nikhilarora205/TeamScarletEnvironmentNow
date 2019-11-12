import React, { Component } from 'react';
 
import { NavLink } from 'react-router-dom';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import 'bootstrap/dist/css/bootstrap.min.css';


class Navigation extends Component{


   render(){
      return(
   <Navbar bg="dark" fixed="top" variant="dark" expand="lg">
      <Navbar.Brand>Environment Now</Navbar.Brand>
  
      <Navbar.Collapse id="basic-navbar-nav">
         <Nav className="mr-auto">
            <Nav.Link href="/">Home</Nav.Link>
            <Nav.Link href="/about">About</Nav.Link>
            <Nav.Link href="/contact">Contact</Nav.Link>
         </Nav>
      </Navbar.Collapse>
   </Navbar>
      )
   }
}
 
export default Navigation;