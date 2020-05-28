import './footer.scss';

import React from 'react';

import { Col, Row } from 'reactstrap';

const Footer = props => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
        <p>A custom footer with no class neither style, fully updated by the developper of webapp react client app</p>
      </Col>
    </Row>
  </div>
);

export default Footer;
