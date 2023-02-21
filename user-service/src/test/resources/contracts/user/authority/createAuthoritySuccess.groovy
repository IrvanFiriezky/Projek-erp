package contracts.user.authority

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "create authority"

    request {
        url "/user-service/authority"
        method POST()
        headers {
            contentType(applicationJson())
            header "X-Api-Version": "1"
            header "Accept-Language": "id-ID"
        }
        body (
            authorityName: "DOMAIN_AUTHORITY_NEW"
        )
    }

    response {
        status CREATED()
        headers {
            contentType(applicationJson())
        }
        body (
            id: 2,
            authorityName: "DOMAIN_AUTHORITY_NEW",
            createdAt: "2023-01-30 15:30:59",
            createdBy: 1,
            updatedAt: "2023-01-30 15:30:59",
            updatedBy: 1,
            deleted: false
        )
    }

}
