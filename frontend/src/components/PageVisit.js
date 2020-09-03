import React, { useEffect, useState } from 'react';

import {
  Button,
  Card,
  CardHeader,
  CardBody,
  NavItem,
  NavLink,
  Nav,
  Progress,
  Table,
  Container,
  Row,
  Col
} from "reactstrap";

class PageVisit extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      error: null,
      isLoaded: false,
      items: []
    };
  }

  componentDidMount() {
    fetch("/page-visits")
      .then(res => res.json())
      .then(
        (result) => {
          this.setState({
            isLoaded: true,
            items: result
          });
        },
        (error) => {
          this.setState({
            isLoaded: true,
            error
          });
        }
      )
  }

  render() {
    const { error, isLoaded, items } = this.state;
    if (error) {
      return <div>Error: {error.message}</div>;
    } else if (!isLoaded) {
      return <div>Loading...</div>;
    } else {
      return (
        <Card className="shadow">
                        <CardHeader className="border-0">
                          <Row className="align-items-center">
                            <div className="col">
                              <h3 className="mb-0">Page visits</h3>
                            </div>
                            <div className="col text-right">
                              <Button
                                color="primary"
                                href="#pablo"
                                onClick={e => e.preventDefault()}
                                size="sm"
                              >
                                See all
                              </Button>
                            </div>
                          </Row>
                        </CardHeader>
                        <Table className="align-items-center table-flush" responsive>
                          <thead className="thead-light">
                            <tr>
                              <th scope="col">Page name</th>
                              <th scope="col">Visitors</th>
                              <th scope="col">Unique users</th>
                              <th scope="col">Bounce rate</th>
                            </tr>
                          </thead>
                          <tbody>
                            {items.map(item => (
                              <tr>
                                <th scope="row">{item}</th>
                                <td>4,569</td>
                                <td>340</td>
                                <td>
                                  <i className="fas fa-arrow-up text-success mr-3" />{" "}
                                  46,53%
                                </td>
                              </tr>
                            ))}
                          </tbody>
                        </Table>
                      </Card>
      );
    }
  }
}

export default PageVisit;