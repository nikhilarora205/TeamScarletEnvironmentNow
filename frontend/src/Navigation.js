import React from 'react';
 
import { NavLink } from 'react-router-dom';
 
const Navigation = () => {
    return (
       <div>
          <NavLink to="/"> Home </NavLink>
          <br></br>
          <NavLink to="/compare"> Compare </NavLink>
          <br></br> 
          <NavLink to="/compare2"> Compare2 </NavLink>
          <br></br> 
          <NavLink to="/about"> About </NavLink>
          <br></br> 
          <NavLink to="/contact"> Contact </NavLink>
       </div>
    );
}
 
export default Navigation;