import './footer.scss';

import React from 'react';

import { Col, Row } from 'reactstrap';

const Footer = props => (
  <div className="footer page-content bg-primary">
    <Row>
      <Col md="12">
        <p> &copy; Copyright Webuy </p>
      </Col>
    </Row>
  </div>
);

export default Footer;
