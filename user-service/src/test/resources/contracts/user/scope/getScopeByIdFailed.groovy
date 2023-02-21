package contracts.user.scope

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return 404 for scope id=20"

    request {
        url "/user-service/scope/20"
        method GET()
        headers {
            header "X-Api-Version": "1"
            header "Accept-Language": "id-ID"
        }
    }

    response {
        status 404
        headers {
            contentType(applicationJson())
        }
        
        body (
                status: "NOT_FOUND",
                message: "Scope dengan id 20 tidak ada",
                errors: [
                    "Data tidak ada"
                ]
        )
    }

}
