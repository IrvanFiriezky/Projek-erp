package contracts.user.scope

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    description "should return list userScopeHasDto by user id = 12"

    request {
        url "/user-service/scope/me"
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
            """
            [
                {
                    "scopeName": "USER_SCOPE_OWN",
                    "scopeValue": "12"
                }
            ]
            """
        )
    }

}
