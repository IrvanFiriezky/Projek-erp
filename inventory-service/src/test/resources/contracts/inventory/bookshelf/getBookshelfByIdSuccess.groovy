package contracts.inventory.bookshelf

import org.springframework.cloud.contract.spec.Contract

Contract.make {
  description "should return bookshelf by id=1"

  request {
    url "/inventory-service/bookshelf/1"
    method GET()
    headers {
      header "X-Api-Version": "1"
      header "Accept-Language": "id-ID"
    }
  }

  response {
    status OK()
    headers{
      contentType(applicationJson())
    }
    body (
      id: 1,
      bookshelfName: "Test bookshelf name 1",
      createdAt: "2023-01-30 15:30:59",
      createdBy: 1,
      updatedAt: "2023-01-30 15:30:59",
      updatedBy: 1,
      deleted: false
    )
  }
}