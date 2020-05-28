import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IShop } from 'app/shared/model/shop.model';
import { getEntities as getShops } from 'app/entities/shop/shop.reducer';
import { getEntity, updateEntity, createEntity, reset } from './offer.reducer';
import { IOffer } from 'app/shared/model/offer.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IOfferUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const OfferUpdate = (props: IOfferUpdateProps) => {
  const [productId, setProductId] = useState('0');
  const [shopId, setShopId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { offerEntity, products, shops, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/offer');
  };

  useEffect(() => {
    if (!isNew) {
      props.getEntity(props.match.params.id);
    }

    props.getProducts();
    props.getShops();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...offerEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="weBuyApp.offer.home.createOrEditLabel">Create or edit a Offer</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : offerEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="offer-id">ID</Label>
                  <AvInput id="offer-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="startLabel" for="offer-start">
                  Start
                </Label>
                <AvField id="offer-start" type="date" className="form-control" name="start" />
              </AvGroup>
              <AvGroup>
                <Label id="endLabel" for="offer-end">
                  End
                </Label>
                <AvField id="offer-end" type="date" className="form-control" name="end" />
              </AvGroup>
              <AvGroup>
                <Label id="discountPriceLabel" for="offer-discountPrice">
                  Discount Price
                </Label>
                <AvField id="offer-discountPrice" type="string" className="form-control" name="discountPrice" />
              </AvGroup>
              <AvGroup>
                <Label id="discountQuantityLabel" for="offer-discountQuantity">
                  Discount Quantity
                </Label>
                <AvField id="offer-discountQuantity" type="string" className="form-control" name="discountQuantity" />
              </AvGroup>
              <AvGroup>
                <Label for="offer-product">Product</Label>
                <AvInput id="offer-product" type="select" className="form-control" name="product.id">
                  <option value="" key="0" />
                  {products
                    ? products.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.name}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="offer-shop">Shop</Label>
                <AvInput id="offer-shop" type="select" className="form-control" name="shop.id">
                  <option value="" key="0" />
                  {shops
                    ? shops.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/offer" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  products: storeState.product.entities,
  shops: storeState.shop.entities,
  offerEntity: storeState.offer.entity,
  loading: storeState.offer.loading,
  updating: storeState.offer.updating,
  updateSuccess: storeState.offer.updateSuccess,
});

const mapDispatchToProps = {
  getProducts,
  getShops,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(OfferUpdate);
