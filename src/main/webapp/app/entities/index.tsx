import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Address from './address';
import Shop from './shop';
import Product from './product';
import Offer from './offer';
import ShopGroup from './shop-group';
import Group from './group';
import Message from './message';
import GroupInvitation from './group-invitation';
import FriendRequest from './friend-request';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}address`} component={Address} />
      <ErrorBoundaryRoute path={`${match.url}shop`} component={Shop} />
      <ErrorBoundaryRoute path={`${match.url}product`} component={Product} />
      <ErrorBoundaryRoute path={`${match.url}offer`} component={Offer} />
      <ErrorBoundaryRoute path={`${match.url}shop-group`} component={ShopGroup} />
      <ErrorBoundaryRoute path={`${match.url}group`} component={Group} />
      <ErrorBoundaryRoute path={`${match.url}message`} component={Message} />
      <ErrorBoundaryRoute path={`${match.url}group-invitation`} component={GroupInvitation} />
      <ErrorBoundaryRoute path={`${match.url}friend-request`} component={FriendRequest} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
