import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { openFile, byteSize, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './shop-group.reducer';
import { IShopGroup } from 'app/shared/model/shop-group.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShopGroupProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ShopGroup = (props: IShopGroupProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { shopGroupList, match, loading } = props;
  return (
    <div>
      <h2 id="shop-group-heading">
        Shop Groups
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Shop Group
        </Link>
      </h2>
      <div className="table-responsive">
        {shopGroupList && shopGroupList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Logo</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {shopGroupList.map((shopGroup, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${shopGroup.id}`} color="link" size="sm">
                      {shopGroup.id}
                    </Button>
                  </td>
                  <td>{shopGroup.name}</td>
                  <td>
                    {shopGroup.logo ? (
                      <div>
                        {shopGroup.logoContentType ? (
                          <a onClick={openFile(shopGroup.logoContentType, shopGroup.logo)}>
                            <img src={`data:${shopGroup.logoContentType};base64,${shopGroup.logo}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {shopGroup.logoContentType}, {byteSize(shopGroup.logo)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${shopGroup.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${shopGroup.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${shopGroup.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Shop Groups found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ shopGroup }: IRootState) => ({
  shopGroupList: shopGroup.entities,
  loading: shopGroup.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ShopGroup);
