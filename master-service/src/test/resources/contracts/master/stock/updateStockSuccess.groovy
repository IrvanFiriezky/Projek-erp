package contracts.master.stock

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "update stock"

    request {
        url "/master-service/stock/1"
        method PATCH()
        headers {
            contentType(applicationJson())
            header "X-Api-Version": "1"
            header "Accept-Language": "id-ID"
        }
        body (
            productId: 1,
            totalStock: 25
        )
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body (
            id: 1,
            productId: 1,
            totalStock: 25,
            createdAt: "2023-01-30 15:30:59",
            createdBy: 1,
            updatedAt: "2023-01-30 15:30:59",
            updatedBy: 1,
            deleted: false
        )
    }

}
