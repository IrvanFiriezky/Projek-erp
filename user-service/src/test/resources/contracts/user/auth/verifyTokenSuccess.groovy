package contracts.user.auth

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return jwt token"

    request {
        url "/auth-service/verification/token/11111111-2222-3333-4444-555555555555"
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
            userId: 12,
            username: "user-test",
            accessToken: "11111111-2222-3333-4444-555555555555",
            createdAt: "2023-01-30 15:30:59",
            createdBy: 10,
            updatedAt: "2023-01-30 15:30:59",
            updatedBy: 10,
            deleted: false
        )
    }

}
