package contracts.user.scope

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return scope by id=1"

    request {
        url "/user-service/scope/1"
        method GET()
        headers {
            header "X-Api-Version": "1"
            header "Accept-Language": "id-ID"
        }
    }

    response {
        status OK()
        headers {
            contentType(applicationJson())
        }
        body (
                id: 1,
                scopeName: "DOMAIN_SCOPE_TEST",
                createdAt: "2023-01-30 15:30:59",
                createdBy: 1,
                updatedAt: "2023-01-30 15:30:59",
                updatedBy: 1,
                deleted: false
        )
    }

}
