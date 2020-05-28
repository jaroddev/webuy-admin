import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './offer.reducer';
import { IOffer } from 'app/shared/model/offer.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOfferDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OfferDetail = (props: IOfferDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { offerEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Offer [<b>{offerEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="start">Start</span>
          </dt>
          <dd>{offerEntity.start ? <TextFormat value={offerEntity.start} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="end">End</span>
          </dt>
          <dd>{offerEntity.end ? <TextFormat value={offerEntity.end} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="discountPrice">Discount Price</span>
          </dt>
          <dd>{offerEntity.discountPrice}</dd>
          <dt>
            <span id="discountQuantity">Discount Quantity</span>
          </dt>
          <dd>{offerEntity.discountQuantity}</dd>
          <dt>Product</dt>
          <dd>{offerEntity.product ? offerEntity.product.name : ''}</dd>
          <dt>Shop</dt>
          <dd>{offerEntity.shop ? offerEntity.shop.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/offer" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/offer/${offerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ offer }: IRootState) => ({
  offerEntity: offer.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OfferDetail);
