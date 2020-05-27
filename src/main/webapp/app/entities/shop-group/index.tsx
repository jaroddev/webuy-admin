import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ShopGroup from './shop-group';
import ShopGroupDetail from './shop-group-detail';
import ShopGroupUpdate from './shop-group-update';
import ShopGroupDeleteDialog from './shop-group-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ShopGroupDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ShopGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ShopGroupUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ShopGroupDetail} />
      <ErrorBoundaryRoute path={match.url} component={ShopGroup} />
    </Switch>
  </>
);

export default Routes;
