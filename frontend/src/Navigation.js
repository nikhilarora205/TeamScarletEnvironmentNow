import React from 'react';
 
import { NavLink } from 'react-router-dom';
 
const Navigation = () => {
    return (
       <div>
          <NavLink to="/"> Home </NavLink>
          <NavLink to="/about"> About </NavLink>
          <NavLink to="/contact"> Contact </NavLink>
          <NavLink to="/compare"> Compare2 </NavLink>
          <NavLink to="/landing"> Landing </NavLink>
       </div>
    );
}
 
export default Navigation;