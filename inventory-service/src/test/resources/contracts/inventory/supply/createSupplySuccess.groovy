package contracts.inventory.supply

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "create supply"

    request {
        url "/inventory-service/supply"
        method POST()
        headers {
            contentType(applicationJson())
            header "X-Api-Version": "1"
            header "Accept-Language": "id-ID"
        }
        body (
                supplyName: "Test supply name new",
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
            supplyName: "Test supply name new",
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
