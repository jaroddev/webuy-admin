import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GroupInvitation from './group-invitation';
import GroupInvitationDetail from './group-invitation-detail';
import GroupInvitationUpdate from './group-invitation-update';
import GroupInvitationDeleteDialog from './group-invitation-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GroupInvitationDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GroupInvitationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GroupInvitationUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GroupInvitationDetail} />
      <ErrorBoundaryRoute path={match.url} component={GroupInvitation} />
    </Switch>
  </>
);

export default Routes;
