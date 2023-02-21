package contracts.user.authority

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return 404 for authority id=20"

    request {
        url "/user-service/authority/20"
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
                message: "Authority dengan id 20 tidak ada",
                errors: [
                    "Data tidak ada"
                ]
        )
    }

}
