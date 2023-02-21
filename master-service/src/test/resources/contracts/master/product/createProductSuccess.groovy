package contracts.master.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "create product"

    request {
        url "/master-service/product"
        method POST()
        headers {
            contentType(applicationJson())
            header "X-Api-Version": "1"
            header "Accept-Language": "id-ID"
        }
        body (
            productName: "Test product name new",
            totalStock: 25
        )
    }

    response {
        status CREATED()
        headers {
            contentType(applicationJson())
        }
        body (
            id: 2,
            productName: "Test product name new",
            status: 1,
            totalStock: 25,
            createdAt: "2023-01-30 15:30:59",
            createdBy: 1,
            updatedAt: "2023-01-30 15:30:59",
            updatedBy: 1,
            deleted: false
        )
    }

}
