import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './shop-group.reducer';
import { IShopGroup } from 'app/shared/model/shop-group.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShopGroupDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ShopGroupDetail = (props: IShopGroupDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { shopGroupEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          ShopGroup [<b>{shopGroupEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{shopGroupEntity.name}</dd>
          <dt>
            <span id="logo">Logo</span>
          </dt>
          <dd>
            {shopGroupEntity.logo ? (
              <div>
                {shopGroupEntity.logoContentType ? (
                  <a onClick={openFile(shopGroupEntity.logoContentType, shopGroupEntity.logo)}>
                    <img src={`data:${shopGroupEntity.logoContentType};base64,${shopGroupEntity.logo}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {shopGroupEntity.logoContentType}, {byteSize(shopGroupEntity.logo)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/shop-group" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/shop-group/${shopGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ shopGroup }: IRootState) => ({
  shopGroupEntity: shopGroup.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ShopGroupDetail);
