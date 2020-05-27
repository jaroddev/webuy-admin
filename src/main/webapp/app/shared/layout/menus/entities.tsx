import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu" style={{ maxHeight: '80vh', overflow: 'auto' }}>
    <MenuItem icon="asterisk" to="/address">
      Address
    </MenuItem>
    <MenuItem icon="asterisk" to="/shop">
      Shop
    </MenuItem>
    <MenuItem icon="asterisk" to="/product">
      Product
    </MenuItem>
    <MenuItem icon="asterisk" to="/offer">
      Offer
    </MenuItem>
    <MenuItem icon="asterisk" to="/shop-group">
      Shop Group
    </MenuItem>
    <MenuItem icon="asterisk" to="/group">
      Group
    </MenuItem>
    <MenuItem icon="asterisk" to="/message">
      Message
    </MenuItem>
    <MenuItem icon="asterisk" to="/group-invitation">
      Group Invitation
    </MenuItem>
    <MenuItem icon="asterisk" to="/friend-request">
      Friend Request
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
