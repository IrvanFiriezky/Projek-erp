package contracts.user.scope

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "create scope"

    request {
        url "/user-service/scope"
        method POST()
        headers {
            contentType(applicationJson())
            header "X-Api-Version": "1"
            header "Accept-Language": "id-ID"
        }
        body (
            scopeName: "DOMAIN_SCOPE_NEW"
        )
    }

    response {
        status CREATED()
        headers {
            contentType(applicationJson())
        }
        body (
            id: 2,
            scopeName: "DOMAIN_SCOPE_NEW",
            createdAt: "2023-01-30 15:30:59",
            createdBy: 1,
            updatedAt: "2023-01-30 15:30:59",
            updatedBy: 1,
            deleted: false
        )
    }

}
